package use_case.loginOAuth;

import entity.SpotifyAuth;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.io.InputStream;

public class LoginOAuthInteractor implements LoginOAuthInputBoundary{
    LoginOAuthUserDataAccessInterface dao;
    LoginOAuthOutputBoundary presenter;
    public LoginOAuthInteractor(LoginOAuthUserDataAccessInterface dao, LoginOAuthOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }
    public void execute(LoginOAuthInputData data) throws IOException {
        // make the http POST request for the access token
        String code = data.getCode();
        URL url = new URL("https://accounts.spotify.com/api/token?grant_type=authorization_code&code=" +
                code + "&redirect_uri=https://www.google.com/");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        String clientIDAndSecret = SpotifyAuth.getClientId() + ":" + SpotifyAuth.getClientSecret();
        String clientBase64 = new String(Base64.getEncoder().encode(clientIDAndSecret.getBytes()));
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Authorization", "Basic " + clientBase64);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        http.setChunkedStreamingMode(286);
        int responseCode = http.getResponseCode();
        System.out.println(http.getResponseMessage());
        // parse the InputStream with json simple package
        if (HttpURLConnection.HTTP_OK == responseCode) {
            InputStream inputStream = http.getInputStream();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject;
            try {
                jsonObject = (JSONObject)jsonParser.parse(
                        new InputStreamReader(inputStream, "UTF-8"));
                System.out.println(jsonObject.toJSONString());
                SpotifyAuth.setAccessToken((String)jsonObject.get("access_token"));
                SpotifyAuth.setRefreshToken((String)jsonObject.get("refresh_token"));
            }
            catch (ParseException e) {
                System.out.println("InputStream could not be parsed into JSON object");
            }
            System.out.println(SpotifyAuth.getAccessToken());
            System.out.println(SpotifyAuth.getRefreshToken());
            presenter.prepareSuccessView(new LoginOAuthOutputData());
        }
        else {
            presenter.prepareFailView(Integer.toString(responseCode), http.getResponseMessage());
        }
        http.disconnect();
    }

}
