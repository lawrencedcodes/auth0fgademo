package com.lawrencedcodes.auth0fgademo;

import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientCheckRequest;
import dev.openfga.sdk.api.client.model.ClientCheckResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AuthorizationService {

    private final OpenFgaClient fgaClient;

    public AuthorizationService(OpenFgaClient fgaClient) {
        this.fgaClient = fgaClient;
    }

    /**
     * Checks if a user has a specific relation to an object.
     *
     * @param user     The user ID (e.g., "user:81684243-93ad-451d-855e-711573f348b9")
     * @param relation The relation (e.g., "viewer", "editor", "owner")
     * @param object   The object ID (e.g., "document:roadmap")
     * @return CompletableFuture<Boolean> True if authorized, false otherwise.
     */
    public CompletableFuture<Boolean> check(String user, String relation, String object) {
        ClientCheckRequest request = new ClientCheckRequest()
                .user(user)
                .relation(relation)
                ._object(object);

        try {
            return fgaClient.check(request)
                    .thenApply(ClientCheckResponse::getAllowed);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}
