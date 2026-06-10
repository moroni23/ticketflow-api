# TicketFlow API

**TicketFlow API** is a backend application for IT support ticket management, built with **Java**, **Spring Boot**, **PostgreSQL**, **JWT authentication**, and **Hexagonal Architecture**.

The project simulates a real-world Service Desk system, including user authentication, role-based access control, ticket creation, ticket assignment, support queues, comments, ticket history, filters, pagination, and business rules based on user roles.

---

## Table of Contents

* [About the Project](#about-the-project)
* [Main Features](#main-features)
* [Architecture](#architecture)
* [Technologies](#technologies)
* [Business Rules](#business-rules)
* [Project Structure](#project-structure)
* [Database Migrations](#database-migrations)
* [Authentication and Authorization](#authentication-and-authorization)
* [API Endpoints](#api-endpoints)
* [How to Run Locally](#how-to-run-locally)
* [Environment Configuration](#environment-configuration)
* [Test Data Suggestion](#test-data-suggestion)
* [Future Improvements](#future-improvements)
* [Author](#author)

---

## About the Project

TicketFlow API was created as a study and portfolio project focused on backend development best practices.

The goal is not to build only a simple CRUD system, but a more complete and realistic IT Service Desk API, applying concepts commonly used in professional backend applications, such as:

* Clean separation of responsibilities
* Hexagonal Architecture
* Domain-driven business rules
* Authentication with JWT
* Role-based authorization
* Secure password storage
* Global exception handling
* Pagination and filters
* Ticket history and audit trail
* Support queues
* Ticket comments
* PostgreSQL persistence
* Database versioning with Flyway

---

## Main Features

### User Management

* Create users
* Support for different user roles:

  * `ADMIN`
  * `TECHNICIAN`
  * `USER`
* Password encryption using BCrypt
* Login with email and password
* Authenticated user endpoint

### Authentication

* JWT-based authentication
* Stateless security
* Protected endpoints
* Role-based access control

### Categories

* Create ticket categories
* List categories
* Duplicate category validation

### Support Queues

* Create support queues
* List support queues
* Duplicate queue validation
* Link tickets to support queues
* Filter tickets by support queue

### Tickets

* Create tickets using the authenticated user
* List tickets with pagination
* Search tickets by:

  * Status
  * Priority
  * Support queue
* Find ticket by ID
* Assign tickets to technicians
* Update ticket status
* Update ticket priority
* Return enriched ticket responses with:

  * Creator name
  * Assigned technician name
  * Category name
  * Support queue name

### Comments

* Add comments to tickets
* List ticket comments
* Comments are linked to the authenticated user

### Ticket History

* Automatically registers important ticket events:

  * Ticket created
  * Status changed
  * Priority changed
  * Technician assigned
  * Comment added
  * Ticket closed

---

## Architecture

This project follows **Hexagonal Architecture**, also known as **Ports and Adapters Architecture**.

The main idea is to keep the business logic independent from frameworks, databases, and external technologies.

### Main Layers

```text
application/core/domain
```

Contains the business domain classes, such as:

* `User`
* `Ticket`
* `Category`
* `SupportQueue`
* `TicketHistory`
* `TicketComment`

```text
application/core/usecase
```

Contains the application use cases, such as:

* `CreateTicketUseCase`
* `SearchTicketsUseCase`
* `AssignTicketUseCase`
* `CreateUserUseCase`
* `LoginUseCase`

```text
application/ports/in
```

Contains input ports, which define what the application can do.

```text
application/ports/out
```

Contains output ports, which define what the application needs from external layers, such as repositories, token providers, and password encoders.

```text
adapters/in/web
```

Contains REST controllers, request DTOs, and response DTOs.

```text
adapters/out/persistence
```

Contains JPA entities, Spring Data repositories, and persistence adapters.

```text
adapters/out/security
```

Contains security-related adapters, such as JWT generation and password encoding.

```text
config
```

Contains Spring configuration classes responsible for wiring use cases and application components.

---

## Technologies

* Java
* Spring Boot
* Spring Web
* Spring Security
* Spring Data JPA
* PostgreSQL
* Flyway
* JWT
* BCrypt
* Maven
* Swagger / OpenAPI
* Bean Validation
* Git
* GitHub

---

## Business Rules

### User Roles

The application has three main roles:

| Role         | Description                                                                         |
| ------------ | ----------------------------------------------------------------------------------- |
| `ADMIN`      | Has administrative access and can manage categories, queues, and ticket assignment. |
| `TECHNICIAN` | Can work on assigned tickets and update technical information.                      |
| `USER`       | Can create and follow their own tickets.                                            |

---

### Ticket Access

* `ADMIN` can view all tickets.
* `TECHNICIAN` can view all tickets.
* `USER` can only view tickets created by themselves.

---

### Ticket Assignment

* Only `ADMIN` can assign tickets to technicians.
* `TECHNICIAN` cannot assign tickets.
* `USER` cannot assign tickets.

---

### Ticket Status and Priority Updates

* `ADMIN` can update status and priority of any ticket.
* `TECHNICIAN` can update status and priority only for tickets assigned to themselves.
* `USER` cannot update ticket status or priority.

---

### Ticket Creation

When a user creates a ticket:

* The creator is taken from the authenticated JWT token.
* The initial status is `OPEN`.
* The ticket starts without an assigned technician.
* A valid category is required.
* A valid support queue is required.
* A ticket history event is automatically created.

---

## Project Structure

```text
src/main/java/com/moroni/ticketflow
│
├── adapters
│   ├── in
│   │   ├── security
│   │   └── web
│   │       ├── auth
│   │       ├── category
│   │       ├── common
│   │       ├── exception
│   │       ├── supportqueue
│   │       ├── ticket
│   │       ├── ticketcomment
│   │       ├── tickethistory
│   │       └── user
│   │
│   └── out
│       ├── persistence
│       │   ├── category
│       │   ├── supportqueue
│       │   ├── ticket
│       │   ├── ticketcomment
│       │   ├── tickethistory
│       │   └── user
│       └── security
│
├── application
│   ├── core
│   │   ├── domain
│   │   ├── exception
│   │   └── usecase
│   │
│   └── ports
│       ├── in
│       └── out
│
└── config
```

---

## Database Migrations

The project uses Flyway to manage database versioning.

Migration files are located at:

```text
src/main/resources/db/migration
```

Current migrations include:

```text
V1__create_users_table.sql
V2__create_categories_table.sql
V3__create_tickets_table.sql
V4__create_ticket_comments_table.sql
V5__create_ticket_history_table.sql
V6__create_support_queues_table.sql
V7__add_support_queue_to_tickets.sql
```

---

## Authentication and Authorization

The API uses JWT authentication.

After logging in, the API returns a token:

```json
{
  "token": "jwt-token",
  "type": "Bearer",
  "expiresIn": 3600,
  "user": {
    "id": "user-id",
    "name": "Admin Sistema",
    "email": "admin@email.com",
    "role": "ADMIN"
  }
}
```

For protected endpoints, send the token in the request header:

```http
Authorization: Bearer jwt-token
```

---

## API Endpoints

### Auth

| Method | Endpoint          | Access        |
| ------ | ----------------- | ------------- |
| `POST` | `/api/auth/login` | Public        |
| `GET`  | `/api/auth/me`    | Authenticated |

---

### Users

| Method | Endpoint                 | Access |
| ------ | ------------------------ | ------ |
| `POST` | `/api/users`             | Public |
| `GET`  | `/api/users/technicians` | ADMIN  |

---

### Categories

| Method | Endpoint          | Access        |
| ------ | ----------------- | ------------- |
| `POST` | `/api/categories` | ADMIN         |
| `GET`  | `/api/categories` | Authenticated |

---

### Support Queues

| Method | Endpoint              | Access        |
| ------ | --------------------- | ------------- |
| `POST` | `/api/support-queues` | ADMIN         |
| `GET`  | `/api/support-queues` | Authenticated |

---

### Tickets

| Method  | Endpoint                                  | Access             |
| ------- | ----------------------------------------- | ------------------ |
| `POST`  | `/api/tickets`                            | Authenticated      |
| `GET`   | `/api/tickets`                            | Authenticated      |
| `GET`   | `/api/tickets/{id}`                       | Authenticated      |
| `PATCH` | `/api/tickets/{id}/assign/{technicianId}` | ADMIN              |
| `PATCH` | `/api/tickets/{id}/status`                | ADMIN / TECHNICIAN |
| `PATCH` | `/api/tickets/{id}/priority`              | ADMIN / TECHNICIAN |

Ticket filters:

```http
GET /api/tickets?page=0&size=10
GET /api/tickets?status=OPEN&page=0&size=10
GET /api/tickets?priority=HIGH&page=0&size=10
GET /api/tickets?supportQueueId={supportQueueId}&page=0&size=10
GET /api/tickets?status=OPEN&priority=HIGH&supportQueueId={supportQueueId}&page=0&size=10
```

---

### Ticket Comments

| Method | Endpoint                           | Access        |
| ------ | ---------------------------------- | ------------- |
| `POST` | `/api/tickets/{ticketId}/comments` | Authenticated |
| `GET`  | `/api/tickets/{ticketId}/comments` | Authenticated |

---

### Ticket History

| Method | Endpoint                          | Access        |
| ------ | --------------------------------- | ------------- |
| `GET`  | `/api/tickets/{ticketId}/history` | Authenticated |

---

## Swagger

After running the project, access Swagger UI at:

```http
http://localhost:8080/swagger-ui.html
```

OpenAPI docs:

```http
http://localhost:8080/v3/api-docs
```

---

## How to Run Locally

### Requirements

Before running the project, install:

* Java
* Maven
* PostgreSQL
* Git

---

### Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/ticketflow-api.git
cd ticketflow-api
```

---

### Create PostgreSQL database

Create a database named:

```sql
ticketflow
```

Example:

```sql
CREATE DATABASE ticketflow;
```

---

### Configure application properties

Update your local configuration with your PostgreSQL credentials.

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ticketflow
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true

jwt.secret=TicketFlowSecretKeyForJwtGenerationMustBeAtLeast32CharactersLong
jwt.expiration-in-seconds=3600
```

---

### Run the project

Using Maven:

```bash
mvn spring-boot:run
```

Or using the Maven wrapper:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

---

## Example Requests

### Create an admin user

```http
POST /api/users
Content-Type: application/json
```

```json
{
  "name": "Admin Sistema",
  "email": "admin@email.com",
  "password": "123456",
  "role": "ADMIN"
}
```

---

### Login

```http
POST /api/auth/login
Content-Type: application/json
```

```json
{
  "email": "admin@email.com",
  "password": "123456"
}
```

---

### Create a support queue

```http
POST /api/support-queues
Authorization: Bearer jwt-token
Content-Type: application/json
```

```json
{
  "name": "N1 - Service Desk",
  "description": "First level support queue for initial triage."
}
```

---

### Create a ticket

```http
POST /api/tickets
Authorization: Bearer jwt-token
Content-Type: application/json
```

```json
{
  "title": "VPN não conecta",
  "description": "Usuário informa erro ao tentar conectar na VPN corporativa.",
  "priority": "HIGH",
  "categoryId": "category-uuid",
  "supportQueueId": "support-queue-uuid"
}
```

---

## Future Improvements

Planned improvements for this project:

* Move ticket between support queues
* Register queue change history
* Create support teams
* Link technicians to queues
* SLA by priority and queue
* Attachments in tickets
* Dashboard with ticket indicators
* Email or system notifications
* Automated tests with JUnit and Mockito
* Docker and Docker Compose
* CI/CD pipeline with GitHub Actions
* Cloud deployment
* Front-end application

---

## Author

Developed by **Moroni Pereira**.

This project is part of a backend development learning path focused on Java, Spring Boot, software architecture, and real-world enterprise application design.
