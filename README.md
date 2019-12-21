# Contract testing

Note: producer/provider are terms which can be used interchangeably

## Steps

1. Write a consumer test against a mock with expectations of how the provider should actually behave. Re: [apidemo.ConsumerPactJunitTest](file://consumer/src/test/java/apidemo.ConsumerPactJunitTest.java)

Use below command to run the consumer test
```zsh
./gradlew :consumer:test
```

2. Step 1 would generate a .json file with the expectation of how the provider behavior should be
3. Copy pact to provider project by executing below gradle task in the consumer project

> Note: This problem of moving over Pact files is solved by Pact broker 

```zsh
./gradlew publishPact
```

Running this would publish a pact folder in `/build` directory of producer
module, for now, do below instructions till you are able to figure out the E2E
flow

```zsh
cd <path_to_producer>/build
mv pacts ../target/pacts
```


4. On trying to run provider gradle task for the provider side of test
```zsh
 ./gradlew clean build :producer:pactVerify

> Blocked on being able to run producer verification test [Link](https://github.com/DiUS/pact-workshop-jvm#verifying-the-springboot-provider)

## Pending tasks

- Example for provider states
- Create POST API in springboot for twitter auth API

