package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.home.HomeController;
import interface_adapter.home.HomeViewModel;
import interface_adapter.profile.ProfileViewModel;
import use_case.home.HomeUserDataAccessInterface;
import view.HomeView;
import view.ProfileView;

import javax.swing.*;
import java.io.IOException;

public class ProfileViewFactory {
    public ProfileViewFactory() {}
    public static ProfileView create(ViewManagerModel viewManagerModel, ProfileViewModel homeViewModel) {
        return new ProfileView(homeViewModel, viewManagerModel);
    }
}
