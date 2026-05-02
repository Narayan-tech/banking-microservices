# Banking Microservices Project

## Overview
This project is a microservices-based banking system built using Spring Boot, where each service is independently deployed and communicates via REST APIs.

## Services

### 1. Account Service (Port: 8081)
- Create account
- Deposit money
- Withdraw money
- Delete account

### 2. Transaction Service (Port: 8082)
- Logs all transactions
- Stores transaction details like type, amount, source, target, timestamp

### 3. Fund Transfer Service (Port: 8083)
- Handles fund transfer between accounts
- Calls Account Service for withdraw and deposit
- Calls Transaction Service for logging
- Implements rollback mechanism to maintain data consistency if any step fails during transfer

---

## Tech Stack
- Java
- Spring Boot
- Spring Data JPA
- REST APIs
- MySQL
- Maven

---

## Features
- Microservices architecture
- Inter-service communication using RestTemplate
- Input validation using @Valid
- Global exception handling using @RestControllerAdvice
- Rollback mechanism for failed transactions
- Logging support
- Distributed transaction handling using compensating rollback logic

---

## API Endpoints

### Account Service
- POST /api/accounts
- POST /api/accounts/deposit
- POST /api/accounts/withdraw
- DELETE /api/accounts/{id}

### Fund Transfer Service
- POST /api/fund-transfer

### Transaction Service
- POST /api/transactions

---

## How to Run

1. Start Account Service (8081)
2. Start Transaction Service (8082)
3. Start Fund Transfer Service (8083)

Use Postman to test APIs.

---

## Sample Request (Fund Transfer)

```json
{
  "sourceAccountNumber": "ACC1001",
  "targetAccountNumber": "ACC1002",
  "amount": 100
}
```


## 👨‍💻 Author
Narayan  
LinkedIn: [Narayan](https://www.linkedin.com/in/narayan-gurusamy-9585b6214/)
