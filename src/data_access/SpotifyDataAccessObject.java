package data_access;

import entity.SpotifyAuth;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import use_case.artists_playlist_maker.ArtistsPmUserDataAccessInterface;
import use_case.home.HomeUserDataAccessInterface;
import use_case.loginOAuth.LoginOAuthUserDataAccessInterface;
import use_case.playlists.PlaylistsUserDataAccessInterface;
import use_case.split_playlist.SplitUserDataAccessInterface;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import entity.Song;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.stream.Collectors;

public class SpotifyDataAccessObject implements PlaylistsUserDataAccessInterface, SplitUserDataAccessInterface, HomeUserDataAccessInterface, LoginOAuthUserDataAccessInterface, ArtistsPmUserDataAccessInterface{

    public Set<String> get_playlists(){
        return get_playlistMap().keySet();
    }

    public Map<String, String> get_playlistMap(){
        Map<String, String> playlist_map = new HashMap<>();
        try {
            // Use your pre-existing access token
            String accessToken = SpotifyAuth.getAccessToken();

            // Use the access token to make a request to get the user's playlists
            String endpoint = "https://api.spotify.com/v1/me/playlists";

            URL playlistsUrl = new URL(endpoint);
            HttpURLConnection playlistsConnection = (HttpURLConnection) playlistsUrl.openConnection();
            playlistsConnection.setRequestMethod("GET");
            playlistsConnection.setRequestProperty("Authorization", "Bearer " + accessToken);
            int playlistsResponseCode = playlistsConnection.getResponseCode();

            if (playlistsResponseCode == 200) {
                InputStream inputStream = playlistsConnection.getInputStream();
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject)jsonParser.parse(
                        new InputStreamReader(inputStream, "UTF-8"));

                JSONArray playlists = (JSONArray) jsonObject.get("items");
                for (Object playlist : playlists) {
                    JSONObject playlistObj = (JSONObject) playlist;
                    String playlistName = (String) playlistObj.get("name");
                    String playlistID = (String) playlistObj.get("id");
                    playlist_map.put(playlistName, playlistID);
                }

                playlistsConnection.disconnect();
            } else {
                System.err.println("Error: Unable to retrieve playlists. Response code: " + playlistsResponseCode);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return playlist_map;
    }

    public List<Song> getSongs(String playlistID){
        String accessToken = SpotifyAuth.getAccessToken();
        String url = "https://api.spotify.com/v1/playlists/" + playlistID + "/tracks";
        List<Song> songsInPlaylist = new ArrayList<>();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + accessToken)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response.body());
            JSONArray tracks = (JSONArray) jsonObject.get("items");

            for (Object o : tracks) {
                JSONObject track = (JSONObject) ((JSONObject) o).get("track");
                String name = (String) track.get("name");
                String songId = (String) track.get("id");

                Map<String, Long> artists = new HashMap<>();
                JSONArray artistsArray = (JSONArray) track.get("artists");
                for(Object artist: artistsArray){
                    JSONObject JasonArtist = (JSONObject) artist;

                    String artistId = (String) JasonArtist.get("id");
                    // Fetching artist details
                    String artistUrl = "https://api.spotify.com/v1/artists/" + artistId;
                    HttpRequest artistRequest = HttpRequest.newBuilder()
                            .uri(URI.create(artistUrl))
                            .header("Authorization", "Bearer " + accessToken)
                            .build();
                    HttpResponse<String> artistResponse = client.send(artistRequest, HttpResponse.BodyHandlers.ofString());
                    JSONObject artistDetails = (JSONObject) parser.parse(artistResponse.body());

                    String artistName = (String) artistDetails.get("name");
                    Long popularity = (Long) artistDetails.get("popularity");

                    //add to the map
                    artists.put(artistName, popularity);
                }
                Song song = new Song(songId, name, artists);
                songsInPlaylist.add(song);
                System.out.println(name);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songsInPlaylist;
    }

    public String getUserId() throws IOException, InterruptedException, ParseException {
        String url = "https://api.spotify.com/v1" + "/me";
        String accessToken = SpotifyAuth.getAccessToken();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject responseObject = (JSONObject) new JSONParser().parse(response.body());
        return (String) responseObject.get("id");
    }

    public String getAccountName() throws IOException, InterruptedException, ParseException {
        String url = "https://api.spotify.com/v1" + "/me";
        String accessToken = SpotifyAuth.getAccessToken();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject responseObject = (JSONObject) new JSONParser().parse(response.body());
        return (String) responseObject.get("display_name");
    }

    public String createPlaylist(String playlistName, String userId) throws IOException, InterruptedException, ParseException {
        String url = "https://api.spotify.com/v1" + "/users/" + userId + "/playlists";
        String accessToken = SpotifyAuth.getAccessToken();
        HttpClient client = HttpClient.newHttpClient();
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", playlistName);
        requestBody.put("public", true);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toJSONString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject responseObject = (JSONObject) new JSONParser().parse(response.body());
        return (String) responseObject.get("id");
    }

    public void addSongsToPlaylist(String playlistId, List<String> songIds) throws IOException, InterruptedException {
        String url = "https://api.spotify.com/v1" + "/playlists/" + playlistId + "/tracks";
        String accessToken = SpotifyAuth.getAccessToken();
        HttpClient client = HttpClient.newHttpClient();
        JSONArray uris = new JSONArray();
        songIds.forEach(id -> uris.add("spotify:track:" + id));

        JSONObject requestBody = new JSONObject();
        requestBody.put("uris", uris);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toJSONString()))
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

        @Override
        public List<String> getTopArtists(String searchQuery, int limit) throws IOException, InterruptedException, ParseException {
            // Encode the search query to handle spaces and special characters
            String encodedSearchQuery = URLEncoder.encode(searchQuery, StandardCharsets.UTF_8);

            String url = "https://api.spotify.com/v1/search?q=" + encodedSearchQuery + "&type=artist&limit=" + limit;
            String accessToken = SpotifyAuth.getAccessToken();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + accessToken)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject responseObject = (JSONObject) new JSONParser().parse(response.body());
            JSONObject artistsObject = (JSONObject) responseObject.get("artists");
            JSONArray items = (JSONArray) artistsObject.get("items");

            List<String> artists = new ArrayList<>();
            for (Object item : items) {
                JSONObject artist = (JSONObject) item;
                artists.add((String) artist.get("name"));
            }
            return artists;
        }

    public List<String> getArtistsTopTracks(List<String> queries, int limit) {
        List<String> topTracks = new ArrayList<>();

        for (String artistName : queries) {
            try {
                // Encode the artist name to handle spaces and special characters
                String encodedArtistName = URLEncoder.encode(artistName, StandardCharsets.UTF_8);

                String url = "https://api.spotify.com/v1/search?q=" + encodedArtistName + "&type=track&limit=" + limit;
                String accessToken = SpotifyAuth.getAccessToken();

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Authorization", "Bearer " + accessToken)
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                JSONObject responseObject = (JSONObject) new JSONParser().parse(response.body());
                JSONObject tracksObject = (JSONObject) responseObject.get("tracks");
                JSONArray items = (JSONArray) tracksObject.get("items");

                for (Object item : items) {
                    JSONObject track = (JSONObject) item;
                    String trackName = (String) track.get("id");
                    topTracks.add(trackName);
                }
            } catch (IOException | InterruptedException | ParseException e) {
                e.printStackTrace();
            }
        }

        return topTracks;
    }

    public List<String> chooseArtistsAndCreatePlaylist() {
        try {
            // Get the user's top artists
            List<String> topArtists = getTopArtists("Maneskin", 5);

            // Display the list of top artists to the user
            displayArtistList(topArtists);

            // Allow the user to choose an artist
            String selectedArtist = getUserInput("Select an artist by entering its name: ");

            // Keep track of selected artists
            List<String> selectedArtists = new ArrayList<>();
            selectedArtists.add(selectedArtist);

            // Get the top tracks for the selected artist
            List<String> topTracks = getArtistsTopTracks(selectedArtists, 10);

            // Filter out tracks that are already in the user's playlists
            List<String> newTracks = filterExistingTracks(topTracks, new ArrayList<>(get_playlistMap().values()));


            // Prompt the user for a unique playlist name
            String playlistName = promptForUniquePlaylistName();

            // Create a new playlist
            String userId = getUserId();
            String playlistId = createPlaylist(playlistName, userId);

            // Add the new tracks to the playlist
            addSongsToPlaylist(playlistId, newTracks);

            System.out.println("Playlist created successfully!");

            return newTracks;
        } catch (IOException | InterruptedException | ParseException e) {
            e.printStackTrace();
        }

        return Collections.emptyList(); // Return an empty list in case of an error
    }

    private String promptForUniquePlaylistName() {
        Scanner scanner = new Scanner(System.in);
        String playlistName;

        do {
            System.out.print("Enter a unique playlist name: ");
            playlistName = scanner.nextLine().trim();

            // Check if the playlist name already exists
            if (playlistExistsForUser(playlistName)) {
                System.out.println("A playlist with the same name already exists. Please choose a different name.");
            }

        } while (playlistExistsForUser(playlistName));

        return playlistName;
    }

    private boolean playlistExistsForUser(String playlistName) {
        // Implement logic to check if the playlist name already exists for the user
        Set<String> userPlaylists = get_playlists(); // Assuming get_playlists() returns a set of existing playlist names
        return userPlaylists.contains(playlistName);
    }

    private void displayArtistList(List<String> artists) {
        System.out.println("Top Artists:");
        for (int i = 0; i < artists.size(); i++) {
            System.out.println((i + 1) + ". " + artists.get(i));
        }
    }

    private String getUserInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private List<String> filterExistingTracks(List<String> tracks, List<String> existingPlaylists) {
        // Create a set to store unique track names
        Set<String> uniqueTracks = new HashSet<>(tracks);

        // Iterate through each playlist and remove tracks that are already in the existing playlists
        for (String playlistId : existingPlaylists) {
            List<Song> songsInPlaylist = getSongs(playlistId);

            // Extract the song ids from the playlist
            Set<String> songIdsInPlaylist = songsInPlaylist.stream()
                    .map(Song::getId)
                    .collect(Collectors.toSet());

            // Remove tracks that are already in the playlist
            uniqueTracks.removeIf(songIdsInPlaylist::contains);
        }

        // Convert the set back to a list
        return new ArrayList<>(uniqueTracks);
    }






}
