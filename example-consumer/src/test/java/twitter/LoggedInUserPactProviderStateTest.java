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
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class LoggedInUserPactProviderStateTest {
    String baseUrl = "/api/auth";
    String currentUserUrl = String.format("%s/me", baseUrl);

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("TwitterProvider", this);

    @Pact(consumer = "LoggedInTwitterConsumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        // Define how the response for users profile looks like
        DslPart currentUserBody = new PactDslJsonBody()
                .stringType("_id")
                .stringType("name")
                .stringType("username")
                .integerType("__v");

        // Define Pact using builder
        return builder
                .given("User is already logged in")
                .uponReceiving("Request for current users profile")
                .path(currentUserUrl)
                .method("GET")
                .headerFromProviderState("Authorization", "Bearer ${token}", "Bearer 84a29669-5d79-462c-94db-e1ec17358e0e")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(currentUserBody)
                .toPact();
    }

    @Test
    @PactVerification
    public void runTest() {
        RestClient client = new RestClient();

        String url = mockProvider.getUrl() + currentUserUrl;
        System.out.println(String.format("Url: %s", url));
        Response response = client.get(url);
        Assert.assertEquals(200, response.getStatusCode());
    }
}

