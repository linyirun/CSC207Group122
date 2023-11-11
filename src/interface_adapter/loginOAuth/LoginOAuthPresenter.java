package interface_adapter.loginOAuth;

import use_case.loginOAuth.LoginOAuthOutputBoundary;
import use_case.loginOAuth.LoginOAuthOutputData;
import interface_adapter.ViewManagerModel;
import interface_adapter.loginOAuth.LoginOAuthViewModel;
import interface_adapter.loginOAuth.LoginOAuthState;
import java.util.ArrayList;

public class LoginOAuthPresenter implements LoginOAuthOutputBoundary {
    ViewManagerModel viewManagerModel;
    LoginOAuthViewModel loginOAuthViewModel;

    public LoginOAuthPresenter(ViewManagerModel viewManagerModel, LoginOAuthViewModel loginOAuthViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loginOAuthViewModel = loginOAuthViewModel;

    }
    @Override
    public void prepareSuccessView(LoginOAuthOutputData data) {
        viewManagerModel.setActiveView("Split Playlist");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String httpErrorCode, String httpErrorMessage) {
        LoginOAuthState loginOAuthState = loginOAuthViewModel.getState();
        String errorMessage = httpErrorMessage + "\n" + httpErrorCode + "\nToken was invalid";
        loginOAuthState.setOAuthError(errorMessage);
        this.loginOAuthViewModel.setState(loginOAuthState);
        this.loginOAuthViewModel.firePropertyChangedError();
    }

}
