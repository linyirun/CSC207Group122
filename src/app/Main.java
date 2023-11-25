package app;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;

import data_access.SpotifyDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.artists_playlist_maker.ArtistsPmViewModel;
import data_access.FileUserDataAccessObject;
import entity.UserFactory;
import interface_adapter.loginOAuth.LoginOAuthViewModel;
import interface_adapter.playlists.PlaylistsViewModel;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.split_playlist.SplitState;
import interface_adapter.split_playlist.SplitViewModel;
import interface_adapter.home.HomeViewModel;
import view.*;

public class Main {
    public static void main(String[] args) {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        JFrame application = new JFrame("Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // The data for the views, such as username and password, are in the ViewModels.
        // This information will be changed by a presenter object that is reporting the
        // results from the use case. The ViewModels are observable, and will
        // be observed by the Views.
        LoginOAuthViewModel loginOAuthViewModel = new LoginOAuthViewModel();
        SplitViewModel splitViewModel = new SplitViewModel();
        PlaylistsViewModel playlistsViewModel = new PlaylistsViewModel();
        HomeViewModel homeViewModel = new HomeViewModel();
        ArtistsPmViewModel artistsPmViewModel = new ArtistsPmViewModel();
        ProfileViewModel profileViewModel = new ProfileViewModel();

        FileUserDataAccessObject userDataAccessObject;
        SpotifyDataAccessObject spotifyDataAccessObject;
        try {
            userDataAccessObject = new FileUserDataAccessObject("./users.csv", new UserFactory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        spotifyDataAccessObject = new SpotifyDataAccessObject();

        LoginOAuthView loginOAuthView = LoginOAuthUseCaseFactory.create(viewManagerModel, loginOAuthViewModel, homeViewModel, spotifyDataAccessObject);
        views.add(loginOAuthView, loginOAuthView.viewName);
        SplitView splitView = SplitUseCaseFactory.create(viewManagerModel, splitViewModel, playlistsViewModel,spotifyDataAccessObject, spotifyDataAccessObject);
        views.add(splitView, splitView.viewName);
        HomeView homeView = HomeUseCaseFactory.create(viewManagerModel, homeViewModel, profileViewModel, spotifyDataAccessObject);
        views.add(homeView, homeView.viewName);
        ArtistsPlaylistMakerView artistsPlaylistMakerView = ArtistsPmUseCaseFactory.create(viewManagerModel, artistsPmViewModel,spotifyDataAccessObject);
        views.add(artistsPlaylistMakerView, artistsPlaylistMakerView.viewName);
        ProfileView profileView = ProfileViewFactory.create(viewManagerModel, profileViewModel);
        views.add(profileView, profileView.viewName);

        // temporary - remove after factory is implemented
//        MergeView mergeView = new MergeView();

        viewManagerModel.setActiveView(loginOAuthView.viewName);
        viewManagerModel.firePropertyChanged();

        application.setSize(1000, 600);
        application.setVisible(true);
    }
}