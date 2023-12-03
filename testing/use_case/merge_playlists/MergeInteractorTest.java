package use_case.merge_playlists;

import entity.Song;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MergeInteractorTest {

    private MergeDataAccessInterface mergeDAO;
    private MockMergePresenter mockMergePresenter;
    private MergeInteractor mergeInteractor;

    @BeforeEach
    void init() {
        mergeDAO = new MockMergeDAO();
        mockMergePresenter = new MockMergePresenter();
        mergeInteractor = new MergeInteractor(mergeDAO, mockMergePresenter);
    }

    @Test
    void mergePlaylists() {

    }

    @Test
    void getPlaylists() {
        List<String> expectedNames = new ArrayList<>();
        expectedNames.add("Playlist 1");
        expectedNames.add("Playlist 2");

        Set<String> expectedNamesSet = new HashSet<>(expectedNames);

        List<String> result = mergeInteractor.getPlaylists();
        Set<String> resultsNamesSet = new HashSet<>(result);

        // Don't care about the order
        assertEquals(expectedNamesSet, resultsNamesSet);
    }

    @Test
    void returnHome() {

    }

    private class MockMergePresenter implements MergeOutputBoundary {

        public MergeOutputData outputData = null;
        @Override
        public void prepareSuccessView(MergeOutputData response) {
            outputData = response;
        }

        @Override
        public void prepareFailView(String error) {

        }
    }


    // Mock MergeDAO class
    // only supported new playlist name is New Playlist
    private class MockMergeDAO implements MergeDataAccessInterface {
        private Map<String, String> playlistMap;
        private Map<String, List<Song>> playlistMapToSongs;
        private Map<String, Map<String, String>> songIDToAudioFeaturesMap;
        private Song song1;
        private Song song2;
        private Song song3;

        private final String USER_ID = "UserID";
        public MockMergeDAO() {
            // Assign this DAO certain playlists and songs similar to those in the actual DAO
            // Assume the user has these two playlists
            playlistMap = new HashMap<>();
            playlistMap.put("Playlist 1", "playlistid1");
            playlistMap.put("Playlist 2", "playlistid2");


            playlistMapToSongs = new HashMap<>();
            songIDToAudioFeaturesMap = new HashMap<>();

            Map<String, Long> artistsSong1 = new HashMap<>();
            Map<String, Long> artistsSong2 = new HashMap<>();
            Map<String, Long> artistsSong3 = new HashMap<>();

            artistsSong1.put("Artist1", 1L);
            artistsSong2.put("Artist2", 2L);
            artistsSong3.put("Artist3", 3L);

            song1 = new Song("songid1", "Song 1", artistsSong1);
            song2 = new Song("songid2", "Song 2", artistsSong2);
            song3 = new Song("songid3", "Song 3", artistsSong3);

            List<Song> playlist1 = new ArrayList<>();
            List<Song> playlist2 = new ArrayList<>();

            playlist1.add(song1);
            playlist1.add(song2);

            playlist2.add(song3);

            playlistMapToSongs.put("playlistid1", playlist1);
            playlistMapToSongs.put("playlistid2", playlist2);

            // Give audio features map to each song
            Map<String, String> audioFeatures1 = new HashMap<>();
            Map<String, String> audioFeatures2 = new HashMap<>();
            Map<String, String> audioFeatures3 = new HashMap<>();

            audioFeatures1.put("valence", "0.95");
            audioFeatures1.put("tempo", "120.0");
            audioFeatures1.put("instrumentalness", "0.9");

            audioFeatures2.put("valence", "0.10");
            audioFeatures2.put("tempo", "80.0");
            audioFeatures2.put("instrumentalness", "0.05");

            audioFeatures3.put("valence", "0.50");
            audioFeatures3.put("tempo", "100.0");
            audioFeatures3.put("instrumentalness", "0.2");

            songIDToAudioFeaturesMap.put("songid1", audioFeatures1);
            songIDToAudioFeaturesMap.put("songid2", audioFeatures2);
            songIDToAudioFeaturesMap.put("songid3", audioFeatures3);
        }


        @Override
        public Map<String, String> getPlaylistMap() {
            return playlistMap;
        }

        @Override
        public List<Song> getSongs(String playlistID) {
            return playlistMapToSongs.get(playlistID);
        }

        @Override
        public String getUserId() throws IOException, InterruptedException, ParseException {
            return USER_ID;
        }

        @Override
        public String createPlaylist(String playlistName, String userId) throws IOException, InterruptedException, ParseException {
            if (userId.equals(USER_ID)) {
                List<Song> newSongList = new ArrayList<>();
                playlistMap.put("New Playlist", "playlistid3");
                playlistMapToSongs.put(playlistMap.get(playlistName), newSongList);
                return playlistName;
            }
            return null;
        }

        @Override
        public void addSongsToPlaylist(String playlistId, List<String> songIds) throws IOException, InterruptedException {
            for (String songId : songIds) {
                if (songId.equals(song1.getId())) playlistMapToSongs.get(playlistId).add(song1);
                if (songId.equals(song2.getId())) playlistMapToSongs.get(playlistId).add(song2);
                if (songId.equals(song3.getId())) playlistMapToSongs.get(playlistId).add(song3);
            }
        }

        @Override
        public List<Map<String, String>> getSongsAudioFeatures(List<String> songIds) {
            List<Map<String, String>> audioFeatures = new ArrayList<>();

            for (int i = 0; i < songIds.size(); i++) {
                audioFeatures.add(songIDToAudioFeaturesMap.get(songIds.get(i)));
            }
            return audioFeatures;
        }
    }
}