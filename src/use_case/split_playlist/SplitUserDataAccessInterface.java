package use_case.split_playlist;
import entity.Song;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.Map;

public interface SplitUserDataAccessInterface {
    Map<String, String> getPlaylistMap();

    List<Song> getSongs(String playlistID);

    String getUserId() throws IOException, InterruptedException, ParseException;

    String createPlaylist(String playlistName, String userId) throws IOException, InterruptedException, ParseException;

    void addSongsToPlaylist(String playlistId, List<String> songIds) throws IOException, InterruptedException;

    List<String> getSongInterval(String playlistId, int startTime, int endTime);

}
