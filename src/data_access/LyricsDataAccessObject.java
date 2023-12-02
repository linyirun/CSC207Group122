package data_access;

import entity.GeniusAuth;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import use_case.Lyrics.LyricsDataAccessInterface;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class LyricsDataAccessObject implements LyricsDataAccessInterface {

    @Override
    public String getLyrics(String songName){
        try{
            // Split the string using '|'
            String[] parts = songName.split("\\|");

            // Trim the song and artist names
            String song = parts[0].trim();
            String[] artists = parts[1].split(",");

            // Remove features in title to optimize search, these happen in brackets ex. (feat. 21)
            // These mess up our Genius API search, use regex to remove them.
            Pattern pattern = Pattern.compile("\\([^)]+\\)");
            Matcher matcher = pattern.matcher(song);

            // Replace content inside brackets with an empty string
            song= matcher.replaceAll("");

            System.out.println(song);
            for (int i = 0; i < artists.length; i++) {
                artists[i] = artists[i].trim();
                System.out.println(artists[i]);
            }

            String tokenUrl = "https://api.genius.com/search?q=";
            String access_token = GeniusAuth.getAccessToken();

            String encodedSearchTerm = URLEncoder.encode(song, "UTF-8");
            String searchParam = tokenUrl + encodedSearchTerm;

            HttpURLConnection connection = (HttpURLConnection) new URL(searchParam).openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer " + access_token);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject;

                try {
                    String url = "";
                    jsonObject = (JSONObject) jsonParser.parse(
                            new InputStreamReader(inputStream, "UTF-8"));
                    //System.out.println(jsonObject.toJSONString());
                    JSONArray hits = (JSONArray) ((JSONObject) jsonObject.get("response")).get("hits");
                    boolean stoploop = false;
                    for (Object item : hits) {
                        JSONObject track = ((JSONObject) item);
                        JSONObject result = (JSONObject) track.get("result");

                        String itemArtist = (String) result.get("artist_names");
                        url = (String) result.get("url");

                        System.out.println(track.toJSONString());
                        System.out.println(itemArtist);
                        System.out.println(url);

                        for (String artist : artists) {
                            if (itemArtist.contains(artist)) {
                                System.out.println("MATCH:");
                                System.out.println(artist);
                                stoploop = true;
                            }
                        }
                        if (stoploop) {
                            break;
                        }
                    }
                    // If we did not find a match for our song with the artists, just grab lyrics for
                    // first song that shows up
                    if (!stoploop) {
                        System.out.println("couldnt find song");
                        return "Could not find lyrics";
                    }

                    String lyrics = request(url);
                    return lyrics;



                } catch (ParseException e) {
                    System.out.println("InputStream could not be parsed into JSON object");
                    return null;
                }

            } else {
                System.out.println("Token request failed: " + responseCode);
                return null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return "Could not find lyrics";
        }
    }

    private String request(String url){
        try {
            Connection connection = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0")
                    .referrer("http://www.google.com")
                    .followRedirects(true);
            Document doc = connection.get();

            if (connection.response().statusCode() == 200) {
                System.out.println("Link: " + url);
                Elements lyricsDiv = doc.select("div.Lyrics__Container-sc-1ynbvzw-1.kUgSbL");
                String lyrics = lyricsDiv.text();
                System.out.println("test:");
                System.out.println(lyrics);

                return lyrics;

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
