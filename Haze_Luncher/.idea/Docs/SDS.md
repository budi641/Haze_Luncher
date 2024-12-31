# **Software Design Specification (SDS) for Haze Launcher**

## **1. Introduction**

### **1.1 Purpose of the System**

The purpose of the **Haze Launcher** is to provide an integrated platform for managing game libraries, purchasing games, tracking achievements, and managing social features. It consolidates multiple gaming functionalities into a single, user-friendly application, similar to Steam, allowing users to streamline their gaming experience.

### **1.2 Design Goals**

- **Modularity:** The system should be designed in a modular way to allow easy updates and maintenance. Different functionalities (e.g., game purchase, user management) should be encapsulated into independent components.
- **Scalability:** The architecture should support scaling as the user base and feature set grow.
- **Usability:** The system should provide a smooth and intuitive user experience, with simple navigation and a visually appealing design.
- **Security:** User data should be stored securely, with encrypted passwords and payment methods.
- **Performance:** The system should be optimized for fast load times and low latency, supporting up to 50 concurrent users.

---

## **2. High-Level Software Architecture**

### **2.1 Subsystem Decomposition**

The system is broken down into the following subsystems:
- **User Management:** Responsible for user authentication, registration, and profile management.
- **Game Store and Library Management:** Handles browsing, purchasing, and managing the user’s game library.
- **Achievements and Social Features:** Tracks game achievements, friends, and notifications.
- **Payment System:** Processes transactions securely.
- **Email Notification:** Sends confirmation emails and other user notifications.

**Diagram:**
**[Insert High-Level Architecture Diagram here]**

---

### **2.2 Hardware/Software Mapping**

The **Haze Launcher** application uses the following technologies:
- **Frontend:** **Thymeleaf** is used for rendering dynamic HTML templates and integrating with backend logic.
- **Backend:** **Spring Boot** is used to build the backend API, handling user authentication, game purchases, etc.
- **Database:** **PostgreSQL** or **MySQL** stores user data, games, libraries, and achievements.
- **IDE:** Development is done using **IntelliJ IDEA**, which supports Java, Spring Boot, and Thymeleaf.

**Diagram:**
**[Insert Hardware/Software Mapping Diagram here]**

---

### **2.3 Persistent Data Management**

The data persistence layer uses a relational database (e.g., PostgreSQL) for storing user data, game libraries, achievements, and transactions. The ORM layer (via **Spring Data JPA**) maps entities like **User**, **Game**, **Library**, **Achievement**, etc., to corresponding tables.

**Diagram:**
**[Insert ER Diagram here]**

---

### **2.4 Access Control and Security**

Security features include:
- **Password Encryption:** User passwords are encrypted using **BCrypt** or a similar hashing algorithm.
- **OAuth 2.0 Authentication:** Users can authenticate using OAuth 2.0 (e.g., through Google or Facebook login).
- **Payment Security:** Payment information is processed via secure external gateways (e.g., Stripe or PayPal).
- **Authorization:** Role-based access control for admins and regular users, with admin privileges for managing the game catalog.

---

### **2.5 Boundary Conditions**

- **Error Handling:** The system will handle errors gracefully, with clear messages for the user in case of failures (e.g., invalid login, payment failure).
- **Timeouts and Limits:** The system will handle timeouts gracefully and ensure that requests beyond a certain time limit (e.g., 30 seconds) return appropriate error messages.
- **Session Management:** User sessions will expire after 30 minutes of inactivity and will require re-authentication.

---

## **3. Low-Level Design**

### **3.1 Object Design Trade-offs**

Trade-offs include:
- **Memory vs. Performance:** Caching user data and game information can improve performance but increases memory usage. We’ll use an in-memory cache (e.g., **Redis**) for frequently accessed data.
- **Complexity vs. Modularity:** A modular design is prioritized to support scalability, even if it introduces some initial complexity.

---

### **3.2 Final Object Design**

Key classes include:
- **User:** Manages user information, including login, registration, and profile data.
- **Game:** Represents a game in the catalog or user’s library.
- **Library:** Manages the list of games a user owns.
- **Achievement:** Tracks progress in individual games.
- **Payment:** Handles transactions during game purchases.
- **EmailService:** Manages email notifications.

**Diagram:**
**[Insert Refined Class Diagram here]**

---

### **3.3 Packages**

The system is organized into the following packages:
- **com.hazelaucher.user**: Handles user-related functionality.
- **com.hazelaucher.game**: Manages game catalog, purchases, and libraries.
- **com.hazelaucher.social**: Handles friends and achievements.
- **com.hazelaucher.payment**: Contains payment logic.
- **com.hazelaucher.notification**: Manages email notifications.
- **com.hazelaucher.web**: Contains Thymeleaf templates for frontend views.

**Diagram:**
**[Insert Package Diagram here]**

---

### **3.4 Class Interfaces**

Key interfaces include:
- **IUserService**: Manages user operations like registration and login.
- **IGameService**: Manages game-related operations like browsing and purchasing.
- **IPaymentService**: Handles payment transactions.
- **IEmailService**: Manages email notifications.

---

### **3.5 Design Patterns**

The following design patterns are used:
1. **Singleton Pattern** for services like **EmailService** and **PaymentService** to ensure one instance is shared.
2. **Observer Pattern** for notifying users when achievements are unlocked.
3. **Factory Method Pattern** to handle different payment methods (e.g., credit card, PayPal).

---

## **4. Improvement Summary (Iteration 2)**

In the second iteration, we plan to improve:
- **Performance Optimization:** Caching frequently used data to reduce database load.
- **UI Enhancements:** Refining the UI based on user feedback.
- **Additional Features:** Adding support for more payment methods and social features like chat.

---

## **5. Glossary & References**

- **Game Library:** The collection of games that a user owns.
- **Achievement:** A task or goal within a game that can be unlocked.
- **OAuth 2.0:** An authorization framework used for third-party authentication.
- **BCrypt:** A password hashing algorithm.
- **ORM (Object-Relational Mapping):** A technique for converting between object-oriented models and relational databases.

---

### **Appendices:**

- **Refined Class Diagram:**  
  **[Insert Refined Class Diagram here]**

- **Interaction Diagrams:**  
  **[Insert Interaction Diagrams here]**

- **Persistence Layer Design (ER Diagram, ORM details):**  
  **[Insert ER Diagram here]**

- **UI Wireframes/Mockups:**  
  **[Insert UI Wireframes here]**

- **Well-commented and Documented Code:**  
  **[Code snippets and explanations will be provided in iteration 2]**

- **Fully-Dressed Use Cases:**  
  **[Insert Use Cases here]**

- **Use Case Diagram:**  
  **[Insert Use Case Diagram here]**

- **Vision Document:**  
  **[Insert Vision Document here]**

- **Supplementary Specs Document:**  
  **[Insert Supplementary Specs Document here]**

- **Business/Domain Rules:**  
  **[Insert Business/Domain Rules here]**

- **Logical Architecture:**  
  **[Insert Logical Architecture Diagram here]**

- **Domain Model:**  
  **[Insert Domain Model Diagram here]**

- **Design Class Diagrams:**  
  **[Insert Design Class Diagrams here]**

- **Sequence Diagrams (or Comm Diagrams):**  
  **[Insert Sequence Diagrams here]**

- **Activity Diagrams (representing business flow):**  
  **[Insert Activity Diagrams here]**

- **State Machine Diagrams (especially for UI Navigation):**  
  **[Insert State Machine Diagrams here]**

- **GUI Snapshots:**  
  **[Insert GUI Snapshots here]**

- **Design Patterns (show explicitly how you use 3 of them):**  
  **[Insert Design Patterns here]**

- **Summary Sheet (1 page):**  
  **[Insert Summary Sheet here]**

- **Lessons Learnt (1 page):**  
  **[Insert Lessons Learnt here]**