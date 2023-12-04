package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.artists_playlist_maker.ArtistsPmController;
import interface_adapter.artists_playlist_maker.ArtistsPmViewModel;
import interface_adapter.merge_playlists.MergeController;
import interface_adapter.merge_playlists.MergeViewModel;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.artists_playlist_maker.ArtistsPmInputBoundary;
import use_case.artists_playlist_maker.ArtistsPmInputData;
import use_case.merge_playlists.MergeInputBoundary;
import use_case.merge_playlists.MergeInputData;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MergeViewTest {

    private MergeViewModel mergeViewModel;
    private TestMergeController mergeController;
    private TestViewManagerModel viewManagerModel;
    private MergeView mergeView; ;

    @BeforeEach
    void setUp() {
        mergeViewModel = new MergeViewModel();
        mergeController = new TestMergeController();
        viewManagerModel = new TestViewManagerModel();
        mergeView = new MergeView(mergeViewModel, mergeController, viewManagerModel);
    }




    @Test
    void testCreateMergeInputData() {
        // Arrange
        mergeView.instrumentalRadioButton.setSelected(true);
        mergeView.sadValenceRadioButton.setSelected(true);
        mergeView.slowTempoRadioButton.setSelected(true);

        List<String> selectedPlaylists = Arrays.asList("Playlist1", "Playlist2");
        String givenName = "NewPlaylist";

        // Act
        MergeInputData mergeInputData = mergeView.createMergeInputData(selectedPlaylists, givenName);

        // Assert
        assertNotNull(mergeInputData);
        assertEquals(MergeInputData.INSTRUMENTAL_CHOICE, mergeInputData.getInstrumentalChoice());
        assertEquals(MergeInputData.SAD_CHOICE, mergeInputData.getValenceChoice());
        assertEquals(MergeInputData.SLOW_CHOICE, mergeInputData.getTempoChoice());
    }

    @Test
    void testMergePlaylists() {
        // Arrange
        List<String> selectedPlaylists = Arrays.asList("Playlist1", "Playlist2");
        mergeView.selectedPlaylistsModel.addAll(selectedPlaylists);

        // Act
        mergeView.mergePlaylists();

        // Assert
        assertTrue(mergeView.selectedPlaylistsModel.isEmpty());
    }

    @Test
    void testDeleteSelectedPlaylist() {
        // Arrange
        mergeView.selectedPlaylistsModel.addElement("Playlist1");
        mergeView.selectedPlaylistsList.setSelectedIndex(0);

        // Act
        mergeView.deleteSelectedPlaylist();

        // Assert
        assertTrue(mergeView.selectedPlaylistsModel.isEmpty());
    }


    // Additional tests can be added for other methods in the view

    // Test-specific implementations for avoiding mocks and verifies

    private static class TestMergeController extends MergeController {

        /**
         * Constructs with the provided {@code ArtistsPmInputBoundary}.
         */
        public TestMergeController() {
            super(new TestMergeInteractor());
        }


    }

    private static class TestMergeInteractor implements MergeInputBoundary {



        @Override
        public void mergePlaylists(MergeInputData data) {

        }

        @Override
        public void returnHome() {

        }

        @Override
        public List<String> getPlaylists() {
            return Arrays.asList("Playlist1", "Playlist2");
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
