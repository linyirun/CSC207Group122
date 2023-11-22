package interface_adapter.artists_playlist_maker;

import interface_adapter.ViewManagerModel;
import use_case.artists_playlist_maker.ArtistsPmOutputBoundary;

public class ArtistsPmPresenter implements ArtistsPmOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final ArtistsPmViewModel artistsPmViewModel;


    public ArtistsPmPresenter(ViewManagerModel viewManagerModel, ArtistsPmViewModel artistsPmViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.artistsPmViewModel = artistsPmViewModel;

    }

    @Override
    public void playlistCreatedSuccessfully() {
        artistsPmViewModel.getState().setPlaylistCreated(true);
        artistsPmViewModel.firePropertyChanged();

    }

    @Override
    public void noArtistsSelectedError() {

        artistsPmViewModel.getState().setPlaylistCreated(false);
        artistsPmViewModel.firePropertyChanged();
    }
}
