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

## Controllers

The AuthorController has these endpoints

* Get "/id/{id}" - returns AuthorResponse

* Get "/first_name/{firstName}" - returns AuthorDTOs which is a list of a Hibernate projection

* Post "/" takes an AuthorRequest and returns all rows matching the parameters in an AuthorResponses object.
    * AuthorRequest
        * String firstName
        * String lastName
        * String email
        * String username
        * int pageNumber - which page to return
        * int pageSize - max entries per page
        * Sort sort - Sort ascending/descending and which fields to sort on

AuthorResponses contains

* List of AuthorResponse (the Author, books and comments)
* int currentPage
* int totalPages
* long totalElements - total rows in the database matching the search criteria
    