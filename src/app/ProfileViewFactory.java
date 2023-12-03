package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.profile.ProfileViewModel;
import view.ProfileView;

public class ProfileViewFactory {
    private ProfileViewFactory() {
    }

    public static ProfileView create(ViewManagerModel viewManagerModel, ProfileViewModel homeViewModel) {
        return new ProfileView(homeViewModel, viewManagerModel);
    }
}
