# Bank Challenge

This API service provides endpoints for managing clients, bank accounts, and transactions. It is separated into two microservices: `client-service` and `bank-account-service`. The application is containerized using Docker for easy deployment.

## Getting Started

### Prerequisites

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/)

### Running the Services

1. Navigate to the root directory of the project:
    ```bash
    cd bank-challenge
    ```

2. Start both microservices using Docker Compose:
    ```bash
    docker-compose up -d
    ```

3. Verify that the services are running by accessing:
    ```
    http://localhost
    ```

## API Endpoints

### Client Service

- **GET /client/api/clients** - Retrieve all clients.
- **GET /client/api/clients/{id}** - Retrieve a client by ID.
- **POST /client/api/clients** - Create a new client.
- **PUT /client/api/clients/{id}** - Update an existing client.
- **PATCH /client/api/clients/{id}** - Partially update an existing client.
- **DELETE /client/api/clients/{id}** - Delete a client by ID.

### Bank Account Service

- **GET /account/api/bank-accounts** - Retrieve all bank accounts. Optionally, you can filter by `client` using a request parameter, e.g., `GET /account/api/bank-accounts?client={clientId}`.
- **GET /account/api/bank-accounts/{id}** - Retrieve a bank account by ID.
- **POST /account/api/bank-accounts** - Create a new bank account.
- **PUT /account/api/bank-accounts/{id}** - Update an existing bank account.
- **PATCH /account/api/bank-accounts/{id}** - Partially update an bank account client.
- **DELETE /account/api/bank-accounts/{id}** - Delete a bank account by ID.

### Transactions

- **GET /account/api/transactions** - Retrieve all transactions.
- **GET /account/api/transactions/{id}** - Retrieve a transaction by ID.
- **POST /account/api/transactions** - Create a new transaction.
- **PUT /account/api/transactions/{id}** - Update an existing transaction.
- **PATCH /account/api/transactions/{id}** - Partially update an existing transaction.
- **DELETE /account/api/transactions/{id}** - Delete a transaction by ID.

### Reports

- **GET /account/api/transactions/report?client={clientId}&startDate={startDate}&endDate={endDate}** - Retrieve all transactions filtering by clientId between two dates.

## Postman Collection

A Postman collection is provided to help you test the API endpoints. You can import the `postman.json` file into Postman to get started.

1. Open Postman.
2. Click on `Import` in the top left corner.
3. Select the `postman.json` file from the repository.
4. Start testing the endpoints.
