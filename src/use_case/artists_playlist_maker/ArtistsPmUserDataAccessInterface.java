package use_case.artists_playlist_maker;

import entity.Song;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Data access interface for Artists Playlist Maker use case.
 */
public interface ArtistsPmUserDataAccessInterface {

    /**
     * Retrieves a list of top artists based on the provided query and limit.
     *
     * @param query the search query
     * @param limit the maximum number of results
     * @return a list of top artist names
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     * @throws ParseException       if an error occurs while parsing
     */
    List<String> getTopArtists(String query, int limit) throws IOException, InterruptedException, ParseException;

    /**
     * Retrieves a list of top tracks for the given list of artist names and limit.
     *
     * @param queries the list of artist names
     * @param limit   the maximum number of results per artist
     * @return a list of top track IDs
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     * @throws ParseException       if an error occurs while parsing
     */
    List<String> getArtistsTopTracks(List<String> queries, int limit) throws IOException, InterruptedException, ParseException;

    /**
     * Retrieves the user's Spotify ID.
     *
     * @return the user's Spotify ID
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     * @throws ParseException       if an error occurs while parsing
     */
    String getUserId() throws IOException, InterruptedException, ParseException;

    /**
     * Creates a new playlist with the specified name for the given user.
     *
     * @param playlistName the name of the playlist
     * @param userId       the user's Spotify ID
     * @return the ID of the created playlist
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     * @throws ParseException       if an error occurs while parsing
     */
    String createPlaylist(String playlistName, String userId) throws IOException, InterruptedException, ParseException;

    /**
     * Adds a list of songs to the specified playlist.
     *
     * @param playlistId the ID of the playlist
     * @param songIds    the list of song IDs to add
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    void addSongsToPlaylist(String playlistId, List<String> songIds) throws IOException, InterruptedException;

    /**
     * Retrieves a list of songs in the specified playlist.
     *
     * @param playlistID the ID of the playlist
     * @return a list of Song objects
     */
    List<Song> getSongs(String playlistID);

    /**
     * Retrieves a list of track IDs in the specified playlist.
     *
     * @param playlistID the ID of the playlist
     * @return a list of track IDs
     */
    List<String> getTrackIds(String playlistID);

    /**
     * Retrieves a mapping of playlist names to playlist IDs.
     *
     * @return a map of playlist names to playlist IDs
     */
    Map<String, String> getPlaylistMap();

    /**
     * Retrieves a set of playlist names.
     *
     * @return a set of playlist names
     */
    Set<String> getPlaylists();
}
