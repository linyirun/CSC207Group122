package use_case.artists_playlist_maker;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

/**
 * Interactor for Artists Playlist Maker use case.
 */
public class ArtistsPmInteractor implements ArtistsPmInputBoundary {

    ArtistsPmUserDataAccessInterface dao;
    ArtistsPmOutputBoundary presenter;

    /**
     * Constructs an ArtistsPmInteractor with the provided data access object and presenter.
     *
     * @param dataAccessObject the data access object
     * @param presenter        the presenter
     */
    public ArtistsPmInteractor(ArtistsPmUserDataAccessInterface dataAccessObject, ArtistsPmOutputBoundary presenter) {
        dao = dataAccessObject;
        this.presenter = presenter;
    }

    /**
     * Retrieves a list of top artists based on the provided input data.
     *
     * @param inputData the input data containing user preferences
     * @return a list of top artist names
     * @throws IOException          if an I/O error occurs
     * @throws ParseException       if an error occurs while parsing
     * @throws InterruptedException if the operation is interrupted
     */

    public List<String> showTopArtists(ArtistsPmInputData inputData) throws IOException, ParseException, InterruptedException {
        return dao.getTopArtists(inputData.getArtistName(), 5);
    }

    /**
     * Creates a playlist based on the provided input data.
     *
     * @param inputData the input data containing user preferences
     */

    public void createPlaylist(ArtistsPmInputData inputData) {
        try {
            List<String> selectedArtists = inputData.getSelectedArtists();

            boolean includeInPlaylist = inputData.isIncludeInPlaylist();

            if (selectedArtists.isEmpty()) {
                presenter.noArtistsSelectedError();
                return;
            }

            List<String> topTracks = dao.getArtistsTopTracks(selectedArtists, inputData.getNumberOfSongs());
            List<String> newTracks = topTracks;
            if (!includeInPlaylist){
                newTracks = filterExistingTracks(topTracks, new ArrayList<>(dao.getPlaylistMap().values()));
            }

            String playlistName = promptForUniquePlaylistName();
            String userId = dao.getUserId();
            String playlistId = dao.createPlaylist(playlistName, userId);
            dao.addSongsToPlaylist(playlistId, newTracks);
            presenter.playlistCreatedSuccessfully();
            System.out.println("Playlist created successfully!");

        } catch (IOException | InterruptedException | ParseException e) {
            e.printStackTrace();
        }
    }

    List<String> filterExistingTracks(List<String> tracks, List<String> existingPlaylists) {
        Set<String> uniqueTracks = new HashSet<>(tracks);

        for (String playlistId : existingPlaylists) {
            List<String> songIdsInPlaylist = dao.getTrackIds(playlistId);
            uniqueTracks.removeIf(songIdsInPlaylist::contains);
        }

        return new ArrayList<>(uniqueTracks);
    }

    private String promptForUniquePlaylistName() {
        Set<String> existingPlaylists = dao.getPlaylists();
        String suggestedName;

        do {
            suggestedName = generateRandomName();
        } while (existingPlaylists.contains(suggestedName));

        return suggestedName;
    }

    private String generateRandomName() {
        String[] prefixes = {"Cool", "Awesome", "Fantastic", "Groovy", "Epic", "Sizzling", "Magical", "Dazzling"};
        String[] suffixes = {"Playlist", "Mix", "Jams", "Vibes", "Sounds", "Grooves", "Melodies", "Beats"};
        int randomPrefixIndex = new Random().nextInt(prefixes.length);
        int randomSuffixIndex = new Random().nextInt(suffixes.length);
        String randomPrefix = prefixes[randomPrefixIndex];
        String randomSuffix = suffixes[randomSuffixIndex];
        int randomNumber = new Random().nextInt(1000);

        return randomPrefix + " " + randomSuffix + " " + randomNumber;
    }
}
