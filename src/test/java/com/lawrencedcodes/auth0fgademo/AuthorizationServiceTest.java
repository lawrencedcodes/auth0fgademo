package com.lawrencedcodes.auth0fgademo;

import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientCheckResponse;
import dev.openfga.sdk.api.client.model.ClientCheckRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthorizationServiceTest {

    @Test
    void testCheckAllowed() throws Exception {
        OpenFgaClient mockClient = Mockito.mock(OpenFgaClient.class);
        ClientCheckResponse mockResponse = Mockito.mock(ClientCheckResponse.class);
        
        when(mockResponse.getAllowed()).thenReturn(true);
        when(mockClient.check(any(ClientCheckRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));

        AuthorizationService service = new AuthorizationService(mockClient);
        Boolean result = service.check("user:jon", "viewer", "document:doc1").get();

        assertTrue(result);
    }
}
