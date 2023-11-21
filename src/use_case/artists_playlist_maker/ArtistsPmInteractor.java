package use_case.artists_playlist_maker;

import entity.SpotifyAuth;
import org.json.simple.parser.ParseException;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;
import use_case.login.LoginUserDataAccessInterface;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ArtistsPmInteractor implements ArtistsPmInputBoundary{

    ArtistsPmUserDataAccessInterface dao;
    ArtistsPmOutputBoundary presenter;
    public ArtistsPmInteractor(ArtistsPmUserDataAccessInterface dataAccessObject, ArtistsPmOutputBoundary presenter) {
        dao = dataAccessObject;
        this.presenter = presenter;

    }
    public void execute() throws IOException, ParseException, InterruptedException {
        List<String> artists = dao.chooseArtistsAndCreatePlaylist();
//        URL url = new URL("https://accounts.spotify.com/authorize?client_id=" + SpotifyAuth.getClientId() + "&response_type" +
//                "=code&scope=" + SpotifyAuth.getScope() + "&redirect_uri=http://localhost:8080/callback") ;
//        HttpURLConnection http = (HttpURLConnection) url.openConnection();
//        http.setRequestMethod("GET");
//        int responseCode = http.getResponseCode();
//        System.out.println(responseCode);
//        System.out.println(HttpURLConnection.HTTP_OK);
//        if (HttpURLConnection.HTTP_OK == responseCode) {
//            presenter.prepareSuccessView(new LoginOutputData(http.getURL(), false));
//        }
//        else{
//            presenter.prepareFailView(Integer.toString(responseCode), http.getResponseMessage());
//        }
    }


}
