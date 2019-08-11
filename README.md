# spring-boot-features
Sample RnD project that shows how to create microservices with Spring Boot 2.0 containerized by Docker and orchestrate by Kubernetes and Istio. 

Also this project contains samples with Apache Kafka and Spring Data. 
As load tests is also shown Gatling



By default, Spring Boot configures the application to connect to an in-memory store with the username sa and an empty password.
H2 database has an embedded GUI console for browsing the contents of a database and running SQL queries.
we can navigate to http://localhost:port/h2-console 

Spring Boot will automatically pick up the data.sql in src/main/resources and run it against our configured H2 database during application startup. 
This is a good way to seed the database for testing or other purposes.