# Movie Booking System

A REST API for booking movie tickets, built with Spring Boot. It covers the core flow of a ticketing platform: movies, theaters, screens, seats, shows, bookings, and payments, with role-based access control for regular users and admins.

## Tech Stack

- **Java 17**
- **Spring Boot 4.0.5**
- **Spring Web** – REST controllers
- **Spring Data JPA / Hibernate** – persistence
- **Spring Security** – HTTP Basic auth with role-based authorization
- **PostgreSQL** – database
- **Lombok** – boilerplate reduction
- **Maven** – build tool

## Features

- CRUD APIs for movies, theaters, screens, seats, and shows
- Booking creation with seat selection, status updates, and cancellation
- Payment records tied to bookings
- Role-based access (`USER` / `ADMIN`) enforced via Spring Security
- Auto-provisioned admin account on first startup
- Centralized exception handling with structured error responses

## Data Model

| Entity | Description |
|---|---|
| `User` | Registered user with a role (`USER` or `ADMIN`) |
| `Movie` | Title, genre, duration, release date, rating |
| `Theater` | Name and location |
| `Screen` | Belongs to a theater, has a seating capacity |
| `Seat` | Belongs to a screen; has a type (`REGULAR`, `VIP`, `ACCESSIBLE`) |
| `Show` | A movie scheduled on a screen at a given date/time and price |
| `Booking` | Links a user and a show, with status and total amount |
| `BookedSeat` | Join entity linking a booking to specific seats |
| `Payment` | One-to-one with a booking; tracks method, status, and transaction ID |

## Authentication & Authorization

The API uses HTTP Basic authentication backed by the `users` table (passwords are hashed with BCrypt).

| Endpoint | Access |
|---|---|
| `POST /users` | Public (registration) |
| `GET /movies/**` | Public |
| `/bookings/**`, `/payments/**` | `USER` or `ADMIN` |
| `/movies/**`, `/screens/**`, `/seats/**`, `/users/**` (write operations) | `ADMIN` only |
| Everything else | Any authenticated user |

On first startup, an admin account is auto-created (configurable via environment variables — see below) so you have a way to log in and manage the catalog immediately.

## API Overview

All controllers expose standard `POST`, `GET /{id}`, `PUT /{id}`, and `DELETE /{id}` endpoints, plus:

- `PUT /bookings/{id}/status?status=...` – update a booking's status

Resources: `/users`, `/movies`, `/theaters`, `/screens`, `/seats`, `/shows`, `/bookings`, `/payments`

## Getting Started

### Prerequisites

- Java 17+
- Maven (or use the included `mvnw` wrapper)
- PostgreSQL running locally, with a `movie_booking` database created

### Configuration

Database credentials and the admin bootstrap are read from environment variables:

| Variable | Default | Purpose |
|---|---|---|
| `DB_USERNAME` | `postgres` | PostgreSQL username |
| `DB_PASSWORD` | *(empty)* | PostgreSQL password |
| `APP_ADMIN_BOOTSTRAP_ENABLED` | `true` | Create a default admin on startup |
| `APP_ADMIN_NAME` | `System Admin` | Bootstrap admin's name |
| `APP_ADMIN_EMAIL` | `admin@moviebookingsystem.local` | Bootstrap admin's email (login username) |
| `APP_ADMIN_PASSWORD` | `admin123` | Bootstrap admin's password |
| `APP_ADMIN_PHONE` | `9999999999` | Bootstrap admin's phone number |

Set these before running, e.g.:

```bash
export DB_USERNAME=postgres
export DB_PASSWORD=your_password
export APP_ADMIN_PASSWORD=change_me
```

> Change the default admin password before deploying anywhere beyond local development.

### Run

```bash
cd demo
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

### Build

```bash
./mvnw clean package
java -jar target/movie-booking-system-0.0.1-SNAPSHOT.jar
```

## Example Usage

Register a user:

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Jane Doe","email":"jane@example.com","password":"secret123","phoneNumber":"9876543210","role":"USER"}'
```

Browse movies (no auth required):

```bash
curl http://localhost:8080/movies
```

Create a booking (authenticated user):

```bash
curl -X POST http://localhost:8080/bookings \
  -u jane@example.com:secret123 \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"showId":1,"seatIds":[1,2]}'
```

## Project Structure

```
demo/src/main/java/com/example/demo
├── controller/    # REST endpoints
├── services/      # Business logic
├── repository/    # Spring Data JPA repositories
├── model/         # JPA entities
├── dto/
│   ├── request/   # Request payloads
│   └── response/  # Response payloads
├── config/        # Security config, user details service, admin bootstrap
└── exception/     # Global exception handling
```

## License

No license specified yet — add one (e.g. MIT) if you plan to share or accept contributions.
