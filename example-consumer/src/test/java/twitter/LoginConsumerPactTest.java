package twitter;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import client.Response;
import client.RestClient;
import org.intellij.lang.annotations.Language;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class LoginConsumerPactTest {
    String url = "/api/auth/login";

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("TwitterProvider", "localhost", 8081, this);

    @Pact(consumer = "TwitterConsumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        String requestBody = "{\n" +
                "  \"username\": \"sayantan\",\n" +
                "  \"password\": \"secretpwd\"\n" +
                "}";

        DslPart body = new PactDslJsonBody()
                .booleanType("auth")
                .stringType("token")
                .stringType("user_id");

        return builder
                .given("POST request to login was made")
                .uponReceiving("POST request")
                .path(url)
                .headers(headers)
                .method("POST")
                .body(requestBody)
                .willRespondWith()
                .status(200)
                .body(body)
                .toPact();
    }

    @Test
    @PactVerification
    public void runTest() {
        @Language("JSON") String requestBody = "{\n" +
                "  \"username\": \"sayantan\",\n" +
                "  \"password\": \"secretpwd\"\n" +
                "}";

        RestClient client = new RestClient();
        Response response = client.post("http://localhost:8081" + url, requestBody);
        Assert.assertEquals(200, response.getStatusCode());
    }

}
