package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.home.HomeController;
import interface_adapter.home.HomePresenter;
import interface_adapter.home.HomeViewModel;
import interface_adapter.merge_playlists.MergeController;
import interface_adapter.merge_playlists.MergePresenter;
import interface_adapter.merge_playlists.MergeViewModel;
import interface_adapter.playlists.PlaylistsController;
import interface_adapter.playlists.PlaylistsViewModel;
import use_case.home.HomeInputBoundary;
import use_case.home.HomeInteractor;
import use_case.home.HomeOutputBoundary;
import use_case.home.HomeUserDataAccessInterface;
import use_case.merge_playlists.MergeDataAccessInterface;
import use_case.merge_playlists.MergeInputBoundary;
import use_case.merge_playlists.MergeInteractor;
import use_case.merge_playlists.MergeOutputBoundary;
import use_case.playlists.PlaylistsUserDataAccessInterface;
import view.HomeView;
import view.MergeView;
import view.ViewManager;

import javax.swing.*;
import java.io.IOException;

public class MergeUseCaseFactory {
    // Prevents instantiation
    private MergeUseCaseFactory() {

    }

    public static MergeView create(ViewManagerModel viewManagerModel, MergeViewModel mergeViewModel,
                                   PlaylistsViewModel playlistsViewModel, MergeDataAccessInterface mergeDataAccessInterface,
                                   PlaylistsUserDataAccessInterface playlistsUserDataAccessInterface) {

        MergeController mergeController = createMergeUseCase(viewManagerModel, mergeViewModel, mergeDataAccessInterface);

        try {
            PlaylistsController playlistsController = SplitUseCaseFactory.createPlaylistsUseCase(viewManagerModel, playlistsViewModel, playlistsUserDataAccessInterface);
            return new MergeView(mergeViewModel, mergeController, viewManagerModel, playlistsViewModel, playlistsController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to create Merge View.");
        }
        return null;
    }

    private static MergeController createMergeUseCase(
            ViewManagerModel viewManagerModel, MergeViewModel mergeViewModel, MergeDataAccessInterface mergeDataAccessInterface) {
        MergeOutputBoundary mergeOutputBoundary = new MergePresenter(mergeViewModel, viewManagerModel);

        MergeInputBoundary mergeInteractor = new MergeInteractor(mergeDataAccessInterface, mergeOutputBoundary);

        MergeController mergeController = new MergeController(mergeInteractor);
        return mergeController;
    }
}
