/**
 * Controller for Artists Playlist Maker feature.
 */
package interface_adapter.artists_playlist_maker;

import org.json.simple.parser.ParseException;
import use_case.artists_playlist_maker.ArtistsPmInputBoundary;
import use_case.artists_playlist_maker.ArtistsPmInputData;

import java.io.IOException;
import java.util.List;

public class ArtistsPmController {
    ArtistsPmInputBoundary interactor;

    /**
     * Constructs with the provided {@code ArtistsPmInputBoundary}.
     *
     * @param interactor the input boundary
     */
    public ArtistsPmController(ArtistsPmInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Shows top artists based on input data.
     *
     * @param inputData user preferences
     * @return list of top artist names
     * @throws RuntimeException if an error occurs
     */
    public List<String> showTopArtists(String artistName) {
        try {
            ArtistsPmInputData inputData = new ArtistsPmInputData(artistName, null, -1, false);
            return interactor.showTopArtists(inputData);
        } catch (IOException e) {

        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Creates a playlist based on input data.
     *
     * @param inputData user preferences
     */
    public void createPlaylist(List<String> selectedArtists, int numberOfSongs, boolean includeInPlaylist) {

        ArtistsPmInputData inputData = new ArtistsPmInputData(null, selectedArtists, numberOfSongs, includeInPlaylist);
        interactor.createPlaylist(inputData);

    }
}
