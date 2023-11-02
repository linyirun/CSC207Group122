package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {

    public LoginPresenter(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel) {

    }
    public void prepareSuccessView(LoginOutputData user) {

    }

    @Override
    public void prepareFailView(String error) {

    }
}
