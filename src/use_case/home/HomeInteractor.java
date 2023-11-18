package use_case.home;

import interface_adapter.home.HomePresenter;

public class HomeInteractor implements HomeInputBoundary{
    HomeOutputBoundary presenter;
    HomeUserDataAccessInterface dao;

    public HomeInteractor(HomeOutputBoundary presenter, HomeUserDataAccessInterface dao) {
        this.presenter = presenter;
        this.dao = dao;
    }

    public void execute(HomeInputData data) {
        String button_name = data.getButtonName();
        if (button_name.equals("split")) {
            presenter.prepareSuccessView(new HomeOutputData("Split Playlist"));
        }
        else {
            presenter.prepareFailView("Event source not defined");
        }
    }
}
