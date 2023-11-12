package interface_adapter.split_playlist;
import java.util.HashMap;
import java.util.Map;

public class SplitState {
    String createdPlaylistNames;
    public SplitState(){
        this.createdPlaylistNames = "";
    }
    public void setCreatedPlaylistNames(String createdPlaylistNames) {
        this.createdPlaylistNames = createdPlaylistNames;
    }

    public String getCreatedPlaylistNames(){
        return createdPlaylistNames;
    }
}
