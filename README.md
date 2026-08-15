# Spring Auth — Session & JWT

A Spring Boot project demonstrating two authentication strategies built on the same user model — Spring Security's default session-based authentication and a custom JWT token-based implementation — along with email verification and role-based authorization (RBAC).

## Tech Stack
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA / Hibernate
- MySQL
- Gradle

## Status
🚧 In Progress

## Planned Features
- [ ] User registration
- [ ] Email verification flow
- [ ] Session-based authentication
- [ ] JWT token-based authentication
- [ ] Role-based access control (RBAC)

### Architecture
#### Controller Layer → Service Layer → Repository Layer → Database

**Entity Layer**
- `User` — core entity with `isEnabled` and `role` fields
- `VerificationToken` — maps users to email verification tokens, deleted post-verification

## Getting Started

### Prerequisites
- Java 17
- MySQL
- Gradle

### Run Locally
```bash
git clone https://github.com/ramchoudhary-cloud/spring-auth-session-jwt.git
cd spring-auth-session-jwt
./gradlew bootRun
```

## Database Setup

Choose either MySQL (via Docker) or H2 (in-memory, zero setup) depending on your preference.

### Option 1 — MySQL via Docker

**1. Run MySQL container (if not already running)**
```bash
docker run -d --name mysql-shared -v mysql-data:/var/lib/mysql -p 3307:3306 -e MYSQL_ROOT_PASSWORD=yourpassword mysql:8
```

**2. Create the database**
```bash
docker exec -it mysql-shared mysql -u root -p
```
```sql
CREATE DATABASE auth_demo_db;
```

**3. Configure `application.properties`**
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/auth_demo_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

---

### Option 2 — H2 (In-Memory, No Setup Required)

Fastest way to run the project without installing or configuring any external database — useful for quick testing or demoing the project.

**1. Add H2 dependency to `build.gradle`**
```groovy
runtimeOnly 'com.h2database:h2'
```

**2. Configure `application.properties`**
```properties
spring.datasource.url=jdbc:h2:mem:auth_demo_db
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

**3. Access H2 Console (optional)**
Once the app is running, visit: http://localhost:8080/h2-console.

Use JDBC URL `jdbc:h2:mem:auth_demo_db`, username `sa`, blank password.

Note: H2 is in-memory — all data is lost when the application stops. Use MySQL for persistent local development.

Update `application.properties` with your MySQL connection details and JWT secret.

## Author
Ram Choudhary — Backend Software Engineer