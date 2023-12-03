package use_case.home;

import entity.Song;

import java.util.List;
import java.util.Map;

public interface HomeInputBoundary {
    void execute(HomeInputData data);

    Map<String, String> getPlaylistsMap();

    List<Song> getSongs(String playlistID);
}
