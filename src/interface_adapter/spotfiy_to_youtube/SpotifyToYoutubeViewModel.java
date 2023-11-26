package interface_adapter.spotfiy_to_youtube;

import interface_adapter.ViewModel;
import interface_adapter.split_playlist.SplitState;
import view.SpotifyToYoutubeView;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SpotifyToYoutubeViewModel extends ViewModel {

    public static final String CONVERT_BUTTON_LABEL = "Convert";
    public static final String VIEW_TITLE = "Spotify To Youtube Converter";

    private SpotifyToYoutubeState state = new SpotifyToYoutubeState();

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public SpotifyToYoutubeViewModel() {super("Spot To YT");};

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }
    public void firePropertyChangedRefresh() {
        support.firePropertyChange("refresh", null, this.state);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }

    public SpotifyToYoutubeState getState(){
        return state;
    }

    public void setState(SpotifyToYoutubeState givenState){
        this.state = givenState;
    }

}
