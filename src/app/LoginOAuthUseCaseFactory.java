package app;

import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.loginOAuth.LoginOAuthController;
import interface_adapter.loginOAuth.LoginOAuthPresenter;
import interface_adapter.loginOAuth.LoginOAuthViewModel;
import interface_adapter.home.HomeViewModel;
import use_case.loginOAuth.LoginOAuthInputBoundary;
import use_case.loginOAuth.LoginOAuthOutputBoundary;
import use_case.loginOAuth.LoginOAuthInteractor;
import use_case.loginOAuth.LoginOAuthUserDataAccessInterface;
import view.LoginOAuthView;

import javax.swing.*;
import java.io.IOException;

public class LoginOAuthUseCaseFactory {

    /**
     * Prevent instantiation.
     */
    private LoginOAuthUseCaseFactory() {
    }

    public static LoginOAuthView create(
            ViewManagerModel viewManagerModel, LoginOAuthViewModel loginOAuthViewModel, HomeViewModel homeViewModel,
            LoginOAuthUserDataAccessInterface userDataAccessObject) {

        try {
            LoginOAuthController loginOAuthController = createLoginUseCase(viewManagerModel, loginOAuthViewModel, homeViewModel, userDataAccessObject);
            return new LoginOAuthView(loginOAuthViewModel, loginOAuthController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error TODO");
        }

        return null;
    }

    private static LoginOAuthController createLoginUseCase(
            ViewManagerModel viewManagerModel,
            LoginOAuthViewModel loginOAuthViewModel, HomeViewModel homeViewModel,
            LoginOAuthUserDataAccessInterface userDataAccessObject) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        LoginOAuthOutputBoundary loginOutputBoundary = new LoginOAuthPresenter(viewManagerModel, loginOAuthViewModel, homeViewModel);

        LoginOAuthInputBoundary loginOAuthInteractor = new LoginOAuthInteractor(userDataAccessObject, loginOutputBoundary);

        return new LoginOAuthController(loginOAuthInteractor);
    }
}
