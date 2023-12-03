package interface_adapter.playlists;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PlaylistsViewModel extends ViewModel {
    public static final String GET_PLAYLIST_BUTTON_LABEL = "get/refresh playlists";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private PlaylistsState state = new PlaylistsState();

    public PlaylistsViewModel() {
        super("Playlists");
    }

    public void firePropertyChanged() {
        System.out.println("viewmodel fire property change" + state.getPlaylistMap().keySet());
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public PlaylistsState getState() {
        return state;
    }

    public void setState(PlaylistsState givenState) {
        this.state = givenState;
    }
}
