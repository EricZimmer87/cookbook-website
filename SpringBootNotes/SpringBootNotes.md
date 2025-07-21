# Overview

## Model:

Basically a framework for each table in the database.

## Service:

Handles all the logic for interacting with the database and additional back-end logic. Acts as an intermediary between the controller and the repository.

## Repository:

Focuses purely on interacting with the database.

## Controller:

Handles HTTP requests and defines routes (or endpoints). It basically acts as the router.

# Database

## jakarta.persistence Library

The jakarta.persistence package provides the annotations and interfaces that Spring Boot (or any JPA implementation) uses, such as:

| Annotation / Interface     | Purpose                                                   |
| -------------------------- | --------------------------------------------------------- |
| `@Entity`                  | Marks a class as a persistent entity (maps to a DB table) |
| `@Id`                      | Marks a field as the primary key                          |
| `@GeneratedValue`          | Defines how primary key values are generated              |
| `@Column`                  | Customizes the column mapping                             |
| `@ManyToOne`, `@OneToMany` | Defines relationships between tables                      |
| `EntityManager`, `Query`   | Provides low-level access to DB operations                |

## Model

### User Entity Overview

#### 📦 Package

`com.cookbookwebsite.model`

---

#### 🧱 Class: `User`

This class is annotated with `@Entity`, indicating that it maps to a `Users` table in the MySQL database.

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String passwordHash;
}
```

#### 🔍 Field Breakdown

##### @Id

    •	Marks userId as the primary key.

##### @GeneratedValue(strategy = GenerationType.IDENTITY)

    •	Lets the database auto-increment the userId, just like AUTO_INCREMENT in MySQL.

⸻

##### @ManyToOne / @JoinColumn

    •	User has a many-to-one relationship with the Role entity (many users can have one role).
    •	@JoinColumn(name = "roleId") links the roleId foreign key in the Users table to the Role table’s roleId.

⸻

##### @Column(nullable = false)

    •	Ensures the field cannot be null in the database.

⸻

##### @Column(unique = true)

    •	Ensures the userEmail field is unique — no two users can register with the same email.

⸻

##### 🔐 Password Storage

    •	The field passwordHash is expected to store a secure, hashed password, not plain text.
    •	Use a hashing algorithm like BCrypt when saving passwords.

⸻

#### 📝 Notes

    •	This entity will be managed by Spring Data JPA.
    •	When the application runs, Spring will auto-create or update the Users table based on this class, assuming JPA is configured to do so (spring.jpa.hibernate.ddl-auto=update).
