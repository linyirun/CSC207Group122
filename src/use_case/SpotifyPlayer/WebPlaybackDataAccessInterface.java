package use_case.SpotifyPlayer;

import entity.Song;

import java.io.IOException;
import java.util.List;

public interface WebPlaybackDataAccessInterface {
     List<Song> getSongs(String playlistID) throws IOException;


}
