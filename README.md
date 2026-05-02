# Smart Campus API — Report

---

## Part 1 — Q1: JAX-RS Resource Lifecycle & Thread Safety

In JAX-RS, resource classes follow a per-request lifecycle. This means a new instance of the resource class is created for each incoming HTTP request. The runtime does not treat resource classes as singletons.

Because of this, instance variables do not persist between requests. Any data stored inside them is lost after the request completes. Therefore, shared data such as rooms and sensors must be stored in static collections or external structures.

Since multiple requests can be handled at the same time, thread safety is important. Proper handling of shared data ensures that issues like race conditions or inconsistent data do not occur.

This design ensures that the API remains stable and reliable when accessed by multiple users.

---

## Part 1 — Q2: Importance of HATEOAS

HATEOAS (Hypermedia as the Engine of Application State) allows APIs to include links in their responses, enabling clients to navigate the system dynamically.

Instead of relying on fixed documentation, clients can discover available endpoints through the API itself. This reduces dependency on hardcoded URLs and improves flexibility.

This approach makes the API easier to maintain, as changes in endpoints only need to be updated on the server side. It also improves usability for developers by making responses self-descriptive.

---

## Part 2 — Q1: IDs vs Full Objects

When returning a list of rooms, two approaches can be used: returning only IDs or returning full objects.

Returning only IDs reduces the size of the response but requires additional requests to fetch complete details. This increases the number of API calls and can slow down performance.

Returning full objects provides all necessary information in a single request. Although the response size is larger, it reduces the need for multiple calls and improves efficiency.

In this system, returning full objects is more practical and beneficial.

---

## Part 2 — Q2: Idempotency of DELETE

DELETE operations are considered idempotent because repeating the same request results in the same final state.

For example, deleting a room once removes it from the system. Sending the same request again does not change the state further, as the room is already deleted.

Even though responses may differ, the final result remains the same, satisfying idempotency.

---

## Part 3 — Q1: @Consumes JSON Mismatch

The @Consumes(MediaType.APPLICATION_JSON) annotation ensures that only JSON data is accepted by the API.

If a client sends data in another format such as XML or plain text, JAX-RS automatically rejects the request with a 415 Unsupported Media Type error.

This validation happens before the request reaches the method, ensuring only valid data is processed.

---

## Part 3 — Q2: QueryParam vs Path

Using query parameters for filtering, such as `/sensors?type=CO2`, is more appropriate than using path segments.

Query parameters are designed for filtering and searching collections, while path segments represent specific resources.

They provide flexibility, support multiple filters, and align better with REST principles.

---

## Part 4 — Q1: Sub-Resource Locator Benefits

The Sub-Resource Locator pattern allows handling nested resources in separate classes.

This improves code organization by separating responsibilities. For example, sensor-related operations and reading-related operations are handled in different classes.

This approach reduces complexity, improves maintainability, and makes the API easier to extend.

---

## Part 5 — Q1: Why HTTP 422 Instead of 404

HTTP 404 is used when a requested endpoint does not exist.

HTTP 422 is used when the request is valid, but the data inside it is incorrect.

For example, when creating a sensor with a non-existing room ID, the endpoint exists and the JSON is valid, but the reference is incorrect. Therefore, 422 is more accurate.

---

## Part 5 — Q2: Risks of Exposing Stack Traces

Exposing Java stack traces can reveal sensitive internal details such as class structures, file paths, and application logic.

This information can be used by attackers to identify vulnerabilities and exploit the system.

To prevent this, APIs should return generic error messages while logging detailed errors internally.

---

## Part 5 — Q3: Logging Using Filters

JAX-RS filters allow logging to be handled globally across the application.

This avoids adding logging code to every method, reduces duplication, and ensures consistency.

Filters also help maintain clean business logic and allow easy extension for additional features.

---
