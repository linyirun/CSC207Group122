package use_case.merge_playlists;

import java.util.Map;

public class MergeOutputData {

    // name of the new playlist
    private String newPlaylistName;

    public MergeOutputData(String newPlaylistName){
        this.newPlaylistName = newPlaylistName;
    }

    public String getPlaylistName() {
        return this.newPlaylistName;
    }
}
