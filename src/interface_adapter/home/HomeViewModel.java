package interface_adapter.home;

import interface_adapter.ViewModel;

import javax.swing.text.View;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class HomeViewModel extends ViewModel {
    public final String SPLIT_PLAYLIST_NAME = "Split Playlist";

    public final String ARTISTS_PLAYLIST_MAKER_NAME = "Artists Playlist Maker";
    private HomeState state = new HomeState();

    public HomeViewModel() {
        super("Home");
    }

    public void setState(HomeState state) {
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

    public HomeState getState() {
        return state;
    }
}
