# Contract testing : Beyond functional API tests

## Step 1: Write a consumer test with JUnitRule 

* Write a consumer test against a mock with expectations of how the provider should behave. 

Example test: `/example-consumer/src/test/java/apidemo/ConsumerPactJunitTest.java`

Use below command to run the consumer test

```zsh
./gradlew :example-consumer:clean test
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

## Step 3: Start a local instance of pact broker and publish the pacts to it

Before this, ensure below configs are updated in `build.gradle` file
This makes sure that we are pointing to a local version of PACT broker

```bash
pactBrokerUrl = mybrokerUrl
pactBrokerUsername = ''
pactBrokerPassword = ''

// Update below to ensure local PACT broker is being used
pactBrokerUrl = "http://localhost"
```

Execute below to start local pact broker

```bash
docker-compose up
``` 


```bash
➜  grasp-contract-testing git:(master) ✗ ./gradlew :example-consumer:pactPublish

> Task :example-consumer:pactPublish FAILED
Publishing 'JunitRuleConsumer-ExampleProvider.json' ... FAILED! 409 Conflict - This is the first time a pact has been published for "ExampleProvider".
The name "ExampleProvider" is very similar to the following existing consumers/providers:
* Example API
If you meant to specify one of the above names, please correct the pact configuration, and re-publish the pact.
If the pact is intended to be for a new consumer or provider, please manually create "ExampleProvider" using the following command, and then re-publish the pact:
$ curl -v -XPOST -H "Content-Type: application/json" -d "{\"name\": \"ExampleProvider\"}" http://localhost/pacticipants
If the pact broker requires basic authentication, add '-u <username:password>' to the command.
To disable this check, set `check_for_potential_duplicate_pacticipant_names` to false in the configuration.
```

```curl
curl -v -XPOST -H "Content-Type: application/json" -d "{\"name\": \"ExampleProvider\"}" http://localhost/pacticipants
```

```bash
➜  grasp-contract-testing git:(master) ✗ ./gradlew :example-consumer:pactPublish                                                                              

> Task :example-consumer:pactPublish
Publishing 'JunitRuleConsumer-ExampleProvider.json' ... HTTP/1.1 201 Created
```

### Step 4 Run the pact against the provider

Great, now that the consumer pacts are present in PACT broker, we can trigger the provider side of verification using gradle

Pre-requisites:

Ensure the provider API is up and running. Using `./gradlew :example-provider:bootRun`

```zsh
 ./gradlew :example-provider:pactVerify
```

## Summarizing

Here are the steps in sequence

```bash
# Consumer pact generation
./gradlew :example-consumer:clean test

# Publish to broker (remember to start pact broker via docker-compose up first)
./gradlew :example-consumer:pactPublish

# Start provider service (if not started already)
./gradlew :example-provider:bootRun

# Trigger provider verification
 ./gradlew :example-provider:pactVerify
```

