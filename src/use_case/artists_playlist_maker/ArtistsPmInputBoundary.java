package use_case.artists_playlist_maker;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface ArtistsPmInputBoundary {

    void execute() throws IOException, ParseException, InterruptedException;

}
