package use_case.Lyrics;

public class LyricsInputData {

    private final String songName;

    public LyricsInputData(String songName) {
        this.songName = songName;
    }

    public String getSongName() {
        return this.songName;
    }


}
