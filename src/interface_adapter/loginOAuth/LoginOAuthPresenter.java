package interface_adapter.loginOAuth;

import interface_adapter.ViewManagerModel;
import interface_adapter.home.HomeState;
import interface_adapter.home.HomeViewModel;
import use_case.loginOAuth.LoginOAuthOutputBoundary;
import use_case.loginOAuth.LoginOAuthOutputData;

public class LoginOAuthPresenter implements LoginOAuthOutputBoundary {
    ViewManagerModel viewManagerModel;
    LoginOAuthViewModel loginOAuthViewModel;
    HomeViewModel homeViewModel;

    public LoginOAuthPresenter(ViewManagerModel viewManagerModel, LoginOAuthViewModel loginOAuthViewModel, HomeViewModel homeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loginOAuthViewModel = loginOAuthViewModel;
        this.homeViewModel = homeViewModel;

    }

    @Override
    public void prepareSuccessView(LoginOAuthOutputData data) {
        HomeState homeState = homeViewModel.getState();
        homeState.setDisplayName(data.getDisplayName());
        homeViewModel.setState(homeState);
        homeViewModel.firePropertyChanged();
        viewManagerModel.setActiveView("Home");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailViewHTTP(String httpErrorCode, String httpErrorMessage) {
        LoginOAuthState loginOAuthState = loginOAuthViewModel.getState();
        String errorMessage = httpErrorMessage + "\n" + httpErrorCode + "\nToken was invalid";
        loginOAuthState.setOAuthError(errorMessage);
        this.loginOAuthViewModel.setState(loginOAuthState);
        this.loginOAuthViewModel.firePropertyChangedError();
    }

    @Override
    public void prepareFailViewDesktop(String error) {
        LoginOAuthState loginOAuthState = loginOAuthViewModel.getState();
        loginOAuthState.setOAuthError(error);
        this.loginOAuthViewModel.setState(loginOAuthState);
        this.loginOAuthViewModel.firePropertyChangedError();
    }

}
