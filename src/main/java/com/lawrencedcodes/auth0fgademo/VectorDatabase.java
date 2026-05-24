package com.lawrencedcodes.auth0fgademo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VectorDatabase {

    public List<Document> search(String prompt) {
        // Mock: Blindly returning semantic matches, including restricted data.
        return List.of(
                new Document("roadmap", "roadmap.pdf", "Q3 Engineering Roadmap: Move to Spring Boot 3."),
                new Document("marketing", "marketing.pdf", "Campaign launch date is next Tuesday."),
                new Document("payroll", "payroll.pdf", "CEO Salary: $1,000,000. Highly Confidential.")
        );
    }
}