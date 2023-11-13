package use_case.Lyrics;

import app.SpotifyAuth;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LyricsInteractor implements MergeInputBoundary {

    private MergeDataAccessInterface mergeDataAccessObject;
    @Override
    public void execute(MergeInputData data) {
    try {
        URL url = new URL("https://api.spotify.com/v1/users/smedjan/playlists");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        httpConn.setRequestProperty("Authorization", "Bearer " + SpotifyAuth.accessToken);

        int result = httpConn.getResponseCode();
        if (result == 200) {
            InputStream responseStream = httpConn.getInputStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";
            System.out.println(response);

        } else {
            System.out.println("Request failed.");
        }
    }
    catch (IOException e) {
            e.printStackTrace();
        }


    }

}
