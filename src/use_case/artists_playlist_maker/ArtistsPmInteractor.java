package use_case.artists_playlist_maker;

import entity.Song;
import entity.SpotifyAuth;
import org.json.simple.parser.ParseException;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;
import use_case.login.LoginUserDataAccessInterface;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ArtistsPmInteractor implements ArtistsPmInputBoundary{

    ArtistsPmUserDataAccessInterface dao;
    ArtistsPmOutputBoundary presenter;
    public ArtistsPmInteractor(ArtistsPmUserDataAccessInterface dataAccessObject, ArtistsPmOutputBoundary presenter) {
        dao = dataAccessObject;
        this.presenter = presenter;

    }
    public List<String> showTopArtists(String artistName) throws IOException, ParseException, InterruptedException {
        return dao.getTopArtists(artistName,5);
//        URL url = new URL("https://accounts.spotify.com/authorize?client_id=" + SpotifyAuth.getClientId() + "&response_type" +
//                "=code&scope=" + SpotifyAuth.getScope() + "&redirect_uri=http://localhost:8080/callback") ;
//        HttpURLConnection http = (HttpURLConnection) url.openConnection();
//        http.setRequestMethod("GET");
//        int responseCode = http.getResponseCode();
//        System.out.println(responseCode);
//        System.out.println(HttpURLConnection.HTTP_OK);
//        if (HttpURLConnection.HTTP_OK == responseCode) {
//            presenter.prepareSuccessView(new LoginOutputData(http.getURL(), false));
//        }
//        else{
//            presenter.prepareFailView(Integer.toString(responseCode), http.getResponseMessage());
//        }
    }

    @Override
    public void createPlaylist(List<String> selectedArtists) {
        try {
            // Get the user's top artists


            // Get the top tracks for the selected artist
            List<String> topTracks = dao.getArtistsTopTracks(selectedArtists, 10);

            // Filter out tracks that are already in the user's playlists
            List<String> newTracks = filterExistingTracks(topTracks, new ArrayList<>(dao.get_playlistMap().values()));


            // Prompt the user for a unique playlist name
            String playlistName = promptForUniquePlaylistName();

            // Create a new playlist
            String userId = dao.getUserId();
            String playlistId = dao.createPlaylist(playlistName, userId);

            // Add the new tracks to the playlist
            dao.addSongsToPlaylist(playlistId, newTracks);

            System.out.println("Playlist created successfully!");

        } catch (IOException | InterruptedException | ParseException e) {
            e.printStackTrace();
        }

    }

    private List<String> filterExistingTracks(List<String> tracks, List<String> existingPlaylists) {
        // Create a set to store unique track names
        Set<String> uniqueTracks = new HashSet<>(tracks);

        // Iterate through each playlist and remove tracks that are already in the existing playlists
        for (String playlistId : existingPlaylists) {
            List<Song> songsInPlaylist = dao.getSongs(playlistId);

            // Extract the song ids from the playlist
            Set<String> songIdsInPlaylist = songsInPlaylist.stream().map(Song::getId).collect(Collectors.toSet());

            // Remove tracks that are already in the playlist
            uniqueTracks.removeIf(songIdsInPlaylist::contains);
        }

        // Convert the set back to a list
        return new ArrayList<>(uniqueTracks);
    }


    private String promptForUniquePlaylistName() {

        Set<String> existingPlaylists = dao.get_playlists();// Replace this with your actual method to fetch existing playlist names

        String suggestedName;

        do {
            suggestedName = generateRandomName();
        } while (existingPlaylists.contains(suggestedName));

        return suggestedName;
    }


    private String generateRandomName() {
        // Adjust this method to customize the generation of random names
        String[] prefixes = {"Cool", "Awesome", "Fantastic", "Groovy", "Epic", "Sizzling", "Magical", "Dazzling"};
        String[] suffixes = {"Playlist", "Mix", "Jam", "Vibes", "Sounds", "Groove", "Melody", "Beats"};
        int randomPrefixIndex = new Random().nextInt(prefixes.length);
        int randomSuffixIndex = new Random().nextInt(suffixes.length);

        String randomPrefix = prefixes[randomPrefixIndex];
        String randomSuffix = suffixes[randomSuffixIndex];

        // Add a random number to the name to make it even less probable
        int randomNumber = new Random().nextInt(1000);

        return randomPrefix + " " + randomSuffix + " " + randomNumber;
    }





}
