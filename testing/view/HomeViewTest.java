package view;

import entity.Song;
import interface_adapter.ViewManagerModel;
import interface_adapter.home.HomeController;
import interface_adapter.home.HomeState;
import interface_adapter.home.HomeViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.Lyrics.LyricsInputBoundary;
import use_case.SpotifyPlayer.WebPlaybackInputBoundary;
import use_case.home.HomeInputBoundary;
import view.HomeView;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class HomeViewTest {

    private HomeControllerStub controllerStub;
    private HomeViewModelStub viewModelStub;
    private ViewManagerModelStub viewManagerModelStub;
    private HomeView homeView;

    @BeforeEach
    public void setUp() {
        // Create stubs
        controllerStub = new HomeControllerStub();
        viewModelStub = new HomeViewModelStub();
        viewManagerModelStub = new ViewManagerModelStub();

        // Create the HomeView with stubs
        homeView = new HomeView(controllerStub, viewModelStub, viewManagerModelStub);
    }
    @Test
    public void testButtonClickCallsController() {
        // Given
        String buttonText = HomeViewModelStub.SPLIT_PLAYLIST_NAME;

        // When
        homeView.actionPerformed(createActionEvent(buttonText));
        System.out.println(controllerStub.executeCalled);
        System.out.println(controllerStub.lastExecutedCommand);
//        // Then
//        assertTrue(controllerStub.executeCalled);
//        assertEquals(buttonText, controllerStub.lastExecutedCommand);
    }
    @Test
    public void testPlaylistsListSelectionListener() {
        // Given
        String selectedPlaylist = "Test Playlist";

        // When
        homeView.playlistsList.setSelectedValue(selectedPlaylist, true);
        System.out.println(homeView.playlistsList.getSelectedValue());
    }
    @Test
    public void testLyricsExcecuted() {
        // Given
        String selectedPlaylist = "Test Playlist";

        // When
        homeView.actionOnPressSong("hello");
        System.out.println(homeView.playlistsList.getSelectedValue());
        assertTrue(controllerStub.executeCalled);
    }
    @Test
    public void testUpdateSongs() {
        // Given
        String selectedPlaylist = "Test Playlist";

        // When
        homeView.updateSongs("hello");
        System.out.println(homeView.playlistsList.getSelectedValue());
        assertTrue(controllerStub.executeCalled);
    }

    // Add more test cases based on your specific functionality

    // Helper method to create a stub ActionEvent
    private ActionEvent createActionEvent(String command) {
        return new ActionEvent(this, ActionEvent.ACTION_PERFORMED, command);
    }

    // Stub implementations for HomeController, HomeViewModel, and ViewManagerModel
    private static class HomeControllerStub extends HomeController {
        boolean executeCalled = false;
        boolean getSongsCalled = false;

        public String lastExecutedCommand;
        String lastGetSongsPlaylistID;


        public HomeControllerStub() {
            super(null, null, null);
        }

        @Override
        public void execute(String command) {
//            System.out.println("excecute");
            this.executeCalled = true;
            this.lastExecutedCommand = command;
        }

        @Override
        public List<Song> getSongs(String playlistID) {
            getSongsCalled = true;
            lastGetSongsPlaylistID = playlistID;
            // Return a dummy list or handle as needed for testing
            this.executeCalled = true;
            return Collections.emptyList();
        }
        public String getLyrics(String songName) {
            this.executeCalled = true;
            return "hey";
        }
    }

    private static class HomeViewModelStub extends HomeViewModel {
        public static final String VIEW_NAME = "Home";

        public static final String SPLIT_PLAYLIST_NAME = "Split Playlist";
        public static final String MERGE_PLAYLIST_NAME = "Merge Playlist";
        public static final String ARTISTS_PLAYLIST_MAKER_NAME = "Artists Playlist Maker";
        public static final String SPOTIFY_TO_YT_NAME = "Spotify To Youtube";
        public static final String LISTEN = "Listen";

        private final PropertyChangeSupport support = new PropertyChangeSupport(this);

        private HomeState state = new HomeState();

        public  HomeViewModelStub() {
            super();
        }

        // This is what the Signup Presenter will call to let the ViewModel know
        // to alert the View
        public void firePropertyChanged() {
            support.firePropertyChange("state", null, this.state);
        }

        public void addPropertyChangeListener(PropertyChangeListener listener) {
            support.addPropertyChangeListener(listener);
        }

        public HomeState getState() {
            return state;
        }

        public void setState(HomeState state) {
            this.state = state;
        }
    }

    private static class ViewManagerModelStub extends ViewManagerModel {
        private final PropertyChangeSupport support = new PropertyChangeSupport(this);
        private String activeViewName;

        public void setActiveView(String activeView) {
            this.activeViewName = activeView;
        }

        public void firePropertyChanged() {
            support.firePropertyChange("view", null, this.activeViewName);
        }

        public void addPropertyChangeListener(PropertyChangeListener listener) {
            support.addPropertyChangeListener(listener);
        }
    }
}
