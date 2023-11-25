package use_case.loginOAuth;


public interface LoginOAuthOutputBoundary {
    void prepareSuccessView(LoginOAuthOutputData data);

    void prepareFailViewHTTP(String httpErrorCode, String httpErrorMessage);

    void prepareFailViewDesktop(String error);
}
