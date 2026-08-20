# Graph Report - Authentication-Authorization  (2026-08-16)

## Corpus Check
- 4 files · ~2,586 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 112 nodes · 180 edges · 14 communities (11 shown, 3 thin omitted)
- Extraction: 97% EXTRACTED · 3% INFERRED · 0% AMBIGUOUS · INFERRED: 6 edges (avg confidence: 0.82)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- Repository & Service Layer
- JWT Filter & Token Utils
- User REST Controller
- README Overview
- User & VerificationToken Entities
- Security Configuration
- Application Tests
- User Registration Flow
- Gradle Wrapper Script
- Application Entry Point
- README Architecture Layers
- BCrypt Encoder Bean

## God Nodes (most connected - your core abstractions)
1. `User` - 15 edges
2. `VerificationToken` - 12 edges
3. `UserService` - 11 edges
4. `Spring Auth — Session & JWT` - 11 edges
5. `UserRepository` - 8 edges
6. `UserController` - 8 edges
7. `VerificationRepository` - 7 edges
8. `JwtFilters` - 5 edges
9. `JwtUtils` - 5 edges
10. `UserAlreadyExistException` - 4 edges

## Surprising Connections (you probably didn't know these)
- `UserRepository` --references--> `User`  [EXTRACTED]
  src/main/java/com/ram/Authentication/Authorization/repository/UserRepository.java → src/main/java/com/ram/Authentication/Authorization/entity/User.java
- `VerificationRepository` --references--> `VerificationToken`  [EXTRACTED]
  src/main/java/com/ram/Authentication/Authorization/repository/VerificationRepository.java → src/main/java/com/ram/Authentication/Authorization/entity/VerificationToken.java
- `VerificationToken` --references--> `User`  [EXTRACTED]
  src/main/java/com/ram/Authentication/Authorization/entity/VerificationToken.java → src/main/java/com/ram/Authentication/Authorization/entity/User.java
- `UserService` --references--> `UserRepository`  [EXTRACTED]
  src/main/java/com/ram/Authentication/Authorization/service/UserService.java → src/main/java/com/ram/Authentication/Authorization/repository/UserRepository.java
- `UserService` --references--> `VerificationRepository`  [EXTRACTED]
  src/main/java/com/ram/Authentication/Authorization/service/UserService.java → src/main/java/com/ram/Authentication/Authorization/repository/VerificationRepository.java

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Controller → Service → Repository → Database Request Flow** — readme_controller_layer, readme_service_layer, readme_repository_layer, readme_database_layer [EXTRACTED 1.00]
- **Planned Authentication & Authorization Features** — readme_session_authentication, readme_jwt_authentication, readme_email_verification_flow, readme_rbac [EXTRACTED 1.00]
- **Project Tech Stack** — readme_java_17, readme_spring_boot, readme_spring_security, readme_spring_data_jpa_hibernate, readme_mysql, readme_gradle [EXTRACTED 1.00]

## Communities (14 total, 3 thin omitted)

### Community 0 - "Repository & Service Layer"
Cohesion: 0.19
Nodes (9): org.springframework.data.jpa.repository.JpaRepository, org.springframework.security.core.userdetails.UserDetails, org.springframework.security.core.userdetails.UserDetailsService, org.springframework.stereotype.Repository, org.springframework.stereotype.Service, UserNotVerifiedException, UserRepository, VerificationRepository (+1 more)

### Community 1 - "JWT Filter & Token Utils"
Cohesion: 0.23
Nodes (10): com.ram.Authentication.Authorization.entity.User, io.jsonwebtoken.Claims, jakarta.servlet.FilterChain, jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpServletResponse, org.springframework.stereotype.Component, org.springframework.web.filter.OncePerRequestFilter, Override (+2 more)

### Community 2 - "User REST Controller"
Cohesion: 0.19
Nodes (9): com.ram.Authentication.Authorization.exceptions.UserAlreadyExistException, com.ram.Authentication.Authorization.exceptions.UserNotVerifiedException, com.ram.Authentication.Authorization.service.UserService, ExceptionHandler, GetMapping, org.springframework.http.ResponseEntity, PostMapping, RestController (+1 more)

### Community 3 - "README Overview"
Cohesion: 0.16
Nodes (15): Ram Choudhary (Author), Email Verification Flow, Gradle, H2 (In-Memory Database), Java 17, JWT Token-Based Authentication, MySQL, Role-Based Access Control (RBAC) (+7 more)

### Community 4 - "User & VerificationToken Entities"
Cohesion: 0.36
Nodes (9): lombok.AllArgsConstructor, lombok.Getter, lombok.NoArgsConstructor, lombok.Setter, Entity, User, Entity, VerificationToken (+1 more)

### Community 5 - "Security Configuration"
Cohesion: 0.33
Nodes (7): BCryptPasswordEncoder, org.springframework.context.annotation.Bean, org.springframework.context.annotation.Configuration, org.springframework.security.config.annotation.web.builders.HttpSecurity, org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder, org.springframework.security.web.SecurityFilterChain, AuthenticationAuthorizationConfigue

### Community 6 - "Application Tests"
Cohesion: 0.60
Nodes (3): org.junit.jupiter.api.Test, org.springframework.boot.test.context.SpringBootTest, ApplicationTests

### Community 8 - "Gradle Wrapper Script"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 10 - "README Architecture Layers"
Cohesion: 0.50
Nodes (4): Controller Layer, Database, Repository Layer, Service Layer

## Knowledge Gaps
- **7 isolated node(s):** `Ram Choudhary (Author)`, `Java 17`, `Spring Boot`, `Spring Data JPA / Hibernate`, `Spring Security` (+2 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **3 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `User` connect `User & VerificationToken Entities` to `Repository & Service Layer`, `User Registration Flow`?**
  _High betweenness centrality (0.076) - this node is a cross-community bridge._
- **Why does `VerificationToken` connect `User & VerificationToken Entities` to `Repository & Service Layer`?**
  _High betweenness centrality (0.054) - this node is a cross-community bridge._
- **What connects `Ram Choudhary (Author)`, `Java 17`, `Spring Boot` to the rest of the system?**
  _7 weakly-connected nodes found - possible documentation gaps or missing edges._