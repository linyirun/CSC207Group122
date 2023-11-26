package interface_adapter.spotfiy_to_youtube;

import use_case.spotify_to_youtube.SpotifyToYoutubeInputBoundary;
import use_case.spotify_to_youtube.SpotifyToYoutubeInputData;
import use_case.spotify_to_youtube.SpotifyToYoutubeInteractor;

import java.io.IOException;
import java.util.List;

public class SpotifyToYoutubeController {

    private SpotifyToYoutubeInputBoundary interactor;

    public SpotifyToYoutubeController(SpotifyToYoutubeInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(List<String> selectedPlaylists, String givenName, boolean isConnectedToYT){
        if (!isConnectedToYT) {
            try {
                interactor.connectToYoutube();
            }
            catch (InterruptedException | IOException e) {
                System.out.println("Server did not work");
            }
            return;
        }
        interactor.execute(new SpotifyToYoutubeInputData());
    }

}
