package use_case.artists_playlist_maker;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public interface ArtistsPmInputBoundary {

    List<String> showTopArtists(ArtistsPmInputData inputData) throws IOException, ParseException, InterruptedException;

    void createPlaylist(ArtistsPmInputData inputData);
}
