package use_case.GeniusAuth;

public interface GeniusOutputBoundary {

    void prepareSuccessView(GeniusOutputData response);
    void prepareFailView(String error);
}
