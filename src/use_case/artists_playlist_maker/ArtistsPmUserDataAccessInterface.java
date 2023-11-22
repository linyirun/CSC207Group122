package use_case.artists_playlist_maker;

import entity.Song;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ArtistsPmUserDataAccessInterface {

    List<String> getTopArtists(String query, int limit) throws IOException, InterruptedException, ParseException;

    List<String> getArtistsTopTracks(List<String> queries, int limit) throws IOException, InterruptedException, ParseException;

    String getUserId() throws IOException, InterruptedException, ParseException;

String createPlaylist(String playlistName, String userId) throws IOException, InterruptedException, ParseException;

void addSongsToPlaylist(String playlistId, List<String> songIds) throws IOException, InterruptedException;

List<Song> getSongs(String playlistID);

 Map<String, String> get_playlistMap();

    Set<String> get_playlists();










}
