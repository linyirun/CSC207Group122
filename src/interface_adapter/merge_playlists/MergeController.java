package interface_adapter.merge_playlists;

import use_case.merge_playlists.MergeInputBoundary;
import use_case.merge_playlists.MergeInputData;

import java.util.List;

public class MergeController {

    private MergeInputBoundary mergeUseCaseInteractor;

    public MergeController(MergeInputBoundary mergeUseCaseInteractor) {
        this.mergeUseCaseInteractor = mergeUseCaseInteractor;
    }

    public void execute(MergeInputData data) {
        if (data.isReturnHome()) {
            mergeUseCaseInteractor.returnHome();
        } else {
            mergeUseCaseInteractor.mergePlaylists(data);
        }
    }
}
