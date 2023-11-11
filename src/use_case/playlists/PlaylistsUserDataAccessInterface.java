package use_case.playlists;

import java.util.Map;
import java.util.Set;

public interface PlaylistsUserDataAccessInterface {
    Set<String> get_playlists();

    Map<String, String> get_playlistMap();
}
