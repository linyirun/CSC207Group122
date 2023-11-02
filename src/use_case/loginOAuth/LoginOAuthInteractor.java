package use_case.loginOAuth;

public class LoginOAuthInteractor implements LoginOAuthInputBoundary{
    LoginOAuthUserDataAccessInterface dao;
    LoginOAuthOutputBoundary presenter;
    public LoginOAuthInteractor(LoginOAuthUserDataAccessInterface dao, LoginOAuthOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }
    public void execute(LoginOAuthInputData data) {

    }
}
