package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RestClient {
    public Response get(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");

            return prepareResponseObj(con);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response post(String apiUrl, String requestBody) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            return prepareResponseObj(con);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Response prepareResponseObj(HttpURLConnection con) throws IOException {
        Integer statusCode = con.getResponseCode();
        String response = getResponse(con);
        System.out.println(String.format("Response:  Code: %d Body: %s", statusCode, response));

        return new Response(statusCode, response);
    }

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
