package use_case.split_playlist;

public class SplitOutputData {
    private String createdPlaylistNames;
    public SplitOutputData(String createdPlaylistNames){
        this.createdPlaylistNames = createdPlaylistNames;
    }

    public String getCreatedPlaylistNames(){
        return createdPlaylistNames;
    }
}
