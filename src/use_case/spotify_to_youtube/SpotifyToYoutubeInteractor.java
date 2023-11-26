package use_case.spotify_to_youtube;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import data_access.YouTubeDataAccessObject;
import entity.YoutubeAuth;
import interface_adapter.spotfiy_to_youtube.SpotifyToYoutubePresenter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import java.util.ArrayList;
import java.util.List;

public class SpotifyToYoutubeInteractor implements SpotifyToYoutubeInputBoundary{
    SpotifyToYoutubeDataAccessInterface dao;
    SpotifyToYoutubeOutputBoundary presenter;
    String code;

    public SpotifyToYoutubeInteractor(SpotifyToYoutubeDataAccessInterface dao, SpotifyToYoutubeOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    @Override
    public void execute(SpotifyToYoutubeInputData data) {
//        try {
//
//            System.out.println(YouTubeDataAccessObject.searchVideos().get(0));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
        List<String> d = new ArrayList<>();
        d.add("kffacxfA7G4");
        d.add("Fv_3oRUo_d8");

        try {
            YouTubeDataAccessObject.createPlaylist("hello world", d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void connectToYoutube(){
        // Create an instance of HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // Define the URL for the GET request
        String url = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + YoutubeAuth.getClientId() + "&redirect_uri="
                + YoutubeAuth.getRedirectURI() + "&response_type=code&scope=" + YoutubeAuth.getScope();

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
                    presenter.prepareFailView(new SpotifyToYoutubeOutputData("URL is invalid", true));
                }
            } else {
                presenter.prepareFailView(new SpotifyToYoutubeOutputData("Could not open link in browser", true));
            }

        } catch (Exception e) {
            e.printStackTrace();
            presenter.prepareFailView(new SpotifyToYoutubeOutputData("Could not send http request", true));
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
        // Define the URL for the GET request
        String url2 = "https://oauth2.googleapis.com/token";
        String requestBody = "client_id=" + YoutubeAuth.getClientId() + "&redirect_uri="
                + YoutubeAuth.getRedirectURI() + "&grant_type=authorization_code&code=" + code + "&client_secret=" +
                YoutubeAuth.getClientSecret();

        // Create an instance of HttpRequest
        HttpRequest httpRequest2 = HttpRequest.newBuilder()
                .uri(URI.create(url2)).header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            // Send the HTTP request and get the response
            HttpResponse<String> response = httpClient.send(httpRequest2, HttpResponse.BodyHandlers.ofString());

            // Print the response status code
            int statusCode = response.statusCode();
            System.out.println("Status Code: " + statusCode);

            // Print the response body
            String responseBody = response.body();
            System.out.println("Response Body:\n" + responseBody);

            JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.body());
            YoutubeAuth.setAccessToken((String) jsonObject.get("access_token"));
            System.out.println(YoutubeAuth.getAccessToken());
            presenter.prepareSuccessView(new SpotifyToYoutubeOutputData("Connected to YouTube", false));
        }
        catch (Exception e) {
            e.printStackTrace();
            presenter.prepareFailView(new SpotifyToYoutubeOutputData("Could not send http request", true));
        }
    }
    public void createServer(int port) {
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
