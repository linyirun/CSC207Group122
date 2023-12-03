package use_case.SpotifyPlayer;

public class WebPlaybackInputData {

    private String currentSong;
    private String playlistId;

    public WebPlaybackInputData(String currentSong, String playlistId) {this.currentSong = currentSong; this.playlistId=playlistId;}

    public String getCurrentSong() {return this.currentSong;}
    public String getPlaylistId() {return this.playlistId;}



}
