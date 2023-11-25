package interface_adapter.merge_playlists;

import use_case.merge_playlists.MergeInputBoundary;
import use_case.merge_playlists.MergeInputData;

import java.util.List;

public class MergeController {

    private MergeInputBoundary mergeUseCaseInteractor;

    public MergeController(MergeInputBoundary mergeUseCaseInteractor) {
        this.mergeUseCaseInteractor = mergeUseCaseInteractor;
    }

    public void mergePlaylists(MergeInputData data) {
        mergeUseCaseInteractor.mergePlaylists(data);
    }
    public void returnHome() {
        mergeUseCaseInteractor.returnHome();
    }

    /**
     * @return List of the user's playlist names
     */
    public List<String> getPlaylists() {
        return mergeUseCaseInteractor.getPlaylists();
    }
}
