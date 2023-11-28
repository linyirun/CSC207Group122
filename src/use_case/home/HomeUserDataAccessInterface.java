package use_case.home;

import entity.Song;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface HomeUserDataAccessInterface {
    Map<String, List<String>> getUserTopTracksAndArtists() throws ParseException, IOException, InterruptedException;

    Map<String, String> getPlaylistMap();

    List<Song> getSongs(String playlistID);
}
