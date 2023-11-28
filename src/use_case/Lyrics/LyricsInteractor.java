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

public class LyricsInteractor implements LyricsInputBoundary{
    LyricsDataAccessInterface dao;
    LyricsOutputBoundary presenter;

    public LyricsInteractor (LyricsDataAccessInterface dao, LyricsOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }
    public String execute(LyricsInputData inputData) {
        try {
            String songName = inputData.getSongName();
            String lyrics = dao.getLyrics(songName);
            return lyrics;

        }
        catch (Exception e) {
            System.out.println(e);
            return "Could not get song";

        }

    }
}
