package use_case.split_playlist;

public interface SplitInputBoundary {
    void execute(SplitInputData splitInputData);

    void splitByLength(String playlistName, int startTime, int endTime);
}
