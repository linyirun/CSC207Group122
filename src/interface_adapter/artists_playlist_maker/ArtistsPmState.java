/**
 * State the state for Artists Playlist Maker.
 */
package interface_adapter.artists_playlist_maker;

import java.util.ArrayList;
import java.util.List;

public class ArtistsPmState {

    private final List<String> searchResults;
    private final String selectedArtist;
    private final List<String> chosenArtists;

    private boolean playlistCreated;

    /**
     * Constructs a new ArtistsPmState with default values.
     */
    public ArtistsPmState() {
        this.searchResults = new ArrayList<>();
        this.selectedArtist = "";
        this.chosenArtists = new ArrayList<>();
        this.playlistCreated = false;
    }


    /**
     * Checks if a playlist has been created.
     *
     * @return true if a playlist has been created, false otherwise
     */
    public boolean isPlaylistCreated() {
        return playlistCreated;
    }

    /**
     * Sets the playlist creation status.
     *
     * @param playlistCreated the playlist creation status
     */
    public void setPlaylistCreated(boolean playlistCreated) {
        this.playlistCreated = playlistCreated;
    }
}
