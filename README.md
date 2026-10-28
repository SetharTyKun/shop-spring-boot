# Lab 2: Spring Boot shop project

1. Understanding pom.xml

- What does the `<parent>` give me?

  The parent is `spring-boot-starter-parent` 4.1.1. My project inherits from it. It gives me:
  - **Dependency management**: a list of library versions (Spring, Jackson, Tomcat, HikariCP, H2, and so on) that were tested to work together.
  - **Build defaults**: UTF-8 encoding, the Java version from `<java.version>` (25), and compiler settings.
  - **Plugin setup**: `spring-boot-maven-plugin` is already configured.

- Why do the starters have no `<version>`?

  The parent has a `<dependencyManagement>` section, which works like a price list. When Maven sees a dependency without a version, it looks the version up in that list. This keeps all libraries on matching versions, so they do not clash. The same is true for `h2` and the plugin.

- What does `spring-boot-maven-plugin` do at `package` time?

  It runs the `repackage` goal. The normal small jar becomes one executable "fat jar": - my classes go in `BOOT-INF/classes` - all dependency jars go in `BOOT-INF/lib` - a launcher is added, so `java -jar` can start the app - the original jar is kept as `*.jar.original`

Dependency tree (first level)

Command: `./mvnw dependency:tree`

```
edu.itc.se:shop:jar:0.0.1-SNAPSHOT
+- org.springframework.boot:spring-boot-h2console:jar:4.1.1:compile
+- org.springframework.boot:spring-boot-starter-actuator:jar:4.1.1:compile
+- org.springframework.boot:spring-boot-starter-aspectj:jar:4.1.1:compile
+- org.springframework.boot:spring-boot-starter-jdbc:jar:4.1.1:compile
+- org.springframework.boot:spring-boot-starter-validation:jar:4.1.1:compile
+- org.springframework.boot:spring-boot-starter-webmvc:jar:4.1.1:compile
+- org.springframework.boot:spring-boot-devtools:jar:4.1.1:runtime (optional)
+- com.h2database:h2:jar:2.4.240:runtime
+- org.springframework.boot:spring-boot-starter-actuator-test:jar:4.1.1:test
+- org.springframework.boot:spring-boot-starter-jdbc-test:jar:4.1.1:test
+- org.springframework.boot:spring-boot-starter-validation-test:jar:4.1.1:test
\- org.springframework.boot:spring-boot-starter-webmvc-test:jar:4.1.1:test
```

2. Running the app

Command: `./mvnw spring-boot:run`

```
curl.exe -i "http://localhost:8080/hello?name=Makara"
HTTP/1.1 200
Content-Type: application/json

{"message":"Hello, Makara!","at":"2026-10-28T11:29:54.847537100Z"}
```

DevTools: after I changed the message to "Hi, " and saved, the app restarted by itself and the response became `Hi, Makara!`.
