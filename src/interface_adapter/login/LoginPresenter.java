package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;
import interface_adapter.loginOAuth.LoginOAuthViewModel;
import interface_adapter.loginOAuth.LoginOAuthState;

public class LoginPresenter implements LoginOutputBoundary {
    private ViewManagerModel viewManagerModel;
    private LoginViewModel loginViewModel;

    private LoginOAuthViewModel loginOAuthViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel, LoginOAuthViewModel loginOAuthViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.loginOAuthViewModel = loginOAuthViewModel;

    }
    public void prepareSuccessView(LoginOutputData data) {
        LoginOAuthState loginOAuthState = loginOAuthViewModel.getState();
        loginOAuthState.setURl(data.getURL());
        this.loginOAuthViewModel.setState(loginOAuthState);
        this.loginOAuthViewModel.firePropertyChanged();

        this.viewManagerModel.setActiveView(loginOAuthViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String httpErrorCode, String httpErrorMessage) {

    }
}
