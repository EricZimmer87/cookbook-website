# jakarta.persistence Library

The jakarta.persistence package provides the annotations and interfaces that Spring Boot (or any JPA implementation) uses, such as:

| Annotation / Interface     | Purpose                                                   |
| -------------------------- | --------------------------------------------------------- |
| `@Entity`                  | Marks a class as a persistent entity (maps to a DB table) |
| `@Id`                      | Marks a field as the primary key                          |
| `@GeneratedValue`          | Defines how primary key values are generated              |
| `@Column`                  | Customizes the column mapping                             |
| `@ManyToOne`, `@OneToMany` | Defines relationships between tables                      |
| `EntityManager`, `Query`   | Provides low-level access to DB operations                |

# Model

## User Entity Overview

### 📦 Package

`com.cookbookwebsite.model`
