/**
 * Provides methods to access the Spotify API for retrieving user playlists, songs,
 * and performing actions like playlist creation and song addition.
 * <p>
 * Implements:
 * - {@link use_case.playlists.PlaylistsUserDataAccessInterface}
 * - {@link use_case.split_playlist.SplitUserDataAccessInterface}
 * - {@link use_case.home.HomeUserDataAccessInterface}
 * - {@link use_case.loginOAuth.LoginOAuthUserDataAccessInterface}
 * - {@link use_case.artists_playlist_maker.ArtistsPmUserDataAccessInterface}
 * <p>
 * Uses the Spotify Web API to interact with the user's Spotify account.
 * <p>
 * Requires a valid access token set with {@link entity.SpotifyAuth#getAccessToken()}.
 */

package data_access;

import entity.Song;
import entity.SpotifyAuth;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import use_case.SpotifyPlayer.WebPlaybackDataAccessInterface;
import use_case.artists_playlist_maker.ArtistsPmUserDataAccessInterface;
import use_case.home.HomeUserDataAccessInterface;
import use_case.loginOAuth.LoginOAuthUserDataAccessInterface;
import use_case.merge_playlists.MergeDataAccessInterface;
import use_case.playlists.PlaylistsUserDataAccessInterface;
import use_case.split_playlist.SplitUserDataAccessInterface;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import org.json.simple.parser.ParseException;
import use_case.spotify_to_youtube.SpotifyToYoutubeDataAccessInterface;
import use_case.spotify_to_youtube.SpotifyToYoutubeDataAccessInterfaceForSpotify;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

public class SpotifyDataAccessObject implements PlaylistsUserDataAccessInterface, SplitUserDataAccessInterface,
        HomeUserDataAccessInterface, LoginOAuthUserDataAccessInterface, MergeDataAccessInterface, ArtistsPmUserDataAccessInterface, SpotifyToYoutubeDataAccessInterfaceForSpotify, WebPlaybackDataAccessInterface {
          
    /**
     * Retrieves the set of playlist names available to the authenticated user.
     *
     * @return a set of playlist names
     */

    public Set<String> getPlaylists(){
        return getPlaylistMap().keySet();
    }
          
     /**
     * Retrieves a map of playlist names to their corresponding playlist IDs for the authenticated user.
     *
     * @return a map of playlist names to playlist IDs
     */

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
                JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

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

    /**
     * Retrieves a list of track IDs for a given playlist ID.
     *
     * @param playlistID the ID of the playlist
     * @return a list of track IDs
     */

    public List<String> getTrackIds(String playlistID) {
        String accessToken = SpotifyAuth.getAccessToken();
        String url = "https://api.spotify.com/v1/playlists/" + playlistID + "/tracks";
        List<String> trackIds = new ArrayList<>();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", "Bearer " + accessToken).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response.body());
            JSONArray tracks = (JSONArray) jsonObject.get("items");

            for (Object o : tracks) {
                JSONObject track = (JSONObject) ((JSONObject) o).get("track");
                String songId = (String) track.get("id");
                trackIds.add(songId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trackIds;
    }


    /**
     * Retrieves a list of songs from a given playlist that fall within a specified duration interval.
     *
     * @param playlistId The ID of the Spotify playlist.
     * @param startTime The start time of the interval in seconds.
     * @param endTime The end time of the interval in seconds.
     * @return A list of song ids, where each song's duration falls within the specified interval.
     */
    public List<String> getSongInterval(String playlistId, int startTime, int endTime) {
        String accessToken = SpotifyAuth.getAccessToken(); // Assuming SpotifyAuth is your class for handling authentication
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
        List<String> matchingSongs = new ArrayList<>();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + accessToken)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response.body());
            JSONArray items = (JSONArray) jsonObject.get("items");

            for (Object item : items) {
                JSONObject itemObject = (JSONObject) item;
                JSONObject track = (JSONObject) itemObject.get("track");
                long durationMs = (long) track.get("duration_ms");
                int durationSeconds = (int) (durationMs / 1000);

                if (durationSeconds >= startTime && durationSeconds <= endTime) {
                    String trackId = (String) track.get("id");
                    matchingSongs.add(trackId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return matchingSongs;
    }

    /**
     * Retrieves a list of {@link entity.Song} objects for a given playlist ID,
     * including information about the song name, artists, and their popularity.
     *
     * @param playlistID the ID of the playlist
     * @return a list of Song objects
     */
    public List<Song> getSongs(String playlistID) {
        String accessToken = SpotifyAuth.getAccessToken();
        String url = "https://api.spotify.com/v1/playlists/" + playlistID + "/tracks";
        List<Song> songsInPlaylist = new ArrayList<>();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", "Bearer " + accessToken).build();
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

                for (Object artist : artistsArray) {
                    JSONObject jsonArtist = (JSONObject) artist;
                    String artistName = (String) jsonArtist.get("name");
                    String artistId = (String) jsonArtist.get("id");

                    // Make an additional request to get artist details
                    String artistUrl = "https://api.spotify.com/v1/artists/" + artistId;
                    HttpRequest artistRequest = HttpRequest.newBuilder().uri(URI.create(artistUrl)).header("Authorization", "Bearer " + accessToken).build();
                    HttpResponse<String> artistResponse = client.send(artistRequest, HttpResponse.BodyHandlers.ofString());

                    JSONObject artistDetails = (JSONObject) parser.parse(artistResponse.body());
                    Long popularity = (Long) artistDetails.get("popularity");

                    // Add to the map
                    artists.put(artistName, popularity);
                }

                Song song = new Song(songId, name, artists);
                songsInPlaylist.add(song);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songsInPlaylist;
    }


    /**
     * Retrieves the user ID of the authenticated user.
     *
     * @return the user ID
     * @throws IOException            if an I/O error occurs
     * @throws InterruptedException   if the operation is interrupted
     * @throws ParseException         if an error occurs during JSON parsing
     */

    public String getUserId() throws IOException, InterruptedException, ParseException {
        String url = "https://api.spotify.com/v1" + "/me";
        String accessToken = SpotifyAuth.getAccessToken();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", "Bearer " + accessToken).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject responseObject = (JSONObject) new JSONParser().parse(response.body());
        return (String) responseObject.get("id");
    }

    /**
     * Retrieves the display name of the authenticated user's account.
     *
     * @return the display name
     * @throws IOException            if an I/O error occurs
     * @throws InterruptedException   if the operation is interrupted
     * @throws ParseException         if an error occurs during JSON parsing
     */

    public String getAccountName() throws IOException, InterruptedException, ParseException {
        String url = "https://api.spotify.com/v1" + "/me";
        String accessToken = SpotifyAuth.getAccessToken();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", "Bearer " + accessToken).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject responseObject = (JSONObject) new JSONParser().parse(response.body());

        String name = (String) responseObject.get("display_name");
        return (String) responseObject.get("display_name");
    }

    /**
    
     * Creates a new playlist for the authenticated user with the specified name.
     *
     * @param playlistName the name of the new playlist
     * @param userId       the ID of the user for whom the playlist is created
     * @return the ID of the newly created playlist
     * @throws IOException            if an I/O error occurs
     * @throws InterruptedException   if the operation is interrupted
     * @throws ParseException         if an error occurs during JSON parsing

     */
    public String createPlaylist(String playlistName, String userId) throws IOException, InterruptedException, ParseException {
        String url = "https://api.spotify.com/v1" + "/users/" + userId + "/playlists";
        String accessToken = SpotifyAuth.getAccessToken();
        HttpClient client = HttpClient.newHttpClient();
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", playlistName);
        requestBody.put("public", true);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", "Bearer " + accessToken).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(requestBody.toJSONString())).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject responseObject = (JSONObject) new JSONParser().parse(response.body());
        return (String) responseObject.get("id");
    }


    /**
     * Adds a list of songs to the specified playlist.
     *
     * @param playlistId the ID of the playlist
     * @param songIds    a list of song IDs to be added to the playlist
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */

    public void addSongsToPlaylist(String playlistId, List<String> songIds) throws IOException, InterruptedException {
        String url = "https://api.spotify.com/v1" + "/playlists/" + playlistId + "/tracks";
        String accessToken = SpotifyAuth.getAccessToken();
        HttpClient client = HttpClient.newHttpClient();
        JSONArray uris = new JSONArray();
        songIds.forEach(id -> uris.add("spotify:track:" + id));

        JSONObject requestBody = new JSONObject();
        requestBody.put("uris", uris);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", "Bearer " + accessToken).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(requestBody.toJSONString())).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Retrieves a list of top artists based on the provided search query and limit.
     *
     * @param searchQuery the search query for top artists
     * @param limit       the maximum number of artists to retrieve
     * @return a list of top artist names
     * @throws IOException            if an I/O error occurs
     * @throws InterruptedException   if the operation is interrupted
     * @throws ParseException         if an error occurs during JSON parsing
     */

    @Override
    public List<String> getTopArtists(String searchQuery, int limit) throws IOException, InterruptedException, ParseException {
        // Encode the search query to handle spaces and special characters
        String encodedSearchQuery = URLEncoder.encode(searchQuery, StandardCharsets.UTF_8);

        String url = "https://api.spotify.com/v1/search?q=" + encodedSearchQuery + "&type=artist&limit=" + limit;
        String accessToken = SpotifyAuth.getAccessToken();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", "Bearer " + accessToken).build();

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

    /**
     * Retrieves a list of top tracks for a list of artist queries and a specified limit.
     *
     * @param queries a list of artist names to search for
     * @param limit   the maximum number of top tracks per artist
     * @return a list of track IDs representing top tracks
     */

    public List<String> getArtistsTopTracks(List<String> queries, int limit) {
        List<String> topTracks = new ArrayList<>();

        for (String artistName : queries) {
            try {
                // Encode the artist name to handle spaces and special characters
                String encodedArtistName = URLEncoder.encode(artistName, StandardCharsets.UTF_8);

                String url = "https://api.spotify.com/v1/search?q=" + encodedArtistName + "&type=track&limit=" + limit;
                String accessToken = SpotifyAuth.getAccessToken();

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", "Bearer " + accessToken).build();

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

    @Override
    public Map<String, List<String>> getUserTopTracksAndArtists() throws ParseException, IOException, InterruptedException{
        Map<String, List<String>> topTracksAndArtists = new HashMap<>();

        // first fetch the top 10 user tracks
        String url1 = "https://api.spotify.com/v1" + "/me/top/tracks?time_range=long_term&limit=10";
        String accessToken = SpotifyAuth.getAccessToken();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create(url1)).header("Authorization", "Bearer " + accessToken).build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        JSONObject responseObject1 = (JSONObject) new JSONParser().parse(response1.body());
        JSONArray trackObjects = (JSONArray) responseObject1.get("items");
        List<String> topTracks = new ArrayList<>();
        for (Object item : trackObjects) {
            JSONObject track = (JSONObject) item;
            String trackName = (String) track.get("name");
            topTracks.add(trackName);
        }
        topTracksAndArtists.put("tracks", topTracks);

        // then fetch the top 10 user artists
        String url2 = "https://api.spotify.com/v1" + "/me/top/artists?time_range=long_term&limit=10";
        HttpRequest request2 = HttpRequest.newBuilder().uri(URI.create(url2)).header("Authorization", "Bearer " + accessToken).build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        JSONObject responseObject = (JSONObject) new JSONParser().parse(response2.body());
        JSONArray artistObjects = (JSONArray) responseObject.get("items");
        List<String> topArtists = new ArrayList<>();
        for (Object item : artistObjects) {
            JSONObject track = (JSONObject) item;
            String trackName = (String) track.get("name");
            topArtists.add(trackName);
        }
        topTracksAndArtists.put("artists", topArtists);
        return topTracksAndArtists;
    }

    /**
     * Returns a list of maps of audio features for each requested song.
     * Note: this will only work for lists with less than 100 songs.
     *
     * @param songIds - ids of songs to get the audio features for
     * @return List of (Map of String to String): map of audio features corresponding to each song
     */
    @Override
    public List<Map<String, String>> getSongsAudioFeatures(List<String> songIds)  {
        String token = SpotifyAuth.getAccessToken();

        HttpClient httpClient = HttpClient.newHttpClient();

        // Build the request
        String trackIdsParam = String.join(",", songIds);
        String apiUrl = "https://api.spotify.com/v1/audio-features/?ids=" + URLEncoder.encode(trackIdsParam, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", "Bearer " + token)
                .build();

        // Send the request and handle the response
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            List<Map<String, String>> listOfAudioFeaturesMap = new ArrayList<>();

            // List of audio features we want to get from the Spotify Api
            // We can always add more if we need
            List<String> features = new ArrayList<>();

            features.add("danceability");
            features.add("energy");
            features.add("instrumentalness");
            features.add("valence");
            features.add("tempo");

            if (response.statusCode() == 200) {




                // Process the response
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(response.body());

                // Get the array of audio features
                JSONArray audioFeatures = (JSONArray) json.get("audio_features");

                // Iterate through each track's audio features
                for (Object obj : audioFeatures) {
                    JSONObject track = (JSONObject) obj;
                    // Stores the audio features for each song
                    Map<String, String> audioFeaturesMap = new HashMap<>();
                    if (track == null) continue;

                    for (String audioFeature : features) {
                        // Get all of this track's audio features and put it into a map
                        audioFeaturesMap.put(audioFeature, track.get(audioFeature).toString());
                    }

                    // Add the features map to a list so we can return it
                    listOfAudioFeaturesMap.add(audioFeaturesMap);
                }

                return listOfAudioFeaturesMap;
            } else {
                System.out.println("Error: " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
