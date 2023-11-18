package interface_adapter.home;

import use_case.home.HomeInputBoundary;
import use_case.home.HomeInputData;

public class HomeController {
    HomeInputBoundary interactor;
    public HomeController(HomeInputBoundary interactor) {
        this.interactor = interactor;
    }
    public void execute(String button_name) {
        interactor.execute(new HomeInputData(button_name));
    }
}
