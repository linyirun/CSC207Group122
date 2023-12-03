package interface_adapter.spotfiy_to_youtube;

import java.util.List;

public class SpotifyToYoutubeState {
    private List<String> playlistNames;
    private boolean isConnectedToYT = false;
    private String msg;


    public SpotifyToYoutubeState() {
    }

    public List<String> getPlaylistNames() {
        return this.playlistNames;
    }

    public void setPlaylistNames(List<String> playlistNames) {
        this.playlistNames = playlistNames;
    }

    public boolean getIsConnectedToYT() {
        return isConnectedToYT;
    }

    public void setIsConnectedToYT() {
        this.isConnectedToYT = true;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
