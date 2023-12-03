package use_case.split_playlist;

import java.io.BufferedReader;

public class SplitInputData {

    private String playlistName;


    public SplitInputData(String givenPlaylistName){
        this.playlistName = givenPlaylistName;
    }

    public String getPlaylistName(){
        return playlistName;
    }

}
