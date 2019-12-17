package twitter;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import client.Response;
import client.RestClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.twitter.auth.AuthResponse;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class AuthAPIContractTests {
    private String requestBody = "{\n" +
            "\t\"name\": \"Sayantan\",\n" +
            "\t\"username\": \"sayantan\",\n" +
            "\t\"password\": \"secretpwd\"\n" +
            "}";

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule(
            "twitter_auth", "localhost", 8080, this);

    @Pact(consumer = "login_form")
    public RequestResponsePact createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        String expectedResponse = "{\n" +
                "  \"auth\": true,\n" +
                "  \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVkZjhlZjU0OWZiZDJmMDAxNWEwNDk1MCIsImlhdCI6MTU3NjU5NTI4NCwiZXhwIjoxNTc2NjgxNjg0fQ.2F5Hm8NQNb3DRZHaCTZ45fN-WDFRn4-5p-6fUT7IOIo\",\n" +
                "  \"user_id\": \"5df8ef549fbd2f0015a04950\"\n" +
                "}";

        return builder
                .given("user tries to log on")
                .uponReceiving("POST request")
                .path("/api/auth/register")
                .headers(headers)
                .body(requestBody)
                .method("POST")
                .willRespondWith()
                .status(200)
                .body(expectedResponse)
                .toPact();
    }

    @Test
    @PactVerification
    public void runConsumerContractTest() throws JsonProcessingException {
        RestClient client = new RestClient();
        Response response = client.post("http://localhost:8080/api/auth/register", requestBody);
        String responseBody = response.getResponseBody();

        AuthResponse authResponse = new ObjectMapper().readValue(responseBody, AuthResponse.class);
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals(true, authResponse.getAuth());
    }
}
