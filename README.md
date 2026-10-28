## Task 2: IoC, Dependency Injection and Bean Lifecycle

### What I built

```
edu.itc.shop
├── config/   AppConfig (@Bean Clock), StartupRunner (CommandLineRunner)
└── service/  Notifier, EmailNotifier, SmsNotifier, ConsoleNotifier,
              WelcomeService, ReportBuilder
```

`WelcomeServiceTest` is a plain JUnit test with no Spring context.

### Dependency resolution

| Parameter | Rule | Injected bean |
|---|---|---|
| `Notifier defaultNotifier` | by type, then `@Primary` | `EmailNotifier` |
| `@Qualifier("smsNotifier") Notifier` | qualifier = bean name | `SmsNotifier` |
| `Clock clock` | by type (one candidate) | `@Bean` in `AppConfig` |

All beans use constructor injection with `final` fields; there is no field injection.
`Clock` comes from a `@Bean` method because it is a JDK class that cannot carry `@Service`.

### Lifecycle

`@PostConstruct` logs "ready" at start-up; `@PreDestroy` logs "closing" on Ctrl+C.

```
EmailNotifier ready
SmsNotifier ready
<N> beans; notifiers = [emailNotifier, smsNotifier]
EMAIL to=ann text=Welcome ann! It is HH:mm:ss
SMS to=bob text=Welcome bob! It is HH:mm:ss
prototype instances differ: true
EmailNotifier closing
SmsNotifier closing
```

### Prototype scope

A singleton has one instance per container; a `prototype` bean is created on every request.
Injecting a prototype directly into a singleton would create it only once, so I inject
`ObjectProvider<ReportBuilder>` and call `getObject()`. Two calls gave different
`System.identityHashCode` values (`prototype instances differ: true`).

### Profiles and the `@Primary` rule

`ConsoleNotifier` is `@Profile("dev")` and `@Primary`.

- If `EmailNotifier` is also `@Primary` and active, the `dev` run fails with
  `NoUniqueBeanDefinitionException: more than one 'primary' bean found among candidates`.
- **Rule:** `@Primary` breaks a tie only when exactly one candidate has it. Two active `@Primary` beans make start-up fail.
- **Fix:** `@Profile("!dev")` on `EmailNotifier`.

| Profile | Active notifiers | Used by default |
|---|---|---|
| default | `emailNotifier`, `smsNotifier` | `EMAIL` |
| `dev` | `consoleNotifier`, `smsNotifier` | `CONSOLE` |

```powershell
.\mvnw.cmd spring-boot:run
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

### Unit test without Spring

Constructor injection lets the test call `new WelcomeService(fakeNormal, fakeUrgent, Clock.fixed(...))`.
It asserts the text `Welcome ann! It is 09:15:02` and which notifier received the call.

### Checklist

- [x] Constructor injection, `final` fields, no field injection
- [x] `@Primary`, `@Qualifier`, `@Profile` each change the chosen bean
- [x] Lifecycle logs and prototype proof
- [x] `WelcomeServiceTest` passes without Spring
- [x] Tag `task-2`