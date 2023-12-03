package interface_adapter.merge_playlists;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MergeViewModel extends ViewModel {
    public final String VIEW_TITLE = "Merge Playlists";
    public final String REFRESH_BUTTON_LABEL = "Refresh Playlists";
    public final String MERGE_BUTTON_LABEL = "Merge Playlists";
    public final String GET_PLAYLISTS_LABEL = "Get Playlists";
    public final String HOME_BUTTON_LABEL = "Home";
    public final String INSTRUMENTAL_BUTTON_LABEL = "Instrumental";


    // RadioButtons for filters
    public final String VOCAL_BUTTON_LABEL = "Vocal";
    public final String NONE_LABEL = "Any";
    public final String SAD_VALENCE_BUTTON_LABEL = "Sad";
    public final String NEUTRAL_VALENCE_BUTTON_LABEL = "Neutral";
    public final String HAPPY_VALENCE_BUTTON_LABEL = "Positive";
    public final String SLOW_TEMPO_BUTTON_LABEL = "Slow";
    public final String NORMAL_TEMPO_BUTTON_LABEL = "Normal";
    public final String FAST_TEMPO_BUTTON_LABEL = "Fast";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private MergeState state = new MergeState();

    public MergeViewModel() {
        super("Merge Playlist");
    }

    public void firePropertyChanged() {
        support.firePropertyChange("", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public MergeState getState() {
        return state;
    }

    public void setState(MergeState state) {
        this.state = state;
    }

}
