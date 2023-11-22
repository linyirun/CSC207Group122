package app;

import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.artists_playlist_maker.ArtistsPmController;
import interface_adapter.artists_playlist_maker.ArtistsPmPresenter;
import interface_adapter.artists_playlist_maker.ArtistsPmViewModel;
import use_case.artists_playlist_maker.ArtistsPmInputBoundary;
import use_case.artists_playlist_maker.ArtistsPmInteractor;
import use_case.artists_playlist_maker.ArtistsPmOutputBoundary;
import use_case.artists_playlist_maker.ArtistsPmUserDataAccessInterface;
import view.ArtistsPlaylistMakerView;

import javax.swing.*;
import java.io.IOException;

public class ArtistsPmUseCaseFactory {

    public static ArtistsPlaylistMakerView create(ViewManagerModel viewManagerModel, ArtistsPmViewModel artistsPmViewModel, ArtistsPmUserDataAccessInterface userDataAccessObject) {

        try {
            ArtistsPmController artistsPmController = createLoginUseCase(viewManagerModel, artistsPmViewModel, userDataAccessObject);
            return new ArtistsPlaylistMakerView(artistsPmViewModel, artistsPmController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }

    private static ArtistsPmController createLoginUseCase(ViewManagerModel viewManagerModel, ArtistsPmViewModel artistsPmViewModel, ArtistsPmUserDataAccessInterface userDataAccessObject) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        ArtistsPmOutputBoundary outputBoundary = new ArtistsPmPresenter(viewManagerModel, artistsPmViewModel);

        UserFactory userFactory = new UserFactory();

        ArtistsPmInputBoundary interactor = new ArtistsPmInteractor(userDataAccessObject, outputBoundary);

        return new ArtistsPmController(interactor);
    }

}
