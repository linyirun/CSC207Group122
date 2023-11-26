package use_case.spotify_to_youtube;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import entity.YoutubeAuth;
import interface_adapter.spotfiy_to_youtube.SpotifyToYoutubePresenter;
import use_case.loginOAuth.LoginOAuthInteractor;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SpotifyToYoutubeInteractor implements SpotifyToYoutubeInputBoundary{
    SpotifyToYoutubeDataAccessInterface dao;
    SpotifyToYoutubeOutputBoundary presenter;
    String code;

    public SpotifyToYoutubeInteractor(SpotifyToYoutubeDataAccessInterface dao, SpotifyToYoutubeOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }
    @Override
    public void execute(SpotifyToYoutubeInputData data) throws IOException, InterruptedException{
        // Create an instance of HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // Define the URL for the GET request
        String url = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + YoutubeAuth.getClientId() + "&redirect_uri="
                + YoutubeAuth.getRedirectURI() + "&response_type=token&scope=" + YoutubeAuth.getScope();

        // Create an instance of HttpRequest
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            // Send the HTTP request and get the response
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // Print the response status code
            int statusCode = response.statusCode();
            System.out.println("Status Code: " + statusCode);

            // Print the response body
            String responseBody = response.body();
            System.out.println("Response Body:\n" + responseBody);

            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(response.uri());
                } catch (IOException e) {
                    System.out.println("Problem with URL");
                }
            } else {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        createServer(8000);

        synchronized (this) {

            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted while waiting: " + e.getMessage());
                // Happens if someone interrupts your thread.
            }
        }
    }
    public void createServer(int port) throws IOException {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/callback", new SpotifyToYoutubeInteractor.CallbackHandler());
            server.start();
            System.out.println("Server is listening on port " + port);
        } catch (IOException e) {
            System.out.println("Could not create server, error: " + e);

        }
    }

    class CallbackHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Get the authorization code from the query parameters for GET requests
            URI uri = exchange.getRequestURI();
            String query = uri.getQuery();
            System.out.println(query);
            if (query != null) {
                String code = query.split("=")[1];
                handleAuthorizationCode(code);
            }

            // Send a response to the browser
            String response = "Authorization code received. You can close this window.";
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }

            exchange.close();
        }

        private void handleAuthorizationCode(String code) {
            System.out.println("Authorization code: " + code);

            // Stop the server after handling the callback
            synchronized (SpotifyToYoutubeInteractor.this) {
                SpotifyToYoutubeInteractor.this.code = code;
                SpotifyToYoutubeInteractor.this.notifyAll();
            }
        }
    }
}
