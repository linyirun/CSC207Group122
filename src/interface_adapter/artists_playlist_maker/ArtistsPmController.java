package interface_adapter.artists_playlist_maker;

import org.json.simple.parser.ParseException;
import use_case.artists_playlist_maker.ArtistsPmInputBoundary;
import use_case.login.LoginInputBoundary;

import java.io.IOException;

public class ArtistsPmController {
    ArtistsPmInputBoundary interactor;
    public ArtistsPmController(ArtistsPmInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute() {
        try {
            interactor.execute();
        }
        catch (IOException e) {

        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
