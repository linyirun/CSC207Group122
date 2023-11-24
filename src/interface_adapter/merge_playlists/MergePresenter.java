package interface_adapter.merge_playlists;

import interface_adapter.ViewManagerModel;
import use_case.merge_playlists.MergeOutputBoundary;
import use_case.merge_playlists.MergeOutputData;


public class MergePresenter implements MergeOutputBoundary {

    private MergeViewModel mergeViewModel;
    private ViewManagerModel viewManagerModel;

    public MergePresenter(MergeViewModel mergeViewModel, ViewManagerModel viewManagerModel) {
        this.mergeViewModel = mergeViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(MergeOutputData response) {

    }

    public void prepareFailView(String error){

    }
}
