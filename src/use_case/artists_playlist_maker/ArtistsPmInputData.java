package use_case.artists_playlist_maker;

import java.util.List;

/**
 * Input data class for Artists Playlist Maker use case.
 */
public class ArtistsPmInputData {

    private final String artistName;
    private final List<String> selectedArtists;
    private final int numberOfSongs;

    /**
     * Constructs an ArtistsPmInputData object with the specified parameters.
     *
     * @param artistName      the name of the artist
     * @param selectedArtists the list of selected artists
     * @param numberOfSongs   the number of songs for the playlist
     */
    public ArtistsPmInputData(String artistName, List<String> selectedArtists, int numberOfSongs) {
        this.artistName = artistName;
        this.selectedArtists = selectedArtists;
        this.numberOfSongs = numberOfSongs;
    }

    /**
     * Retrieves the name of the artist.
     *
     * @return the name of the artist
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * Retrieves the list of selected artists.
     *
     * @return the list of selected artists
     */
    public List<String> getSelectedArtists() {
        return selectedArtists;
    }

    /**
     * Retrieves the number of songs for the playlist.
     *
     * @return the number of songs
     */
    public int getNumberOfSongs() {
        return numberOfSongs;
    }
}
