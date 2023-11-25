package use_case.merge_playlists;

import java.util.List;

public interface MergeInputBoundary {

    void mergePlaylists(MergeInputData data);

    void returnHome();

    List<String> getPlaylists();
}
