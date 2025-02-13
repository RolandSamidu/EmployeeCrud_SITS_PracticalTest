<h1>SpringBoot Application - Simple Employee CRUD App</h1>
# Spring Boot Application with MySQL - Setup Guide

This guide will walk you through setting up and running a Spring Boot application with a MySQL database.

## Prerequisites

Ensure you have the following installed:

- Java 17+
- Maven
- MySQL Server
- Spring Boot

---

## 1. Clone the Repository

```sh
git clone https://github.com/your-repository.git
cd your-repository
```

---

## 2. Database Setup

1. Start MySQL server.
2. Open MySQL CLI or a GUI like MySQL Workbench.
3. Create a new database:

```sql
CREATE DATABASE your_db;
```

4. Update `src/main/resources/application.properties` (or `application.yml`) with your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/springboot_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

---

## 3. Build & Run the Application

### Using Maven

```sh
mvn clean install
mvn spring-boot:run
```

### Using Java Command

```sh
mvn package
java -jar target/your-app-name.jar
```

---

## 5. Verify Database Connection

To check if tables are created, run:

```sql
USE springboot_db;
SHOW TABLES;
SELECT * FROM your_table;
```

---


