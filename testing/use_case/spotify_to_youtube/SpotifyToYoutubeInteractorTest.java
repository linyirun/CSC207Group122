package use_case.spotify_to_youtube;

import entity.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SpotifyToYoutubeInteractorTest {

    private SpotifyToYoutubeDataAccessInterface dao;
    private SpotifyToYoutubeDataAccessInterfaceForSpotify dao_2;
    private SpotifyToYoutubeOutputBoundary presenter;
    private SpotifyToYoutubeInteractor interactor;

    @BeforeEach
    void setUp() {
        dao = new SpotifyToYoutubeDataAccessInterfaceStub();
        dao_2 = new SpotifyToYoutubeDataAccessInterfaceForSpotifyStub();
        presenter = new SpotifyToYoutubeOutputBoundaryStub();
        interactor = new SpotifyToYoutubeInteractor(dao, dao_2, presenter);
    }

    @Test
    void execute_Success() {
        // Arrange
        SpotifyToYoutubeInputData inputData = new SpotifyToYoutubeInputData(
                Arrays.asList("Playlist1", "Playlist2"),
                Arrays.asList("Artist1", "Artist2")
        );

        // Act
        interactor.execute(inputData);

        // Assert
        List<SpotifyToYoutubeOutputData> successDataList = ((SpotifyToYoutubeOutputBoundaryStub) presenter).getSuccessDataList();
        assertEquals(2, successDataList.size());

        for (SpotifyToYoutubeOutputData successData : successDataList) {
            assertEquals("Playlist successfully created", successData.getMessage());
//            assertFalse(successData.getErrored());
        }

        List<SpotifyToYoutubeOutputData> failDataList = ((SpotifyToYoutubeOutputBoundaryStub) presenter).getFailDataList();
        assertEquals(0, failDataList.size());
    }

    @Test
    void connectToYoutube_Success() throws IOException, InterruptedException {
        // Act
        interactor.connectToYoutube();
        // Assert
        List<SpotifyToYoutubeOutputData> successDataList = ((SpotifyToYoutubeOutputBoundaryStub) presenter).getSuccessDataList();
        assertEquals(1, successDataList.size());
        assertEquals("Connected to YouTube", successDataList.get(0).getMessage());
//        assertFalse(successDataList.get(0).getErrored());

        List<SpotifyToYoutubeOutputData> failDataList = ((SpotifyToYoutubeOutputBoundaryStub) presenter).getFailDataList();
        assertEquals(0, failDataList.size());
    }
    

    // Stub for SpotifyToYoutubeDataAccessInterface
    private static class SpotifyToYoutubeDataAccessInterfaceStub implements SpotifyToYoutubeDataAccessInterface {

        private List<String> videoIds;

        public void setVideoIds(List<String> videoIds) {
            this.videoIds = videoIds;
        }

        @Override
        public List<String> searchVideos(List<Song> songs) {
            return videoIds;
        }

        @Override
        public void createPlaylist(String playlistName, List<String> videoIDs) {
            // Not implemented for this stub
        }
    }

    // Stub for SpotifyToYoutubeDataAccessInterfaceForSpotify
    // Stub for SpotifyToYoutubeDataAccessInterfaceForSpotify
    private static class SpotifyToYoutubeDataAccessInterfaceForSpotifyStub implements SpotifyToYoutubeDataAccessInterfaceForSpotify {

        private List<Song> songs;
        private Map<String, String> playlistMap;
        private boolean throwException;

        public void setSongs(List<Song> songs) {
            this.songs = songs;
        }

        public void setPlaylistMap(Map<String, String> playlistMap) {
            this.playlistMap = playlistMap;
        }

        public void setThrowException(boolean throwException) {
            this.throwException = throwException;
        }

        @Override
        public List<Song> getSongs(String playlistID) {
            return songs;
        }

        @Override
        public Map<String, String> getPlaylistMap() {
            HashMap<String, String> playlistMap = new HashMap<>();
            playlistMap.put("Playlist1", "Playlist1_id");
            playlistMap.put("Playlist2", "Playlist2_id");
            return playlistMap;
        }
    }

    // Stub for SpotifyToYoutubeOutputBoundary
    private static class SpotifyToYoutubeOutputBoundaryStub implements SpotifyToYoutubeOutputBoundary {

        private List<SpotifyToYoutubeOutputData> successDataList = new ArrayList<>();
        private List<SpotifyToYoutubeOutputData> failDataList = new ArrayList<>();

        @Override
        public void prepareSuccessView(SpotifyToYoutubeOutputData data) {
            this.successDataList.add(data);
        }

        @Override
        public void prepareFailView(SpotifyToYoutubeOutputData failData) {
            this.failDataList.add(failData);
        }

        public List<SpotifyToYoutubeOutputData> getSuccessDataList() {
            return successDataList;
        }

        public List<SpotifyToYoutubeOutputData> getFailDataList() {
            return failDataList;
        }
    }
}
