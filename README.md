# Task 3: JDBC, JdbcClient and transactions

## Step 3: Raw JDBC vs JdbcClient

- Raw JDBC `findAll()`: 22 lines (replace with your real count).
  JdbcClient `findAll()`: 3 lines.

## Step 7: Duplicate name exception

- Inserting a duplicate name throws `org.springframework.dao.DuplicateKeyException`
  (a subclass of `DataIntegrityViolationException`).
- Where the translation happened: H2 throws a raw `SQLException`
  (error code 23505, unique violation). JdbcClient runs on JdbcTemplate, which
  passes the `SQLException` to a `SQLExceptionTranslator`. The translator maps the
  database error code to Spring's own unchecked `DuplicateKeyException`.
  With raw JDBC you would get the plain `SQLException` instead.

# Task: Notifier, profiles and @Primary

## Step 5: @Primary rule

- Without the `dev` profile: `ConsoleNotifier` is not created because of
  `@Profile("dev")`. The beans are `emailNotifier` and `smsNotifier`, and
  `EmailNotifier` is used because it is `@Primary`.
- With `--spring.profiles.active=dev`: `ConsoleNotifier` is also created and is
  also `@Primary`. Startup fails with `NoUniqueBeanDefinitionException`:
  "more than one 'primary' bean found among candidates:
  [consoleNotifier, emailNotifier, smsNotifier]".
- Rule: if several beans match an injection point, Spring uses the one marked
  `@Primary`. If two or more matching beans are `@Primary` and both are active,
  Spring cannot choose and fails at startup. A `@Qualifier` at the injection point
  overrides `@Primary`.