package apidemo;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import client.Response;
import client.RestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.apidemo.Greeting;
import org.intellij.lang.annotations.Language;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

public class ConsumerPactJunitTest {

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("ExampleProvider", "localhost", 8081, this);

    @Pact(consumer = "JunitRuleConsumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        @Language("JSON") String response = "{\n" +
                "  \"id\": 3,\n" +
                "  \"content\": \"Hello, Gaurav!\"\n" +
                "}";

        return builder
                .given("Get greeting")
                .uponReceiving("GET request")
                .path("/greeting")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(response)
                .toPact();
    }

    @Test
    @PactVerification
    public void runTest() throws IOException {
        RestClient client = new RestClient();
        Response response = client.get("http://localhost:8081/greeting");
        String responseBody = response.getResponseBody();
        Greeting greeting = new ObjectMapper().readValue(responseBody, Greeting.class);

        Assert.assertEquals(200, response.getStatusCode());
    }
}
