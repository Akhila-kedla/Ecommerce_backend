# Ecommerce_backend

# ✅ Ecommerce Backend Application

A feature-rich **Spring Boot–based eCommerce backend** providing core functionality for users, authentication, product catalog management, shopping cart, and order processing.
Supports secure access, role-based authorization, and scalable architecture.

---

## 🚀 Features

✅ User Registration & Login
✅ Role-based Access (ADMIN / CUSTOMER)
✅ Product CRUD (Create, Read, Update, Delete)
✅ Search by Category
✅ Cart Management
✅ Order Placement (Checkout)
✅ Database Persistence using MySQL
✅ Basic Authentication
✅ Tested with JUnit & Mockito
✅ REST API Design

---

## 🏗️ Tech Stack

| Component  | Technology              |
| ---------- | ----------------------- |
| Backend    | Spring Boot 3           |
| Security   | Spring Security (Basic) |
| Database   | MySQL                   |
| ORM        | Hibernate/JPA           |
| Testing    | JUnit 5, Mockito        |
| API Tool   | Postman                 |
| Build Tool | Maven                   |

---

## 📂 Project Structure

```
ecommerce-backend
│── src/main/java/com/ecommerce
│   ├── controller
│   ├── service
│   ├── entity
│   ├── repository
│   ├── dto
│   ├── security
│   └── exception
│
│── src/test/java/com/ecommerce
│
│── resources
│   ├── application.properties
│
│── pom.xml
│── README.md
```

---

## ⚙️ Installation & Setup

### ✅ Prerequisites

Ensure you have installed:

* Java 17+
* Maven 3+
* MySQL

### ✅ Clone repository

```bash
git clone https://github.com/<your-username>/<repo-name>.git
cd <repo-name>
```

### ✅ Configure Database

Update `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### ✅ Install Dependencies

```bash
mvn clean install
```

### ✅ Run Application

```
mvn spring-boot:run
```

Server starts at:

```
http://localhost:8080
```

---

## 🧪 Running Tests

Run all unit tests:

```bash
mvn test
```

---

## 📘 API Documentation

All APIs follow REST standards.

### ✅ Authentication

| Method | Endpoint             | Description       |
| ------ | -------------------- | ----------------- |
| POST   | `/api/auth/register` | Register new user |
| POST   | `/api/auth/login`    | Login             |

### Sample Register

```json
{
  "name": "Akhila Kedla",
  "email": "akhila@example.com",
  "password": "123456",
  "role": "ADMIN"
}
```

---

### ✅ Product APIs

| Method | Endpoint                            | Description             |
| ------ | ----------------------------------- | ----------------------- |
| POST   | `/api/products`                     | Add product (Admin)     |
| GET    | `/api/products`                     | Get all products        |
| GET    | `/api/products/{id}`                | Get product by ID       |
| PUT    | `/api/products/{id}`                | Update product          |
| DELETE | `/api/products/{id}`                | Delete product          |
| GET    | `/api/products/category/{category}` | Get product by category |

---

### ✅ Cart APIs

| Method | Endpoint                                           | Description      |
| ------ | -------------------------------------------------- | ---------------- |
| GET    | `/api/cart/{userId}`                               | Get user cart    |
| POST   | `/api/cart/{userId}/add/{productId}?quantity=x`    | Add product      |
| PUT    | `/api/cart/{userId}/update/{productId}?quantity=x` | Update quantity  |
| DELETE | `/api/cart/{userId}/remove/{productId}`            | Remove from cart |

---

### ✅ Order APIs

| Method | Endpoint                                        | Description         |
| ------ | ----------------------------------------------- | ------------------- |
| POST   | `/api/orders/checkout/{userId}?paymentMode=UPI` | Place order         |
| GET    | `/api/orders/user/{userId}`                     | Get user orders     |
| PUT    | `/api/orders/{orderId}/status?status=DELIVERED` | Update order status |

---

## 🗄️ Database Schema

### User Table

| Field    | Type    |
| -------- | ------- |
| id       | bigint  |
| name     | varchar |
| email    | varchar |
| password | varchar |
| role     | enum    |

### Product Table

| Field       | Type    |
| ----------- | ------- |
| id          | bigint  |
| name        | varchar |
| description | varchar |
| price       | double  |
| stock       | int     |
| category    | varchar |

### Cart / CartItem

Stores products user added before checkout.

### Orders / OrderItems

Stores completed order details.

---

## 📦 Postman Collection

✅ Provided
You can import it from:

```
postman_collection.json
```


## 🔐 Security

Basic Authentication enforced:

* Admin: can manage products
* User: can place orders & manage cart

---

## ❗ Error Handling

Centralized exception handling via:

```
@GlobalExceptionHandler
```

Returns structured JSON:

```json
{
  "message": "Resource not found",
  "timestamp": "2025-01-01",
  "path": "/api/products/10"
}
```

---

## 📝 Logging

Uses `Logger` for:
✔ Errors
✔ Checkout
✔ Stock validation

---

## ✅ Future Enhancements

* JWT-based authentication
* Payment gateway integration
* Product review system
* Inventory auto alerts
* Email notifications

---

## 🧑‍💻 Author

**Akhila Kedla**
📧 akhilakedla25@gmail.com

---

