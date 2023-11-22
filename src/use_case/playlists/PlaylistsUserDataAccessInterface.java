package use_case.playlists;

import java.util.Map;
import java.util.Set;

public interface PlaylistsUserDataAccessInterface {
    Set<String> getPlaylists();

    Map<String, String> getPlaylistMap();
}
