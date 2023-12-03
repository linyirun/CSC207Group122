package interface_adapter.home;

import entity.Song;
import use_case.Lyrics.LyricsInputBoundary;
import use_case.Lyrics.LyricsInputData;
import use_case.home.HomeInputBoundary;
import use_case.home.HomeInputData;

import java.util.List;
import java.util.Map;

public class HomeController {
    HomeInputBoundary interactor;
    LyricsInputBoundary lyricsInteractor;

    public HomeController(HomeInputBoundary interactor, LyricsInputBoundary lyricsInteractor) {
        this.interactor = interactor;
        this.lyricsInteractor = lyricsInteractor;
    }

    public void execute(String button_name) {
        interactor.execute(new HomeInputData(button_name));
    }

    public Map<String, String> getPlaylistsMap() {
        return interactor.getPlaylistsMap();
    }

    public List<Song> getSongs(String playlistID) {
        return interactor.getSongs(playlistID);
    }

    public String getLyrics(String songName) {
        return this.lyricsInteractor.execute(new LyricsInputData(songName));
    }

}
