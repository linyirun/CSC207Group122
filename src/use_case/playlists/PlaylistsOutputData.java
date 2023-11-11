package use_case.playlists;

import java.util.Map;

public class PlaylistsOutputData {
    private Map<String, String> playlistMap;

    public PlaylistsOutputData(Map<String, String> givenMap){
        this.playlistMap = givenMap;
    }

    public Map<String,String> getPlaylistMap(){
        return playlistMap;
    }
}
