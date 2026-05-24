package com.lawrencedcodes.auth0fgademo;

import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientCheckRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class RagChatController {

    private final OpenFgaClient fgaClient;
    private final VectorDatabase vectorDb;
    private final LlmService llmService;

    public RagChatController(OpenFgaClient fgaClient, VectorDatabase vectorDb, LlmService llmService) {
        this.fgaClient = fgaClient;
        this.vectorDb = vectorDb;
        this.llmService = llmService;
    }

    @PostMapping("/ask")
    public String handleChatPrompt(@RequestBody ChatRequest request) throws Exception {

        // 1. Get the human user's identity
        // Note: Hardcoded for frictionless local testing in this PoC.
        // In a production app, this would be extracted via @AuthenticationPrincipal Jwt jwt
        String userId = "auth0|123";

        // 2. The Blind Search
        // The database blindly returns semantically relevant documents
        List<Document> relevantDocs = vectorDb.search(request.prompt());
        List<Document> authorizedDocs = new ArrayList<>();

        /* * 3. The Interception & OpenFGA Filter
         * Note: For this PoC, we are iterating synchronously.
         * In a high-scale production environment, you would optimize this
         * by either using OpenFGA's BatchCheck API, concurrent CompletableFutures,
         * or the ListObjects API for pre-filtering.
         */
        for (Document doc : relevantDocs) {

            // Ask FGA: Does this specific user have permission to view this specific document?
            var checkRequest = new ClientCheckRequest()
                    .user("user:" + userId)
                    .relation("viewer")
                    ._object("document:" + doc.id());

            var response = fgaClient.check(checkRequest).get();

            // Only add the document to the payload if FGA explicitly allows it
            if (response.getAllowed() != null && response.getAllowed()) {
                authorizedDocs.add(doc);
            }
        }

        // 4. The Safe Generation
        // Pass the prompt and ONLY the mathematically authorized documents to the LLM
        return llmService.generateResponse(request.prompt(), authorizedDocs);
    }
}