package use_case.loginOAuth;

import use_case.login.LoginOutputData;

public interface LoginOAuthOutputBoundary {
    void prepareSuccessView(LoginOAuthOutputData data);

    void prepareFailView(String httpErrorCode, String httpErrorMessage);
}
