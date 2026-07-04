# Access Control System

A full-stack access control application built with Spring Boot and Flutter. Users can register, book spaces, and log their physical entry into those spaces.

## Tech Stack

**Backend**
- Java 21 + Spring Boot 4
- Spring Security + JWT authentication
- Spring Data JPA + Hibernate
- MariaDB

**Frontend**
- Flutter (Dart)
- HTTP package for REST API calls
- Shared Preferences for token storage

## Architecture

```
Flutter App
    │
    │  HTTP + JWT
    ▼
Spring Boot REST API
    │
    │  JPA/Hibernate
    ▼
MariaDB Database
```

The backend follows a layered architecture:

```
Controller → Service → Repository → Database
                ↕
              Mapper
                ↕
              DTO
```

## Database Schema

```
AppUser
├── id (PK)
├── name
├── email (unique)
├── password (BCrypt)
└── role

Space
├── id (PK)
├── name
├── capacity
├── description
└── status

Scheduling
├── id (PK)
├── scheduled_date
├── start_time
├── end_time
├── status
├── user_id (FK → AppUser)
└── space_id (FK → Space)

AccessLog
├── id (PK)
├── entry_timestamp
├── success
└── scheduling_id (FK → Scheduling)
```

## Getting Started

### Prerequisites

- Java 21
- Maven
- MariaDB
- Flutter 3.24+
- Android SDK (for Android builds)

### Backend Setup

1. Create the database:
```sql
CREATE DATABASE projeto_passe;
```

2. Configure environment variables or edit `application.properties`:
```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/projeto_passe
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
jwt.secret=your-secret-key-at-least-32-characters
jwt.expiration=86400000
```

3. Run the backend:
```bash
cd backend
./mvnw spring-boot:run
```

The server starts on `http://localhost:8080`.

### Frontend Setup

1. Install dependencies:
```bash
cd frontend
flutter pub get
```

2. Update the base URL in all service files if not running locally:
```dart
final String baseUrl = 'http://localhost:8080';
```

3. Run the app:
```bash
flutter run -d linux   # Linux desktop
flutter run            # Android device
```

## API Endpoints

### Auth (public)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Login and receive JWT token |
| GET | `/auth/me` | Get current authenticated user |

### Users (authenticated)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/users` | Get all users |
| GET | `/users/{id}` | Get user by ID |

### Spaces (authenticated)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/spaces` | Get all spaces |
| GET | `/spaces/{id}` | Get space by ID |
| POST | `/spaces` | Create a new space |

### Schedulings (authenticated)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/schedulings` | Get all schedulings |
| GET | `/schedulings/{id}` | Get scheduling by ID |
| POST | `/schedulings` | Create a new scheduling |

### Access Logs (authenticated)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/accessLogs` | Get all access logs |
| GET | `/accessLogs/{id}` | Get access log by ID |
| POST | `/accessLogs` | Log a space entry |

## Authentication

All endpoints except `/auth/register` and `/auth/login` require a valid JWT token in the request header:

```
Authorization: Bearer <token>
```

## App Flow

```
Register / Login
      ↓
View available spaces
      ↓
Book a space (select date and time)
      ↓
View my bookings
      ↓
Tap "Enter" to log physical access
```

## Project Structure

```
access-control-project/
├── backend/
│   └── src/main/java/access_control/
│       ├── controller/
│       ├── dto/
│       ├── entity/
│       ├── mapper/
│       ├── repository/
│       ├── security/
│       └── service/
└── frontend/
    └── lib/
        ├── models/
        ├── screens/
        └── services/
```

