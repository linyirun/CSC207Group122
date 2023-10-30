package interface_adapter.merge_playlist;

import use_case.merge_playlist.MergeOutputBoundary;
import use_case.merge_playlist.MergeOutputData;
//import interface_adapter.merge_playlist.MergeViewModel;


public class MergePresenter implements MergeOutputBoundary {

//    private MergeViewModel mergeViewModel;

    @Override
    public void prepareSuccessView(MergeOutputData response) {

    }

    public void prepareFailView(String error){

    }
}
