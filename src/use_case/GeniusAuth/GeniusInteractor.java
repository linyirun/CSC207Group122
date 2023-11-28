package use_case.GeniusAuth;

import entity.GeniusAuth;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class GeniusInteractor {

    private GeniusDataAccessInterface mergeDataAccessObject;

    public static void execute() {
        String clientSecret = GeniusAuth.getClientSecret();
        String clientId = GeniusAuth.getClientId();
        try {
            String tokenUrl = "https://api.genius.com/oauth/token";
            String clientIdAndSecret = clientId + ":" + clientSecret;

            String clientBase64 = Base64.getEncoder().encodeToString(clientIdAndSecret.getBytes());

            HttpURLConnection connection = (HttpURLConnection) new URL(tokenUrl).openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + clientBase64);
            connection.getOutputStream().write("grant_type=client_credentials".getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject;
                try {
                    jsonObject = (JSONObject) jsonParser.parse(
                            new InputStreamReader(inputStream, "UTF-8"));
                    System.out.println(jsonObject.toJSONString());
                    GeniusAuth.setAccessToken((String) jsonObject.get("access_token"));
                    System.out.println(GeniusAuth.getAccessToken());
                } catch (ParseException e) {
                    System.out.println("InputStream could not be parsed into JSON object");
                }

            } else {
                System.out.println("Token request failed: " + responseCode);
            }
        }
        catch (IOException e) {
                e.printStackTrace();
            }
    }
    public static void getUrl(){
        try{
            //String tokenUrl = "https://api.genius.com/search?q=WAP";
            String tokenUrl = "https://api.genius.com/songs/1125517";
            String access_token = GeniusAuth.getAccessToken();

            HttpURLConnection connection = (HttpURLConnection) new URL(tokenUrl).openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer " + access_token);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject;
                try {
                    jsonObject = (JSONObject) jsonParser.parse(
                            new InputStreamReader(inputStream, "UTF-8"));
                    System.out.println(jsonObject.toJSONString());
                } catch (ParseException e) {
                    System.out.println("InputStream could not be parsed into JSON object");
                }

            } else {
                System.out.println("Token request failed: " + responseCode);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void stealLyrics(){
        String url = "https://genius.com/Dire-straits-money-for-nothing-lyrics";
        Document doc = request(url);

    }

    private static Document request(String url){
        try {
            Connection connection = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0")
                    .referrer("http://www.google.com")
                    .followRedirects(true);
            Document doc = connection.get();

            if (connection.response().statusCode() == 200) {
                System.out.println("Link: " + url);
//                System.out.println(doc.text());
                String text = doc.text();
                String wantedPart = text.substring(text.indexOf("["));
                // this comes after the lyrics end in text along with around 5 chars of numbers and spaces prior
                int end = wantedPart.indexOf("Embed Cancel");
                wantedPart = wantedPart.substring(0, end - 4);
                System.out.println(wantedPart);
                return doc;
            }
            else{
                System.out.println("error");
                return null;
            }
        }
        catch (IOException e){
            System.out.println(e);
            return null;
        }
    }

}
