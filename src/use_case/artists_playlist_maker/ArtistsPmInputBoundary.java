package use_case.artists_playlist_maker;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * Input boundary for Artists Playlist Maker use case.
 */
public interface ArtistsPmInputBoundary {

    /**
     * Retrieves a list of top artists based on the provided input data.
     *
     * @param inputData the input data containing user preferences
     * @return a list of top artist names
     * @throws IOException          if an I/O error occurs
     * @throws ParseException       if an error occurs while parsing
     * @throws InterruptedException if the operation is interrupted
     */
    List<String> showTopArtists(ArtistsPmInputData inputData) throws IOException, ParseException, InterruptedException;

    /**
     * Creates a playlist based on the provided input data.
     *
     * @param inputData the input data containing user preferences
     */
    void createPlaylist(ArtistsPmInputData inputData);
}
