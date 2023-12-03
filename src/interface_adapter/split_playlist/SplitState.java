package interface_adapter.split_playlist;

public class SplitState {
    String createdPlaylistNames;

    public SplitState() {
        this.createdPlaylistNames = "";
    }

    public String getCreatedPlaylistNames() {
        return createdPlaylistNames;
    }

    public void setCreatedPlaylistNames(String createdPlaylistNames) {
        this.createdPlaylistNames = createdPlaylistNames;
    }
}
