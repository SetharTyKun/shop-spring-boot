# Lab 2: Spring Boot shop project

1. Understanding pom.xml

  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>4.1.1</version>
    <relativePath/> <!-- lookup parent from repository -->
  </parent>

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

3. Running with spring-boot:run and DevTools

I ran `./mvnw spring-boot:run` and changed the message from "Hello" to "Hi" while it was running. The log showed
`Restarting due to 2 class path changes`, and the next call returned `"Hi, Ann!"`. DevTools restarted the app
automatically (about 0.2 seconds).
4. Package and run the jar

`./mvnw -q package`, then `java -jar target/shop-0.0.1-SNAPSHOT.jar --server.port=9090`.
`/hello` answered on port 9090.

The jar is about 28 MB (29,757,103 bytes). My own code is only a few KB. The rest is a "fat jar":
spring-boot-maven-plugin copies every dependency (embedded Tomcat, Spring MVC, Jackson, HikariCP, H2, Actuator)
into BOOT-INF/lib. Because the server is inside the jar, `java -jar` starts the whole app by itself.

5. Actuator and auto-configuration

`GET /actuator/health` returns `{"groups":["liveness","readiness"],"status":"UP"}`.

I started the jar with `--debug` and searched the condition evaluation report. DataSourceAutoConfiguration
matched because of this line:

```
@ConditionalOnClass found required classes 'javax.sql.DataSource',
'org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType' (OnClassCondition)
```

Spring Boot found the JDBC and H2 classes on the classpath, so it configured a data source (a HikariCP pool)
automatically, without any database code from me.