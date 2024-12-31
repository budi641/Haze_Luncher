
# **Software Design Specification (SDS) for Haze Launcher**

## **1. Introduction**

### **1.1 Purpose of the System**

The purpose of the **Haze Launcher** is to create an all-in-one platform for game management, which integrates game purchasing, library management, achievement tracking, and social interaction into a single user-friendly application. It aims to replace fragmented gaming platforms by providing a seamless experience.

### **1.2 Design Goals**

- **Efficiency**: Fast and responsive UI/UX for a seamless user experience.
- **Scalability**: The system should be able to handle increasing users and features without degradation in performance.
- **Security**: Data protection is a priority, ensuring user information (such as passwords, emails) is encrypted and secure.
- **Maintainability**: The design should allow for easy updates and future feature integrations.

---

## **2. High-level Software Architecture**

### **2.1 Subsystem Decomposition**

The system is divided into the following primary subsystems:
- **User Management**: Handles user registration, authentication, and profile management.
- **Game Store**: Allows users to browse, search, and purchase games.
- **Library Management**: Manages user libraries, tracking purchased games.
- **Achievement Tracking**: Tracks and displays user achievements in games.
- **Social Network**: Manages user friends, friend requests, and notifications.

### **2.2 Hardware/Software Mapping**

The **Haze Launcher** is developed using:
- **Java** for backend development.
- **Spring Boot** for application setup and API development.
- **Thymeleaf** for rendering dynamic web pages.
- **MySQL** as the relational database for persistent data storage.

### **2.3 Persistent Data Management**

Data will be stored in a MySQL database. The following entities will be persisted:
- **Users**
- **Games**
- **Libraries** (mapping users and their games)
- **Achievements**
- **Friends**
- **Payments**

The relationships among these entities are managed using **Spring Data JPA**.

### **2.4 Access Control and Security**

- **Authentication**: Managed via Spring Security using **JWT** (JSON Web Tokens) for secure user login and session management.
- **Authorization**: Role-based access control (admin, user).
- **Data Encryption**: Passwords will be encrypted using **BCrypt**.

### **2.5 Boundary Conditions**

- **Load Balancing**: The system should handle at least 50 concurrent users with a response time of less than 3 seconds.
- **Availability**: The system should ensure at least 99.9% uptime.
- **Error Handling**: Proper logging and graceful error handling in case of system failures.

---

## **3. Low-level Design**

### **3.1 Object Design Trade-offs**

- **Encapsulation**: Methods and attributes will be encapsulated within their respective classes to maintain data integrity.
- **Inheritance**: Common user functionalities will be placed in a base class, while extended functionalities (like admin features) will inherit from this base class.
- **Database Design**: We'll use a normalized relational database design to ensure data consistency and avoid data duplication.

### **3.2 Final Object Design**

The core objects in the system include:
- **User**
- **Game**
- **Library**
- **Achievement**
- **Payment**
- **Friend**

These objects have well-defined relationships, and the system is designed to handle these entities efficiently.

### **3.3 Packages**

The system’s packages are organized as follows:
- `com.hazelaucher.model` – Domain models (User, Game, etc.)
- `com.hazelaucher.service` – Service layer handling business logic
- `com.hazelaucher.controller` – Controller layer for handling HTTP requests
- `com.hazelaucher.repository` – Data access layer (repositories for each entity)
- `com.hazelaucher.security` – Security and authentication

### **3.4 Class Interfaces**

Each class provides interfaces for its methods, including CRUD operations for managing users, games, and purchases.

Example for `UserService` interface:
```java
public interface UserService {
    User registerUser(User user);
    User authenticateUser(String email, String password);
    List<Game> getLibrary(Long userId);
}
```

### **3.5 Design Patterns**

The following design patterns are applied:
1. **Singleton Pattern**: Used for managing the configuration settings across the system.
2. **Factory Pattern**: Used to create different types of **Payment** objects based on the user's choice.
3. **Observer Pattern**: Used to notify users of new game releases or updates in their library.

---

## **4. Persistence Layer Design**

### **4.1 ER Diagram**

The **Entity Relationship (ER) Diagram** shows the entities in the system and their relationships, including **Users**, **Games**, **Achievements**, and **Payments**.

**[Insert ER Diagram here]**

### **4.2 ORM Details**

ORM (Object-Relational Mapping) is achieved using **Spring Data JPA** to map the Java classes to database tables.

#### **Entities**

- **User**:
  - `userId`, `email`, `password`, etc.
  - Primary Key: `userId`
  - Relationships: A **User** can have multiple **Games** and **Achievements**.

  ```java
  @Entity
  @Table(name = "users")
  public class User {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long userId;

      private String email;
      private String password;
      private String firstName;
      private String lastName;
  }
  ```

- **Game**:
  - `gameId`, `name`, `description`, `price`, etc.
  - Primary Key: `gameId`
  - Relationships: A **Game** can have multiple **Achievements** and belongs to many **Users**.

  ```java
  @Entity
  @Table(name = "games")
  public class Game {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long gameId;

      private String name;
      private String description;
      private Double price;
  }
  ```

- **Library**:
  - Represents the relationship between users and games.
  
  ```java
  @Entity
  @Table(name = "libraries")
  public class Library {
      @Id
      private Long userId;
      
      @Id
      private Long gameId;
  }
  ```

- **Achievement**:
  - Tracks in-game achievements for users.
  
  ```java
  @Entity
  @Table(name = "achievements")
  public class Achievement {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long achievementId;

      private String name;
      private String description;
  }
  ```

---

## **5. Interaction Diagrams**

### **5.1 User Login Flow**

**[Insert Interaction Diagram for User Login here]**

This interaction diagram shows the process of a user logging in to the system, including the interactions between the **UI**, **Backend**, and **Database**.

### **5.2 Game Purchase Flow**

**[Insert Interaction Diagram for Game Purchase here]**

This interaction diagram illustrates how a user purchases a game, with the **UI** requesting data from the **Game Service** and updating the **Database**.

---

## **6. Improvement Summary (Iteration 2)**

This section will outline the improvements made during the second iteration of the design process. 

---

## **8. Glossary & References**

- **JPA**: Java Persistence API.
- **BCrypt**: A hashing algorithm used for password encryption.
- **Spring Boot**: A framework used for building the backend services of the system.
- **Thymeleaf**: A Java template engine used for rendering dynamic web pages.

