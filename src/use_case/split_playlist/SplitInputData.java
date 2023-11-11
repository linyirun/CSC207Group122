package use_case.split_playlist;

import java.io.BufferedReader;

public class SplitInputData {

    private String PlaylistName;

    private String byTime;

    public SplitInputData(String givenPlaylistName, String givenbyTime){
        this.PlaylistName = givenPlaylistName;
        this.byTime = givenbyTime;
    }

    public String getPlaylistName(){
        return PlaylistName;
    }

    public String getByTime(){
        return byTime;
    }
}
