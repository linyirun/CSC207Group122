package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.home.HomeController;
import interface_adapter.home.HomePresenter;
import interface_adapter.home.HomeViewModel;
import interface_adapter.playlists.PlaylistsController;
import interface_adapter.playlists.PlaylistsViewModel;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.spotfiy_to_youtube.SpotifyToYoutubeViewModel;
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
            ViewManagerModel viewManagerModel, HomeViewModel homeViewModel,ProfileViewModel profileViewModel, SpotifyToYoutubeViewModel spotifyToYoutubeViewModel,
            HomeUserDataAccessInterface dao) {

        try {
            HomeController homeController = createHomeUseCase(viewManagerModel, homeViewModel, profileViewModel, spotifyToYoutubeViewModel, dao);
            return new HomeView(homeController, homeViewModel, viewManagerModel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error TODO");
        }

        return null;
    }

    private static HomeController createHomeUseCase(
            ViewManagerModel viewManagerModel,
            HomeViewModel homeViewModel, ProfileViewModel profileViewModel, SpotifyToYoutubeViewModel spotifyToYoutubeViewModel,
            HomeUserDataAccessInterface dao) throws IOException {
        HomeOutputBoundary homePresenter = new HomePresenter(viewManagerModel, homeViewModel, profileViewModel, spotifyToYoutubeViewModel);

        HomeInputBoundary homeInteractor = new HomeInteractor(homePresenter, dao);

        return new HomeController(homeInteractor);
    }
}
