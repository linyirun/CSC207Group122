package data_access;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import use_case.Lyrics.LyricsDataAccessInterface;

import java.io.IOException;

public class LyricsDataAccessObject implements LyricsDataAccessInterface {

    @Override
    public String getLyrics() {
        return null;
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
