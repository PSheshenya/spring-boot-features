# Spring Boot features
Sample RnD project that shows how to create microservices with **Spring Boot 2.0** containerized by Docker and orchestrate by Kubernetes and Istio. 

Also this project contains code with **Apache Kafka** and **Spring Data**.

Documentation in **Swagger2 API**

Testing with JUnit and some load test with [Gatling](https://gatling.io)


## Prerequisites

The project can be run as a Maven project with Java 8 or 11 depends on `java.version` parameter in pom file.

## Project Structure

The project consists of the following type of class and files:

- Controllers
- Servise
- Repositories
- Configs (Swagger, apps)
- Tests (Unit and Load)
- .sql and .http files
- Dockerfile, Kubernetes and Istio files

## Workflow

**When you use docker container, you should change the port in urls below**

Basic steps with code:

- open IDE
- `git clone`
- compiling the whole project
  - run `./mvnw clean install` 
- run main in SpringBootFeaturesApplication
- checking links 
  - using **Actuator** open http://localhost:8080/actuator
  - **H2 database** GUI console navigate to http://localhost:8080/h2-console 
  - **Swagger API** open http://localhost:8080/swagger-ui.html


## DataSource
By default, this project configures the application to connect to an in-memory store H2 with the username sa and an empty password.
H2 database has an embedded GUI console for browsing the contents of a database and running SQL queries.
we can navigate to http://localhost:port/h2-console 

Spring Boot will automatically pick up the data.sql in `src/main/resources` and run it against our configured H2 database during application startup. 
This is a good way to seed the database for testing or other purposes.


## Swagger
Use swagger API http://localhost:8080/swagger-ui.html

We can navigate for different versions of our service (Select a spec) also choose needed Response content type

## Load Tests
### Gatling
When app run, we can start load tests by **Gatling** (see https://gatling.io/docs/current/extensions/maven_plugin/)
```
sheshenya@mbp-sheshenya: ~/spring-boot-features (master) $ ./mvnw gatling:test
```
As a result we can get report in `target/gatling` folder  
By default load tests skiped in pom file
### wrk  
HTTP benchmarking wia **wrk** tool https://github.com/wg/wrk 
```
~/spring-boot-features (master) $ wrk -t 10 -c 1000 -d 10s http://localhost:8080/actuator/info
Running 10s test @ http://localhost:8080/actuator/info
  10 threads and 1000 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   119.76ms  108.52ms   1.72s    79.51%
    Req/Sec   311.33     84.08   730.00     73.23%
  30901 requests in 10.09s, 14.83MB read
  Socket errors: connect 0, read 893, write 0, timeout 10
Requests/sec:   3062.32
Transfer/sec:      1.47MB

~/spring-boot-features (master) $ wrk -t 10 -c 1000 -d 10s http://localhost:8080/projects/alex/1
Running 10s test @ http://localhost:8080/projects/alex/1
  10 threads and 1000 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   260.58ms   89.31ms 427.79ms   72.69%
    Req/Sec   203.91     41.80   340.00     67.68%
  20231 requests in 10.07s, 5.31MB read
  Socket errors: connect 0, read 1042, write 0, timeout 0
  Non-2xx or 3xx responses: 20231
Requests/sec:   2009.83
Transfer/sec:    539.77KB
```

## Dockerize App 
Build the image
`$ docker build -t psheshenya/demo-service:latest .`


Also yiu can get image from DockerHub
`$ docker docker pull psheshenya/demo-service`
Run the image
`$ docker run --rm -d -p 80:8080  psheshenya/demo-service:latest`

Now, you can request api through your http client as Postman etc

## CI/CD Steps
CI/CD process generally follows the following scheme:
- Checkout Code
- Run Unit Tests
- Run Sonar
- Dockerize App
- Push dockerized app to Docker Registry
- Deploy the dockerized app on K8s/Istio

PS:
I'm stuck on the waitlist for the GitHub Actions Beta and automated build Configured just on DockerHub (ref on GitHub project)

### Branching information:
* `master` the latest version of the project