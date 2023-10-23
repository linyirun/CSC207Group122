package src.interface_adapter.merge_playlist;

import src.use_case.merge_playlist.MergeOutputBoundary;
import src.use_case.merge_playlist.MergeOutputData;


public class MergePresenter implements MergeOutputBoundary {

    private MergeViewModel mergeViewModel;

    @Override
    public void prepareSuccessView(MergeOutputData response) {

    }

    public void prepareFailView(String error){

    }
}
