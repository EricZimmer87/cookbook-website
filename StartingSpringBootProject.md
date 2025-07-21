# 🍳 CookbookWebsite - Spring Boot Setup (Maven Edition)

## 📦 Project Setup with Spring Initializr

Create your project at: [https://start.spring.io](https://start.spring.io)

### Project Settings:

| Field        | Value                          |
| ------------ | ------------------------------ |
| Project      | **Maven**                      |
| Language     | **Java**                       |
| Spring Boot  | **3.2.5** (or latest stable)   |
| Group        | `com.cookbookwebsite`          |
| Artifact     | `cookbookwebsite`              |
| Name         | `cookbookwebsite`              |
| Description  | `Demo project for Spring Boot` |
| Package name | `com.cookbookwebsite`          |
| Packaging    | `Jar`                          |
| Java         | `17` or `21`                   |

### Dependencies to Add:

- Spring Web
- Spring Data JPA
- MySQL Driver
- Lombok _(optional but recommended)_

---

## 🗂️ Initial Steps After Downloading

1. Click **Generate** to download the `.zip` file.
2. Extract the zip file.
3. Open the folder in **IntelliJ IDEA**.
4. Wait for IntelliJ to finish indexing the Maven project.

---

## ⚙️ Configure MySQL Connection

Edit the file:

```
src/main/resources/application.properties
```

Add the following:

```properties
spring.application.name=CookbookWebsite

spring.datasource.url=jdbc:mysql://localhost:3306/CookbookWebsite
spring.datasource.username=root
spring.datasource.password=root1234

spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

## 🚀 Run the Project

From IntelliJ:
• Right-click CookbookWebsiteApplication.java
• Click Run ‘CookbookWebsiteApplication’

Or from terminal:

```
./mvnw spring-boot:run
```

## Next Steps

    •	Create your model/, repository/, service/, and controller/ packages
    •	Add your saved Java files
    •	Update package declarations to match com.cookbookwebsite
    •	Start building endpoints and test them with Postman!
