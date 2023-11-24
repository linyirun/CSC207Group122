/**
 * Presenter for Artists Playlist Maker feature.
 * Implements {@link use_case.artists_playlist_maker.ArtistsPmOutputBoundary}.
 */
package interface_adapter.artists_playlist_maker;

import interface_adapter.ViewManagerModel;
import use_case.artists_playlist_maker.ArtistsPmOutputBoundary;

public class ArtistsPmPresenter implements ArtistsPmOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final ArtistsPmViewModel artistsPmViewModel;


    /**
     * Constructs the presenter with the provided {@code ViewManagerModel} and {@code ArtistsPmViewModel}.
     *
     * @param viewManagerModel   the view manager model
     * @param artistsPmViewModel the view model for Artists Playlist Maker
     */
    public ArtistsPmPresenter(ViewManagerModel viewManagerModel, ArtistsPmViewModel artistsPmViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.artistsPmViewModel = artistsPmViewModel;

    }

    /**
     * Signals successful playlist creation.
     */
    @Override
    public void playlistCreatedSuccessfully() {
        artistsPmViewModel.getState().setPlaylistCreated(true);
        artistsPmViewModel.firePropertyChanged();

    }

    /**
     * Signals an error when no artists are selected.
     */
    @Override
    public void noArtistsSelectedError() {

        artistsPmViewModel.getState().setPlaylistCreated(false);
        artistsPmViewModel.firePropertyChanged();
    }
}
