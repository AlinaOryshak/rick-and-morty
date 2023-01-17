## Rick and Morty API

### Description
The web application on a regular basis downloads data from a third-party service to the internal database
using Cron job to implement data synchronization . 
API requests work with a local database and are documented using Swagger.
Furthermore, application has tests and can be run with a help of Docker.

---

### Endpoints 
+ `/movie-characters/random` - The request randomly generates a wiki about one character 
in the universe the animated series Rick & Morty.
+ `/movie-characters/by-name` - The request takes a string as an argument, and returns a list of all characters 
whose name contains the search string.

---

### Technologies
+ Java 17
+ Spring Boot 3.0.1
+ Spring Data JPA 3.0.1
+ PostgresQL 42.5.1
+ Liquibase 4.17.2
+ Lombok

---

### How to run 
+ `docker pull alinaoryshak/rick-and-morty-app` - pull the image
+ `docker run rick-and-morty-app` - run the image
