package use_case.Lyrics;

public interface LyricsOutputBoundary {

    void prepareSuccessView(LyricsOutputData response);
    void prepareFailView(String error);
}
