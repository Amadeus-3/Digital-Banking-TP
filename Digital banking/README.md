# Digital Banking Application

A comprehensive Spring Boot backend application implementing a digital banking system based on Professor Mohamed Youssfi's architecture.

## Architecture Overview

This application follows a layered architecture pattern:

- **DAO Layer**: JPA Entities and Repositories
- **Service Layer**: Business Logic with DTOs and Mappers
- **Web Layer**: REST API Controllers

## Technical Stack

- **Java**: 17+
- **Spring Boot**: 3.2.0
- **Spring Data JPA**: Database access layer
- **H2 Database**: In-memory database (configurable to MySQL)
- **Lombok**: Boilerplate code reduction
- **Maven**: Dependency management

## Project Structure

```
src/main/java/com/digitalbanking/
├── DigitalBankingApplication.java   # Main application with data initialization
├── enums/
│   ├── AccountStatus.java           # CREATED, ACTIVATED, SUSPENDED
│   └── OperationType.java           # DEBIT, CREDIT
├── entities/
│   ├── Customer.java                # Customer entity
│   ├── BankAccount.java             # Abstract bank account (Single Table Inheritance)
│   ├── CurrentAccount.java          # Current account with overdraft
│   ├── SavingAccount.java           # Saving account with interest rate
│   └── AccountOperation.java        # Transaction operations
├── repositories/
│   ├── CustomerRepository.java
│   ├── BankAccountRepository.java
│   └── AccountOperationRepository.java
├── dtos/
│   ├── CustomerDTO.java
│   ├── BankAccountDTO.java
│   ├── CurrentAccountDTO.java
│   ├── SavingAccountDTO.java
│   ├── AccountOperationDTO.java
│   ├── DebitDTO.java
│   ├── CreditDTO.java
│   ├── TransferRequestDTO.java
│   └── AccountHistoryDTO.java
├── exceptions/
│   ├── CustomerNotFoundException.java
│   ├── BankAccountNotFoundException.java
│   └── BalanceNotSufficientException.java
├── mappers/
│   └── BankAccountMapperImpl.java   # Entity to DTO mapper
├── services/
│   ├── BankAccountService.java      # Service interface
│   └── BankAccountServiceImpl.java  # Service implementation
└── web/
    ├── CustomerRestController.java  # Customer REST endpoints
    └── BankAccountRestController.java # Account REST endpoints
```

## Key Features

### JPA Inheritance Strategy

The application uses **Single Table Inheritance** for bank accounts:

- All account types are stored in a single table
- `TYPE` discriminator column distinguishes account types
- `CA` for Current Accounts
- `SA` for Saving Accounts

### REST API Endpoints

#### Customer Management

- `GET /customers` - List all customers
- `GET /customers/{id}` - Get customer by ID
- `GET /customers/search?keyword={keyword}` - Search customers
- `POST /customers` - Create new customer
- `PUT /customers/{id}` - Update customer
- `DELETE /customers/{id}` - Delete customer

#### Account Management

- `GET /accounts` - List all accounts
- `GET /accounts/{accountId}` - Get account details
- `GET /accounts/{accountId}/operations` - Get account operations
- `GET /accounts/{accountId}/pageOperations?page={page}&size={size}` - Get paginated operations

#### Transactions

- `POST /accounts/debit` - Debit from account
  ```json
  {
    "accountId": "xxx",
    "amount": 1000,
    "description": "Payment"
  }
  ```

- `POST /accounts/credit` - Credit to account
  ```json
  {
    "accountId": "xxx",
    "amount": 1000,
    "description": "Deposit"
  }
  ```

- `POST /accounts/transfer` - Transfer between accounts
  ```json
  {
    "sourceAccountId": "xxx",
    "destinationAccountId": "yyy",
    "amount": 1000,
    "description": "Transfer"
  }
  ```

## Running the Application

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Steps

1. **Clone or download the project**

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - API: http://localhost:8080
   - H2 Console: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:digital_banking_db`
     - Username: `sa`
     - Password: (leave empty)

## Data Initialization

The application automatically seeds data on startup:

- 3 Customers: Hassan, Imane, Mohamed
- 2 Accounts per customer (1 Current, 1 Saving)
- 5 random operations per account (mix of debits and credits)

## Configuration

### Using MySQL Instead of H2

Uncomment the MySQL configuration in [application.properties](src/main/resources/application.properties:17-20):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/digital_banking_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

## Exception Handling

The application implements custom checked exceptions:

- `CustomerNotFoundException` - When customer is not found
- `BankAccountNotFoundException` - When account is not found
- `BalanceNotSufficientException` - When account balance is insufficient for debit

## Testing the API

You can test the API using:

- **Postman**: Import the endpoints and test
- **cURL**: Command line testing
- **H2 Console**: Direct database access

### Example cURL Commands

```bash
# Get all customers
curl http://localhost:8080/customers

# Create a customer
curl -X POST http://localhost:8080/customers \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com"}'

# Debit from account
curl -X POST http://localhost:8080/accounts/debit \
  -H "Content-Type: application/json" \
  -d '{"accountId":"xxx","amount":100,"description":"Withdrawal"}'
```

## Author

Based on Professor Mohamed Youssfi's Digital Banking architecture tutorial series.

## License

This project is for educational purposes.
