package use_case.loginOAuth;

import entity.SpotifyAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

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
        // parse the response NOT GENERALIZED PARSING
        if (HttpURLConnection.HTTP_OK == responseCode) {
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                response.append(inputLine);
            }
            in.close();
            String[] jsonDataInString = new String[10];
            int j = 0;
            for (int i = 0; i < response.length() - 1; i++) {
                if (response.substring(i, i + 1).equals("\"")) {
                    int nextQuoteIndex = response.indexOf("\"", i + 1);
                    jsonDataInString[j] = response.substring(i + 1, nextQuoteIndex);
                    j++;
                    i = nextQuoteIndex;
                }
            }
            System.out.println(response);
            SpotifyAuth.setAccessToken(jsonDataInString[1]);
            SpotifyAuth.setRefreshToken(jsonDataInString[6]);
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
