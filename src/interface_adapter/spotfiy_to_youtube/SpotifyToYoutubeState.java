package interface_adapter.spotfiy_to_youtube;

import java.util.List;

public class SpotifyToYoutubeState {
    public List<String> playlistNames;
    public SpotifyToYoutubeState(SpotifyToYoutubeState copy) {this.playlistNames = copy.playlistNames;}
    public SpotifyToYoutubeState() {}

    public void setPlaylistNames(List<String> playlistNames) {
        this.playlistNames = playlistNames;
    }
    public List<String> getPlaylistNames() {
        return this.playlistNames;
    }
}
