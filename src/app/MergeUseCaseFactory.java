package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.merge_playlists.MergeController;
import interface_adapter.merge_playlists.MergePresenter;
import interface_adapter.merge_playlists.MergeViewModel;
import use_case.merge_playlists.MergeDataAccessInterface;
import use_case.merge_playlists.MergeInputBoundary;
import use_case.merge_playlists.MergeInteractor;
import use_case.merge_playlists.MergeOutputBoundary;
import view.MergeView;

public class MergeUseCaseFactory {
    // Prevents instantiation
    private MergeUseCaseFactory() {

    }

    public static MergeView create(ViewManagerModel viewManagerModel, MergeViewModel mergeViewModel, MergeDataAccessInterface mergeDataAccessInterface) {

        MergeController mergeController = createMergeUseCase(viewManagerModel, mergeViewModel, mergeDataAccessInterface);
        return new MergeView(mergeViewModel, mergeController, viewManagerModel);
    }

    private static MergeController createMergeUseCase(ViewManagerModel viewManagerModel, MergeViewModel mergeViewModel, MergeDataAccessInterface mergeDataAccessInterface) {
        MergeOutputBoundary mergeOutputBoundary = new MergePresenter(mergeViewModel, viewManagerModel);

        MergeInputBoundary mergeInteractor = new MergeInteractor(mergeDataAccessInterface, mergeOutputBoundary);

        MergeController mergeController = new MergeController(mergeInteractor);
        return mergeController;
    }
}
