import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Greeting;
import org.intellij.lang.annotations.Language;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

public class ConsumerPactJunitTest {

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("test_provider", "localhost", 8080, this);

    @Pact(consumer = "test_consumer")
    public RequestResponsePact createFragment(PactDslWithProvider builder) {
        @Language("JSON") String response = "{\n" +
                "  \"id\": 3,\n" +
                "  \"content\": \"Hello, Gaurav!\"\n" +
                "}";

        return builder
                .given("another test state")
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
        Response response = client.get("http://localhost:8080/greeting");
        String responseBody = response.getResponseBody();

        Greeting greeting  = new ObjectMapper().readValue(responseBody, Greeting.class);
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("Hello,Gaurav!", greeting.getContent());
    }
}
