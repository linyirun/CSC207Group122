package use_case.spotify_to_youtube;

import entity.Song;

import java.util.List;

public interface SpotifyToYoutubeDataAccessInterface {

    List<String> searchVideos(List<Song> songs);


    void createPlaylist(String playlistName, List<String> videoIDs);
}
