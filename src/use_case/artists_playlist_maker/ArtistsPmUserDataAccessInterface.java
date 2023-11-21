package use_case.artists_playlist_maker;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public interface ArtistsPmUserDataAccessInterface {

    List<String> getTopArtists(String query, int limit) throws IOException, InterruptedException, ParseException;

    List<String> getArtistsTopTracks(List<String> queries, int limit) throws IOException, InterruptedException, ParseException;

    List<String> chooseArtistsAndCreatePlaylist() throws IOException, InterruptedException, ParseException;


}
