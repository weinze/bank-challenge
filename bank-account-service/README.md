# Bank Account Service

The Bank Account Service provides endpoints for managing bank accounts and transactions.

## Getting Started

### Running the Service

1. Ensure the `client-service` is running. Follow the instructions in the `client-service` directory to start it.

2. Navigate to the `bank-account-service` directory:
    ```bash
    cd bank-account-service
    ```

3. Start the service using Docker Compose:
    ```bash
    docker-compose up -d
    ```

4. Verify that the bank account service is running by accessing:
    ```
    http://localhost:8082
    ```

> **Note:** The `bank-account-service` connects to the `client-service` for client-related operations. Ensure that the `client-service` is running for the `bank-account-service` to function properly.

## API Endpoints

### Bank Accounts

- **GET /api/bank-accounts** - Retrieve all bank accounts.
- **GET /api/bank-accounts/{id}** - Retrieve a bank account by ID.
- **POST /api/bank-accounts** - Create a new bank account.
- **PUT /api/bank-accounts/{id}** - Update an existing bank account.
- **PATCH /api/bank-accounts/{id}** - Partially update an bank account client.
- **DELETE /api/bank-accounts/{id}** - Delete a bank account by ID.

### Transactions

- **GET /api/transactions** - Retrieve all transactions.
- **GET /api/transactions/{id}** - Retrieve a transaction by ID.
- **POST /api/transactions** - Create a new transaction.
- **PUT /api/transactions/{id}** - Update an existing transaction.
- **PATCH /api/transactions/{id}** - Partially update an existing transaction.
- **DELETE /api/transactions/{id}** - Delete a transaction by ID.
