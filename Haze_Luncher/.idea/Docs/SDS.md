
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

1. **Singleton Pattern**: 
   - Used for managing the configuration settings across the system. The `ConfigManager` class ensures that only one instance of configuration settings exists and is accessible throughout the application. This guarantees consistent configuration management across all components.
   
   **Example**: 
   ```java
   public class ConfigManager {
       private static ConfigManager instance;

       private ConfigManager() {
           // Private constructor to prevent instantiation
       }

       public static ConfigManager getInstance() {
           if (instance == null) {
               instance = new ConfigManager();
           }
           return instance;
       }
   }
   ```

2. **Factory Pattern**: 
   - Used to create different types of **Payment** objects based on the user's choice. The `PaymentFactory` class generates instances of payment methods such as `CreditCardPayment`, `PayPalPayment`, etc., depending on the selected payment method. This allows for easy extensibility when adding new payment options.

   **Example**:
   ```java
   public class PaymentFactory {
       public Payment createPayment(String paymentType) {
           if (paymentType.equalsIgnoreCase("creditcard")) {
               return new CreditCardPayment();
           } else if (paymentType.equalsIgnoreCase("paypal")) {
               return new PayPalPayment();
           }
           return null;
       }
   }
   ```

3. **Observer Pattern**: 
   - Used to notify users of new game releases or updates in their library. The `Library` class notifies all subscribed `User` objects when a new game is added to their library or when updates are available. This pattern allows for easy updating of users without tightly coupling the components.

   **Example**:
   ```java
   public class Library {
       private List<User> users = new ArrayList<>();

       public void addUser(User user) {
           users.add(user);
       }

       public void notifyUsers(String gameUpdate) {
           for (User user : users) {
               user.update(gameUpdate);
           }
       }
   }

   public class User {
       public void update(String gameUpdate) {
           System.out.println("New update: " + gameUpdate);
       }
   }
   ```

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
### **7. UI Wireframes/Mockups**

In this section, the user interface design for **Haze Launcher** is presented, showcasing the various key pages designed in Figma. These include:

- **Home Page:** The landing page of the application where users can see featured games, news, and access other sections like the store and library.
- **Login Page:** A page where users enter their credentials to log in to the system.
- **Library Page:** A page where users can view the games they have purchased and manage their game library.
- **Game Page:** A page that displays detailed information about a specific game, including its description, price, and purchase option.
- **News Page:** A page that shows the latest updates, announcements, and news related to the games and the platform.

**Wireframes/Mockups:**
- **Home Page:** 
- **Login Page:** 
- **Library Page:** 
- **Game Page:** 
- **News Page:** 
---
### **8. Vision**

The vision of **Haze Launcher** is to provide a unified platform for gamers, combining game purchasing, library management, achievement tracking, and social interaction in one place. The goal is to offer a seamless, user-friendly experience for managing games, connecting with friends, and staying updated on the latest releases.

Key goals:
- **Convenience:** All gaming needs in one platform.
- **Community:** Connect with friends and share achievements.
- **Simplicity:** Easy-to-use interface for gamers of all levels.
- **Cross-Platform Compatibility:** Accessible across different devices.

---
### **9. Supplementary Specs**

The **Supplementary Specifications** define additional non-functional requirements and constraints for the **Haze Launcher** to ensure it operates effectively in various environments and meets user expectations.

- **Performance:** The platform should support at least 50 concurrent users with response times of ≤3 seconds for major interactions (e.g., browsing games, purchasing).
- **Security:** All sensitive user data, such as passwords and payment details, must be encrypted and stored securely using industry-standard encryption methods.
- **Data Integrity:** The system must ensure consistency and reliability of the user's game library and achievements, even in the event of failures or crashes.
- **Scalability:** The platform should be able to scale easily to support future features, more users, and larger data volumes as the platform grows.
- **Availability:** The platform must ensure 99.9% uptime, with proper failover mechanisms in place to handle outages.
- **Localization:** The platform should support multiple languages and regions to cater to a global user base.
- **Cross-Platform Support:** The application should be accessible from both desktop and mobile devices with a responsive design.
- **Accessibility:** The platform must comply with accessibility standards to ensure that all users, including those with disabilities, can use the platform effectively.

___

### **10. OCL Constraints**

1. **User Registration Constraints:**
   - **Unique Email:**  
     `context User inv: User.allInstances()->forAll(u1, u2 | u1.email <> u2.email)`  
     Ensures that every user has a unique email address.

2. **Password Security Constraints:**
   - **Password Length:**  
     `context User inv: self.password.size() >= 8`  
     Ensures that passwords must be at least 8 characters long.

   - **Password Complexity:**  
     `context User inv: self.password.matches('[A-Za-z0-9]*')`  
     Ensures that passwords must contain a mix of letters and numbers.

3. **Game Purchase Constraints:**
   - **Sufficient Funds:**  
     `context User inv: self.balance >= Game.price`  
     Ensures that a user must have enough funds to purchase a game.

   - **Game Purchase Limit:**  
     `context Library inv: self.games->size() <= 1000`  
     Ensures that a user can only add up to 1000 games to their library (modifiable as needed).

4. **Achievement Tracking Constraints:**
   - **Unlocked Achievement:**  
     `context Achievement inv: self.isUnlocked = true implies self.game.players->includes(self.user)`  
     Ensures that an achievement can only be unlocked by a user who owns the associated game.

5. **Friendship Constraints:**
   - **Unique Friendship Relationship:**  
     `context User inv: self.friends->forAll(friend | friend.friends->includes(self))`  
     Ensures that friendship is bidirectional (i.e., if User A is friends with User B, User B must also be friends with User A).

   - **Maximum Number of Friends:**  
     `context User inv: self.friends->size() <= 500`  
     Ensures that a user cannot have more than 500 friends.

6. **Library Constraints:**
   - **Game Ownership:**  
     `context Library inv: self.user.library->includes(self.game)`  
     Ensures that a game can only be added to a user's library if the user has purchased it.

   - **No Duplicate Games in Library:**  
     `context Library inv: self.games->isUnique()`  
     Ensures that the same game cannot be added multiple times to the library.

7. **Payment Constraints:**
   - **Valid Payment Method:**  
     `context Payment inv: self.paymentMethod.isValid()`  
     Ensures that the selected payment method is valid before processing a payment.

___
### **11. Logical Architecture**

The **Logical Architecture** defines the structure and components of the **Haze Launcher** system, focusing on the interaction between the different modules.

#### **1. System Layers**

- **Presentation Layer (UI)**: User interfaces, built with **Thymeleaf**, providing views like login, library, and game catalog. Communicates with the backend via REST APIs.
- **Application Layer**: Manages user requests and business logic. It includes controllers and services for user, game, payment, and library management.
- **Domain Layer**: Contains the core business logic, with entities like `User`, `Game`, and `Payment`, and manages the persistence of data.
- **Persistence Layer**: Responsible for data storage and retrieval using a relational database (e.g., MySQL), with **Spring Data JPA** handling ORM.

#### **2. Modules and Interactions**

- **User Management**: Handles registration, login, and profile management.
- **Game Catalog**: Manages game browsing, searching, and details.
- **Purchase & Payment**: Handles game purchases and payment processing.
- **Library Management**: Manages the user's owned games and achievements.
- **Achievement Tracking**: Tracks and displays game achievements.
- **Friendship Management**: Manages friend requests and interactions.

#### **3. Component Interaction**

- **Presentation Layer** interacts with the **Application Layer** through HTTP requests.
- The **Application Layer** communicates with the **Domain Layer** to process data, which is then stored/retrieved via the **Persistence Layer**.

**Diagram:**  
**[Place for Diagram]**
___
### **12. Activity Diagrams (Representing Business Flow)**

#### **1. Game Purchase Flow**

**Description**: This diagram represents the sequence of activities involved when a user purchases a game.

- **Start**: The user navigates to the game catalog.
- **Select Game**: User selects a game to purchase.
- **Check Availability**: System checks the game’s availability.
- **Proceed to Payment**: If the game is available, the user proceeds to the payment gateway.
- **Payment Processing**: System processes payment and confirms the transaction.
- **Add to Library**: Upon successful payment, the game is added to the user’s library.
- **Confirmation**: System sends a confirmation email.
- **End**: The purchase process ends.

**Diagram:**  
**[Place for Diagram]**

#### **2. User Registration Flow**

**Description**: This diagram represents the user registration process.

- **Start**: User visits the registration page.
- **Enter Information**: User enters required information (e.g., username, email, password).
- **Validate Input**: System validates the data.
- **Create Account**: If validation is successful, system creates a new user.
- **Send Confirmation**: System sends a confirmation email to the user.
- **Account Activation**: User activates the account via email link.
- **End**: Registration is complete.

**Diagram:**  
**[Place for Diagram]**

#### **3. Achievement Tracking Flow**

**Description**: This diagram shows how the system tracks and updates achievements.

- **Start**: User plays a game and achieves a milestone.
- **Track Progress**: System monitors the user’s progress in the game.
- **Achievement Unlocked**: If progress matches an achievement condition, the system updates the achievement status.
- **Update Profile**: The achievement is added to the user’s profile.
- **End**: Achievement tracking ends.

**Diagram:**  
**[Place for Diagram]**

___
### **13. State Machine Diagrams (UI Navigation)**

State machine diagrams show the states of key UI components and how they transition. Below are the state machine diagrams for **Haze Launcher**'s UI navigation.

#### **1. User Authentication (Login/Logout)**

- **Initial State**: User is logged out.
- **Login State**: User enters credentials. If valid, transitions to **Logged-in State**; otherwise, stays in the **Initial State**.
- **Logged-in State**: User is logged in and can navigate to other sections. User can log out, returning to the **Initial State**.
- **Error State**: If login fails, system shows an error.

**Diagram:**  
**[Place for Diagram]**

#### **2. Game Library Navigation**

- **Initial State**: User is on the homepage.
- **Library State**: User navigates to the game library.
- **Game Details State**: User selects a game to view details.
- **Purchase State**: User buys a game, and it’s added to the library.

**Diagram:**  
**[Place for Diagram]**

#### **3. Achievement Tracking**

- **Initial State**: No achievements unlocked.
- **Game Progress State**: System tracks user progress.
- **Achievement Unlocked State**: Milestone reached, achievement unlocked.

**Diagram:**  
**[Place for Diagram]**

___
### **Glossary**

- **Haze Launcher**: The platform for game management, including purchasing, library management, achievements, and social interactions.
- **User Management**: Handles user registration, authentication, and profile management.
- **Game Store**: Section for browsing, searching, and purchasing games.
- **Library Management**: Manages a user’s game library.
- **Achievement Tracking**: Tracks in-game achievements for users.
- **Social Network**: Manages user friends, friend requests, and notifications.
- **Spring Boot**: Framework used for backend development and API creation.
- **Thymeleaf**: Template engine for rendering dynamic web pages.
- **MySQL**: Relational database for storing persistent data.
- **Spring Data JPA**: Used for ORM to map Java objects to database tables.
- **JWT (JSON Web Tokens)**: Used for secure user authentication and session management.
- **BCrypt**: Algorithm for securely hashing user passwords.
- **Singleton Pattern**: Ensures only one instance of configuration settings across the system.
- **Factory Pattern**: Creates different types of payment objects based on user choice.
- **Observer Pattern**: Notifies users of updates in their library.
- **Entity**: A Java class representing a table in the database (e.g., User, Game, Achievement).
- **CRUD Operations**: Create, Read, Update, Delete operations for managing entities in the system.
- **ER Diagram**: Visual representation of entities and their relationships in the system.
- **ORM (Object-Relational Mapping)**: Technique for converting data between object-oriented programming and relational databases.
- **OCL (Object Constraint Language)**: Formal language used to define constraints on models in UML.
- **UI**: User interface that allows interaction with the system.
