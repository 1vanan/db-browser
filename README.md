Web based database browser application.

Technologies:

* Java 8
* Spring Boot
* H2 in-memory database
* PostgresSql
* Spring Data  
* REST API
* Maven
* Swagger
* Docker

Swagger documentation is available at:
http://localhost:8080/swagger-ui.html

API:
1. Get, post and put operations are available from the corresponding requests on: /ataccama/db-browser/properties
2. Delete operation by id: /ataccama/db-browser/properties/{id}
3. Get all schemas by connection id: /ataccama/db-browser/connections/{id}/schemas
4. Get all views by connection id: /ataccama/db-browser/connections/connections/{id}/view
5. Get all tables by connection id: /ataccama/db-browser/connections/connections/{id}/tables
6. Get all columns by connection id: /ataccama/db-browser/connections/connections/{id}/columns

Data representation:
1. Properties are represented in format: 
   {"id": ,"name": ,"hostname": ,"port": ,"dbname": ,"username": ,"password": }
2. Schemas are represented in format: {"name":}
3. Views are represented in format: {"values": {"column name":, "value": }}
4. Column metadata is represented in format:
   {"tableName": ,"columnName": ,"position": ,"defaultValue": ,"nullable": ,"dataType": ,"comments": }
5. Table metadata is represented in format:
   {"tableName": ,"tableType": ,"catalogName": ,"schemaName": ,"comments": }

To build the project correctly run Docker, as it's used in @DbBrowsingServiceTest.