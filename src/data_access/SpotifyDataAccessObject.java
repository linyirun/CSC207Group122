package data_access;

import entity.SpotifyAuth;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import use_case.home.HomeUserDataAccessInterface;
import use_case.loginOAuth.LoginOAuthUserDataAccessInterface;
import use_case.playlists.PlaylistsUserDataAccessInterface;
import use_case.split_playlist.SplitUserDataAccessInterface;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import entity.Song;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.parser.ParseException;
import java.io.IOException;

public class SpotifyDataAccessObject implements PlaylistsUserDataAccessInterface, SplitUserDataAccessInterface, HomeUserDataAccessInterface, LoginOAuthUserDataAccessInterface {

    public Set<String> getPlaylists(){
        return getPlaylistMap().keySet();
    }

    public Map<String, String> getPlaylistMap(){
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

}
