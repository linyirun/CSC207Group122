package use_case.login;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import entity.SpotifyAuth;
import java.util.Base64;

public class LoginInteractor implements LoginInputBoundary {
    LoginUserDataAccessInterface dao;
    LoginOutputBoundary presenter;
    public LoginInteractor(LoginUserDataAccessInterface dataAccessObject, LoginOutputBoundary presenter) {
        dao = dataAccessObject;
        this.presenter = presenter;

    }
    public void execute() throws IOException {
        URL url = new URL("https://accounts.spotify.com/authorize?client_id=" + SpotifyAuth.getClientId() + "&response_type" +
                "=code&scope=" + SpotifyAuth.getScope() + "&redirect_uri=http://localhost:8080/callback") ;
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("GET");
        int responseCode = http.getResponseCode();
        System.out.println(responseCode);
        System.out.println(HttpURLConnection.HTTP_OK);
        if (HttpURLConnection.HTTP_OK == responseCode) {
            presenter.prepareSuccessView(new LoginOutputData(http.getURL(), false));
        }
        else{
            presenter.prepareFailView(Integer.toString(responseCode), http.getResponseMessage());
        }
    }


}