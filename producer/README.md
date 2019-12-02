# Simple Spring boot app

This project creates a simple API which prints a greeting and is used to demonstrate consumer driven contract tests
Created by following steps from [spring.io](https://spring.io/guides/gs/rest-service/)

## How to run

```ignorelang
# Run the app using below
./gradlew :producer:bootRun

# Alternatively, build using below
./gradlew build

# and run using below:
java -jar target/gs-rest-service-0.1.0.jar
```

if you hit a url like `http://localhost:8080/greeting`, you should see a JSON response like below:

```json
{"id":2,"content":"Hello, World!"}
``` 

Also you can pass a query param to the request and see the param printed in response:

```ignorelang
http://localhost:8080/greeting?name=Gaurav
```

```json
{"id":3,"content":"Hello, Gaurav!"}
```