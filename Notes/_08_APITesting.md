# API Testing — Complete Reference Guide

---

## Table of Contents
1. [HTTP & REST Fundamentals](#1-http--rest-fundamentals)
2. [HTTP Methods](#2-http-methods)
3. [HTTP Status Codes](#3-http-status-codes)
4. [Request & Response Structure](#4-request--response-structure)
5. [Authentication & Authorization](#5-authentication--authorization)
6. [REST Assured — Setup & Basics](#6-rest-assured--setup--basics)
7. [REST Assured — All Operations](#7-rest-assured--all-operations)
8. [JSON Path & XML Path](#8-json-path--xml-path)
9. [Schema Validation](#9-schema-validation)
10. [Request Specification & Response Specification](#10-request-specification--response-specification)
11. [Postman Concepts](#11-postman-concepts)
12. [API Testing Strategy](#12-api-testing-strategy)
13. [Interview Q&A](#13-interview-qa)

---

## 1. HTTP & REST Fundamentals

### REST Constraints
1. **Client-Server**: Separation of UI and data storage concerns
2. **Stateless**: Each request contains all information needed — server stores no session
3. **Cacheable**: Responses must define themselves as cacheable or non-cacheable
4. **Uniform Interface**: Consistent resource identification via URIs
5. **Layered System**: Client can't tell if it's connected directly to server or intermediary
6. **Code on Demand** (optional): Server can send executable code

### REST vs SOAP

| | REST | SOAP |
|--|------|------|
| Protocol | HTTP | HTTP, SMTP, TCP |
| Data format | JSON, XML, plain text | XML only |
| Contract | No strict contract (OpenAPI/Swagger optional) | WSDL required |
| Performance | Fast, lightweight | Heavy due to XML envelope |
| State | Stateless | Can be stateful |
| Security | HTTPS, OAuth | WS-Security, built-in |
| Use case | Web/mobile APIs | Enterprise, banking, legacy |

### URI Structure

```
https://api.saucedemo.com/v1/users/123/orders?status=active&limit=10#section

\___/   \________________/ \_/ \___/ \_______/ \____________________/ \_____/
scheme       host          path    resource  path param   query string   fragment
```

---

## 2. HTTP Methods

| Method | Purpose | Has Body? | Idempotent? | Safe? |
|--------|---------|-----------|-------------|-------|
| `GET` | Retrieve resource | No | ✅ | ✅ |
| `POST` | Create new resource | Yes | ❌ | ❌ |
| `PUT` | Replace resource entirely | Yes | ✅ | ❌ |
| `PATCH` | Partial update | Yes | ❌ (usually) | ❌ |
| `DELETE` | Remove resource | No | ✅ | ❌ |
| `HEAD` | Like GET but no body — check if resource exists | No | ✅ | ✅ |
| `OPTIONS` | What methods does this endpoint support? | No | ✅ | ✅ |

**Idempotent**: Same result no matter how many times you call it.
**Safe**: No side effects — doesn't modify server state.

```
POST   /api/users              → Create user
GET    /api/users              → Get all users
GET    /api/users/123          → Get user 123
PUT    /api/users/123          → Replace user 123 completely
PATCH  /api/users/123          → Update user 123 partially
DELETE /api/users/123          → Delete user 123
GET    /api/users/123/orders   → Get orders for user 123
```

---

## 3. HTTP Status Codes

### 2xx — Success

| Code | Name | When |
|------|------|------|
| 200 | OK | Successful GET, PUT, PATCH, DELETE |
| 201 | Created | Successful POST (resource created) |
| 202 | Accepted | Async processing started |
| 204 | No Content | Success, no response body (DELETE, PUT) |

### 3xx — Redirection

| Code | Name | When |
|------|------|------|
| 301 | Moved Permanently | URL changed permanently |
| 302 | Found | Temporary redirect |
| 304 | Not Modified | Cached version is fresh |

### 4xx — Client Error

| Code | Name | When |
|------|------|------|
| 400 | Bad Request | Malformed request, validation error |
| 401 | Unauthorized | Not authenticated (no/invalid token) |
| 403 | Forbidden | Authenticated but not authorized |
| 404 | Not Found | Resource doesn't exist |
| 405 | Method Not Allowed | HTTP method not supported |
| 409 | Conflict | Duplicate resource, version conflict |
| 422 | Unprocessable Entity | Semantic validation failed |
| 429 | Too Many Requests | Rate limit exceeded |

### 5xx — Server Error

| Code | Name | When |
|------|------|------|
| 500 | Internal Server Error | Generic server error |
| 502 | Bad Gateway | Invalid response from upstream |
| 503 | Service Unavailable | Server overloaded or maintenance |
| 504 | Gateway Timeout | Upstream server timeout |

---

## 4. Request & Response Structure

### HTTP Request

```
POST /api/v1/login HTTP/1.1
Host: api.saucedemo.com
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Accept: application/json
User-Agent: RestAssured/5.0

{
  "username": "standard_user",
  "password": "secret_sauce"
}
```

**Parts:**
- **Request Line**: method + URI + HTTP version
- **Headers**: metadata (Content-Type, Authorization, Accept, etc.)
- **Body**: data (for POST/PUT/PATCH)

### HTTP Response

```
HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Content-Length: 185
X-RateLimit-Remaining: 99
Cache-Control: no-cache

{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "user": {
    "id": 1,
    "username": "standard_user",
    "role": "customer"
  }
}
```

### Important Headers

| Header | Direction | Purpose | Example |
|--------|-----------|---------|---------|
| `Content-Type` | Request/Response | Body format | `application/json` |
| `Accept` | Request | Expected response format | `application/json` |
| `Authorization` | Request | Auth credentials | `Bearer <token>` |
| `X-API-Key` | Request | API key auth | `X-API-Key: abc123` |
| `Cache-Control` | Response | Caching directive | `no-cache` |
| `Location` | Response | URL of created resource | `/api/users/456` |
| `X-RateLimit-*` | Response | Rate limit info | `X-RateLimit-Limit: 100` |
| `ETag` | Response | Resource version fingerprint | `"abc123"` |
| `If-None-Match` | Request | Conditional GET (with ETag) | `"abc123"` |

---

## 5. Authentication & Authorization

### Basic Auth

```
Authorization: Basic base64(username:password)
Authorization: Basic c3RhbmRhcmRfdXNlcjpzZWNyZXRfc2F1Y2U=
```

### Bearer Token (JWT)

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
                      eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4ifQ.
                      SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

**JWT Structure**: `header.payload.signature` (base64 encoded)
- Header: algorithm (`HS256`)
- Payload: claims (`sub`, `name`, `exp`, `iat`)
- Signature: verifies integrity

### OAuth 2.0 Flow

```
1. Client redirects user to Authorization Server
2. User logs in, grants permission
3. Auth Server returns Authorization Code
4. Client exchanges code for Access Token
5. Client uses Access Token in API calls
6. Access Token expires → use Refresh Token to get new one
```

### API Key

```
X-API-Key: your-api-key-here
# or as query param:
GET /api/products?api_key=your-api-key-here
```

---

## 6. REST Assured — Setup & Basics

### Maven Dependency

```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.3.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>json-path</artifactId>
    <version>5.3.0</version>
</dependency>
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>json-schema-validator</artifactId>
    <version>5.3.0</version>
</dependency>
```

### Static Imports (put at top of test class)

```java
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
```

### Base Configuration

```java
// In @BeforeClass or @BeforeSuite
RestAssured.baseURI = "https://api.example.com";
RestAssured.port = 443;
RestAssured.basePath = "/v1";
RestAssured.useRelaxedHTTPSValidation();  // ignore SSL issues

// Or use RequestSpecification
RequestSpecification requestSpec = new RequestSpecBuilder()
    .setBaseUri("https://api.example.com")
    .setBasePath("/v1")
    .addHeader("Authorization", "Bearer " + token)
    .addHeader("Content-Type", "application/json")
    .setRelaxedHTTPSValidation()
    .build();
RestAssured.requestSpecification = requestSpec;
```

---

## 7. REST Assured — All Operations

### GET Request

```java
// Simple GET
Response response = given()
    .header("Authorization", "Bearer " + token)
    .queryParam("status", "active")
    .queryParam("limit", 10)
.when()
    .get("/users")
.then()
    .statusCode(200)
    .body("users.size()", greaterThan(0))
    .body("users[0].username", notNullValue())
    .extract().response();

// Extract values
String responseBody   = response.getBody().asString();
int statusCode        = response.getStatusCode();
String contentType    = response.getContentType();
long responseTime     = response.getTime();
String firstUsername  = response.jsonPath().getString("users[0].username");
List<String> names    = response.jsonPath().getList("users.username");
```

### POST Request

```java
// With JSON body as String
String requestBody = "{ \"username\": \"new_user\", \"password\": \"pass123\", \"role\": \"admin\" }";

given()
    .header("Content-Type", "application/json")
    .body(requestBody)
.when()
    .post("/users")
.then()
    .statusCode(201)
    .header("Location", containsString("/users/"))
    .body("id", notNullValue())
    .body("username", equalTo("new_user"));

// With POJO (automatically serialized to JSON if Jackson/Gson is on classpath)
User newUser = new User("new_user", "pass123", "admin");

Response response = given()
    .contentType(ContentType.JSON)
    .body(newUser)
.when()
    .post("/users")
.then()
    .statusCode(201)
    .extract().response();

User createdUser = response.as(User.class);   // deserialize response to POJO
```

### PUT Request

```java
given()
    .contentType(ContentType.JSON)
    .pathParam("id", 123)
    .body("{ \"username\": \"updated_user\", \"role\": \"customer\" }")
.when()
    .put("/users/{id}")
.then()
    .statusCode(200)
    .body("username", equalTo("updated_user"));
```

### PATCH Request

```java
given()
    .contentType(ContentType.JSON)
    .pathParam("id", 123)
    .body("{ \"role\": \"admin\" }")      // only update role
.when()
    .patch("/users/{id}")
.then()
    .statusCode(200)
    .body("role", equalTo("admin"));
```

### DELETE Request

```java
given()
    .header("Authorization", "Bearer " + token)
    .pathParam("id", 123)
.when()
    .delete("/users/{id}")
.then()
    .statusCode(204);      // No Content — typical for DELETE

// Verify deletion
given()
    .pathParam("id", 123)
.when()
    .get("/users/{id}")
.then()
    .statusCode(404);      // Should be gone
```

### Validations

```java
.then()
    // Status
    .statusCode(200)
    .statusCode(anyOf(is(200), is(201)))

    // Headers
    .header("Content-Type", containsString("application/json"))
    .headers("X-Custom", notNullValue(), "Cache-Control", equalTo("no-cache"))

    // Body — Hamcrest matchers
    .body("name", equalTo("John"))
    .body("age", greaterThan(18))
    .body("roles", hasItems("admin", "user"))
    .body("address.city", equalTo("New York"))
    .body("items.size()", equalTo(3))
    .body("items[0].price", lessThan(100.0f))
    .body("items.name", containsInAnyOrder("item1", "item2", "item3"))
    .body("$", hasKey("token"))                 // root level
    .body(not(containsString("password")))       // body as string

    // Response time
    .time(lessThan(2000L))                       // under 2 seconds
```

### Authentication in REST Assured

```java
// Basic Auth
given().auth().basic("username", "password")

// Bearer Token
given().header("Authorization", "Bearer " + token)

// OAuth 2.0
given().auth().oauth2(accessToken)

// API Key as header
given().header("X-API-Key", apiKey)

// API Key as query param
given().queryParam("api_key", apiKey)
```

---

## 8. JSON Path & XML Path

### JSON Path Examples

```java
// Sample response:
// {
//   "user": { "id": 1, "name": "John", "roles": ["admin","user"] },
//   "orders": [
//     { "id": 101, "product": "Chrome", "price": 50.0 },
//     { "id": 102, "product": "Firefox", "price": 30.0 }
//   ]
// }

JsonPath jp = response.jsonPath();

jp.getString("user.name")                  // "John"
jp.getInt("user.id")                       // 1
jp.getList("user.roles")                   // ["admin","user"]
jp.getList("user.roles", String.class)     // typed list
jp.getString("orders[0].product")          // "Chrome"
jp.getFloat("orders[1].price")             // 30.0
jp.getList("orders.product")               // ["Chrome","Firefox"] — list of field
jp.getInt("orders.size()")                 // 2
jp.get("orders.findAll { it.price > 40 }.product")  // ["Chrome"] — Groovy GPath
jp.getString("orders.min { it.price }.product")      // "Firefox"
```

### XML Path Examples

```java
// Sample XML response
XmlPath xp = response.xmlPath();

xp.getString("users.user[0].name")
xp.getInt("users.user[0].@id")               // attribute
xp.getList("users.user.name")                 // all names
xp.getList("users.user.findAll { it.role == 'admin' }.name")
```

---

## 9. Schema Validation

```java
// JSON Schema validation — ensure response structure is correct
given()
    .get("/users/1")
.then()
    .statusCode(200)
    .body(matchesJsonSchemaInClasspath("schemas/user_schema.json"));

// user_schema.json (src/test/resources/schemas/)
{
  "$schema": "http://json-schema.org/draft-07/schema",
  "type": "object",
  "required": ["id", "username", "email"],
  "properties": {
    "id":       { "type": "integer" },
    "username": { "type": "string", "minLength": 3 },
    "email":    { "type": "string", "format": "email" },
    "role":     { "type": "string", "enum": ["admin", "customer"] }
  }
}
```

---

## 10. Request Specification & Response Specification

```java
// Reusable request spec
RequestSpecification authSpec = new RequestSpecBuilder()
    .setBaseUri("https://api.example.com")
    .addHeader("Authorization", "Bearer " + token)
    .addHeader("Content-Type", "application/json")
    .addHeader("Accept", "application/json")
    .setRelaxedHTTPSValidation()
    .build();

// Reusable response spec
ResponseSpecification successSpec = new ResponseSpecBuilder()
    .expectStatusCode(200)
    .expectContentType(ContentType.JSON)
    .expectResponseTime(lessThan(3000L))
    .build();

// Use in tests — reduces boilerplate
given()
    .spec(authSpec)
    .pathParam("id", 123)
.when()
    .get("/users/{id}")
.then()
    .spec(successSpec)
    .body("username", notNullValue());
```

---

## 11. Postman Concepts

### Collection Runner
- Run all requests in a collection in sequence
- Pass variables between requests using `pm.environment.set("token", ...)`
- Pre-request scripts: set up data before request
- Test scripts: validate response after request

### Pre-request Script (JavaScript)

```javascript
// Set dynamic timestamp
pm.environment.set("timestamp", new Date().toISOString());

// Generate random ID
pm.environment.set("userId", Math.floor(Math.random() * 1000));
```

### Test Script (JavaScript)

```javascript
// Status code
pm.test("Status is 200", () => pm.response.to.have.status(200));

// Response body
const body = pm.response.json();
pm.test("User name exists", () => pm.expect(body.name).to.not.be.null);
pm.test("Role is admin", () => pm.expect(body.role).to.equal("admin"));

// Save token for next request
pm.environment.set("authToken", body.token);

// Response time
pm.test("Response under 2s", () => pm.expect(pm.response.responseTime).to.be.below(2000));

// Headers
pm.test("JSON content type", () => {
    pm.expect(pm.response.headers.get("Content-Type")).to.include("application/json");
});
```

### Environment Variables

```
Base URL:   {{baseUrl}}             = https://api.saucedemo.com
Auth Token: {{authToken}}           = (set by login test)
User ID:    {{userId}}              = (set after create user)
```

---

## 12. API Testing Strategy

### What to Test in Every API

1. **Happy path** — valid inputs, expected success response
2. **Status codes** — correct code for each scenario
3. **Response body** — all required fields present, correct types, correct values
4. **Response headers** — Content-Type, cache headers, security headers
5. **Negative scenarios** — invalid inputs, missing required fields, wrong types
6. **Authorization** — unauthenticated (401), unauthorized role (403)
7. **Boundary testing** — empty strings, null, max length, special characters
8. **Response time** — SLA compliance (e.g., < 2s under normal load)
9. **Schema validation** — response structure matches contract
10. **Sequential flows** — create → read → update → delete

### Test Case Examples for Login API

```java
// 1. Valid login
// 2. Wrong password → 401
// 3. Non-existent user → 401
// 4. Empty username → 400
// 5. Empty password → 400
// 6. Both empty → 400
// 7. SQL injection in username → 400/401 (not 500)
// 8. XSS in username → 400/401 (not echoed back)
// 9. Very long username (1000 chars) → 400
// 10. Response contains token → 200
// 11. Response time < 2s
// 12. Response schema valid
// 13. No password in response body
```

---

## 13. Interview Q&A

**Q: What is the difference between authentication and authorization?**
> **Authentication** — Who are you? Verifying identity (username/password, token).
> **Authorization** — What can you do? Verifying permissions (role-based access).
> A user can be authenticated (valid token) but not authorized (no admin role → 403).

**Q: What is idempotency?**
> An operation is idempotent if calling it multiple times has the same effect as calling it once. `GET`, `PUT`, `DELETE` are idempotent. `POST` is not — calling POST twice creates two resources.

**Q: What is the difference between PUT and PATCH?**
> `PUT` replaces the **entire** resource — omitted fields are set to null/default. `PATCH` does a **partial** update — only sends the fields to change. Use PUT when you want to replace, PATCH when you want to update specific fields.

**Q: What is REST Assured's `given()`, `when()`, `then()` pattern?**
> BDD-style API: `given()` sets up the request (headers, body, auth), `when()` performs the HTTP action (GET/POST etc.), `then()` validates the response. Readable, self-documenting test structure.

**Q: How do you handle token refresh in API tests?**
> In `@BeforeClass` or `@BeforeMethod`, call the login API to get a fresh token, store it in a variable, and include it in all subsequent requests via a `RequestSpecification`.

**Q: What is JSON Path? How is it different from XPath?**
> JSON Path is to JSON what XPath is to XML — a query language for navigating JSON. `$` = root, `.` = child, `[n]` = array index, `[*]` = all. REST Assured uses Groovy's GPath syntax. XPath is for XML with more complex navigation capabilities.

**Q: What is the difference between a 401 and 403?**
> `401 Unauthorized` — Not authenticated. No valid credentials provided. Client should authenticate.
> `403 Forbidden` — Authenticated but not authorized. You're logged in but don't have permission for this resource.

**Q: How do you test an API that depends on another API's output?**
> Sequential test flow: call API1, extract the response value (e.g., user ID), use it as input for API2. Use `extract().response()` and `.jsonPath().getString()` to capture values between requests. Store in instance variables or `RequestSpecification` path params.
