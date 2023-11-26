package use_case.spotify_to_youtube;

public interface SpotifyToYoutubeOutputBoundary {
    void prepareSuccessView(SpotifyToYoutubeOutputData data);

    void prepareFailView(SpotifyToYoutubeOutputData failData);
}
