Web based database browser application.

Technologies:

* Java 8
* Spring Boot
* H2 in-memory database
* Spring Data  
* REST API
* Maven
* Swagger

Swagger documentation is available at:
http://localhost:8080/swagger-ui.html


1. Get, post and put operations are available from the corresponding requests on: /ataccama/db-browser/properties
2. Delete operation by id: /ataccama/db-browser/properties/{id}
3. Properties are represented in format: 
   {"id": ,"name": ,"hostname": ,"port": ,"dbname": ,"username": ,"password": }