package interface_adapter.loginOAuth;

import use_case.loginOAuth.LoginOAuthInputBoundary;
import use_case.loginOAuth.LoginOAuthInputData;

import java.io.IOException;

public class LoginOAuthController {
    LoginOAuthInputBoundary interactor;

    public LoginOAuthController(LoginOAuthInputBoundary interactor) {
        this.interactor = interactor;
    }
    public void execute(String code) {
        try {
            interactor.execute(new LoginOAuthInputData(code));
        }
        catch (IOException e) {
            System.out.println("Token Invalid");
        }
    }
}
