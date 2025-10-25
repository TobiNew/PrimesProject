# PrimeProject Microservice

## Overview

This is a microservice built in Java 17 using Maven. It is an API that when given a numeric value and a parameter, it wil return a list of prime numbers up to and including the given value.

Requirements
-   JDK 17 or later
-   Maven 3.3.x or later

## Installation

-   Clone the repository:

    git clone https://github.com/TobiOl/PrimeProject.git


Build the project:

    mvn clean install

Run the application:

    mvn spring-boot:run

The application will start on [http://localhost:8080](http://localhost:8080) by default.

You will be able to access the Swagger UI at:
```
~/swagger-ui/index.html
```
## Usage
To use the API, use the following endpoint:
```
GET /getPrime/{value}
```
### Request Parameter

| Parameter | Type        | Description                 | Possible values                | Required | Default value |
|-----------|-------------|-----------------------------|--------------------------------|----------|---------------|
| algorithm | Query param | The chosen algorithm to use | DEFAULT, SIEVE_OF_ERATHOSTENES | No       | DEFAULT       |

Output can be returned as either json or XML using

    application/json
or

    application/xml
Example request:

    curl -X 'GET' \
      'http://localhost:8080/getPrime/10?algorithm=SIEVE_OF_ERATHOSTENES' \
      -H 'accept: application/json'

Example Response:

    {
    "primeNumbers": [
		    2,
		    3,
		    5,
		    7
	    ]
    }