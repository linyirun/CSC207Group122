package interface_adapter.home;

import interface_adapter.ViewManagerModel;
import interface_adapter.loginOAuth.LoginOAuthState;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;
import use_case.home.HomeOutputBoundary;
import use_case.home.HomeOutputData;

import javax.swing.text.View;

public class HomePresenter implements HomeOutputBoundary {
    ViewManagerModel viewManagerModel;
    HomeViewModel homeViewModel;
    ProfileViewModel profileViewModel;
    public HomePresenter(ViewManagerModel viewManagerModel, HomeViewModel homeViewModel, ProfileViewModel profileViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homeViewModel = homeViewModel;
        this.profileViewModel = profileViewModel;
    }

    public void prepareSuccessView(HomeOutputData data) {
        if (data.getProfileObjects() != null) {
            ProfileState state = profileViewModel.getState();
            state.setProfileObjects(data.getProfileObjects());
            profileViewModel.firePropertyChanged();
        }
        viewManagerModel.setActiveView(data.getViewName());
        viewManagerModel.firePropertyChanged();
    }
    public void prepareFailView(String error) {
        HomeState homeState = homeViewModel.getState();
        homeState.setButtonErrorMsg(error);
        this.homeViewModel.setState(homeState);
        this.homeViewModel.firePropertyChanged();
    }

}
