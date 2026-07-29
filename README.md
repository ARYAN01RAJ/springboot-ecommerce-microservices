# 🛒 E-Commerce Backend Microservices

A scalable backend-only **E-Commerce Microservices Application** built using **Spring Boot** and **Spring Cloud**. The project follows a distributed architecture where each microservice has a single responsibility and communicates using both synchronous (OpenFeign) and asynchronous (Apache Kafka) messaging.

It demonstrates modern backend development concepts such as **JWT Authentication, API Gateway, Service Discovery, Kafka Event Streaming, RESTful APIs, Bean Validation, Global Exception Handling, and Layered Architecture.**

---

# 🚀 Project Highlights

- ✅ Spring Boot Microservices
- ✅ JWT Authentication & Authorization
- ✅ Spring Security
- ✅ API Gateway
- ✅ Eureka Service Discovery
- ✅ OpenFeign Client
- ✅ Apache Kafka Integration
- ✅ MySQL Database
- ✅ Spring Data JPA & Hibernate
- ✅ Bean Validation
- ✅ Global Exception Handling
- ✅ Swagger / OpenAPI Documentation
- ✅ RESTful APIs
- ✅ Layered Architecture
- ✅ Unit Testing (Service Layer)

---

# 🏗️ Architecture

```
                           Client
                              │
                              ▼
                     API Gateway Service
                              │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
        ▼                     ▼                     ▼
 Authentication        Item Service         Order Service
      Service                │                   │
                              │                  │
                              ▼                  ▼
                    Inventory Service      Notification Service
                              │                  ▲
                              └──────────┬───────┘
                                         │
                                    Apache Kafka
                                         │
                                   Event Messaging

                    Eureka Server (Service Discovery)

                         MySQL Databases
```

---

# 📦 Microservices

## 🔐 Authentication Service

Responsible for user authentication and authorization.

### Features

- User Registration
- User Login
- JWT Token Generation
- Password Encryption
- Spring Security
- Token Validation

---

## 📦 Item Service

Responsible for managing products/items.

### Features

- Create Item
- Update Item
- Delete Item
- Get Item By ID
- Get All Items
- Bean Validation
- JPA Repository

---

## 📊 Inventory Service

Responsible for inventory and warehouse management.

### Features

- Inventory CRUD
- Stock Management
- Warehouse Information
- Inventory Validation

---

## 🛍️ Order Service

Responsible for placing and managing orders.

### Features

- Place Order
- View Orders
- Update Orders
- Delete Orders
- OpenFeign Communication
- Kafka Event Publishing

---

## 📢 Notification Service

Responsible for handling notifications.

### Features

- Kafka Consumer
- Event Processing
- Notification APIs
- Easily Extendable for Email/SMS

---

## 🌐 API Gateway

Acts as the single entry point for all requests.

### Features

- Request Routing
- JWT Authentication
- Security Filters
- Centralized Access

---

## 📡 Eureka Server

Responsible for service discovery.

### Features

- Automatic Service Registration
- Dynamic Discovery
- Load Balancing Support

---

# ⚙️ Technology Stack

| Technology | Version |
|------------|---------|
| Java | 21 |
| Spring Boot | 4.1.0 |
| Spring Framework | 7.x |
| Spring Security | 7.x |
| Spring Cloud Gateway | Spring Cloud |
| Spring Cloud Netflix Eureka | Spring Cloud |
| Spring Data JPA | 4.x |
| Hibernate | 7.x |
| Apache Kafka | 4.x |
| Spring for Apache Kafka | 4.x |
| OpenFeign | Spring Cloud OpenFeign |
| JWT (JJWT) | 0.12.x |
| MySQL | 8.x |
| Maven | 3.9+ |
| Lombok | Latest |
| JUnit 5 | 5.x |
| Mockito | 5.x |
| Swagger / OpenAPI | springdoc-openapi |

---

# 🔄 Inter-Service Communication

### Synchronous

- OpenFeign Client

Used for:

- Order Service ↔ Inventory Service
- Authentication Requests

---

### Asynchronous

Apache Kafka

Used for:

- Event Publishing
- Event Consumption
- Notification Processing
- Loose Coupling Between Services

---

# 🔒 Security

- Spring Security
- JWT Authentication
- Stateless Authentication
- Password Encryption
- Protected REST APIs

---

# 📁 Project Structure

```
microservice-project
│
├── authentication-service
├── eureka-service
├── gateway-service
├── inventory-service
├── item-service
├── notification-service
├── order-service
│
├── README.md
├── .gitignore
└── pom.xml (if using parent pom)
```

---

# 📂 Layered Architecture

Each microservice follows:

```
Controller
     │
     ▼
Service
     │
     ▼
Repository
     │
     ▼
Database
```

---

# 📖 API Documentation

Swagger UI is available for every service.

Example

```
http://localhost:8081/swagger-ui/index.html
```

Replace the port according to the service.

---

# ▶️ Running the Project

Start the services in the following order:

1. Eureka Server
2. Authentication Service
3. API Gateway
4. Item Service
5. Inventory Service
6. Order Service
7. Notification Service

---

# 🧪 Testing

Implemented Unit Tests for:

- Service Layer
- Business Logic
- Repository Mocking using Mockito

> Controller tests have intentionally been omitted from this repository due to test-slice/security configuration differences while preserving the working production implementation.

---

## Configuration

The repository does not include the actual `application.properties` and `application.yml` files because they may contain local database credentials, JWT secrets, or other environment-specific configuration.

Each service includes an example configuration file:

- `application-example.properties`
- `application-example.yml`

To run the project:

1. Copy the example file.
2. Rename it to `application.properties` or `application.yml`.
3. Replace the placeholder values with your own local configuration.

# 🌱 Future Improvements

- Docker Compose
- Kubernetes Deployment
- Centralized Config Server
- Redis Caching
- Circuit Breaker (Resilience4j)
- Distributed Tracing
- ELK Logging
- CI/CD Pipeline
- Method-Level Security
- OAuth2 Integration

---

# 👨‍💻 Author

**Aryan Raj**

Computer Science Undergraduate

Backend Developer | Java | Spring Boot | Microservices

GitHub: https://github.com/ARYAN01RAJ

---

# ⭐ If you found this project useful, consider giving it a star.