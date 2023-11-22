package interface_adapter.artists_playlist_maker;

import org.json.simple.parser.ParseException;
import use_case.artists_playlist_maker.ArtistsPmInputBoundary;
import use_case.artists_playlist_maker.ArtistsPmInputData;

import java.io.IOException;
import java.util.List;

public class ArtistsPmController {
    ArtistsPmInputBoundary interactor;

    public ArtistsPmController(ArtistsPmInputBoundary interactor) {
        this.interactor = interactor;
    }

    public List<String> showTopArtists(ArtistsPmInputData inputData) {
        try {
            return interactor.showTopArtists(inputData);
        } catch (IOException e) {

        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void createPlaylist(ArtistsPmInputData inputData) {

        interactor.createPlaylist(inputData);

    }
}
