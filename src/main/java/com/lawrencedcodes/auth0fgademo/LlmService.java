package com.lawrencedcodes.auth0fgademo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LlmService {

    public String generateResponse(String prompt, List<Document> authorizedDocs) {

        // Safety check: if FGA stripped everything out
        if (authorizedDocs.isEmpty()) {
            return "I do not have access to any relevant documents to answer your prompt: '" + prompt + "'";
        }

        // Extract just the filenames to prove which documents made it to the LLM
        String safeFilenames = authorizedDocs.stream()
                .map(Document::filename)
                .collect(Collectors.joining(", "));

        // Format the mock LLM response
        return String.format(
                "Prompt received: '%s'\n\n" +
                        "Based on your permissions, I was only granted context from the following files: [%s].\n\n" +
                        "AI Summary: The company is launching a campaign next Tuesday and migrating to Spring Boot 3.",
                prompt, safeFilenames
        );
    }
}
