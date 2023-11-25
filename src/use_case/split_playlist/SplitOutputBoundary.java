package use_case.split_playlist;



public interface SplitOutputBoundary {
    void prepareSuccessView(SplitOutputData user);

    void prepareFailView(String error);
}
