# Contract testing

## Step 1: Write a consumer test with JUnitRule 

* Write a consumer test against a mock with expectations of how the provider should behave. 

Example test: `/example-consumer/src/test/java/apidemo/ConsumerPactJunitTest.java`

Use below command to run the consumer test

```zsh
./gradlew :example-consumer:test
```

* Step 1 would generate a .json file with the expectation of how the provider behavior should be in 
`grasp-contract-testing/pacts` folder with the name of the consumer and provider, in this case as `JunitRuleConsumer-ExampleProvider.json`

## Step 2: Define the provider API

`example-provider` project creates a simple API which prints a greeting 
Created by following steps from [spring.io](https://spring.io/guides/gs/rest-service/)

### How to run

```bash
# Run the app using below
./gradlew :example-provider:bootRun

# Alternatively, build using below
./gradlew build

# and run using below:
java -jar target/gs-rest-service-0.1.0.jar
```

if you hit a url like `http://localhost:8082/greeting`, you should see a JSON response like below:

```json
{"id":2,"content":"Hello, World!"}
``` 

Also you can pass a query param to the request and see the param printed in response:

```bash
http://localhost:8080/greeting?name=Gaurav
```

```json
{"id":3,"content":"Hello, Gaurav!"}
```

You can kill the server using Ctrl + C (Mac) however this will not kill the tomcat server.

To manually kill any process listening on port 8081 use below:

```bash
kill `lsof -i -n -P | grep TCP | grep 8081 | tr -s " " "\n" | sed -n 2p`
```

### Step 3: Run  the pact against the provider

* On trying to run provider gradle task for the provider side of test

```zsh
 ./gradlew clean build :producer:pactVerify
```

> Blocked on being able to run producer verification test [Link](https://github.com/DiUS/pact-workshop-jvm#verifying-the-springboot-provider)

## Pending tasks

- Example for provider states
- Create POST API in springboot for twitter auth API


## Simple provider API



