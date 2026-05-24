# Auth0 FGA Demo

This project demonstrates how to integrate [Auth0 Fine-Grained Authorization (FGA)](https://fga.dev) into a Spring Boot application.

## Prerequisites

- An Auth0 FGA account and a Store ID.
- API credentials (Client ID and Client Secret).

## Configuration

Update `src/main/resources/application.properties` with your FGA credentials:

```properties
fga.api-url=https://api.us1.fga.dev
fga.store-id=YOUR_STORE_ID
fga.client-id=YOUR_CLIENT_ID
fga.client-secret=YOUR_CLIENT_SECRET
```

Alternatively, you can set these as environment variables:
- `FGA_API_URL`
- `FGA_STORE_ID`
- `FGA_CLIENT_ID`
- `FGA_CLIENT_SECRET`

## FGA Model

A sample FGA model is provided in `model.fga`. You can upload this to your FGA store using the [FGA Dashboard](https://dashboard.fga.dev) or the FGA CLI.

## Usage

The application exposes a REST API to check permissions:

`GET /api/authz/check?user=user:jon&relation=viewer&object=document:roadmap`

Response: `true` or `false`

## Implementation Details

- `OpenFgaConfig.java`: Configures the `OpenFgaClient` bean.
- `AuthorizationService.java`: Encapsulates the FGA check logic.
- `FgaController.java`: Provides a REST endpoint for permission checks.
