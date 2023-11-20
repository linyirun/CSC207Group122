package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.home.HomeController;
import interface_adapter.home.HomePresenter;
import interface_adapter.home.HomeViewModel;
import interface_adapter.playlists.PlaylistsController;
import interface_adapter.playlists.PlaylistsViewModel;
import use_case.home.HomeInputBoundary;
import use_case.home.HomeInteractor;
import use_case.home.HomeOutputBoundary;
import use_case.home.HomeUserDataAccessInterface;
import view.HomeView;

import javax.swing.*;
import java.io.IOException;

public class HomeUseCaseFactory {
    private HomeUseCaseFactory() {
    }

    public static HomeView create(
            ViewManagerModel viewManagerModel, HomeViewModel homeViewModel,
            HomeUserDataAccessInterface dao, PlaylistsController playlistsController, PlaylistsViewModel playlistsViewModel) {

        try {
            HomeController homeController = createHomeUseCase(viewManagerModel, homeViewModel, dao);
            return new HomeView(homeController, homeViewModel, playlistsController, playlistsViewModel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error TODO");
        }

        return null;
    }

    private static HomeController createHomeUseCase(
            ViewManagerModel viewManagerModel,
            HomeViewModel homeViewModel,
            HomeUserDataAccessInterface dao) throws IOException {
        HomeOutputBoundary homePresenter = new HomePresenter(viewManagerModel, homeViewModel);

        HomeInputBoundary homeInteractor = new HomeInteractor(homePresenter, dao);

        return new HomeController(homeInteractor);
    }
}
