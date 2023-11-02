package interface_adapter.loginOAuth;

import use_case.loginOAuth.LoginOAuthInputBoundary;

public class LoginOAuthController {
    LoginOAuthInputBoundary interactor;

    public LoginOAuthController(LoginOAuthInputBoundary interactor) {
        this.interactor = interactor;
    }
    public void execute() {

    }
}
