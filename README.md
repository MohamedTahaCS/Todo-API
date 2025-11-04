# Todo API

A RESTful API for managing todos built with Spring Boot 3.3.3, Java 21, PostgreSQL, and JWT authentication.

## Features

- ✅ User registration and authentication with JWT
- ✅ CRUD operations for todos
- ✅ PostgreSQL database integration
- ✅ Swagger/OpenAPI documentation
- ✅ Docker support
- ✅ Security with JWT tokens
- ✅ DTO pattern implementation
- ✅ Exception handling

## Tech Stack

- **Spring Boot**: 3.3.3
- **Java**: 21
- **Build Tool**: Maven
- **Database**: PostgreSQL
- **Security**: JWT (JSON Web Tokens)
- **Documentation**: Swagger/OpenAPI 3
- **Containerization**: Docker & Docker Compose

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- Docker and Docker Compose (for containerized deployment)
- PostgreSQL (if running locally without Docker)

## Getting Started

### Running with Docker Compose (Recommended)

1. Clone the repository or navigate to the project directory
2. Build and start all services:
   ```bash
   docker-compose up --build
   ```

   This will start:
   - PostgreSQL database on port 5432
   - Todo API on port 8080

3. Access the application:
   - API: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - API Docs: http://localhost:8080/api-docs

### Running Locally (Without Docker)

1. **Start PostgreSQL database**:
   - Create a database named `tododb`
   - Update `application.yml` with your database credentials

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

   Or run the main class `TodoApiApplication` from your IDE

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token

### Todos (Requires Authentication)

- `GET /api/todos` - Get all todos for authenticated user
- `GET /api/todos/{id}` - Get todo by ID
- `POST /api/todos` - Create a new todo
- `PUT /api/todos/{id}` - Update a todo
- `PATCH /api/todos/{id}/toggle` - Toggle todo completion status
- `DELETE /api/todos/{id}` - Delete a todo

## Authentication

1. Register a new user:
   ```json
   POST /api/auth/register
   {
     "username": "john_doe",
     "email": "john@example.com",
     "password": "password123"
   }
   ```

2. Login:
   ```json
   POST /api/auth/login
   {
     "username": "john_doe",
     "password": "password123"
   }
   ```

3. Use the returned JWT token in subsequent requests:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

## Swagger Documentation

Once the application is running, access Swagger UI at:
- http://localhost:8080/swagger-ui.html

You can:
- View all available endpoints
- Test API endpoints directly from the UI
- Authenticate using the "Authorize" button

## Configuration

Key configuration properties in `application.yml`:

- Database connection settings
- JWT secret and expiration time
- Server port (default: 8080)
- Swagger UI path

For production, set the `JWT_SECRET` environment variable with a strong, random secret key.

## Project Structure

```
src/main/java/com/todo/
├── config/          # Configuration classes (Security, JWT, OpenAPI)
├── controller/      # REST controllers
├── dto/            # Data Transfer Objects
├── entity/         # JPA entities
├── exception/      # Exception handlers
├── repository/     # JPA repositories
└── service/        # Business logic services
```

## Development with IntelliJ IDEA

1. Open the project in IntelliJ IDEA
2. Import Maven project (IntelliJ should auto-detect)
3. Configure JDK 21 as the project SDK
4. Run `TodoApiApplication` main class

The project is configured with:
- Java 21 language level
- Maven build system
- Lombok annotation processing

## Building

```bash
mvn clean package
```

The JAR file will be created in `target/todo-api-1.0.0.jar`

## Testing

```bash
mvn test
```

## License

This project is open source and available under the MIT License.
