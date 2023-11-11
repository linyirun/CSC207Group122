package interface_adapter.split_playlist;
import java.util.HashMap;
import java.util.Map;

public class SplitState {
    Map playlist_map;
    public SplitState(){
        this.playlist_map = new HashMap();
    }

    public void setPlaylistMap(Map playlist_map) {
        this.playlist_map = playlist_map;
    }
}
