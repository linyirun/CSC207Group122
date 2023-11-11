package use_case.split_playlist;


import use_case.login.LoginOutputData;

public interface SplitOutputBoundary {
    void prepareSuccessView(SplitOutputData user);

    void prepareFailView(String error);
}
