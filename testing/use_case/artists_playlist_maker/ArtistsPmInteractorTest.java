package use_case.artists_playlist_maker;

import entity.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ArtistsPmInteractorTest {

    private ArtistsPmInteractor interactor;
    private ArtistsPmUserDataAccessInterfaceStub dao;
    private ArtistsPmOutputBoundaryStub presenter;

    @BeforeEach
    void setUp() {
        dao = new ArtistsPmUserDataAccessInterfaceStub();
        presenter = new ArtistsPmOutputBoundaryStub();
        interactor = new ArtistsPmInteractor(dao, presenter);
    }

    @Test
    void filterExistingTracks_SomeExistingTracks() {
        // Arrange
        List<String> tracks = Arrays.asList("Track1", "Track2", "Track3");
        List<String> existingPlaylists = Arrays.asList("Playlist1", "Playlist2");

        // Set up stubs
        dao.setTrackIds("Playlist1", Collections.emptyList());
        dao.setTrackIds("Playlist2", Collections.emptyList());

        // Act
        List<String> filteredTracks = interactor.filterExistingTracks(tracks, existingPlaylists);

        // Assert
        assertEquals(tracks.size(), filteredTracks.size());
        assertTrue(filteredTracks.containsAll(tracks));
    }

    @Test
    void filterExistingTracks_AllExistingTracks() {
        // Arrange
        List<String> tracks = Arrays.asList("Track1", "Track2", "Track3");
        List<String> existingPlaylists = Arrays.asList("Playlist1", "Playlist2");

        // Set up stubs
        dao.setTrackIds("Playlist1", tracks);
        dao.setTrackIds("Playlist2", tracks);

        // Act
        List<String> filteredTracks = interactor.filterExistingTracks(tracks, existingPlaylists);

        // Assert
        assertTrue(filteredTracks.isEmpty());
    }

    @Test
    void filterExistingTracks_MixedExistingTracks() {
        // Arrange
        List<String> tracks = Arrays.asList("Track4", "Track3");
        List<String> existingPlaylists = Arrays.asList("Playlist1", "Playlist2");

        // Set up stubs
        dao.setTrackIds("Playlist1", Arrays.asList("Track1", "Track2"));
        dao.setTrackIds("Playlist2", Collections.singletonList("Track3"));

        // Act
        List<String> filteredTracks = interactor.filterExistingTracks(tracks, existingPlaylists);

        // Assert
        assertEquals(1, filteredTracks.size());
    }

    @Test

    void showTopArtists_Success() throws IOException, InterruptedException, ParseException, org.json.simple.parser.ParseException {
        // Arrange
        ArtistsPmInputData inputData = new ArtistsPmInputData(
                "ArtistName", Arrays.asList("Artist1", "Artist2"), 5, true);

        // Set up stubs
        dao.setTopArtists(Arrays.asList("Artist1", "Artist2", "Artist3", "Artist4", "Artist5"));

        // Act
        List<String> topArtists = interactor.showTopArtists(inputData);

        // Assert
        assertNotNull(topArtists);
        assertEquals(5, topArtists.size());
    }

    @Test
    void showTopArtists_EmptyResult() throws IOException, InterruptedException, ParseException, org.json.simple.parser.ParseException {
        // Arrange
        ArtistsPmInputData inputData = new ArtistsPmInputData(
                "NonExistentArtist", Arrays.asList(), 5, true);

        // Set up stubs
        dao.setTopArtists(Collections.emptyList());

        // Act
        List<String> topArtists = interactor.showTopArtists(inputData);

        // Assert
        assertNotNull(topArtists);
        assertTrue(topArtists.isEmpty());
    }




    @Test
    void createPlaylist_Success() throws IOException, InterruptedException, ParseException {
        // Arrange
        ArtistsPmInputData inputData = new ArtistsPmInputData(
                "ArtistName", Arrays.asList("Artist1", "Artist2"), 5, true);

        // Set up stubs
        dao.setTopArtists(Arrays.asList("Artist1", "Artist2", "Artist3", "Artist4", "Artist5"));
        dao.setArtistsTopTracks(Arrays.asList("Track1", "Track2", "Track3", "Track4", "Track5"));
        dao.setPlaylists(Collections.emptySet());
        dao.setUserId("UserID");
        dao.setCreatedPlaylistId("NewPlaylistID");

        // Act
        interactor.createPlaylist(inputData);

        // Assert
        assertEquals(true, presenter.playlistCreatedSuccessfully);
        assertEquals(false, presenter.noArtistsSelectedError);
    }

    @Test
    void createPlaylist_NoArtistsSelected() throws IOException, InterruptedException, ParseException {
        // Arrange
        ArtistsPmInputData inputData = new ArtistsPmInputData(
                "ArtistName", Collections.emptyList(), 5, true);

        // Act
        interactor.createPlaylist(inputData);

        // Assert
        assertEquals(true, presenter.noArtistsSelectedError);
        assertEquals(false, presenter.playlistCreatedSuccessfully);
    }

    @Test
    void createPlaylist_PlaylistAlreadyExists() throws IOException, InterruptedException, ParseException {
        // Arrange
        ArtistsPmInputData inputData = new ArtistsPmInputData(
                "ArtistName", Arrays.asList("Artist1", "Artist2"), 5, true);

        // Set up stubs
        dao.setTopArtists(Arrays.asList("Artist1", "Artist2", "Artist3", "Artist4", "Artist5"));
        dao.setArtistsTopTracks(Arrays.asList("Track1", "Track2", "Track3", "Track4", "Track5"));
        dao.setPlaylists(Collections.singleton("ExistingPlaylist"));
        dao.setUserId("UserID");

        // Act
        interactor.createPlaylist(inputData);

        // Assert
        assertEquals(false, presenter.noArtistsSelectedError);
        assertEquals(true, presenter.playlistCreatedSuccessfully);
    }

    // Stub for ArtistsPmUserDataAccessInterface
    private static class ArtistsPmUserDataAccessInterfaceStub implements ArtistsPmUserDataAccessInterface {
        private List<String> topArtists;
        private List<String> artistsTopTracks;
        private Set<String> playlists;
        private String userId;
        private String createdPlaylistId;
        private Map<String, List<String>> trackIdsMap;

        public void setTopArtists(List<String> topArtists) {
            this.topArtists = topArtists;
        }

        public void setArtistsTopTracks(List<String> artistsTopTracks) {
            this.artistsTopTracks = artistsTopTracks;
        }

        public void setPlaylists(Set<String> playlists) {
            this.playlists = playlists;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setCreatedPlaylistId(String createdPlaylistId) {
            this.createdPlaylistId = createdPlaylistId;
        }

        public void setTrackIds(String playlistId, List<String> trackIds) {
            if (trackIdsMap == null) {
                trackIdsMap = new HashMap<>();
            }
            trackIdsMap.put(playlistId, trackIds);
        }

        @Override
        public List<String> getTopArtists(String query, int limit) {
            return topArtists;
        }

        @Override
        public List<String> getArtistsTopTracks(List<String> queries, int limit) {
            return artistsTopTracks;
        }

        @Override
        public String getUserId() {
            return userId;
        }

        @Override
        public String createPlaylist(String playlistName, String userId) {
            return createdPlaylistId;
        }

        @Override
        public void addSongsToPlaylist(String playlistId, List<String> songIds) {
            // Not implemented for this stub
        }

        @Override
        public List<Song> getSongs(String playlistID) {
            // Not implemented for this stub
            return null;
        }

        @Override
        public List<String> getTrackIds(String playlistID) {
            return trackIdsMap.getOrDefault(playlistID, Collections.emptyList());
        }

        @Override
        public Map<String, String> getPlaylistMap() {
            // Not implemented for this stub
            return null;
        }

        @Override
        public Set<String> getPlaylists() {
            return playlists;
        }
    }


    // Stub for ArtistsPmOutputBoundary
    private static class ArtistsPmOutputBoundaryStub implements ArtistsPmOutputBoundary {
        private boolean playlistCreatedSuccessfully;
        private boolean noArtistsSelectedError;

        @Override
        public void playlistCreatedSuccessfully() {
            playlistCreatedSuccessfully = true;
        }

        @Override
        public void noArtistsSelectedError() {
            noArtistsSelectedError = true;
        }
    }
}
