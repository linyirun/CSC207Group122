package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.artists_playlist_maker.ArtistsPmController;
import interface_adapter.artists_playlist_maker.ArtistsPmViewModel;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.artists_playlist_maker.ArtistsPmInputBoundary;
import use_case.artists_playlist_maker.ArtistsPmInputData;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ArtistsPlaylistMakerViewTest {

    private ArtistsPmViewModel artistsPmViewModel;
    private TestArtistsPmController artistsPmController;
    private TestViewManagerModel viewManagerModel;
    private ArtistsPlaylistMakerView artistsPlaylistMakerView;

    @BeforeEach
    void setUp() {
        artistsPmViewModel = new ArtistsPmViewModel();
        artistsPmController = new TestArtistsPmController();
        viewManagerModel = new TestViewManagerModel();
        artistsPlaylistMakerView = new ArtistsPlaylistMakerView(artistsPmViewModel, artistsPmController, viewManagerModel);
    }

    @Test
    void searchButtonClicked() {
        // Arrange
        artistsPlaylistMakerView.searchField.setText("Artist1");

        // Act
        artistsPlaylistMakerView.searchButtonClicked();

        // Assert
        assertEquals(2, artistsPlaylistMakerView.searchResultsModel.size());
        assertTrue(artistsPlaylistMakerView.searchResultsModel.contains("Artist1"));
    }

    @Test
    void handleSearchResultsSelection() {
        // Arrange
        artistsPlaylistMakerView.searchResultsModel.addElement("Artist1");
        artistsPlaylistMakerView.searchResultsList.setSelectedIndex(0);

        // Act
        artistsPlaylistMakerView.handleSearchResultsSelection();

        // Assert
        assertEquals(1, artistsPlaylistMakerView.selectedArtistsModel.size());
        assertTrue(artistsPlaylistMakerView.selectedArtistsModel.contains("Artist1"));
    }

    @Test
    void createPlaylistButtonClicked() {
        // Arrange
        artistsPlaylistMakerView.selectedArtistsModel.addElement("Artist1");
        artistsPlaylistMakerView.includeInPlaylistCheckBox.setSelected(true);

        // Act
        artistsPlaylistMakerView.createPlaylistButtonClicked();

        // Assert
        assertTrue(artistsPlaylistMakerView.selectedArtistsModel.isEmpty());
    }

    // Additional tests can be added for other methods in the view

    // Test-specific implementations for avoiding mocks and verifies

    private static class TestArtistsPmController extends ArtistsPmController {

        /**
         * Constructs with the provided {@code ArtistsPmInputBoundary}.
         */
        public TestArtistsPmController() {
            super(new TestArtistsPmInteractor());
        }

        @Override
        public void createPlaylist(List<String> selectedArtists, int numberOfSongs, boolean includeInPlaylist) {
            // No need to implement for testing
        }
    }

    private static class TestArtistsPmInteractor implements ArtistsPmInputBoundary {



        @Override
        public List<String> showTopArtists(ArtistsPmInputData inputData) throws IOException, ParseException, InterruptedException {
            return Arrays.asList("Artist1", "Artist2");
        }

        @Override
        public void createPlaylist(ArtistsPmInputData inputData) {

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
