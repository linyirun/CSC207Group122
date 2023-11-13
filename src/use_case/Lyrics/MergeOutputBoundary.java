package use_case.Lyrics;

public interface MergeOutputBoundary {

    void prepareSuccessView(MergeOutputData response);
    void prepareFailView(String error);
}
