package interface_adapter.playlists;

import interface_adapter.ViewManagerModel;
import use_case.playlists.PlaylistsOutputBoundary;
import use_case.playlists.PlaylistsOutputData;

public class PlaylistsPresenter implements PlaylistsOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final PlaylistsViewModel playlistsViewModel;

    public PlaylistsPresenter(ViewManagerModel viewManagerModel, PlaylistsViewModel playlistsViewModel) {
        this.playlistsViewModel = playlistsViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(PlaylistsOutputData response) {
        PlaylistsState playlistsState = playlistsViewModel.getState();
        playlistsState.setPlaylistMap(response.getPlaylistMap());
        playlistsViewModel.setState(playlistsState);
        System.out.println("Presenter prepare " + playlistsState.getPlaylistMap().toString());
        playlistsViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        // Implement your logic for handling a failed playlists operation here
    }
}
