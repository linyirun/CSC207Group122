package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.spotfiy_to_youtube.SpotifyToYoutubeController;
import interface_adapter.spotfiy_to_youtube.SpotifyToYoutubePresenter;
import interface_adapter.spotfiy_to_youtube.SpotifyToYoutubeViewModel;
import use_case.spotify_to_youtube.*;
import view.LoginOAuthView;
import view.SpotifyToYoutubeView;

import javax.swing.*;
import java.io.IOException;

public class SpotifyToYoutubeUseCaseFactory {
    private SpotifyToYoutubeUseCaseFactory() {
    }

    public static SpotifyToYoutubeView create(
            ViewManagerModel viewManagerModel, SpotifyToYoutubeViewModel spotifyToYoutubeViewModel,
            SpotifyToYoutubeDataAccessInterface userDataAccessObject, SpotifyToYoutubeDataAccessInterfaceForSpotify userDataAccessObject_2) {

        try {
            SpotifyToYoutubeController controller = createSpotifyToYTUseCase(viewManagerModel, spotifyToYoutubeViewModel, userDataAccessObject,userDataAccessObject_2);
            return new SpotifyToYoutubeView(spotifyToYoutubeViewModel, controller, viewManagerModel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "error while creating spotify to youtube use case");
        }

        return null;
    }

    private static SpotifyToYoutubeController createSpotifyToYTUseCase(
            ViewManagerModel viewManagerModel,
            SpotifyToYoutubeViewModel spotifyToYoutubeViewModel,
            SpotifyToYoutubeDataAccessInterface userDataAccessObject, SpotifyToYoutubeDataAccessInterfaceForSpotify userDataAccessObject_2) throws IOException {

        SpotifyToYoutubeOutputBoundary presenter = new SpotifyToYoutubePresenter(viewManagerModel, spotifyToYoutubeViewModel);

        SpotifyToYoutubeInputBoundary interactor = new SpotifyToYoutubeInteractor(userDataAccessObject,userDataAccessObject_2, presenter);

        return new SpotifyToYoutubeController(interactor);
    }
}
