package use_case.merge_playlists;

import java.util.List;

public class MergeInputData {

    private List<String> playlistIds;

    public MergeInputData(List<String> playlistIds) {
        this.playlistIds = playlistIds;
    }

    public List<String> getPlaylistIds() {
        return playlistIds;
    }
}
