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
    String baseUrl = "/api/auth";
    String loginUrl = String.format("%s/login", baseUrl);
    String currentUserUrl = String.format("%s/me", baseUrl);

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("TwitterProvider", this);

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


        DslPart currentUserBody = new PactDslJsonBody()
                .stringType("_id")
                .stringType("name")
                .stringType("username")
                .integerType("__v");

        return builder
                .given("")
                .uponReceiving("request to login")
                .path(loginUrl)
                .headers(headers)
                .method("POST")
                .body(requestBody)
                .willRespondWith()
                .status(200)
                .body(body)
                .given("user is logged in")
                .uponReceiving("request for current logged in user")
                .path(currentUserUrl)
                .headers(headers)
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(currentUserBody)
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

        String url = mockProvider.getUrl() + loginUrl;
        System.out.println(String.format("Url: %s", url));
        Response response = client.post(url, requestBody);
        Assert.assertEquals(200, response.getStatusCode());

        url = mockProvider.getUrl() + currentUserUrl;
        System.out.println(String.format("Url: %s", url));
        response = client.get(url);
        Assert.assertEquals(200, response.getStatusCode());
    }

}
