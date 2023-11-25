package interface_adapter.profile;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ProfileViewModel extends ViewModel {
    private ProfileState state = new ProfileState();

    public static final String ALL_TIME_TOP_ARTISTS = "Your All Time Top 10 Artists";
    public static final String ALL_TIME_TOP_SONGS = "Your All Time Top 10 Songs";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ProfileViewModel() {
        super("Profile");
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public ProfileState getState() {
        return state;
    }

    public void setState(ProfileState givenState) {
        this.state = givenState;
    }
}
