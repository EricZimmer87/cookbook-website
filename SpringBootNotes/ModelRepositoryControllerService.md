# 🧱 1. Model Layer (model/)

Defines the structure of your database tables using JPA annotations.

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

    // getters and setters
}
```

# 🗃️ 2. Repository Layer (repository/)

Interfaces that handle direct access to the database.

```java
public interface UserRepository extends JpaRepository<User, Integer> {
    // Optional<User> findByUserEmail(String email);
}
```

Spring will auto-implement these interfaces — no need to write SQL.

# 🧠 3. Service Layer (service/)

Contains business logic that uses the repository layer.

Interface

```java
public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
}
```

# Implementation

```java
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
```

# 🌐 4. Controller Layer (controller/)

Handles HTTP requests and maps them to service calls.

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}
```

# 🗂️ Folder Structure

```
src/
└── main/
    └── java/
        └── com/
            └── cookbookwebsite/
                ├── controller/
                │   └── UserController.java
                ├── model/
                │   ├── User.java
                │   └── Role.java
                ├── repository/
                │   ├── UserRepository.java
                │   └── RoleRepository.java
                └── service/
                    ├── UserService.java
                    └── UserServiceImpl.java
```
