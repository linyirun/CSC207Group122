package use_case.artists_playlist_maker;

/**
 * Output boundary for Artists Playlist Maker use case.
 */
public interface ArtistsPmOutputBoundary {

    /**
     * Signals that the playlist was created successfully.
     */
    void playlistCreatedSuccessfully();

    /**
     * Signals an error when no artists are selected.
     */

    void noArtistsSelectedError();

}
