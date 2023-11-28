package interface_adapter.home;

import interface_adapter.ViewManagerModel;
import interface_adapter.loginOAuth.LoginOAuthState;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.spotfiy_to_youtube.SpotifyToYoutubeState;
import interface_adapter.spotfiy_to_youtube.SpotifyToYoutubeViewModel;
import use_case.Lyrics.LyricsOutputBoundary;
import use_case.Lyrics.LyricsOutputData;
import use_case.home.HomeOutputBoundary;
import use_case.home.HomeOutputData;

import javax.swing.text.View;

public class HomePresenter implements HomeOutputBoundary, LyricsOutputBoundary {
    ViewManagerModel viewManagerModel;
    HomeViewModel homeViewModel;
    ProfileViewModel profileViewModel;
    SpotifyToYoutubeViewModel spotifyToYoutubeViewModel;
    public HomePresenter(ViewManagerModel viewManagerModel, HomeViewModel homeViewModel, ProfileViewModel profileViewModel, SpotifyToYoutubeViewModel spotifyToYoutubeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homeViewModel = homeViewModel;
        this.profileViewModel = profileViewModel;
        this.spotifyToYoutubeViewModel = spotifyToYoutubeViewModel;
    }

    public void prepareSuccessView(HomeOutputData data) {
        if (data.getProfileObjects() != null) {
            ProfileState state = profileViewModel.getState();
            state.setProfileObjects(data.getProfileObjects());
            profileViewModel.firePropertyChanged();
        }
        else if (data.getPlaylistNames() != null) {
            SpotifyToYoutubeState state = spotifyToYoutubeViewModel.getState();
            state.setPlaylistNames(data.getPlaylistNames());
            spotifyToYoutubeViewModel.firePropertyChangedRefresh();
        }
        viewManagerModel.setActiveView(data.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    public void prepareSuccessView(LyricsOutputData data) {

    }
    public void prepareFailView(String error) {
        HomeState homeState = homeViewModel.getState();
        homeState.setButtonErrorMsg(error);
        this.homeViewModel.setState(homeState);
        this.homeViewModel.firePropertyChanged();
    }

}
