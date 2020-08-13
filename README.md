# Restful service
Store Management restfull application.

Perform the following operations:
CRUD operations for Store.

Tools used:
Spring Boot.
JDK version: 8. Streams.
Database connection pool: HikariCP.
Spring JPA Data.
Build tool: Apache Maven 3.6+. Multi-module project.
Web server: Apache Tomcat.
Application container: Spring IoC. Spring Framework.
Spring configured via Java config.
Database: PostgresSQL 10.+


Search can be done by store name, location(latitude and longitude).


# How to run?
First of all you need:
1) Create db named store.
2) Start app to create db schema. You need to run Application.java
3) Insert two company codes from data.sql file using db admin console or whatever you want.
4) Don`t forget to change db data (application.properties in DATABASE CONNECTION section)


SWAGGER UI API documentation:

Documentation is available by http://localhost:8080/swagger-ui.html#/