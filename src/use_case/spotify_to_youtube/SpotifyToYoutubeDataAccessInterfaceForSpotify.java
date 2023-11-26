package use_case.spotify_to_youtube;

import entity.Song;

import java.util.List;
import java.util.Map;

public interface SpotifyToYoutubeDataAccessInterfaceForSpotify {

    List<Song> getSongs(String playlistID);

    Map<String, String> getPlaylistMap();


}