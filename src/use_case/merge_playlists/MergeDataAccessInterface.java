package use_case.merge_playlists;

import entity.Song;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MergeDataAccessInterface {

    Map<String, String> getPlaylistMap();

    List<Song> getSongs(String playlistID);

    String getUserId() throws IOException, InterruptedException, ParseException;

    String createPlaylist(String playlistName, String userId) throws IOException, InterruptedException, ParseException;

    void addSongsToPlaylist(String playlistId, List<String> songIds) throws IOException, InterruptedException;

    List<Map<String, String>> getSongsAudioFeatures(List<String> songIds);
}
