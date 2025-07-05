# Menu Service

A Spring Boot microservice for managing restaurant menus and items.

## Tech Stack

- Java 17
- Spring Boot 3.5.3
- MySQL 8.0
- Redis 7
- Docker & Docker Compose
- Maven

## Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Maven 3.6 or higher

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/tbsid/menu-service.git
cd menu-service
```

### 2. Start Infrastructure Services

The application requires MySQL and Redis. Start them using Docker Compose:

```bash
docker-compose up -d
```

This will start:
- MySQL on port 3306
- Redis on port 6379

### 3. Database Setup

The database will be automatically created by Docker Compose with:
- Database name: menu_service
- Username: menu_user
- Password: menu_password

#### 3.1 Create Schema
Presently, in the `dev` environment, the application uses hibernate setting `ddl-auto: update`
This will create the schema automatically on application start-up by JPA and initial data will be loaded from:

##### (Optional)
However, if you wish to change `ddl-auto` to `none` or `validate`
you can create the schema manually with DDL scripts, run the create schema script -
- `src/main/resources/database-scripts/01-create-schema.sql`

#### 3.2 Insert Sample Data (Optional)
- `src/main/resources/database-scripts/02-mock-data.sql`

### 4. Build and Run

#### Using Maven

```bash
./mvnw clean install
./mvnw spring-boot:run -Pdev
```

#### Using Docker

```bash
docker build -t menu-service .
docker run -p 8080:8080 menu-service
```

The application will start on `http://localhost:8080`

## Available Profiles

- `dev` - Development environment (default)
- `docker` - Docker environment
- `uat` - UAT environment
- `prod` - Production environment

To run with a specific profile:
```bash
./mvnw spring-boot:run -P <profile-name>
```

## Configuration

### Database Configuration

The application uses HikariCP connection pool with the following default settings:
- Connection Timeout: 30 seconds
- Idle Timeout: 10 minutes
- Max Lifetime: 30 minutes
- Minimum Idle: 10 connections
- Maximum Pool Size: 10 connections

### Redis Configuration

Redis is configured with:
- Host: localhost (configurable)
- Port: 6379
- Timeout: 60 seconds
- Database: 0

## API Documentation

OpenAPI documentation is available at:
- `http://localhost:8080/swagger-ui.html`

## Monitoring

The application exposes the following actuator endpoints under `/api/v1`:
- Health check: `/api/v1/health`
- Metrics: `/api/v1/metrics`
- Info: `/api/v1/info`

## Development

### Adding New Features

1. Create necessary model classes in `com.swiggy.menu_service.model`
2. Add repository interfaces in `com.swiggy.menu_service.repository`
3. Implement service layer in `com.swiggy.menu_service.service`
4. Create DTOs in `com.swiggy.menu_service.dto`
5. Add controllers in `com.swiggy.menu_service.controller`

### Running Tests

```bash
./mvnw test
```
