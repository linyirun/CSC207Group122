package interface_adapter.split_playlist;

import use_case.split_playlist.SplitInputBoundary;
import use_case.split_playlist.SplitInputData;

public class SplitController {
    private final SplitInputBoundary splitUseCaseInteractor;

    public SplitController(SplitInputBoundary splitUseCaseInteractor) {
        this.splitUseCaseInteractor = splitUseCaseInteractor;
    }

    public void execute(SplitInputData splitInputData) {
        splitUseCaseInteractor.execute(splitInputData);
    }

    public void splitByLength(String playlistName, int startTime, int endTime) {
        splitUseCaseInteractor.splitByLength(playlistName, startTime, endTime);
    }
}
