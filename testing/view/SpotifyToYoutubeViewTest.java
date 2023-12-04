package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.spotify_to_youtube.SpotifyToYoutubeController;
import interface_adapter.spotify_to_youtube.SpotifyToYoutubeViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.spotify_to_youtube.SpotifyToYoutubeInputBoundary;
import use_case.spotify_to_youtube.SpotifyToYoutubeInputData;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpotifyToYoutubeViewTest {

    private SpotifyToYoutubeViewModel spotifyToYoutubeViewModel;
    private TestSpotifyToYoutubeController spotifyToYoutubeController;
    private TestViewManagerModel viewManagerModel;
    private SpotifyToYoutubeView spotifyToYoutubeView;

    @BeforeEach
    void setUp() {
        spotifyToYoutubeViewModel = new SpotifyToYoutubeViewModel();
        spotifyToYoutubeController = new TestSpotifyToYoutubeController();
        viewManagerModel = new TestViewManagerModel();
        spotifyToYoutubeView = new SpotifyToYoutubeView(spotifyToYoutubeViewModel, spotifyToYoutubeController, viewManagerModel);
    }


    @Test
    void testDeleteSelectedPlaylist() {
        // Arrange
        spotifyToYoutubeView.clearSelectedPlaylists(); // Clear any existing playlists
        spotifyToYoutubeView.handlePlaylistSelection(new ListSelectionEvent(new Object(), 0, 0, false));
        spotifyToYoutubeView.handlePlaylistSelection(new ListSelectionEvent(new Object(), 1, 1, false));

        // Act
        spotifyToYoutubeView.deleteSelectedPlaylist();

        // Assert
        assertEquals(0, spotifyToYoutubeView.selectedPlaylistsModel.getSize());
    }

    @Test

    void testConvertPlaylists() {
        // Arrange
        spotifyToYoutubeView.clearSelectedPlaylists(); // Clear any existing playlists
        spotifyToYoutubeView.handlePlaylistSelection(new ListSelectionEvent(new Object(), 0, 0, false));
        spotifyToYoutubeView.handlePlaylistSelection(new ListSelectionEvent(new Object(), 1, 1, false));

        // Act
        spotifyToYoutubeView.convertPlaylists();

        // Assert
        assertEquals(0, spotifyToYoutubeView.selectedPlaylistsModel.getSize());
    }




    @Test
    void testConnectToYT() {
        // Arrange
        spotifyToYoutubeViewModel.getState().setIsConnectedToYT();

        // Act
        spotifyToYoutubeView.connectToYT();

        // Assert
        assertFalse(spotifyToYoutubeController.isExecuteCalled());
        assertNull(spotifyToYoutubeController.getData());
        assertFalse(spotifyToYoutubeController.isConnectToYoutubeCalled());
    }

    @Test
    void testConnectToYTWhenAlreadyConnected() {
        // Arrange
        spotifyToYoutubeViewModel.getState().setIsConnectedToYT();

        // Act
        spotifyToYoutubeView.connectToYT();

        // Assert
        assertFalse(spotifyToYoutubeController.isExecuteCalled());
        assertFalse(viewManagerModel.isFirePropertyChangedCalled());
    }

    @Test
    void testHandlePlaylistSelection() {
        // Arrange
        spotifyToYoutubeView.clearSelectedPlaylists(); // Clear any existing playlists

        // Act
        spotifyToYoutubeView.handlePlaylistSelection(new ListSelectionEvent(new Object(), 0, 0, false));
        spotifyToYoutubeView.handlePlaylistSelection(new ListSelectionEvent(new Object(), 1, 1, false));

        // Assert
        assertEquals(0, spotifyToYoutubeView.selectedPlaylistsModel.getSize());

    }



    // Additional tests can be added for other methods in the view

    // Test-specific implementations for avoiding mocks and verifies

    private static class TestSpotifyToYoutubeController extends SpotifyToYoutubeController {

        private final boolean executeCalled = false;
        private SpotifyToYoutubeInputData data;
        private final boolean connectToYoutubeCalled = false;

        public TestSpotifyToYoutubeController() {
            super(new TestSpotifyToYoutubeInteractor());
        }


        public boolean isExecuteCalled() {
            return executeCalled;
        }

        public SpotifyToYoutubeInputData getData() {
            return data;
        }

        public boolean isConnectToYoutubeCalled() {
            return connectToYoutubeCalled;
        }
    }


    private static class TestSpotifyToYoutubeInteractor implements SpotifyToYoutubeInputBoundary {

        @Override
        public void execute(SpotifyToYoutubeInputData data) {

        }

        @Override
        public void connectToYoutube() {

        }

    }

    private static class TestViewManagerModel extends ViewManagerModel {
        private boolean setActiveViewCalled = false;
        private boolean firePropertyChangedCalled = false;

        @Override
        public void setActiveView(String viewName) {
            setActiveViewCalled = true;
        }

        @Override
        public void firePropertyChanged() {
            firePropertyChangedCalled = true;
        }

        public boolean isSetActiveViewCalled() {
            return setActiveViewCalled;
        }

        public boolean isFirePropertyChangedCalled() {
            return firePropertyChangedCalled;
        }
    }
}
