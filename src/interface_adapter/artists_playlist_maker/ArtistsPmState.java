package interface_adapter.artists_playlist_maker;

import java.util.ArrayList;
import java.util.List;

public class ArtistsPmState {

    private List<String> searchResults;
    private String selectedArtist;
    private List<String> chosenArtists;

    private boolean playlistCreated;  // New property

    public ArtistsPmState() {
        this.searchResults = new ArrayList<>();
        this.selectedArtist = "";
        this.chosenArtists = new ArrayList<>();
        this.playlistCreated = false;
    }


    public boolean isPlaylistCreated() {
        return playlistCreated;
    }

    public void setPlaylistCreated(boolean playlistCreated) {
        this.playlistCreated = playlistCreated;
    }

    public List<String> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<String> searchResults) {
        this.searchResults = searchResults;
    }

    public String getSelectedArtist() {
        return selectedArtist;
    }

    public void setSelectedArtist(String selectedArtist) {
        this.selectedArtist = selectedArtist;
    }

    public List<String> getChosenArtists() {
        return chosenArtists;
    }

    public void setChosenArtists(List<String> chosenArtists) {
        this.chosenArtists = chosenArtists;
    }
}
