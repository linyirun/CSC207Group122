package interface_adapter.artists_playlist_maker;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ArtistsPmViewModel extends ViewModel {

    public static final String ARTISTS_BUTTON_LABEL = "ENTER AN ARTIST";

    public static final String CREATE_PLAYLIST_BUTTON_LABEL = "CREATE PLAYLIST";  // New button label
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ArtistsPmState state = new ArtistsPmState();

    public ArtistsPmViewModel() {
        super("Artists Playlist Maker");
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
        if (state.isPlaylistCreated()) {
            support.firePropertyChange("playlistCreated", false, true);
        } else {
            support.firePropertyChange("noArtistsSelectedError", false, true);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public ArtistsPmState getState() {
        return state;
    }

    public void setState(ArtistsPmState state) {
        ArtistsPmState oldState = this.state;
        this.state = state;
        support.firePropertyChange("state", oldState, this.state);
    }
}
