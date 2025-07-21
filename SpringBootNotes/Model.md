# Model

## User Entity Overview

### 📦 Package

`com.cookbookwebsite.model`

---

### 🧱 Class: `User`

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

### 🔍 Field Breakdown

#### @Id

    •	Marks userId as the primary key.

#### @GeneratedValue(strategy = GenerationType.IDENTITY)

    •	Lets the database auto-increment the userId, just like AUTO_INCREMENT in MySQL.

⸻

#### @ManyToOne / @JoinColumn

    •	User has a many-to-one relationship with the Role entity (many users can have one role).
    •	@JoinColumn(name = "roleId") links the roleId foreign key in the Users table to the Role table’s roleId.

⸻

#### @Column(nullable = false)

    •	Ensures the field cannot be null in the database.

⸻

#### @Column(unique = true)

    •	Ensures the userEmail field is unique — no two users can register with the same email.

⸻

#### 🔐 Password Storage

    •	The field passwordHash is expected to store a secure, hashed password, not plain text.
    •	Use a hashing algorithm like BCrypt when saving passwords.

⸻

### 📝 Notes

    •	This entity will be managed by Spring Data JPA.
    •	When the application runs, Spring will auto-create or update the Users table based on this class, assuming JPA is configured to do so (spring.jpa.hibernate.ddl-auto=update).
