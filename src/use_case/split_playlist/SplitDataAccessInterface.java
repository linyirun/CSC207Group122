package use_case.split_playlist;
import java.util.Set;
import java.util.Map;

public interface SplitDataAccessInterface {
    Set<String> get_playlists();

    Map<String, String> get_playlistMap();
}
