# Client Service

The Client Service provides endpoints for managing client information.

## Getting Started

### Running the Service

1. Navigate to the `client-service` directory:
    ```bash
    cd client-service
    ```

2. Start the service using Docker Compose:
    ```bash
    docker-compose up -d
    ```

3. Verify that the client service is running by accessing:
    ```
    http://localhost:8081
    ```

## API Endpoints

- **GET /api/clients** - Retrieve all clients.
- **GET /api/clients/{id}** - Retrieve a client by ID.
- **POST /api/clients** - Create a new client.
- **PUT /api/clients/{id}** - Update an existing client.
- **PATCH /api/clients/{id}** - Partially update an existing client.
- **DELETE /api/clients/{id}** - Delete a client by ID.
