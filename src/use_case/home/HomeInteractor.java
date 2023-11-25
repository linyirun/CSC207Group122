package use_case.home;

import interface_adapter.home.HomePresenter;

import view.MergeView;
import interface_adapter.home.HomeViewModel;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class HomeInteractor implements HomeInputBoundary{
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
          else if (button_name.equals("merge")) {
            presenter.prepareSuccessView(new HomeOutputData(MergeView.viewName));
        }
          else if (button_name.equals(HomeViewModel.ARTISTS_PLAYLIST_MAKER_NAME)) {
            presenter.prepareSuccessView(new HomeOutputData("Artists Playlist Maker"));
        } else if (button_name.equals("profile")) {
            try {
                presenter.prepareSuccessView(new HomeOutputData("Profile", dao.getUserTopTracksAndArtists()));
            }
            catch (ParseException | IOException | InterruptedException e) {
                presenter.prepareFailView("Problem when fetching user data");
            }
        } else {
            presenter.prepareFailView("Event source not defined");
        }
    }
}
