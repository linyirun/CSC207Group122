package interface_adapter.home;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class HomeViewModel extends ViewModel {
    public static final String VIEW_NAME = "Home";

    public static final String SPLIT_PLAYLIST_NAME = "Split Playlist";
    public static final String MERGE_PLAYLIST_NAME = "Merge Playlist";
    public static final String ARTISTS_PLAYLIST_MAKER_NAME = "Artists Playlist Maker";
    public static final String SPOTIFY_TO_YT_NAME = "Spotify To Youtube";
    public static final String LISTEN = "Listen";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private HomeState state = new HomeState();

    public HomeViewModel() {
        super("Home");
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
