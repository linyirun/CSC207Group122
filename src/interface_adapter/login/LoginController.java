package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;
import use_case.login.LoginInteractor;

import java.io.IOException;

public class LoginController {
    LoginInputBoundary interactor;
    public LoginController(LoginInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute() {
        try {
            interactor.execute();
        }
        catch (IOException e) {

        }
    }

}
