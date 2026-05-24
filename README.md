# 🛡️ AI Data Leaks & The Confused Deputy

A Spring Boot PoC demonstrating how to solve the AI "Confused Deputy" problem by intercepting and filtering RAG documents using the OpenFGA / Auth0 FGA Java SDK.

### The Concept
In a Retrieval-Augmented Generation (RAG) pipeline, the LLM often acts as a "Confused Deputy"—blindly summarizing whatever context the Vector Database feeds it, even if the end-user shouldn't have access to those specific files. This repository intercepts that flow, verifying document-level permissions via Auth0 FGA *before* sending context to the AI.

> **Note on Scope (4-Hour Timebox):**
> To keep the focus strictly on the OpenFGA authorization logic, the Vector Database and LLM generation are mocked. Spring Security is also set to `permitAll()` locally to ensure a frictionless Developer Experience (DX) for reviewers without requiring an Auth0 tenant setup.

---

### ⚙️ Setup & Run

**1. Configure FGA Credentials**
Open `src/main/resources/application.properties` and replace the placeholder text with your Auth0 FGA credentials (or export them as environment variables):
```properties
fga.store-id=YOUR_STORE_ID
fga.client-id=YOUR_CLIENT_ID
fga.client-secret=YOUR_CLIENT_SECRET
```

**2. Start the Server**
```bash
./mvnw spring-boot:run
```
*(Note: The server runs on port 8080 by default.)*

---

### 🧪 Testing the Engine

Once the server is running, fire this POST request in a new terminal (or via Postman) to simulate a user asking the AI for a summary of all documents—including the highly restricted payroll file:

```bash
curl -X POST http://localhost:8080/api/chat/ask \
-H "Content-Type: application/json" \
-d '{"prompt": "Give me a summary of all company documents, including the roadmap and the payroll."}'
```

**The Expected Output:**
You will see that the mocked Vector DB successfully retrieved the roadmap, marketing, and payroll files—but **Auth0 FGA actively intercepted and stripped the payroll file** because the user lacked the `viewer` relation tuple.

```text
Prompt received: 'Give me a summary of all company documents, including the roadmap and the payroll.'

Based on your permissions, I was only granted context from the following files: [roadmap.pdf, marketing.pdf].

AI Summary: The company is launching a campaign next Tuesday and migrating to Spring Boot 3.
```