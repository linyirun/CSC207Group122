package interface_adapter.spotfiy_to_youtube;

import interface_adapter.ViewManagerModel;
import use_case.spotify_to_youtube.SpotifyToYoutubeOutputBoundary;
import use_case.spotify_to_youtube.SpotifyToYoutubeOutputData;
import view.ViewManager;

public class SpotifyToYoutubePresenter implements SpotifyToYoutubeOutputBoundary {
    private ViewManagerModel manager;
    private SpotifyToYoutubeViewModel spotifyToYoutubeViewModel;
    public SpotifyToYoutubePresenter(ViewManagerModel manager, SpotifyToYoutubeViewModel spotifyToYoutubeViewModel) {
        this.manager = manager;
        this.spotifyToYoutubeViewModel = spotifyToYoutubeViewModel;
    }
    @Override
    public void prepareSuccessView(SpotifyToYoutubeOutputData data) {

    }

    @Override
    public void prepareFailView(SpotifyToYoutubeOutputData failData) {

    }
}
