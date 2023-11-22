package use_case.split_playlist;

import java.io.BufferedReader;

public class SplitInputData {

    private String playlistName;

    private String byTime;

    public SplitInputData(String givenPlaylistName, String givenbyTime){
        this.playlistName = givenPlaylistName;
        this.byTime = givenbyTime;
    }

    public String getPlaylistName(){
        return playlistName;
    }

    public String getByTime(){
        return byTime;
    }
}
