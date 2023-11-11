package use_case.playlists;


public interface PlaylistsOutputBoundary {
    void prepareSuccessView(PlaylistsOutputData user);

    void prepareFailView(String error);
}
