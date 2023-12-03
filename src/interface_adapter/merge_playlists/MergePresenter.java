package interface_adapter.merge_playlists;

import interface_adapter.ViewManagerModel;
import use_case.merge_playlists.MergeOutputBoundary;
import use_case.merge_playlists.MergeOutputData;


public class MergePresenter implements MergeOutputBoundary {

    private final MergeViewModel mergeViewModel;
    private final ViewManagerModel viewManagerModel;

    public MergePresenter(MergeViewModel mergeViewModel, ViewManagerModel viewManagerModel) {
        this.mergeViewModel = mergeViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(MergeOutputData response) {
        if (response.isReturnHome()) {
            // Go back to the home page
            viewManagerModel.setActiveView("Home");
            viewManagerModel.firePropertyChanged();
        }
    }

    public void prepareFailView(String error) {

    }
}
