package interface_adapter.split_playlist;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SplitViewModel extends ViewModel {
    private SplitState state = new SplitState();

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public static final String SPLIT_BY_MONTH = "Split by month";

    public static final String SPLIT_BY_DAY = "Split by day";

    public static final String SPLIT_BY_YEAR = "Split by year";

    public static final String SPLIT_BY_ARTISTS = "Split by artists";

    public SplitViewModel(){super("Split Playlist");};

    public void firePropertyChanged(){

    }
    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }

    public SplitState getState(){
        return state;
    }

    public void setState(SplitState givenState){
        this.state = givenState;
    }

    public String toString(){
        return state.getCreatedPlaylistNames();
    }
}
