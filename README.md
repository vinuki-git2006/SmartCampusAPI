
 



University of Westminster
smart-campus-api
Report

5COSC022W 
Course Work




Name – B.A.W. Vinuki Buwaneka
UOW ID – w2153001
IIT ID - 20233097


                                                                                                                                                                                                                                                  
Part 1, Q1 — JAX-RS Resource Lifecycle & Thread Safety
In JAX-RS, resource classes follow a per-request lifecycle, meaning a new instance of the resource class is created for every incoming HTTP request. The runtime does not treat resource classes as singletons by default.
Impact on shared state: Because of this, instance variables inside a resource class are not shared between requests. Any data stored in them will be lost once the request is completed. Therefore, to maintain shared data such as rooms and sensors, the data must be stored in static collections or external structures.
Since multiple requests can be processed at the same time, concurrency becomes an important factor. To avoid issues such as race conditions or inconsistent data, thread-safe practices should be followed. In this implementation, shared collections are managed carefully to ensure safe access when multiple users interact with the API simultaneously.
This design ensures that the application remains stable and consistent under concurrent usage.

Part 1, Q2 — Why HATEOAS Is a Hallmark of Advanced RESTful Design
HATEOAS (Hypermedia as the Engine of Application State) allows the API to guide clients by including links in its responses.
For example, the discovery endpoint provides links to main resources such as rooms and sensors. This means the client does not need prior knowledge of all endpoints.
For example, the discovery endpoint (/api/v1) returns links such as:	 
"_links": {
  "rooms": "/api/v1/rooms",
  "sensors": "/api/v1/sensors"
}
This approach has several advantages:
•	Clients can navigate the API dynamically 
•	It reduces dependency on hardcoded URLs 
•	It makes the API more flexible and maintainable 
•	It ensures responses are self-explanatory 
Compared to static documentation, HATEOAS makes the API more adaptive and easier to use, especially when changes occur.
Part 2, Q1 — IDs Only vs Full Objects in List Response
When returning a list of rooms, there are two approaches: returning only IDs or returning full objects.
•	Returning only IDs 
Example: ["LIB-301", "LAB-102", "HALL-01"]
Advantages: Reduces the size of the response, which saves bandwidth.
Disadvantages: It requires additional requests to retrieve full details.
Increasing the number of API calls.

•	Returning full objects 
Advantages:  All relevant information in a single response
Improve performance for clients
Reduces network overhead caused by multiple calls
Disadvantages: Response size is slightly larger
Conclusion:
In this implementation, returning full objects is more efficient and practical, especially for real-world applications where clients need complete data.

Part 2, Q2 — Is DELETE Idempotent?
Yes, DELETE is idempotent because repeating the same request results in the same final state.
In this implementation:
For example:
•	First request deletes the room successfully 
•	Second request does nothing because the room no longer exists 
Even though the responses may differ (e.g., 200 or 404), the final state remains unchanged. Therefore, the DELETE operation satisfies idempotency.


Part 3, Q1 — Consequences of @Consumes(APPLICATION_JSON) Mismatch

The annotation: @Consumes(MediaType.APPLICATION_JSON) ensures that the API only accepts JSON input.
If a client sends a different format: XML or plain text
Result: The request will be rejected automatically by JAX-RS with a 415 Unsupported Media Type error.

This happens before the request reaches the method, meaning no manual validation is needed. This improves reliability by enforcing a strict format for incoming data.



Part 3, Q2 — @QueryParam vs Path Segment for Filtering
Design	Example	Semantics
Query Parameter	/api/v1/sensors?type=CO2	Filters a collection
Path Segment	/api/v1/sensors/type/CO2	Implies resource

Query parameters are designed for filtering and searching within a collection, while path segments represent specific resources.
Advantages of using query parameters:
•	More flexible (supports multiple filters) 
•	Optional usage 
•	Follows REST standards 
•	Keeps URLs clean and meaningful 
Therefore, query parameters provide a better design for filtering operations.

Part 4, Q1 — Architectural Benefits of the Sub-Resource Locator Pattern
The Sub-Resource Locator pattern allows handling nested resources in a clean and modular way.
Instead of managing everything in one class, the main resource delegates specific responsibilities to another class.
Benefits:
•	Improves code organization 
•	Reduces complexity 
•	Enhances readability 
•	Allows better scalability 
•	Make maintenance easier 
In this system, sensor-related readings are handled separately, which keeps the design structured and manageable.

Part 5, Q1 — Why HTTP 422 Over 404 for a Missing Reference in a JSON Payload
•	404 Not Found:
Used when the requested URL does not exist.
•	422 Unprocessable Entity:
Used when the request is valid, but the data inside it is incorrect.
•	Example: 
POST /api/v1/sensors
{
  "roomId": "GHOST-99"
}
•	The endpoint exists 
•	The JSON is valid 
•	The problem is invalid data 
Therefore, HTTP 422 is more accurate because it clearly indicates a semantic error in the request.


Part 5, Q2 — Cybersecurity Risks of Exposing Java Stack Traces
Exposing Java stack traces can reveal sensitive system details.
These may include:
•	Internal class and package names 
•	Application structure 
•	File locations 
•	Execution flow 
Attackers can use this information to identify vulnerabilities and exploit the system.
To prevent this, the API should return generic error messages to users while logging detailed errors internally.


Part 5, Q3 — Why Use JAX-RS Filters Instead of Manual Logging in Every Method
Using JAX-RS filters allows logging to be handled globally instead of adding logging code in every method.
Advantages:
•	Reduces code duplication 
•	Ensures consistent logging 
•	Keeps business logic clean 
•	Makes maintenance easier 
•	Allows easy extension for other features 
Filters provide a centralized and efficient way to handle cross-cutting concerns like logging.

Conclusion: Filters provide a centralized and efficient way to handle cross-cutting concerns like logging.

