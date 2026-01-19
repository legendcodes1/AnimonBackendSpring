# Animon Backend

The **Animon Backend** is a Spring Boot REST API that powers the Animon application, an anime tracking platform that allows users to manage their watch lists, track progress, and store ratings.

This service is designed following clean architecture principles with clear separation of concerns between controllers, services, and persistence layers.

---

## Tech Stack

* **Java 17**
* **Spring Boot**
* **Spring Web (REST APIs)**
* **Spring Data JPA**
* **PostgreSQL** (planned)
* **Hibernate**
* **Maven**
* **Swagger / OpenAPI** (planned)

---

## Project Structure

```text
src/main/java/com/animon
├── controller   # REST controllers
├── service      # Business logic
├── repository   # Data access layer
├── model        # JPA entities
├── dto          # Data Transfer Objects (planned)
├── config       # Application configuration
└── exception    # Global exception handling (planned)
```

---

## Current Features

* Basic Spring Boot application setup
* Layered architecture (controller, service, repository)
* Anime domain modeling (in progress)
* REST API foundation

---

## Planned Features

* CRUD operations for anime watch lists
* User authentication with JWT
* Status tracking (Watching, Completed, Plan to Watch)
* Ratings and progress tracking
* Validation and global error handling
* API documentation with Swagger
* Deployment-ready configuration

---

## Running Locally

### Prerequisites

* Java 17+
* Maven
* PostgreSQL (optional for now)

### Steps

```bash
git clone https://github.com/your-username/animon-backend.git
cd animon-backend
mvn spring-boot:run
```

The API will be available at:

```
http://localhost:8080
```

---

## API Design (Early Draft)

| Method | Endpoint        | Description              |
| ------ | --------------- | ------------------------ |
| GET    | /api/anime      | Get all anime entries    |
| POST   | /api/anime      | Create a new anime entry |
| PUT    | /api/anime/{id} | Update an anime entry    |
| DELETE | /api/anime/{id} | Delete an anime entry    |

---

## Notes

This backend is actively being developed as part of a full-stack portfolio project. The focus is on building production-ready APIs using best practices and modern Java tooling.

---

## License

MIT
