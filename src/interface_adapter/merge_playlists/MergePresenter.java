package interface_adapter.merge_playlists;

import use_case.merge_playlists.MergeOutputBoundary;
import use_case.merge_playlists.MergeOutputData;


public class MergePresenter implements MergeOutputBoundary {

    private MergeViewModel mergeViewModel;

    @Override
    public void prepareSuccessView(MergeOutputData response) {

    }

    public void prepareFailView(String error){

    }
}
