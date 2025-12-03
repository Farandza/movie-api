# Movie-API REST API

A complete Spring Boot + JPA REST API for managing Movies, Actors, and Genres with full CRUD, pagination, search, many-to-many relationships, and safe deletion semantics.

## Features

- Full CRUD operations for **Movies**, **Actors**, and **Genres**
- Many-to-many relationships:
  - A movie can have multiple genres and actors
  - An actor can appear in multiple movies
- Pagination on all list endpoints
- Case-insensitive partial search for movies by title and actors by name
- Filtering movies by genre, release year, and actor
- Partial updates using **PATCH**
- Safe deletion: prevents deleting entities with relationships (400 error)
- Force deletion with `?force=true` that automatically cleans relationships
- Input validation with clear error messages
- Global exception handling with consistent JSON responses
- Uses **SQLite** (or H2 in-memory) as the database

## Technologies Used

- Java 17+
- Spring Boot 3.x
- Spring Data JPA (Hibernate)
- Lombok
- SQLite (with JDBC driver)
- Bean Validation (Jakarta Validation)
- Maven

## Setup

1. Clone the repository
2. Make sure you have Java 17+ and Maven
3. Run:
   ```bash
   ./mvnw spring-boot:run

## API Endpoints

### Genres
| Method | Endpoint                     | Description                              | Query Params / Notes                  |
|--------|------------------------------|------------------------------------------|---------------------------------------|
| POST   | `/api/genres`                | Create a new genre                       | Body: `{ "name": "Sci-Fi" }`          |
| GET    | `/api/genres`                | List all genres (paginated)              | `?page=0&size=20`                     |
| GET    | `/api/genres/{id}`           | Get genre by ID                          |                                       |
| PATCH  | `/api/genres/{id}`           | Update genre name                        |                                       |
| DELETE | `/api/genres/{id}`           | Delete genre (fails if linked)           |                                       |
| DELETE | `/api/genres/{id}?force=true`| Force delete + clean relationships       | Recommended for cleanup               |
| GET    | `/api/genres/{id}/movies`    | Get all movies in this genre             |                                       |

### Actors
| Method | Endpoint                        | Description                                | Query Params / Notes                  |
|--------|---------------------------------|--------------------------------------------|---------------------------------------|
| POST   | `/api/actors`                   | Create actor                               | `birthDate` in YYYY-MM-DD             |
| GET    | `/api/actors`                   | List actors (paginated)                    | `?page=0&size=20`                     |
| GET    | `/api/actors?name=leo`          | Search actors (case-insensitive)           | Partial match                         |
| GET    | `/api/actors/{id}`              | Get actor by ID                            |                                       |
| PATCH  | `/api/actors/{id}`              | Update actor details                       |                                       |
| DELETE | `/api/actors/{id}?force=true`   | Force delete + remove from all movies      |                                       |
| GET    | `/api/actors/{id}/movies`       | Get all movies of this actor               |                                       |

### Movies
| Method | Endpoint                              | Description                                      | Query Params / Notes                              |
|--------|---------------------------------------|--------------------------------------------------|---------------------------------------------------|
| POST   | `/api/movies`                         | Create movie                                     | Use `genreIds` & `actorIds` arrays                |
| GET    | `/api/movies`                         | List all movies (paginated)                      | `?page=0&size=10&sort=title,asc`                 |
| GET    | `/api/movies/search?title=matrix`     | Search movies by title (partial, case-insensitive)|                                                   |
| GET    | `/api/movies?genre=1`                 | Filter by genre                                  |                                                   |
| GET    | `/api/movies?year=2010`               | Filter by release year                           |                                                   |
| GET    | `/api/movies?actor=5`                 | Filter by actor                                  |                                                   |
| GET    | `/api/movies/{id}`                    | Get movie by ID                                  |                                                   |
| GET    | `/api/movies/{id}/actors`             | Get all actors in this movie                     |                                                   |
| PATCH  | `/api/movies/{id}`                    | Partially update movie (title, year, actors, genres) | Replaces actor/genre lists if provided        |
| DELETE | `/api/movies/{id}`                    | Delete movie                                     |                                                   |
