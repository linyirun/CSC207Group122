package interface_adapter.merge_playlists;

import interface_adapter.ViewModel;
import interface_adapter.split_playlist.SplitState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MergeViewModel extends ViewModel {
    private MergeState state = new MergeState();

    public final String MERGE_BUTTON_LABEL = "Merge";
    public final String GET_PLAYLISTS_LABEL = "Get Playlists";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public MergeViewModel(){
        super("Merge Playlist");
    };

    public void firePropertyChanged() {
        support.firePropertyChange("", null, this.state);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }

    public MergeState getState(){
        return state;
    }

    public void setState(MergeState state){
        this.state = state;
    }

}
