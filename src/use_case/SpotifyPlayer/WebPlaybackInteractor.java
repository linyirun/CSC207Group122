package use_case.SpotifyPlayer;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import data_access.SpotifyDataAccessObject;
import entity.Song;
import entity.SpotifyAuth;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static entity.SpotifyAuth.getAccessToken;
import static entity.SpotifyAuth.setAccessToken;

public class WebPlaybackInteractor implements WebPlaybackInputBoundary {
    String currentSongId;

    WebPlaybackDataAccessInterface dao;

    public WebPlaybackInteractor (WebPlaybackDataAccessInterface dao) {
        this.dao = dao;
    }
    public void startClientServer(WebPlaybackInputData data) {
        try {
            String currentSong = data.getCurrentSong();
            String playlistId = data.getPlaylistId();
            String[] parts = currentSong.split("\\|");
            String song = parts[0].trim();
            for (Song entity : dao.getSongs(playlistId)) {
                System.out.println(entity.getName());
                if (song.equals(entity.getName())){
                    System.out.println(entity.getId());
                    this.currentSongId = entity.getId();
                }
            }
            String currentWorkingDirectory = System.getProperty("user.dir");

            String reactAppPath = currentWorkingDirectory + "/public/spotifyplayer";

            ProcessBuilder processBuilder = new ProcessBuilder("npm", "start");
            processBuilder.directory(new File(reactAppPath));

            Process process = processBuilder.start();
            Thread.sleep(2000);
            try {
                Desktop.getDesktop().browse(new URI("http://localhost:3000"));
            }
            catch (Exception e) {
                System.out.println("cannot open localhost");
            }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void StartServer () {
        try {
        // Create an HTTP server that listens on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Create a context for the "/hello" endpoint
        server.createContext("/getAccessToken/", new TokenHandler());

        // Start the server
        server.setExecutor(null); // Use the default executor
        server.start();

        System.out.println("Server started on port 8000");
    }
        catch (IOException e) {
            System.out.println("error start server");
            System.out.println(e);
        }
    }

    // Custom handler for the "/hello" endpoint
     class TokenHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Set the response headers
            System.out.println(exchange.getRequestMethod());
            System.out.println(exchange.getRequestBody());
            System.out.println(exchange.getRequestURI());
            System.out.println(exchange.getRequestHeaders());
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "*");
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1); // No content for OPTIONS response
                exchange.close();
                return;
            }

            String token = getAccessToken();

            exchange.sendResponseHeaders(200, 0);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            // Create a JSON response
            String jsonResponse = "{\"access_token\": \"" + token + "\", \"currentSong\": \"" + WebPlaybackInteractor.this.currentSongId+"\"}";
            // Get the output stream to write the response
            OutputStream outputStream = exchange.getResponseBody();

            // Write the JSON response
            outputStream.write(jsonResponse.getBytes());

            // Close the output stream and send the response
            outputStream.close();
        }
    }
}
