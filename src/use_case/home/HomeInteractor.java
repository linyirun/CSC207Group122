package use_case.home;

import entity.Song;
import interface_adapter.home.HomeViewModel;
import org.json.simple.parser.ParseException;
import view.MergeView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HomeInteractor implements HomeInputBoundary {
    HomeOutputBoundary presenter;
    HomeUserDataAccessInterface dao;

    public HomeInteractor(HomeOutputBoundary presenter, HomeUserDataAccessInterface dao) {
        this.presenter = presenter;
        this.dao = dao;
    }

    public void execute(HomeInputData data) {
        String button_name = data.getButtonName();
        if (button_name.equals(HomeViewModel.SPLIT_PLAYLIST_NAME)) {
            presenter.prepareSuccessView(new HomeOutputData("Split Playlist"));
        } else if (button_name.equals(HomeViewModel.MERGE_PLAYLIST_NAME)) {
            presenter.prepareSuccessView(new HomeOutputData(MergeView.viewName));
        } else if (button_name.equals(HomeViewModel.ARTISTS_PLAYLIST_MAKER_NAME)) {
            presenter.prepareSuccessView(new HomeOutputData("Artists Playlist Maker"));
        } else if (button_name.equals("profile")) {
            try {
                presenter.prepareSuccessView(new HomeOutputData("Profile", dao.getUserTopTracksAndArtists()));
            } catch (ParseException | IOException | InterruptedException e) {
                presenter.prepareFailView("Problem when fetching user data");
            }
        } else if (button_name.equals(HomeViewModel.SPOTIFY_TO_YT_NAME)) {
            Set<String> playlistNames = dao.getPlaylistMap().keySet();
            // Convert this to a list
            List<String> listNames = new ArrayList<>();
            listNames.addAll(playlistNames);
            presenter.prepareSuccessView(new HomeOutputData("Spot To YT", listNames));
        } else {
            presenter.prepareFailView("Event source not defined");
        }
    }

    @Override
    public Map<String, String> getPlaylistsMap() {
        return dao.getPlaylistMap();
    }

    @Override
    public List<Song> getSongs(String playlistID) {
        return dao.getSongs(playlistID);
    }


}
