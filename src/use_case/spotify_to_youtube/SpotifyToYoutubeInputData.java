package use_case.spotify_to_youtube;

import java.util.List;

public class SpotifyToYoutubeInputData {


    List<String> selectedPlaylists;
    List<String> givenArtists;

    public SpotifyToYoutubeInputData(List<String> selectedPlaylists, List<String> givenArtists) {
        this.selectedPlaylists = selectedPlaylists;
        this.givenArtists = givenArtists;
    }


}
