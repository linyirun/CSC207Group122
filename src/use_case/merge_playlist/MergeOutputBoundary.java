package use_case.merge_playlist;

public interface MergeOutputBoundary {

    void prepareSuccessView(MergeOutputData response);
    void prepareFailView(String error);
}
