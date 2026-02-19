# Bookstore Modular Monolith — Spring Modulith

A Spring Boot 3 + Spring Modulith project that demonstrates proper **modular monolith** principles by enforcing strict module boundaries at compile-time and test-time.

---

## Problem Statement

The original codebase had a violation:

```java
// InventoryService.java — BROKEN
import com.bookstore.orders.internal.OrderValidator; // ❌ crosses module boundary
```

Spring Modulith treats any package named `internal` as **module-private**. Importing it from another module causes `ApplicationModules.verify()` to throw an error.

---

## Solution

Introduce a **public facade** (`OrderValidationService`) in the `orders` module's public API. The `inventory` module now depends on this facade instead of the hidden implementation.

```
orders module
 ├── OrderService.java            ← public
 ├── OrderValidationService.java  ← public facade (NEW)  ✅
 └── internal/
       └── OrderValidator.java    ← hidden (unchanged)

inventory module
 └── InventoryService.java        ← uses OrderValidationService ✅
```

### Key Design Decisions

| Concern | Decision |
|---|---|
| Hiding implementation | `OrderValidator` stays in `orders.internal` — never moved |
| Cross-module dependency | `InventoryService` imports `OrderValidationService` (public API only) |
| Spring Modulith rule | No class outside `com.bookstore.orders` may import from `com.bookstore.orders.internal` |

---

## Project Structure

```
src/main/java/com/bookstore/
 ├── Application.java
 ├── orders/
 │     ├── OrderService.java
 │     ├── OrderValidationService.java      ← public facade
 │     └── internal/
 │           └── OrderValidator.java        ← module-private
 └── inventory/
       └── InventoryService.java

src/test/java/com/bookstore/
 └── ModularityTests.java
```

---

## Spring Modulith Concepts Applied

### 1. Module Detection
Spring Modulith auto-discovers modules as **direct sub-packages** of the base package (`com.bookstore`). Here, `orders` and `inventory` are two separate modules.

### 2. Internal Packages
Any sub-package named `internal` is considered **module-private**. Its classes cannot be referenced from outside the enclosing module. Violation at test time → `modules.verify()` fails.

### 3. Public API
Classes directly in a module package (e.g., `com.bookstore.orders`) form the module's **public API** and can be freely imported by other modules.

### 4. Allowed Dependencies
```
inventory → orders (public API only)   ✅
inventory → orders.internal            ❌ (blocked by Modulith)
```

---

## Running the Project

### Prerequisites
- Java 17+
- Maven 3.8+

### Compile

```bash
mvn compile
```

### Run Tests (includes modularity verification)

```bash
mvn test
```

The `ModularityTests#verifiesModularStructure` test calls:
```java
ApplicationModules.of(Application.class).verify();
```
This assertion **passes** with the refactored code.

### Run Application

```bash
mvn spring-boot:run
```

---

## Why This Approach?

The **Facade Pattern** at module boundaries is a Spring Modulith best practice:

- **Encapsulation**: Implementation details (`OrderValidator`) remain hidden and can change freely without affecting other modules.
- **Stable API**: Other modules depend only on the stable public surface (`OrderValidationService`).
- **Testability**: Each module can be tested in isolation.
- **Evolutionary architecture**: Modules can be extracted into independent services later with minimal refactoring.

---

## Tech Stack

| Technology | Version |
|---|---|
| Java | 17 |
| Spring Boot | 3.2.2 |
| Spring Modulith | 1.1.2 |
| Maven | 3.8+ |
