package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.playlists.PlaylistsController;
import interface_adapter.playlists.PlaylistsState;
import interface_adapter.playlists.PlaylistsViewModel;
import interface_adapter.split_playlist.SplitController;
import interface_adapter.split_playlist.SplitState;
import interface_adapter.split_playlist.SplitViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.split_playlist.SplitInputData;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SplitViewTest {

    private SplitViewModelStub splitViewModelStub;
    private ViewManagerModelStub viewManagerModelStub;
    private PlaylistsViewModelStub playlistsViewModelStub;
    private SplitController splitController;
    private PlaylistsController playlistsController;
    private SplitView splitView;

    @BeforeEach
    void setUp() {
        splitViewModelStub = new SplitViewModelStub();
        viewManagerModelStub = new ViewManagerModelStub();
        playlistsViewModelStub = new PlaylistsViewModelStub();
        splitController = new SplitControllerStub();
        playlistsController = new PlaylistsControllerStub();
        splitView = new SplitView(splitController, splitViewModelStub, playlistsController, playlistsViewModelStub, viewManagerModelStub);
    }

    @Test
    void createPanelTest() {
        splitView.createPlaylistPanel();
    }
    @Test
    void showMessageTestEmpty(){
        splitView.showMessage("");
    }
    @Test
    void showMessageTestNonEmpty(){
        splitView.showMessage("WDwda");
    }
    @Test
    void showMessageTestInputError(){

        splitView.showMessage("input error");
    }
    @Test
    void splitPlaylistTest(){
        Map<String, String> mockPlaylistMap = new HashMap<>();
        mockPlaylistMap.put("Playlist1", "ID1");
        mockPlaylistMap.put("Playlist2", "ID2");
        PlaylistsState state = new PlaylistsState();
        state.setPlaylistMap(mockPlaylistMap);

        PropertyChangeEvent event = new PropertyChangeEvent(splitView, "state", "Test", state);
        splitView.propertyChange(event);

    }
    @Test
    void splitPlaylistTest2(){
        PropertyChangeEvent event = new PropertyChangeEvent(splitView, "state", "Test", "Split Playlist");
        splitView.propertyChange(event);

    }

    @Test
    void TestActionListener(){
        ActionEvent evt = new ActionEvent(splitView.splitByLength, 30, "");
        splitView.buttonActionListener.actionPerformed(evt);
    }

    private static class SplitViewModelStub extends SplitViewModel {
        public static final String SPLIT_BY_LENGTH = "Split by length";
        public static final String SPLIT_BY_ARTISTS = "Split by artists";
        private final PropertyChangeSupport support = new PropertyChangeSupport(this);
        private SplitState state = new SplitState();

        public void SplitViewModel() {

        }

        public void firePropertyChanged() {
            support.firePropertyChange("fail to split", null, "fail");
        }

        public void addPropertyChangeListener(PropertyChangeListener listener) {
            support.addPropertyChangeListener(listener);
        }

        public SplitState getState() {
            return state;
        }

        public void setState(SplitState givenState) {
            this.state = givenState;
        }

        public String toString() {
            return state.getCreatedPlaylistNames();
        }
    }

    public class ViewManagerModelStub extends ViewManagerModel {
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

    private static class PlaylistsViewModelStub extends PlaylistsViewModel {
        private final PropertyChangeSupport support = new PropertyChangeSupport(this);
        private PlaylistsState state;

        public void setState(PlaylistsState state) {
            this.state = state;
        }

        public void firePropertyChanged() {
            support.firePropertyChange("state", null, this.state);
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {
            support.addPropertyChangeListener(listener);
        }
    }
    public class SplitControllerStub extends SplitController {

        public SplitControllerStub() {
            super(null);
        }

        @Override
        public void execute(SplitInputData inputData) {
            // You can customize this method based on your testing needs
            // For example, update the viewModelStub with certain data

        }
    }
    public class PlaylistsControllerStub extends PlaylistsController {

        public PlaylistsControllerStub() {
            super(null);
        }

        @Override
        public void execute() {
            // You can customize this method based on your testing needs

        }

    }

}
