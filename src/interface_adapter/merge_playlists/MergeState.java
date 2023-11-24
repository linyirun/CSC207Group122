package interface_adapter.merge_playlists;

import java.util.List;

public class MergeState {

    // If goHome is true, then return to home
    private boolean goHome;

    // List of the selected playlist ids.
    private List<String> selectedIds;

    public MergeState() {
        this.goHome = false;

    }

    public boolean isGoHome() {
        return goHome;
    }

    public void setGoHome(boolean goHome) {
        this.goHome = goHome;
    }

    public List<String> getSelectedIds() {
        return selectedIds;
    }

    public void setSelectedIds(List<String> selectedIds) {
        this.selectedIds = selectedIds;
    }
}
