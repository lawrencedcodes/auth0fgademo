package com.lawrencedcodes.auth0fgademo;

import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.configuration.ClientConfiguration;
import dev.openfga.sdk.api.configuration.ClientCredentials;
import dev.openfga.sdk.api.configuration.Credentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class OpenFgaConfig {

    @Value("${fga.api-url}")
    private String apiUrl;

    @Value("${fga.store-id}")
    private String storeId;

    @Value("${fga.client-id}")
    private String clientId;

    @Value("${fga.client-secret}")
    private String clientSecret;

    @Bean
    public OpenFgaClient fgaClient() throws Exception {
        var config = new ClientConfiguration()
                .apiUrl(apiUrl)
                .storeId(storeId)
                .credentials(new Credentials(
                        new ClientCredentials()
                                .apiTokenIssuer("auth.fga.dev")
                                .apiAudience(apiUrl + "/")
                                .clientId(clientId)
                                .clientSecret(clientSecret)
                ));

        return new OpenFgaClient(config);
    }
}
