package data_access;

import entity.SpotifyAuth;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import use_case.playlists.PlaylistsUserDataAccessInterface;
import use_case.split_playlist.SplitUserDataAccessInterface;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

public class SpotifyDataAccessObject implements PlaylistsUserDataAccessInterface, SplitUserDataAccessInterface {
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
}
