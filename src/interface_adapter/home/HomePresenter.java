package interface_adapter.home;

import interface_adapter.ViewManagerModel;
import interface_adapter.loginOAuth.LoginOAuthState;
import use_case.home.HomeOutputBoundary;
import use_case.home.HomeOutputData;

import javax.swing.text.View;

public class HomePresenter implements HomeOutputBoundary {
    ViewManagerModel viewManagerModel;
    HomeViewModel homeViewModel;
    public HomePresenter(ViewManagerModel viewManagerModel, HomeViewModel homeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homeViewModel = homeViewModel;
    }

    public void prepareSuccessView(HomeOutputData data) {
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
