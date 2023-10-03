# E-commerce service using microservices
I've developed an e-commerce service using the Java and Spring Boot framework, implementing a microservices architecture.

## Architecture for microservices
![alt text](./Blankboard.jpeg)

### Features:
1. OpenFeign Client: Implemented communication between services using OpenFeign service.
2. Config Server: Deployed configurations for all the services on github and a config server that serves the configurations.
3. Gateway: Implemented a gateway service that takes all the requests and redirects them to respective services.
4. Service Registry: implemented Service registry using Netflix Eureka server.
5. Unit testing: Wrote unit testing for all working services for all scenarios.
6. Exception handling with custom exceptions and ControllerAdvice.
7. Implememted Design patterns and SOLID principles

