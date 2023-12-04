package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileViewTest {

    private ProfileViewModelStub profileViewModelStub;
    private ViewManagerModelStub viewManagerModelStub;
    private ProfileView profileView;

    @BeforeEach
    void setUp() {
        profileViewModelStub = new ProfileViewModelStub();
        viewManagerModelStub = new ViewManagerModelStub();
        profileView = new ProfileView(profileViewModelStub, viewManagerModelStub);
    }

    @Test
    void testPropertyChange() {
        Map<String, List<String>> mockProfileObjects = new HashMap<>();

        // Mock data for tracks
        List<String> mockTracks = new ArrayList<>();
        mockTracks.add("Track1");
        mockTracks.add("Track2");
        mockTracks.add("Track3");

        // Mock data for artists
        List<String> mockArtists = new ArrayList<>();
        mockArtists.add("Artist1");
        mockArtists.add("Artist2");
        mockArtists.add("Artist3");

        // Put the mock data into the map
        mockProfileObjects.put("tracks", mockTracks);
        mockProfileObjects.put("artists", mockArtists);
        ProfileState state = new ProfileState();
        state.setProfileObjects(mockProfileObjects);
        profileViewModelStub.setState(state);
        PropertyChangeEvent event = new PropertyChangeEvent(profileView, "state", "Test", "40");
        profileView.propertyChange(event);
    }


    private static class ProfileViewModelStub extends ProfileViewModel {

        private final PropertyChangeSupport support = new PropertyChangeSupport(this);
        private ProfileState state;

        public void setState(ProfileState state) {
            this.state = state;
        }

        public void firePropertyChanged() {
            support.firePropertyChange("state", null, this.state);
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {
            support.addPropertyChangeListener(listener);
        }
        public ProfileState getState(){
            return this.state;
        }
    }

    private static class ViewManagerModelStub extends ViewManagerModel {

        private final PropertyChangeSupport support = new PropertyChangeSupport(this);
        private String activeView;

        public String getActiveView() {
            return activeView;
        }

        @Override
        public void setActiveView(String activeView) {
            this.activeView = activeView;
        }

        @Override
        public void firePropertyChanged() {
            support.firePropertyChange("view", null, this.activeView);
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {
            support.addPropertyChangeListener(listener);
        }
    }
}
