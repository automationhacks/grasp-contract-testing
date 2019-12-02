import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

class Response {
    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    private Integer statusCode;
    private String responseBody;

    public Response(Integer statusCode, String responseBody) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }
}

public class RestClient {
    public Response get(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            Integer statusCode = con.getResponseCode();
            String response = getResponse(con);
            System.out.println(String.format("Response:  Code: %d Body: %s", statusCode, response));

            return new Response(statusCode, response);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NotNull
    private String getResponse(HttpURLConnection con) {
        StringBuilder response = new StringBuilder();

        try (Scanner in = new Scanner(con.getInputStream())) {
            while (in.hasNext()) {
                response.append(in.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
