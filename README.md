# Jonathan Klecker Branch Service

**Repository**: https://github.com/jklecker/BranchServiceGitHub/tree/main

---

## 🚦 Quick Start

**Build, run, and test the service in three commands:**

```bash
# 1. Build the project (compiles, tests, creates JAR)
./gradlew clean build

# 2. Run the service (development mode)
./gradlew bootRun

# 3. Test the API (example)
curl http://localhost:8080/users/octocat | jq .
```

**Or run the application using the Gradle sidebar in your IDE (e.g., IntelliJ IDEA):**

1. Open the project in your IDE (such as IntelliJ IDEA).
2. Locate the **Gradle** tool window (usually on the right or left sidebar).
3. Expand the project tree under `Tasks > application` or `Tasks > bootRun`.
4. Double-click **bootRun** to start the application.
5. Watch the console output for startup logs and access the API at `http://localhost:8080`.

**Or access the interactive Swagger UI for testing:**
```
http://localhost:8080/swagger-ui.html
```

**See [API Documentation](#-api-documentation) for complete endpoint details and examples.**

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Architecture](#-architecture)
- [Key Design Decisions](#-key-design-decisions)
- [Prerequisites](#-prerequisites)
- [Installation & Setup](#-installation--setup)
- [Running the Service](#-running-the-service)
- [API Documentation](#-api-documentation)
- [Interactive API Documentation (Swagger/OpenAPI)](#-interactive-api-documentation-swaggeropenapi)
- [Testing](#-testing)
- [Code Quality](#-code-quality)
- [Development Workflow](#-development-workflow)
- [Configuration](#-configuration)
- [Troubleshooting](#-troubleshooting)

## 🎯 Overview

A Spring Boot REST API that pulls GitHub user data and their repos. Give it a username, and it returns:
- User profile info (name, avatar, location, email, etc.)
- Their public repositories (name and URL)
- Formatted creation dates

Built with caching for better performance, solid error handling, and username validation to catch issues early.

## 🏗️ Architecture

Standard layered architecture - nothing fancy, just clean separation:

```
┌─────────────────────────────────────┐
│      REST Controller Layer          │
│     (GitInfoController)             │
├─────────────────────────────────────┤
│      Service Layer                  │
│ (GitHubService, GitHubServiceEx.)   │
├─────────────────────────────────────┤
│      Mapper Layer                   │
│    (GitHubInfoMapper)               │
├─────────────────────────────────────┤
│      Entity/Model Layer             │
│  (GitHubInfo, GitHubRepository)    │
├─────────────────────────────────────┤
│     External Integration            │
│      (GitHub API via REST)          │
└─────────────────────────────────────┘
```

### What Each Part Does

| Component | Responsibility |
|-----------|-----------------|
| **GitInfoController** | Handles HTTP requests, validates input, manages local caching |
| **GitHubService** | Orchestrates API calls to GitHub, handles errors, combines data |
| **GitHubInfoMapper** | Transforms GitHub API JSON responses into domain objects |
| **GitHubInfo** | Domain model representing a GitHub user with repositories |
| **GitHubRepository** | Domain model representing a single repository |
| **GitHubServiceException** | Custom exception for propagating errors with HTTP status codes |

## 🎨 Key Design Decisions

### 1. **Custom Exception Handling**
- **Decision**: Created `GitHubServiceException` instead of using generic exceptions
- **Rationale**: Allows propagating both error messages AND HTTP status codes from the service layer to the controller, enabling proper error responses without losing context
- **Benefit**: Cleaner separation of concerns and more flexible error handling

### 2. **Dedicated Mapper Layer**
- **Decision**: Created `GitHubInfoMapper` instead of using `@JsonProperty` annotations
- **Rationale**: Provides greater flexibility for data transformation, especially for date formatting and field mapping logic
- **Benefit**: Easier to maintain and extend; separates JSON parsing logic from domain models

### 3. **Controller-Level Caching**
- **Decision**: Implemented caching at the controller layer using `ConcurrentHashMap`
- **Rationale**: Allows serving stale data from cache when GitHub API is unavailable, improving user experience
- **Benefit**: Users get partial results (cached user data) with error indication rather than complete failure
- **Trade-off**: Simple in-memory cache; not suitable for distributed systems (would need Redis for production)

### 4. **Stateless Service Layer**
- **Decision**: Services remain stateless; all state management happens at controller level
- **Rationale**: Makes services testable, scalable, and free of side effects
- **Benefit**: Easy to test in isolation; can be easily distributed across multiple instances

### 5. **Comprehensive Input Validation**
- **Decision**: Strict GitHub username validation using regex pattern
- **Rationale**: GitHub usernames have specific rules: 1-39 chars, alphanumeric + hyphens, no leading/trailing/consecutive hyphens
- **Benefit**: Fails fast with clear error messages; prevents unnecessary API calls

### 6. **Lowercase Package Naming**
- **Decision**: Used lowercase package names (`com.example.jonathanklecherbranchservice`)
- **Rationale**: Follows Java naming conventions; improved code quality compliance
- **Benefit**: Better adherence to Java standards; passes PMD and SpotBugs checks

### 7. **Final Parameters & Variables**
- **Decision**: All method parameters and local variables marked as `final`
- **Rationale**: Makes intent clear; prevents accidental reassignment
- **Benefit**: Improves code safety and readability; helps catch bugs early

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- **Java**: JDK 21 (required)
  ```bash
  java -version
  # Should report version 21.x
  ```
- **Gradle**: 7.0 or higher (included via Gradle Wrapper)
  ```bash
  ./gradlew -version
  ```
- **Git**: For cloning the repository (optional)
- **curl** or **Postman**: For testing API endpoints (optional)

## 🔧 Installation & Setup

### 1. Clone or Download the Project

```bash
# Clone the repository (if using Git)
git clone <repository-url>
cd JonathanKleckerBranchService

# Or extract the ZIP file and navigate to the directory
```

### 2. Verify Project Structure

```bash
# Check if the project structure is correct
ls -la
# You should see: build.gradle, src/, gradle/, README.md, etc.
```

### 3. Build the Project

```bash
# Using Gradle Wrapper (recommended - no separate Gradle installation needed)
./gradlew clean build

# On Windows
gradlew.bat clean build
```

This command will:
- Clean previous build artifacts
- Compile source code
- Run all unit and integration tests
- Create JAR files in `build/libs/`

### 4. Optional: Run Code Quality Checks

```bash
# Run SpotBugs and PMD analysis on main and test code
./gradlew codeQuality

# Reports will be generated in:
# - build/reports/spotbugs/main.html
# - build/reports/spotbugs/test.html
# - build/reports/pmd/main.html
# - build/reports/pmd/test.html
```

## 🚀 Running the Service

### Option 1: Run via Gradle (Recommended for Development)

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

### Option 2: Run the JAR File (Production)

```bash
# Build the project first
./gradlew build

# Run the JAR
java -jar build/libs/JonathanKleckerBranchService-0.0.1-SNAPSHOT.jar
```

### Option 3: Run Tests Only

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests GitInfoControllerTest

# Run tests with verbose output
./gradlew test --info
```

### Verify the Service is Running

```bash
# Check if the service is running (macOS/Linux)
curl http://localhost:8080/users/octocat

# Or using the browser
# Visit: http://localhost:8080/users/octocat
```

## 📡 API Documentation

### Base URL
```
http://localhost:8080
```

### Endpoint: Get GitHub User Info

**Request:**
```http
GET /users/{userName}
```

**Path Parameters:**
- `userName` (string, required): GitHub username (1-39 characters, alphanumeric and hyphens only)

**Response (Success - 200 OK):**
```json
{
  "userName": "octocat",
  "displayName": "The Octocat",
  "avatar": "https://github.com/images/error/octocat_happy.gif",
  "geoLocation": "San Francisco",
  "email": "octocat@github.com",
  "url": "https://api.github.com/users/octocat",
  "createdAt": "Tue, 25 Jan 2011 18:44:36 GMT",
  "repositories": [
    {
      "name": "Hello-World",
      "url": "https://api.github.com/repos/octocat/Hello-World"
    },
    {
      "name": "Spoon-Knife",
      "url": "https://api.github.com/repos/octocat/Spoon-Knife"
    }
  ]
}
```

**Response (User Not Found - 404 Not Found):**
```json
{
  "error": "GitHub user not found or error occurred: 404 NOT_FOUND",
  "status": 404,
  "cached": false
}
```

**Response (User Not Found but Cache Available - 404 Not Found with Cached Data):**
```json
{
  "data": {
    "userName": "octocat",
    "displayName": "The Octocat",
    "avatar": "https://github.com/images/error/octocat_happy.gif"
  },
  "error": "GitHub user not found or error occurred: 404 NOT_FOUND",
  "status": 404,
  "cached": true
}
```

**Response (Invalid Username - 400 Bad Request):**
```json
{
  "error": "Invalid GitHub username"
}
```

### Username Validation Rules

Valid usernames must:
- Be 1-39 characters long
- Start and end with alphanumeric characters (not hyphens)
- Contain only alphanumeric characters and hyphens
- Not have consecutive hyphens (`--`)

**Valid Examples:**
- `octocat`
- `john-doe`
- `user123`

**Invalid Examples:**
- `-invalid` (starts with hyphen)
- `invalid-` (ends with hyphen)
- `in--valid` (consecutive hyphens)
- `a` * 40 (too long)

### Example API Calls

```bash
# Using curl
curl http://localhost:8080/users/octocat

# With formatted output (requires jq)
curl http://localhost:8080/users/octocat | jq .

# Test invalid username
curl http://localhost:8080/users/-invalid

# Test non-existent user
curl http://localhost:8080/users/invalid_user_that_does_not_exist_12345
```

## 📚 Interactive API Documentation (Swagger/OpenAPI)

### Quick Start with Swagger UI

Built-in Swagger UI for testing endpoints without curl:

**Access Swagger UI:**
```
http://localhost:8080/swagger-ui.html
```

**Access OpenAPI JSON Specification:**
```
http://localhost:8080/v3/api-docs
```

**Access OpenAPI YAML Specification:**
```
http://localhost:8080/v3/api-docs.yaml
```

### Features

✅ **Interactive Endpoint Testing**
- Click "Try it out" button on any endpoint
- Enter parameters directly in the UI
- See live responses in real-time
- No need for curl or Postman

✅ **Complete Schema Documentation**
- Request/response models documented
- Field descriptions with examples
- Data types and validation rules
- Required vs optional fields

✅ **HTTP Status Codes**
- All possible responses documented
- Error messages with descriptions
- Real-world response examples

### Using Swagger UI

**Step-by-Step:**
1. Start the application: `./gradlew bootRun`
2. Open browser: `http://localhost:8080/swagger-ui.html`
3. Expand "GitHub Users" section
4. Click on "GET /users/{userName}"
5. Click "Try it out"
6. Enter username: `octocat`
7. Click "Execute"
8. See the response immediately


## 🧪 Testing

### Running Tests

```bash
# Run all tests
./gradlew test

# Run specific test file
./gradlew test --tests GitInfoControllerTest

# Run with output
./gradlew test --info

# Run only integration tests
./gradlew test --tests "*Integration*"
```

### Test Coverage

Tests cover all layers: unit tests for validation/mapping/entities, integration tests for controllers and GitHub API calls.

### Test Results

Test results are generated in:
```
build/test-results/test/
```

View the HTML report:
```bash
# macOS/Linux
open build/reports/tests/test/index.html

# Windows
start build\reports\tests\test\index.html
```

## 🔍 Code Quality

### Quality Metrics

This project maintains high code quality standards while being pragmatic about rule enforcement:

- **SpotBugs**: Detects potential bugs and security issues (effort: max)
- **PMD**: Analyzes code for design flaws and best practices with smart rule customization
- **Final Modifiers**: All parameters and local variables are final
- **Proper Naming Conventions**: Follows Java standards (camelCase, lowercase packages)
- **Exception Handling**: Custom exceptions with HTTP status propagation

### PMD Rule Customization

This project uses a pragmatic approach to PMD:
- Focuses on real issues in production code
- Suppresses or customizes rules that are not relevant or practical for test code
- Documents any major suppressions or customizations in the codebase or ruleset
- Follows Java and Spring Boot best practices

### Running Code Quality Analysis

```bash
# Run SpotBugs and PMD on main and test code
./gradlew codeQuality
```

### Quality Reports

Generated reports are available at:

**SpotBugs:**
- Main Code: `build/reports/spotbugs/main.html`
- Test Code: `build/reports/spotbugs/test.html`

**PMD:**
- Main Code: `build/reports/pmd/main.html`
- Test Code: `build/reports/pmd/test.html`


## 📁 Project Structure

```
src/main/java/com/example/jonathanklecherbranchservice/
├── JonathanKleckerBranchServiceApplication.java  # Main entry point
├── controller/      # REST endpoints
├── service/         # Business logic & GitHub API calls
├── mapper/          # JSON transformation
└── entity/          # Domain models
```

## 🛠️ Development Workflow

### Building

```bash
# Clean and build
./gradlew clean build

# Build without running tests
./gradlew build -x test

# Build with specific JVM options
./gradlew build -Dorg.gradle.jvmargs="-Xmx2g"
```

### Making Changes

1. Edit source files in `src/main/java/`
2. Run tests: `./gradlew test`
3. Check code quality: `./gradlew codeQuality`
4. Rebuild: `./gradlew build`

### Adding New Features

1. Add domain model in `entity/`
2. Add service logic in `service/`
3. Add mapping logic in `mapper/`
4. Add controller endpoint in `controller/`
5. Write tests for each layer
6. Run quality checks before committing

---


## 🐛 Troubleshooting

### Common Issues

**Issue: `gradlew: permission denied`**
```bash
# Solution: Make gradle executable
chmod +x gradlew
```

**Issue: `Java version mismatch`**
```bash
# Check Java version
java -version

# Solution: Update Java to 17+
# Download from: https://www.oracle.com/java/technologies/downloads/
```

**Issue: `Build fails with "Connection refused"`**
```bash
# The service might be trying to reach GitHub API
# Check your internet connection
# If behind a proxy, configure it in gradle.properties
```

**Issue: `Tests fail with "No such file or directory"`**
```bash
# Solution: Clean and rebuild
./gradlew clean build
```

**Issue: `Port 8080 already in use`**
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process (macOS/Linux)
kill -9 <PID>

# Or run on a different port
./gradlew bootRun --args='--server.port=8081'
```

**Issue: `API calls return 404 for valid users`**
```bash
# Check that the service is running
curl http://localhost:8080/users/octocat

# Check GitHub API is accessible
curl https://api.github.com/users/octocat

# Verify username validation
# Usernames must be 1-39 chars, alphanumeric + hyphens only
```

### Getting Help

- Check the [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- Review [GitHub API Documentation](https://docs.github.com/en/rest)
- Check test files for usage examples
- Review error logs: `./gradlew bootRun` (logs print to console)

## 📝 Configuration

### Application Properties

Edit `src/main/resources/application.properties`:

```properties
# Application name
spring.application.name=JonathanKleckerBranchService

# Server port (default: 8080)
server.port=8080

# Logging level
logging.level.root=INFO
logging.level.com.example.jonathanklecherbranchservice=DEBUG
```

### Gradle Build Configuration

Key settings in `build.gradle`:

- **Spring Boot Version**: 4.0.1
- **Java Version**: 21 (enforced via Gradle toolchain)
- **SpotBugs Effort**: max (thorough analysis)
- **PMD Ruleset**: `config/pmd-ruleset.xml`

## 📚 Dependencies

### Main Dependencies

| Dependency | Purpose | Version |
|-----------|---------|---------|
| Spring Boot Starter Web | REST API framework | 4.0.1 |
| Jackson Databind | JSON parsing | Latest |
| Spring Boot Starter Test | Testing framework | 4.0.1 |

### Build Plugins

| Plugin | Purpose |
|--------|---------|
| Spring Boot Gradle Plugin | Package as executable JAR |
| SpotBugs | Bug detection |
| PMD | Code quality analysis |


---

**Version**: 0.0.1-SNAPSHOT
**Java Version**: 21
**Spring Boot**: 4.0.1