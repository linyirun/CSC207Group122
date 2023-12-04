package data_access;

import data_access.YouTubeDataAccessObject;
import entity.Song;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class YouTubeDataAccessObjectTest {

    // Replace these with your actual YouTube API key and access token


    @Test
    void addVideosToPlaylist_shouldAddVideosToPlaylist() {
        YouTubeDataAccessObject youTubeDAO = new YouTubeDataAccessObject();


        // Replace with your actual playlist ID and video IDs
        String playlistId = "YOUR_PLAYLIST_ID";
        List<String> videoIds = List.of("VIDEO_ID_1", "VIDEO_ID_2", "VIDEO_ID_3");

        try {
            youTubeDAO.addVideosToPlaylist(playlistId, videoIds);
        } catch (IOException | ParseException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    void searchVideos_shouldReturnVideoIds() {
        YouTubeDataAccessObject youTubeDAO = new YouTubeDataAccessObject();


        // Replace with your actual Song objects
        List<Song> songs = new ArrayList<>();
        Song song = new Song("SONG_ID", "Song Name", Map.of("Artist 1", 0L, "Artist 2", 0L));
        songs.add(song);

        List<String> videoIds = youTubeDAO.searchVideos(songs);
        assertNotNull(videoIds);
        assertFalse(videoIds.isEmpty());
    }

    @Test
    void createPlaylist_shouldCreatePlaylist() {
        YouTubeDataAccessObject youTubeDAO = new YouTubeDataAccessObject();


        // Replace with your actual playlist name and video IDs
        String playlistName = "Test Playlist";
        List<String> videoIds = List.of("VIDEO_ID_1", "VIDEO_ID_2", "VIDEO_ID_3");

        youTubeDAO.createPlaylist(playlistName, videoIds);
    }
}
