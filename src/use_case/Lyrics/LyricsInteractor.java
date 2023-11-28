package use_case.Lyrics;

import data_access.LyricsDataAccessObject;
import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import entity.GeniusAuth;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.InputStreamReader;
import java.util.Base64;

public class LyricsInteractor {

    private LyricsDataAccessInterface mergeDataAccessObject;

    public static void execute(LyricsInputData inputData) {
        String songName = inputData.getSongName();
        String lyrics = LyricsDataAccessObject.getUrl(songName);


    }

}
