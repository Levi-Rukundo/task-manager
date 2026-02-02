A learning project:

Task Manager API (Jira-Lite Backend)

A production-style REST API for managing projects and tasks with JWT authentication, ownership-based authorization, and integration tests.

Tech Stack:
- Java 17
- Spring Boot
- Spring Security + JWT
- JPA / Hibernate
- H2 (file-based, persistent)
- Maven
- JUnit + MockMvc (integration tests)

Features:
- User registration & login with JWT
- BCrypt password hashing
- Project management (create, list, rename, delete)
- Task management per project (create, list, edit, delete)
- Task workflow: TODO → IN_PROGRESS → DONE
- Filtering & search:
    - ?status=TODO
    - ?q=keyword
- Strict authorization:
    - Users can only access their own projects & tasks
    - Returns 403 Forbidden on cross-user access
- Integration tests covering auth & protected routes

Run Locally: 
    - bash: ./mvnw spring-boot:run

