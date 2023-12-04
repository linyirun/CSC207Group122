package use_case.playlists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class PlaylistsInteractorTest {

    private PlaylistsUserDataAccessInterfaceStub dao;
    private PlaylistsOutputBoundaryStub presenter;
    private PlaylistsInteractor interactor;

    @BeforeEach
    void setUp() {
        dao = new PlaylistsUserDataAccessInterfaceStub();
        presenter = new PlaylistsOutputBoundaryStub();
        interactor = new PlaylistsInteractor(dao, presenter);
    }

    @Test
    void execute_Success() {
        // Arrange
        Map<String, String> playlistMap = new HashMap<>();
        playlistMap.put("Playlist1", "Description1");
        playlistMap.put("Playlist2", "Description2");

        dao.setPlaylistMap(playlistMap);

        // Act
        interactor.execute();

        // Assert
        assertEquals(playlistMap, presenter.getSuccessOutputData().getPlaylistMap());
        assertFalse(presenter.isFailViewPrepared());
    }



    // Stub for PlaylistsUserDataAccessInterface
    private static class PlaylistsUserDataAccessInterfaceStub implements PlaylistsUserDataAccessInterface {
        private Set<String> playlists;
        private Map<String, String> playlistMap;
        private Exception exception;

        public void setException(Exception exception) {
            this.exception = exception;
        }

        @Override
        public Set<String> getPlaylists() {
            if (exception != null) {
                throw new RuntimeException(exception);
            }
            return playlists;
        }

        public void setPlaylists(Set<String> playlists) {
            this.playlists = playlists;
        }

        @Override
        public Map<String, String> getPlaylistMap() {
            if (exception != null) {
                throw new RuntimeException(exception);
            }
            return playlistMap;
        }

        public void setPlaylistMap(Map<String, String> playlistMap) {
            this.playlistMap = playlistMap;
        }
    }

    // Stub for PlaylistsOutputBoundary
    private static class PlaylistsOutputBoundaryStub implements PlaylistsOutputBoundary {
        private PlaylistsOutputData successOutputData;
        private boolean failViewPrepared;

        @Override
        public void prepareSuccessView(PlaylistsOutputData outputData) {
            successOutputData = outputData;
        }

        @Override
        public void prepareFailView(String error) {
            failViewPrepared = true;
        }

        public PlaylistsOutputData getSuccessOutputData() {
            return successOutputData;
        }

        public boolean isFailViewPrepared() {
            return failViewPrepared;
        }
    }
}
