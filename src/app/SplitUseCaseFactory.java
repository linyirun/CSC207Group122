package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.playlists.PlaylistsController;
import interface_adapter.playlists.PlaylistsPresenter;
import interface_adapter.playlists.PlaylistsViewModel;
import interface_adapter.split_playlist.SplitController;
import interface_adapter.split_playlist.SplitPresenter;
import interface_adapter.split_playlist.SplitViewModel;
import use_case.playlists.PlaylistsInputBoundary;
import use_case.playlists.PlaylistsInteractor;
import use_case.playlists.PlaylistsOutputBoundary;
import use_case.playlists.PlaylistsUserDataAccessInterface;
import use_case.split_playlist.SplitInputBoundary;
import use_case.split_playlist.SplitInteractor;
import use_case.split_playlist.SplitOutputBoundary;
import use_case.split_playlist.SplitUserDataAccessInterface;
import view.SplitView;

import javax.swing.*;
import java.io.IOException;


public class SplitUseCaseFactory {

    /**
     * Prevent instantiation.
     */
    private SplitUseCaseFactory() {
    }

    public static SplitView create(ViewManagerModel viewManagerModel, SplitViewModel splitViewModel, PlaylistsViewModel playlistsViewModel, SplitUserDataAccessInterface userDataAccessObject, PlaylistsUserDataAccessInterface userDataAccessObjectPlaylists) {

        try {
            SplitController splitController = createSplitUseCase(viewManagerModel, splitViewModel, userDataAccessObject);
            PlaylistsController playlistsController = createPlaylistsUseCase(viewManagerModel, playlistsViewModel, userDataAccessObjectPlaylists);
            return new SplitView(splitController, splitViewModel, playlistsController, playlistsViewModel, viewManagerModel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "can not create Split View");
        }

        return null;
    }

    private static SplitController createSplitUseCase(ViewManagerModel viewManagerModel, SplitViewModel splitViewModel, SplitUserDataAccessInterface userDataAccessObject) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        SplitOutputBoundary splitOutputBoundary = new SplitPresenter(viewManagerModel, splitViewModel);

        SplitInputBoundary splitInteractor = new SplitInteractor(userDataAccessObject, splitOutputBoundary);

        return new SplitController(splitInteractor);
    }

    private static PlaylistsController createPlaylistsUseCase(ViewManagerModel viewManagerModel, PlaylistsViewModel playlistsViewModel, PlaylistsUserDataAccessInterface userDataAccessObject) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        PlaylistsOutputBoundary playlistsOutputBoundary = new PlaylistsPresenter(viewManagerModel, playlistsViewModel);

        PlaylistsInputBoundary playlistsInteractor = new PlaylistsInteractor(userDataAccessObject, playlistsOutputBoundary);

        return new PlaylistsController(playlistsInteractor);
    }

}
