package use_case.spotify_to_youtube;

public class SpotifyToYoutubeOutputData {
    String message;
    boolean errored;
    public SpotifyToYoutubeOutputData(String msg, boolean errored) {
        this.message = msg;
        this.errored = errored;
    }
    public String getMessage() {
        return this.message;
    }
    public boolean getErrored() {
        return this.errored;
    }
}
