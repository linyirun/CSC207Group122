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

//    //for testing purpose only can be deleted when clening the code but let Raymond know
//    public void changeToSplit(){
//        interactor.changeToSplit();
//    }
}
