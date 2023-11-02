package interface_adapter.loginOAuth;

import use_case.loginOAuth.LoginOAuthOutputBoundary;
import use_case.loginOAuth.LoginOAuthOutputData;
import interface_adapter.ViewManagerModel;
import interface_adapter.loginOAuth.LoginOAuthViewModel;
import interface_adapter.loginOAuth.LoginOAuthState;

public class LoginOAuthPresenter implements LoginOAuthOutputBoundary {
    ViewManagerModel viewManagerModel;
    LoginOAuthViewModel loginOAuthViewModel;

    public LoginOAuthPresenter(ViewManagerModel viewManagerModel, LoginOAuthViewModel loginOAuthViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loginOAuthViewModel = loginOAuthViewModel;

    }
    @Override
    public void prepareSuccessView(LoginOAuthOutputData data) {

    }

    @Override
    public void prepareFailView(String httpErrorCode, String httpErrorMessage) {

    }
}
