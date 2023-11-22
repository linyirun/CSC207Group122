package use_case.artists_playlist_maker;

import java.util.List;

public class ArtistsPmInputData {
    private final String artistName;
    private final List<String> selectedArtists;
    private final int numberOfSongs; // New field for the number of songs

    public ArtistsPmInputData(String artistName, List<String> selectedArtists, int numberOfSongs) {
        this.artistName = artistName;
        this.selectedArtists = selectedArtists;
        this.numberOfSongs = numberOfSongs;
    }

    public String getArtistName() {
        return artistName;
    }

    public List<String> getSelectedArtists() {
        return selectedArtists;
    }

    public int getNumberOfSongs() {
        return numberOfSongs;
    }


}
