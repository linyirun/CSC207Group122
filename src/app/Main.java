package app;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;

import data_access.SpotifyDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.artists_playlist_maker.ArtistsPmViewModel;
import interface_adapter.login.LoginViewModel;
import data_access.FileUserDataAccessObject;
import entity.UserFactory;
import interface_adapter.loginOAuth.LoginOAuthViewModel;
import interface_adapter.playlists.PlaylistsViewModel;
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
        LoginViewModel loginViewModel = new LoginViewModel();
        LoginOAuthViewModel loginOAuthViewModel = new LoginOAuthViewModel();
        SplitViewModel splitViewModel = new SplitViewModel();
        PlaylistsViewModel playlistsViewModel = new PlaylistsViewModel();
        HomeViewModel homeViewModel = new HomeViewModel();
        ArtistsPmViewModel artistsPmViewModel = new ArtistsPmViewModel();

        FileUserDataAccessObject userDataAccessObject;
        SpotifyDataAccessObject spotifyDataAccessObject;
        try {
            userDataAccessObject = new FileUserDataAccessObject("./users.csv", new UserFactory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        spotifyDataAccessObject = new SpotifyDataAccessObject();

        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, loginOAuthViewModel,userDataAccessObject);
        views.add(loginView, loginView.viewName);
        LoginOAuthView loginOAuthView = LoginOAuthUseCaseFactory.create(viewManagerModel, loginOAuthViewModel, homeViewModel, spotifyDataAccessObject);
        views.add(loginOAuthView, loginOAuthView.viewName);
        SplitView splitView = SplitUseCaseFactory.create(viewManagerModel, splitViewModel, playlistsViewModel,spotifyDataAccessObject, spotifyDataAccessObject);
        views.add(splitView, splitView.viewName);
        HomeView homeView = HomeUseCaseFactory.create(viewManagerModel, homeViewModel, spotifyDataAccessObject, splitView.getPlaylistsController(), playlistsViewModel);
        views.add(homeView, homeView.viewName);
        ArtistsPlaylistMakerView artistsPlaylistMakerView = ArtistsPmUseCaseFactory.create(viewManagerModel, artistsPmViewModel,spotifyDataAccessObject);
        views.add(artistsPlaylistMakerView, artistsPlaylistMakerView.viewName);


        // temporary - remove after factory is implemented
//        MergeView mergeView = new MergeView();

//        viewManagerModel.setActiveView(loginView.viewName);
        viewManagerModel.setActiveView(MergeView.viewName);
        viewManagerModel.firePropertyChanged();

        application.setSize(1000, 600);
        application.setVisible(true);

//        http GET https://api.spotify.com/v1/me/playlistsAuthorization:'Bearer 1POdFZRZbvb...qqillRxMr2z'

//        try {
//            URL url = new URL("https://api.spotify.com/v1/me/playlists?" +
//                    "Authorization:'Bearer AQCArGEQuxNX6sj94yfOdmvjRD1z08dhvuHtDOUe3Ia3HDfL4wd9yIHco3dkkSE0vPiJiBqrNKkPe_1pdXQXXNB_3f38nyn4yweBNb_nEWfBVoZYfm4nTxoMrKoD4zX8M1pogtxilIapsIRsZJAjs__fVL2pB-wFmSse7Ts2yrs--uhFttBIo0-PZmEBz3zRllwvYGbuW1wZC0C8OYsyJ8KLxnnzUJdsmWhfriCfdK7IhvIF_NOIMEnHgnRAvAtFuMzdPLs5nwK2Un-3HnbLEg'");
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//
//            int status = con.getResponseCode();
//
//            System.out.println("Response Code: " + status);
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//            String input;
//            StringBuilder response = new StringBuilder();
//            while ((input = in.readLine()) != null) {
//                response.append(input);
//                response.append("\n");
//            }
//            in.close();
//            System.out.println(response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }//added a comment
}