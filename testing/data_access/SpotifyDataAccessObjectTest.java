package data_access;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpotifyDataAccessObjectTest {

    private SpotifyDataAccessObject spotifyDAO;

    @BeforeEach
    void setUp() {
        // Assuming you have a way to set up Spotify authentication before testing
        // You may need to mock certain parts like HTTP responses for unit testing
        spotifyDAO = new SpotifyDataAccessObject();
    }

    @Test
    void testGetPlaylists() {
        assertNotNull(spotifyDAO.getPlaylists());
        // Add more assertions as needed
    }

    @Test
    void testGetPlaylistMap() {
        assertNotNull(spotifyDAO.getPlaylistMap());
        // Add more assertions as needed
    }

    @Test
    void testGetTrackIds() {
        String playlistId = "your_playlist_id_here";
        assertNotNull(spotifyDAO.getTrackIds(playlistId));
        // Add more assertions as needed
    }

    @Test
    void testGetSongInterval() {
        String playlistId = "your_playlist_id_here";
        int startTime = 0;
        int endTime = 300;
        assertNotNull(spotifyDAO.getSongInterval(playlistId, startTime, endTime));
        // Add more assertions as needed
    }

    @Test
    void testGetSongs() {
        String playlistId = "your_playlist_id_here";
        assertNotNull(spotifyDAO.getSongs(playlistId));
        // Add more assertions as needed
    }

    @Test
    void testGetSongsWithPopularity() {
        String playlistId = "your_playlist_id_here";
        assertNotNull(spotifyDAO.getSongsWithPopularity(playlistId));
        // Add more assertions as needed
    }

    @Test
    void testGetUserId() {
        assertDoesNotThrow(() -> {
            spotifyDAO.getUserId();
        });
        // Add more assertions as needed
    }

    @Test
    void testCreatePlaylist() {
        String playlistName = "Test Playlist";
        String userId = "your_user_id_here";
        assertDoesNotThrow(() -> {
            spotifyDAO.createPlaylist(playlistName, userId);
        });
        // Add more assertions as needed
    }

    @Test
    void testGetAccountName() {
        assertDoesNotThrow(() -> {
            spotifyDAO.getAccountName();
        });
        // Add more assertions as needed
    }
    @Test
    void testAddSongsToPlaylist() {
        String playlistId = "your_playlist_id_here";
        List<String> songIds = Arrays.asList("song_id_1", "song_id_2");
        assertDoesNotThrow(() -> {
            spotifyDAO.addSongsToPlaylist(playlistId, songIds);
        });
        // Add more assertions as needed
    }


    @Test
    void testGetSongsAudioFeatures() {
        List<String> songIds = Arrays.asList("song_id_1", "song_id_2");
        assertDoesNotThrow(() -> {
            spotifyDAO.getSongsAudioFeatures(songIds);
        });

        // Add more assertions as needed
    }

}
