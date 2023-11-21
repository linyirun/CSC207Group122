package interface_adapter.artists_playlist_maker;

import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ArtistsPmViewModel extends ViewModel {

    public static final String ARTISTS_BUTTON_LABEL = "ENTER AN ARTIST";

    private ArtistsPmState state = new ArtistsPmState();

    public ArtistsPmViewModel() {
        super("Artists Playlist Maker");
    }

    public void setState(ArtistsPmState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Signup Presenter will call to let the ViewModel know
    // to alert the View
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public ArtistsPmState getState() {
        return state;
    }
}
