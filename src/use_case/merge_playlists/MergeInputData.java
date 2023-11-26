package use_case.merge_playlists;

import java.util.List;

public class MergeInputData {

    // Choices for filters
    // 0 means filter is off
    public static final int ANY = 0;

    public static final int INSTRUMENTAL_CHOICE = 1;
    public static final int VOCAL_CHOICE = 2;

    public static final double INSTRUMENTAL_THRESHOLD = 0.8d;
    public static final double VOCAL_THRESHOLD = 0.2d;

    // Need the ids of the playlists that the user wants to merge
    private List<String> selectedPlaylistNames;
    private String playlistName;

    // if the user wants to return to home
    private boolean returnHome;

    private int instrumentalChoice;

    public MergeInputData(List<String> playlistNames, String playlistName, boolean returnHome, int instrumentalChoice) {
        this.selectedPlaylistNames = playlistNames;
        this.playlistName = playlistName;
        this.returnHome = returnHome;
        this.instrumentalChoice = instrumentalChoice;
    }

    public List<String> getSelectedPlaylistNames() {
        return selectedPlaylistNames;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public boolean isReturnHome() {
        return returnHome;
    }

    public int getInstrumentalChoice() {
        return instrumentalChoice;
    }
}
