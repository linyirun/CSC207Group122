package use_case.merge_playlists;

import java.util.List;

public class MergeInputData {

    // Need the ids of the playlists that the user wants to merge
    private List<String> playlistIds;
    private String playlistName;

    public MergeInputData(List<String> playlistIds, String playlistName) {
        this.playlistIds = playlistIds;
        this.playlistName = playlistName;
    }

    public List<String> getPlaylistIds() {
        return playlistIds;
    }

    public String getPlaylistName() {
        return playlistName;
    }
}
