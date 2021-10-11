package pl.tbs.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

public class DinoPassAPIClient {
    private HttpClient client;
    private HttpRequest request;
    private static final String DINOPASS_API_URL = "https://www.dinopass.com/password/simple";

    public DinoPassAPIClient(){
        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder(URI.create(DINOPASS_API_URL)).header("accept", "text/plain,text/html").build();
    }

    public String getNewPassword() throws IOException, InterruptedException {
        // use the client to send the request
        var response = client.send(request, BodyHandlers.ofString());
        // the response:
        return response.body();
    }
}