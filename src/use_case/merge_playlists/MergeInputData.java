package use_case.merge_playlists;

import java.util.List;

public class MergeInputData {

    // Need the ids of the playlists that the user wants to merge
    private List<String> playlistIds;

    public MergeInputData(List<String> playlistIds) {
        this.playlistIds = playlistIds;
    }

    public List<String> getPlaylistIds() {
        return playlistIds;
    }
}
