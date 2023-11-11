package interface_adapter.loginOAuth;

import use_case.loginOAuth.LoginOAuthInputBoundary;
import use_case.loginOAuth.LoginOAuthInputData;

import java.io.IOException;

public class LoginOAuthController {
    LoginOAuthInputBoundary interactor;

    public LoginOAuthController(LoginOAuthInputBoundary interactor) {
        this.interactor = interactor;
    }
    public void execute() {
        try {
            interactor.execute();
        }
        catch (IOException e) {
            System.out.println("Token Invalid");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
