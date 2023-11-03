package app;

import java.util.Scanner;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class SpotifyAuth {
    // CLASS HAS NO USE NOW, JUST HERE FOR SOME REFERENCE FOR NOW
    public static String accessToken = "";
    public static String expiresIn = "";

    private final static String scope = "playlist-modify-public%20playlist-modify-private";

    private final static String CLIENT_ID = "84e604e1f851429db4c89831cf8d03a4";

    private final static String CLIENT_SECRET = "c15d3e66db08456d98487002aab0fa49";


    public static void getAuthorization() throws IOException {

        URL url = new URL("https://accounts.spotify.com/authorize?client_id=" + CLIENT_ID + "&response_type" +
                "=code&scope=" + scope + "&redirect_uri=https://www.google.com/&show_dialog=true") ;
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("GET");
        System.out.println(http.getRequestProperties());
        int responseCode = http.getResponseCode();
        System.out.println(responseCode);
        System.out.println(HttpURLConnection.HTTP_OK);
        if (HttpURLConnection.HTTP_OK == responseCode) {
            System.out.println("Please visit this url: " + http.getURL());
        }
        else{
            System.out.println("Failed to get authorization link");
        }
        System.out.println("Please enter in the authorization code (in your url)");
        Scanner reader = new Scanner(System.in);
        String code = reader.next();
        http.disconnect();
        getAccessToken(code);
    }
    public static void getAccessToken(String token) throws IOException {
        URL url = new URL("https://accounts.spotify.com/api/token?grant_type=authorization_code&code=" +
                token + "&redirect_uri=https://www.google.com/");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        String clientIDAndSecret = CLIENT_ID + ":" + CLIENT_SECRET;
        String clientBase64 = new String(Base64.getEncoder().encode(clientIDAndSecret.getBytes()));
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Authorization", "Basic " + clientBase64);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        http.setChunkedStreamingMode(286);
        int responseCode = http.getResponseCode();
        System.out.println(http.getResponseMessage());
        System.out.println(HttpURLConnection.HTTP_OK);
        if (HttpURLConnection.HTTP_OK == responseCode) {
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response);
        }
        http.disconnect();
    }
//    public static void getAccessTokenTest(String token) throws IOException {
//        URL url = new URL("https://accounts.spotify.com/api/token?grant_type=client_credentials");
//        HttpURLConnection http = (HttpURLConnection) url.openConnection();
//        String clientIDAndSecret = CLIENT_ID + ":" + CLIENT_SECRET;
//        String clientBase64 = new String(Base64.getEncoder().encode(clientIDAndSecret.getBytes()));
//        http.setRequestMethod("POST");
//        http.setDoOutput(true);
//        http.setRequestProperty("Authorization", "Basic " + clientBase64);
//        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        http.setChunkedStreamingMode(286);
//        int responseCode = http.getResponseCode();
//        System.out.println(http.getResponseMessage());
//        System.out.println(HttpURLConnection.HTTP_OK);
//        if (HttpURLConnection.HTTP_OK == responseCode) {
//            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            // print result
//            System.out.println(response);
//        }
//        http.disconnect();
//    }

}