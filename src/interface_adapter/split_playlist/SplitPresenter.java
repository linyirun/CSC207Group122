package interface_adapter.split_playlist;

import interface_adapter.ViewManagerModel;
import use_case.split_playlist.SplitOutputBoundary;
import use_case.split_playlist.SplitOutputData;

public class SplitPresenter implements SplitOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final SplitViewModel splitViewModel;

    public SplitPresenter(ViewManagerModel viewManagerModel, SplitViewModel splitViewModel) {
        this.splitViewModel = splitViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(SplitOutputData response) {
        SplitState state = new SplitState();
        state.setCreatedPlaylistNames(response.getCreatedPlaylistNames());
        splitViewModel.setState(state);
    }

    @Override
    public void prepareFailView(String error) {
        splitViewModel.firePropertyChanged();
    }
}
