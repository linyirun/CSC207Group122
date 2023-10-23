package src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://youtube.googleapis.com/youtube/v3/search?part=snippet&q=the+weeknd&key=AIzaSyAvLijvxJDqRfGcE6OttWGWc_xaT6ayyK0");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();

            System.out.println("Response Code: " + status);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String input;
            StringBuilder response = new StringBuilder();
            while ((input = in.readLine()) != null) {
                response.append(input);
                response.append("\n");
            }
            in.close();
            System.out.println(response);
        } catch (Exception e) {

        }
    }
}
//Raymond added a comment