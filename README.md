### Task3
## step 3: JDBC

- Raw JDBC `findAll()`: about 22 lines. JdbcClient `findAll()`: 3 lines.
- Duplicate name insert throws: `org.springframework.dao.DuplicateKeyException`
  (subclass of `DataIntegrityViolationException`).
- Where the translation happened: H2 throws a raw `SQLException`
  (error code 23505, unique violation). JdbcClient runs on JdbcTemplate, which
  passes the SQLException to a `SQLExceptionTranslator`. That translator maps the
  database error code to Spring's own unchecked `DuplicateKeyException`.
  With raw JDBC you would get the plain `SQLException` instead.

## step 5
If several beans match an injection point, Spring picks the one marked @Primary. If two or more matching beans are @Primary and both are active (for example EmailNotifier and ConsoleNotifier with the dev profile), Spring cannot choose and fails at startup with NoUniqueBeanDefinitionException. A @Qualifier at the injection point overrides @Primary.