# SkillSprint – Corporate Learning Platform

SkillSprint is an internal corporate learning and skill development platform built with Spring Boot. The platform is designed to model real organizational hierarchies and workflows to facilitate structured learning, mentorship, and professional networking between junior and senior developers, while exposing participants to production-grade backend patterns, scalable architecture, and secure enterprise practices.

---

## Project Overview

SkillSprint enables skill validation, instructor-led learning, complaint handling, and multi-level administrative approvals within an organization. The backend architecture emphasizes correctness, scalability, and security over simple CRUD operations, ensuring a professional-grade system suitable for enterprise usage.

Key objectives:

* Provide a structured learning platform for juniors and freshers.
* Allow senior developers to mentor and evaluate learners.
* Give admins and managers oversight with actionable dashboards.
* Expose learners to scalable and secure system design patterns.

---

## Organizational Role Mapping

SkillSprint maps directly to corporate engineering hierarchies:

```
Super Admin (GM)
│
├── Admin Manager (Architect / Engineering Manager)
│   │
│   ├── Admin (Architect)
│   │   │
│   │   ├── Instructor (Senior Developer / Tech Lead)
│   │   │   │
│   │   │   └── Learner (Junior Developer / Fresher)
```

---

## Production-Ready Technical Implementations

SkillSprint is engineered for scalability, security, and reliability in enterprise environments:

* **Concurrency Management:** Dedicated ThreadPoolTaskExecutor isolates background analytics tasks, preventing heavy DB operations from blocking web requests.

* **Stateless Authentication:** Dual-token system with in-memory Access Tokens and MySQL-stored Refresh Tokens ensures secure, manageable sessions.

* **Optimized Database Access:** Use of JPA projections and DB indexing prevents excessive data fetching and improves performance for hierarchical queries.

* **Asynchronous & Scheduled Tasks:** Background analytics, notifications, and maintenance tasks run asynchronously to maintain low-latency responses.

* **Secure RBAC & Scalable Architecture:** Fine-grained role-based access with stateless REST APIs, ready for microservices evolution.

---

## Role Responsibilities

**Super Admin (GM)**

* Create and manage Admin Managers
* Define global policies
* View organization-wide analytics
* Override critical decisions

**Admin Manager (Architect / Engineering Manager)**

* Monitor admin operations
* Review and approve admin actions
* Assess workload and escalate issues to GM
* Access aggregated analytics

**Admin (Architect)**

* Review instructor applications
* Approve/reject skill validation requests
* Handle complaints and moderation
* Access admin dashboards

**Instructor (Senior Developer / Tech Lead)**

* Develop skill modules
* Validate learners’ submissions
* Provide mentorship
* Review learning progress

**Learner (Junior Developer / Fresher)**

* Enroll in learning modules
* Submit skill validation requests
* Track learning progress
* Interact with instructors for guidance

---

## Technology Stack

* **Backend Framework:** Spring Boot
* **Security:** Spring Security, JWT (Access & Refresh Tokens)
* **Database:** MySQL / PostgreSQL
* **ORM:** Spring Data JPA (Hibernate)
* **Concurrency & Async:** @Async, CompletableFuture, Custom Thread Pools, Schedulers
* **API Style:** RESTful APIs
* **Build Tool:** Maven

---

## Key Features

* Role-based access control (RBAC) aligned with corporate hierarchies
* JWT-based authentication with access and refresh tokens
* Admin and instructor dashboards with async analytics
* Complaint management and resolution system
* Instructor-led skill evaluation and approvals
* Secure, scalable, and production-grade backend architecture

---

## Asynchronous Analytics

Analytics and dashboard metrics are computed asynchronously to ensure:

* Low-latency API responses
* Isolation of independent tasks
* Scalability under high data volume

CompletableFuture and custom executor thread pools are used to optimize concurrency. Periodic metrics recomputation is handled by scheduler threads.

---

## Database Design

The database schema reflects enterprise workflow requirements:

* Users, roles, and role mappings are clearly defined
* Tables exist for skill modules, complaints, approvals, and analytics
* Fully normalized schema to support scalability
* Designed to support multiple workflows without tight coupling

---

## Project Structure

```
QuizApp
 ├── src/main/java/com/harish/quizapp
 │   ├── asyncFunctionCalls  # Async services for admin analytics
 │   ├── AuthenticationFilters # JWT and custom auth filters
 │   ├── config               # Security, async, and scheduler configuration
 │   ├── Controllers          # REST API endpoints
 │   ├── DataRepos            # JPA repositories
 │   ├── Dto                  # Request and response objects
 │   ├── enums                # Enums for status and roles
 │   ├── helpers              # Helper classes
 │   ├── Model                # Entity models
 │   ├── Scheduler            # Scheduled jobs
 │   ├── Service              # Business logic services
 │   └── Util                 # Utilities such as JWT utility
 └── src/main/resources
     ├── application.properties
     └── templates & static files
```

---

## Running the Application

1. Clone the repository.
2. Configure the database and security properties in `application.properties`.
3. Seed initial roles and privileged users.
4. Run the application:

```bash
mvn clean install
mvn spring-boot:run
```

---

## Scalability & Future Scope

* Monolith to microservices evolution is feasible
* Analytics can be offloaded to a dedicated service
* Caching layers like Redis can be integrated
* Event-driven extensions using Kafka or RabbitMQ are possible

---

## Author

**Harish** – Backend-focused developer specializing in Spring Boot, enterprise system design, asynchronous workflows, and scalable backend architecture. This project reflects practical engineering decisions and real-world corporate learning patterns.
