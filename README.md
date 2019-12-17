# Contract testing

Note: producer/provider are terms which can be used interchangeably

## Steps

1. Write a consumer test against a mock with expectations of how the provider should actually behave. Re: [apidemo.ConsumerPactJunitTest](file://consumer/src/test/java/apidemo.ConsumerPactJunitTest.java)

Use below command to run the consumer test
```zsh
./gradlew :consumer:test
```

2. Step 1 would generate a .json file with the expectation of how the provider behavior should be
3. Copy pact to provider project  

```zsh
./gradlew publishPact
```

4. On trying to run provider gradle task for the provider side of test
```zsh
 ./gradlew clean build :producer:pactVerify
```

Getting below error

```text
No signature of method: org.gradle.util.NameValidator.asValidName() is applicable for argument types: (org.codehaus.groovy.runtime.GStringImpl)
```

[Github issue](https://github.com/DiUS/pact-jvm/issues/693)