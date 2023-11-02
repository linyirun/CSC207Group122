package use_case.merge_playlists;

public interface MergeOutputBoundary {

    void prepareSuccessView(MergeOutputData response);
    void prepareFailView(String error);
}
