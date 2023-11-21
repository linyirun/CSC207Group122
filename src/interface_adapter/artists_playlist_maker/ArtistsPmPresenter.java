package interface_adapter.artists_playlist_maker;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.loginOAuth.LoginOAuthState;
import interface_adapter.loginOAuth.LoginOAuthViewModel;
import use_case.artists_playlist_maker.ArtistsPmOutputBoundary;
import use_case.login.LoginOutputData;

public class ArtistsPmPresenter implements ArtistsPmOutputBoundary {

    private ViewManagerModel viewManagerModel;
    private ArtistsPmViewModel artistsPmViewModel;


    public ArtistsPmPresenter(ViewManagerModel viewManagerModel, ArtistsPmViewModel artistsPmViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.artistsPmViewModel = artistsPmViewModel;

    }
    public void prepareSuccessView(LoginOutputData data) {

        this.viewManagerModel.firePropertyChanged();

    }


    public void prepareFailView(String httpErrorCode, String httpErrorMessage) {

    }
}
