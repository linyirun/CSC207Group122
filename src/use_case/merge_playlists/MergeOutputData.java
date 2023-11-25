package use_case.merge_playlists;

import java.util.Map;

public class MergeOutputData {

    // name of the new playlist
    private String newPlaylistName;

    private boolean returnHome;

    public MergeOutputData(String newPlaylistName, boolean returnHome) {
        this.newPlaylistName = newPlaylistName;
        this.returnHome = returnHome;
    }

    public String getNewPlaylistName() {
        return newPlaylistName;
    }

    public boolean isReturnHome() {
        return returnHome;
    }
}
