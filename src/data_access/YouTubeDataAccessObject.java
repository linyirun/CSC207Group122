package data_access;

import entity.Song;
import entity.YoutubeAuth;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YouTubeDataAccessObject{

    private static final String API_KEY = YoutubeAuth.getApiKey();
    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";

    /**
     * Searches for videos on YouTube based on song information.
     *
     * @param songs a list of Song objects containing song names and artists
     * @return a list of video IDs corresponding to the search results
     * @throws IOException   if an I/O error occurs
     * @throws ParseException if an error occurs during JSON parsing
     */
    public List<String> searchVideos(List<Song> songs) throws IOException, ParseException {
        List<String> videoIds = new ArrayList<>();

        try {
            for (Song song : songs) {
                // Build the request URL for searching videos
                String searchVideosUrl = BASE_URL + "search?part=id&type=video&q=" +
                        song.getName() + " " + getFirstArtistName(song.getArtists()) +
                        "&key=" + API_KEY;

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(searchVideosUrl))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                JSONParser jsonParser = new JSONParser();
                JSONObject jsonResponse = (JSONObject) jsonParser.parse(response.body());

                // Extract the video ID from the response
                JSONArray items = (JSONArray) jsonResponse.get("items");
                if (items.size() > 0) {
                    JSONObject firstItem = (JSONObject) items.get(0);
                    JSONObject idObject = (JSONObject) firstItem.get("id");
                    String videoId = (String) idObject.get("videoId");
                    videoIds.add(videoId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return videoIds;
    }

    // Helper method to get the name of the first artist
    private String getFirstArtistName(Map<String, Long> artists) {
        return artists.keySet().stream().findFirst().orElse("");
    }



    public static String createPlaylist(String playlistName, List<String> videoIds) throws IOException, ParseException {
        String accessToken = YoutubeAuth.getAccessToken();
        String createPlaylistUrl = BASE_URL + "playlists?part=snippet,status&key=" + API_KEY;

        try {
            // Build the request body for creating a playlist
            JSONObject playlistBody = new JSONObject();
            JSONObject snippet = new JSONObject();
            snippet.put("title", playlistName);
            playlistBody.put("snippet", snippet);
            playlistBody.put("status", new JSONObject().put("privacyStatus", "public"));

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest createPlaylistRequest = HttpRequest.newBuilder()
                    .uri(URI.create(createPlaylistUrl))
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(playlistBody.toJSONString()))
                    .build();

            HttpResponse<String> createPlaylistResponse = client.send(createPlaylistRequest, HttpResponse.BodyHandlers.ofString());

            // Extract the playlist ID from the response
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) jsonParser.parse(createPlaylistResponse.body());
            String playlistId = (String) jsonResponse.get("id");
            System.out.println("Created Playlist ID: " + playlistId);

            // Add videos to the created playlist
            addVideosToPlaylist(playlistId, videoIds);

            return playlistId;
        } catch (Exception e) {
            System.err.println("Error creating playlist: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Adds a list of videos to the specified playlist on YouTube.
     *
     * @param playlistId the ID of the playlist
     * @param videoIds   a list of video IDs to be added to the playlist
     * @throws IOException   if an I/O error occurs
     * @throws ParseException if an error occurs during JSON parsing
     */

    public static void addVideosToPlaylist(String playlistId, List<String> videoIds) throws IOException, ParseException {
        String accessToken = YoutubeAuth.getAccessToken();
        String addVideosUrl = BASE_URL + "playlistItems?part=snippet&key=" + API_KEY;

        try {
            HttpClient client = HttpClient.newHttpClient();

            for (String videoId : videoIds) {
                // Create the playlist item
                JSONObject playlistItem = new JSONObject();

                // Create the snippet object
                JSONObject snippet = new JSONObject();
                snippet.put("playlistId", playlistId);

                // Create the resourceId object
                JSONObject resourceId = new JSONObject();
                resourceId.put("kind", "youtube#video");
                resourceId.put("videoId", videoId);

                // Set the resourceId directly in the snippet
                snippet.put("resourceId", resourceId);

                // Set the snippet in the playlistItem
                playlistItem.put("snippet", snippet);

                // Build the request body for adding a single video to the playlist
                JSONObject playlistItemBody = new JSONObject();
                playlistItemBody.put("snippet", snippet);

                HttpRequest addVideoRequest = HttpRequest.newBuilder()
                        .uri(URI.create(addVideosUrl))
                        .header("Authorization", "Bearer " + accessToken)
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(playlistItemBody.toJSONString()))
                        .build();

                HttpResponse<String> addVideoResponse = client.send(addVideoRequest, HttpResponse.BodyHandlers.ofString());

                // Check the response status for each video
                if (addVideoResponse.statusCode() == 200) {
                    System.out.println("Video with ID " + videoId + " added to the playlist successfully.");
                } else {
                    System.err.println("Error adding video with ID " + videoId + " to the playlist. Response: " + addVideoResponse.body());
                }
            }
        } catch (Exception e) {
            System.err.println("Error adding videos to playlist: " + e.getMessage());
            e.printStackTrace();
        }
    }


}