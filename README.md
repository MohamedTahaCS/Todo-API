# Todo API

A RESTful API for managing todos built with Spring Boot 3.3.3, Java 21, PostgreSQL, and JWT authentication.

## Features
- JWT-based auth (register/login)
- CRUD for todos with filters (completed, priority) and pagination (page, size)
- Swagger/OpenAPI docs
- Docker Compose for local stack

## Run
- Docker (API + Postgres):
  ```bash
  docker-compose up --build
  ```
  API http://localhost:8080 • Swagger http://localhost:8080/swagger-ui.html • Docs http://localhost:8080/api-docs

- Local (Postgres must be running on localhost:5432 and DB `todo_db`):
  - Configure `src/main/resources/application.properties` or env vars (`SPRING_DATASOURCE_*`, `SPRING_SECURITY_JWT_SECRET`).
  ```bash
  mvn clean package
  mvn spring-boot:run
  ```

## API
Base path: `/api/v1`

- Auth
  - POST `/api/v1/auth/register` (password: min 8 chars, must include upper, lower, digit, special)
  - POST `/api/v1/auth/login`
- Todos (Bearer token required)
  - GET `/api/v1/todos?completed=<true|false>&priority=<LOW|MEDIUM|HIGH>&page=0&size=10`
  - GET `/api/v1/todos/{id}`
  - POST `/api/v1/todos`
  - PUT `/api/v1/todos/{id}`
- PATCH `/api/v1/todos/{id}/toggle` — flips the `completed` status and returns the updated todo
  - DELETE `/api/v1/todos/{id}`

Auth header for protected routes:
```
Authorization: Bearer <token>
```

## Notes
- Swagger UI: `/swagger-ui.html`
- JAR output: `target/todo-api-1.0.0.jar`
