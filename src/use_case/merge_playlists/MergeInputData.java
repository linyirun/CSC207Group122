package use_case.merge_playlists;

import java.util.List;

public class MergeInputData {

    // Need the ids of the playlists that the user wants to merge
    private List<String> selectedPlaylistIds;
    private String playlistName;

    // if the user wants to return to home
    private boolean returnHome;

    public MergeInputData(List<String> playlistIds, String playlistName, boolean returnHome) {
        this.selectedPlaylistIds = playlistIds;
        this.playlistName = playlistName;
        this.returnHome = returnHome;
    }

    public List<String> getSelectedPlaylistIds() {
        return selectedPlaylistIds;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public boolean isReturnHome() {
        return returnHome;
    }
}
