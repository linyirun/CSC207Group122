package interface_adapter.merge_playlists;

import use_case.merge_playlists.MergeInputBoundary;
import use_case.merge_playlists.MergeInputData;

import java.util.List;

public class MergeController implements MergeInputBoundary {

    private MergeInputBoundary mergeUseCaseInteractor;

    public MergeController(MergeInputBoundary mergeUseCaseInteractor) {
        this.mergeUseCaseInteractor = mergeUseCaseInteractor;
    }

    @Override
    public void execute(MergeInputData data) {

    }
}
