# Query by Example and Hibernate Projections

A Spring Boot Query by Example with Hibernate Projection application inspired by Dan Vega's tutorial on simplifying
GraphQL by using Query By Example.

GraphQL uses the GraphQlRepository annotation and QueryByExampleExecutor interface instead of a long list of declared
methods.

Check out Dan's code and documentation
of [traditional vs QBE approach](https://github.com/danvega/graphql-qbe/blob/main/README.md)

## Project Requirements

- Java 21
- Maven 3.9.x
- PostgreSQL 17

## Running the Application

1. Start the application:

```bash
mvn spring-boot:run
```