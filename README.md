Project: Gemini Service (inside FirstScriptAndroidAppium)
===============================================

Overview
--------
This repository (or submodule) includes a small Java helper that calls Google's Generative Language API (Gemini) using OkHttp. The current implementation lives at:

- `src/test/java/aiselfhealing/GeminiService.java`

The helper builds a JSON payload from a text prompt and POSTs it to the `:generateContent` endpoint. The method currently returns the raw JSON response string.

Goals of this README
--------------------
- Explain how to build and run the project locally on macOS (zsh).
- Describe the high-level architecture and sequence flow for the Gemini call.
- Provide a recommended set of improvements to make the service production-ready.

Prerequisites
-------------
- Java 21 (project uses source/target 21 as defined in `pom.xml`).
- Maven 3.x.
- Network access to Google Generative Language API endpoints.
- A valid API key set as environment variable `GEMINI_API_KEY`.

Set environment variable (macOS, zsh):

```bash
export GEMINI_API_KEY="YOUR_API_KEY_HERE"
```

Build and test
--------------
From the project root:

```bash
# Build (single core thread, show errors)
mvn -e -T 1C clean package

# Run all tests
mvn test

# Run a specific test (if you add one for GeminiService)
mvn -Dtest=aiselfhealing.GeminiServiceTest test
```

Usage
-----
`GeminiService` exposes a single static method:

- `String askGemini(String prompt) throws Exception`

Example usage from a test or main method:

```java
String json = GeminiService.askGemini("Hello Gemini, say hello back.");
System.out.println(json);
```

Note: the current method prints the raw JSON response and returns it. The exact path to the generated text depends on the API response format and should be parsed rather than relying on raw strings.

Configuration
-------------
- `GEMINI_API_KEY` - API key used in the request URL query parameter.
- `modelName` and `apiVersion` - currently hard-coded inside `GeminiService.java` as `gemini-2.5-flash` and `v1beta`. You can change these or make them configurable via environment variables or a properties file.

Architecture
------------
Purpose: a minimal wrapper to call a Gemini model and return the response to caller code (tests or other application components).

Components
- GeminiService (class)
  - Builds request payload and headers.
  - Calls the Generative Language API with OkHttp.
  - Returns the response body as a JSON string.
- Caller (tests or app code)
  - Invokes `askGemini`, then parses and consumes the output.
- External: Google Generative Language API (Gemini)

Sequence
1. Caller calls `GeminiService.askGemini(prompt)`.
2. `GeminiService` reads `GEMINI_API_KEY` and builds the JSON body:
   - parts -> contents -> prompt text
3. `GeminiService` sends an HTTPS POST to:
   `https://generativelanguage.googleapis.com/{apiVersion}/models/{modelName}:generateContent?key={GEMINI_API_KEY}`
4. The API returns JSON which `GeminiService` prints and returns to the caller.

ASCII diagram

Caller (test/app)
    |
    | askGemini(prompt)
    v
 +------------------+
 | GeminiService    |
 | - build payload  |
 | - okHttp request |
 +------------------+
    |
    | HTTPS POST
    v
 +-----------------------------+
 | Google Generative Language  |
 | - model: gemini-*           |
 | - returns JSON response     |
 +-----------------------------+
    |
    v
 Caller receives JSON string

Design considerations and recommendations
-----------------------------------------
- Reuse OkHttpClient (it's thread-safe) instead of creating a new client for each request. This reduces connection overhead.
- Validate `GEMINI_API_KEY` at startup and fail fast with a clear error if missing.
- Use try-with-resources to close `Response` and `ResponseBody` safely.
- Check `response.isSuccessful()` and throw a descriptive IOException when the HTTP status is not 2xx.
- Make `modelName` and `apiVersion` configurable via environment variables or application properties.
- Add retries with exponential backoff for transient server errors (5xx) and rate-limit responses (429).
- Return a typed result object (POJO) that contains both raw JSON and an extracted `text` field; this is easier for callers and testing.
- For testing, inject a mockable HTTP client (pass OkHttpClient as a parameter or use a factory) so unit tests don't hit the real API.

Security & best practices
-------------------------
- Never commit `GEMINI_API_KEY` to source control.
- Prefer a secrets manager or CI secret store for production builds/tests.
- Sanitize/validate prompts if they originate from untrusted sources.
- Implement proper logging (not System.out), and ensure logs don't leak secrets.

Troubleshooting
---------------
- If you receive HTTP 401/403: verify your API key and permissions.
- If you receive HTTP 429: you're rate-limited — implement backoff and/or request quota increases.
- If you receive 5xx: treat as transient and retry with backoff.
- If the response body is empty or parsing fails: print the raw JSON (or response body string) and inspect the structure — the extraction path may change with API versions.

Suggested code improvements (example snippets)
--------------------------------------------
1) Validate API key and return helpful error:

```java
String apiKey = System.getenv("GEMINI_API_KEY");
if (apiKey == null || apiKey.isBlank()) {
    throw new IllegalStateException("GEMINI_API_KEY environment variable is required");
}
```

2) Use try-with-resources and check response code:

```java
try (Response response = client.newCall(request).execute()) {
    if (!response.isSuccessful()) {
        String body = response.body() != null ? response.body().string() : "<empty>";
        throw new IOException("Unexpected HTTP code " + response.code() + ": " + body);
    }
    String bodyStr = response.body() == null ? "" : response.body().string();
    return bodyStr;
}
```

3) Extract generated text safely (example; adjust to actual response shape):

```java
JSONObject obj = new JSONObject(bodyStr);
// adapt extraction if response format differs
if (obj.has("candidates")) {
    return obj.getJSONArray("candidates")
              .getJSONObject(0)
              .getJSONObject("content")
              .getJSONArray("parts")
              .getJSONObject(0)
              .getString("text");
}
return bodyStr; // fallback
```

4) Make model and version configurable via environment variables:

```java
String modelName = System.getenv().getOrDefault("GEMINI_MODEL", "gemini-2.5-flash");
String apiVersion = System.getenv().getOrDefault("GEMINI_API_VERSION", "v1beta");
```

Testing recommendations
-----------------------
- Unit tests: mock OkHttp's Call/Response to simulate success/failure and validate JSON body construction.
- Integration test: gated behind presence of `GEMINI_API_KEY` to avoid accidental calls in CI. Use a small prompt and assert expected keys exist in the response.

Where to go next
-----------------
- I can implement a safer `GeminiService` with:
  - injected OkHttpClient (for testability),
  - proper response handling and extraction into a POJO,
  - configuration via environment variables,
  - a unit test that uses a mocked HTTP client.

If you'd like, I will implement these changes now and add a unit test and an integration test template.

Credits
-------
This README was created to document and improve the existing `GeminiService` helper in this repository.
