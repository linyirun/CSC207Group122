package app;

import data_access.LyricsDataAccessObject;
import data_access.SpotifyDataAccessObject;
import data_access.YouTubeDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.artists_playlist_maker.ArtistsPmViewModel;
import interface_adapter.home.HomeViewModel;
import interface_adapter.loginOAuth.LoginOAuthViewModel;
import interface_adapter.merge_playlists.MergeViewModel;
import interface_adapter.playlists.PlaylistsViewModel;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.split_playlist.SplitViewModel;
import interface_adapter.spotfiy_to_youtube.SpotifyToYoutubeViewModel;
import use_case.GeniusAuth.GeniusInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.
        GeniusInteractor.execute();
        // The main application window.
        JFrame application = new JFrame("Tune Transit");
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
        MergeViewModel mergeViewModel = new MergeViewModel();
        ArtistsPmViewModel artistsPmViewModel = new ArtistsPmViewModel();
        ProfileViewModel profileViewModel = new ProfileViewModel();
        SpotifyToYoutubeViewModel spotifyToYoutubeViewModel = new SpotifyToYoutubeViewModel();
        SpotifyDataAccessObject spotifyDataAccessObject;
        YouTubeDataAccessObject youTubeDataAccessObject;
        LyricsDataAccessObject lyricsDataAccessObject;

        spotifyDataAccessObject = new SpotifyDataAccessObject();

        youTubeDataAccessObject = new YouTubeDataAccessObject();

        lyricsDataAccessObject = new LyricsDataAccessObject();

        LoginOAuthView loginOAuthView = LoginOAuthUseCaseFactory.create(viewManagerModel, loginOAuthViewModel, homeViewModel, spotifyDataAccessObject);
        views.add(loginOAuthView, loginOAuthView.viewName);
        SplitView splitView = SplitUseCaseFactory.create(viewManagerModel, splitViewModel, playlistsViewModel, spotifyDataAccessObject, spotifyDataAccessObject);
        views.add(splitView, splitView.viewName);
        HomeView homeView = HomeUseCaseFactory.create(viewManagerModel, homeViewModel, profileViewModel, spotifyToYoutubeViewModel, spotifyDataAccessObject, lyricsDataAccessObject);
        views.add(homeView, homeView.viewName);

        MergeView mergeView = MergeUseCaseFactory.create(viewManagerModel, mergeViewModel, spotifyDataAccessObject);
        views.add(mergeView, MergeView.viewName);
        ArtistsPlaylistMakerView artistsPlaylistMakerView = ArtistsPmUseCaseFactory.create(viewManagerModel, artistsPmViewModel, spotifyDataAccessObject);
        views.add(artistsPlaylistMakerView, artistsPlaylistMakerView.viewName);
        ProfileView profileView = ProfileViewFactory.create(viewManagerModel, profileViewModel);
        views.add(profileView, profileView.viewName);

        SpotifyToYoutubeView spotifyToYoutubeView = SpotifyToYoutubeUseCaseFactory.create(viewManagerModel, spotifyToYoutubeViewModel, youTubeDataAccessObject, spotifyDataAccessObject);
        views.add(spotifyToYoutubeView, spotifyToYoutubeView.viewName);

        // temporary - remove after factory is implemented

        viewManagerModel.setActiveView(loginOAuthView.viewName);
        viewManagerModel.firePropertyChanged();

        application.setSize(1000, 600);
        application.setVisible(true);
    }
}