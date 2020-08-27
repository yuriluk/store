# Restful service
Store Management restfull application.

Perform the following operations:
1. Find stores by a company code.
2. Find stores by a company code and sorted ascending by distance for the provided coordinates.
3. CRUD operations.
4. Two find endpoints also support sorting by any store field.
5. Search can be done by store name, location(latitude and longitude).

Tools used:
• Spring boot
• Unit and integration test coverage (JUnit 5, Restassured, Spring boot tests)
• PostgresSQL 10.+
• Database connection pool: HikariCP.
• Spring Data JPA
• Multi-module Maven project
• Swagger


# How to run?
First of all you need:
1) Create db named store.
2) Start app to create db schema. You need to run Application.java
3) Insert two company codes from data.sql file using db admin console or whatever you want.
4) Don`t forget to change db data (application.properties in DATABASE CONNECTION section)


SWAGGER UI API documentation:

Documentation is available by http://localhost:8080/swagger-ui.html#/