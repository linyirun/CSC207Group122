package use_case.merge_playlists;

import java.util.List;

public class MergeInputData {

    // Choices for filters
    // 0 means filter is off
    public static final int ANY = 0;

    // Instrumental
    public static final int INSTRUMENTAL_CHOICE = 1;
    public static final int VOCAL_CHOICE = 2;

    public static final double INSTRUMENTAL_THRESHOLD = 0.8d;
    public static final double VOCAL_THRESHOLD = 0.2d;

    // Mood/Valence
    public static final int SAD_CHOICE = 1;
    public static final int NEUTRAL_CHOICE = 2;
    public static final int HAPPY_CHOICE = 3;

    public static final double SAD_THRESHOLD = 0.3d;
    public static final double HAPPY_THRESHOLD = 0.7d;

    public static final int SLOW_CHOICE = 1;
    public static final int NORMAL_CHOICE = 2;
    public static final int FAST_CHOICE = 3;

    public static final double SLOW_THRESHOLD = 90.0d;
    public static final double FAST_THRESHOLD = 130.0d;




    // Need the ids of the playlists that the user wants to merge
    private List<String> selectedPlaylistNames;
    private String playlistName;

    // if the user wants to return to home
    private boolean returnHome;

    private int instrumentalChoice = ANY;
    private int valenceChoice = ANY;
    private int tempoChoice = ANY;

    public MergeInputData(List<String> playlistNames, String playlistName, boolean returnHome) {
        this.selectedPlaylistNames = playlistNames;
        this.playlistName = playlistName;
        this.returnHome = returnHome;
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

    public void setInstrumentalChoice(int choice) {
        this.instrumentalChoice = choice;
    }

    public int getValenceChoice() {
        return valenceChoice;
    }

    public void setValenceChoice(int valenceChoice) {
        this.valenceChoice = valenceChoice;
    }

    public int getTempoChoice() {
        return tempoChoice;
    }

    public void setTempoChoice(int tempoChoice) {
        this.tempoChoice = tempoChoice;
    }
}

