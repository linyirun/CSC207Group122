package use_case.split_playlists;
import entity.Song;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import use_case.split_playlist.*;
import interface_adapter.split_playlist.*;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SplitInteractorTest {
    private SplitInteractorTest.MockSplitDAO splitDAO;
    private SplitInteractorTest.MockSplitPresenter mockSplitPresenter;
    private SplitInteractor splitInteractor;

    @BeforeEach
    void init() {
        splitDAO = new SplitInteractorTest.MockSplitDAO();
        mockSplitPresenter = new SplitInteractorTest.MockSplitPresenter();
        splitInteractor = new SplitInteractor(splitDAO, mockSplitPresenter);
    }

    @Test
    void excute(){
        SplitInputData input = new SplitInputData("playlist1");
        splitInteractor.execute(input);
        assertEquals( "Raymond from playlist1\n" +
                "Bill from playlist1\n",mockSplitPresenter.outputData.getCreatedPlaylistNames());
    }

    @Test
    void getUserId(){
        assertEquals("user1", splitInteractor.getUserid());
    }

    @Test
    void createPlaylistWithSongs(){
        List<String> songs = new ArrayList<>();
        songs.add("song3id");
        splitInteractor.createPlaylistWithSongs("song3", songs, "user1");
        assertEquals(songs, splitDAO.Playlists.get("song3Id"));
    }

    @Test
    void splitBylength(){
        splitInteractor.splitByLength("playlist3", 222, 234);
        assertEquals("Songs between 222 seconds to 234 seconds from playlist3",
                mockSplitPresenter.outputData.getCreatedPlaylistNames());
    }

    private class MockSplitPresenter implements SplitOutputBoundary {

        public SplitOutputData outputData = null;
        @Override
        public void prepareSuccessView(SplitOutputData response) {
            outputData = response;
            System.out.println(outputData.getCreatedPlaylistNames());
        }

        @Override
        public void prepareFailView(String error) {

        }
    }

    private class MockSplitDAO implements SplitUserDataAccessInterface{
        public Map<String, String> PlaylistMap;
        public Map<String, List<String>> Playlists;
        public MockSplitDAO(){
            PlaylistMap = new HashMap<>();
            PlaylistMap.put("playlist1", "idOfPlaylist1");
            //PlaylistMap.put("playlist2", "idOfPlaylist2");
            //PlaylistMap.put("playlist3", "idOfPlaylist3");
            List<String> songIds = new ArrayList<>();
            songIds.add("song1Id");
            songIds.add("song2Id");
            Playlists = new HashMap<>();
            Playlists.put("idOfPlaylist1", songIds);
        }
        public Map<String, String> getPlaylistMap(){
            return PlaylistMap;
        };

        public List<Song> getSongs(String playlistID){
            HashMap<String, Long> artistsSong1 = new HashMap<>();
            artistsSong1.put("Raymond", (long) 90);
            artistsSong1.put("Richard", (long) 20);
            Song song1 = new Song("song1Id", "song1",artistsSong1);

            HashMap<String, Long> artistsSong2 = new HashMap<>();
            artistsSong2.put("Bill", (long) 90);
            Song song2 = new Song("song2Id", "song2",artistsSong2);

            List<Song> retured = new ArrayList<>();
            retured.add(song1);
            retured.add(song2);
            return retured;
        };

        public String getUserId() throws IOException, InterruptedException, ParseException{
            return "user1";
        };

        public String createPlaylist(String playlistName, String userId) throws IOException, InterruptedException, ParseException{
            PlaylistMap.put(playlistName, playlistName + "Id");
            Playlists.put(playlistName + "Id", new ArrayList<>());
            return playlistName + "Id";
        };

        public void addSongsToPlaylist(String playlistId, List<String> songIds) throws IOException, InterruptedException{
            Playlists.get(playlistId).addAll(songIds);
        };

        public List<String> getSongInterval(String playlistId, int startTime, int endTime){
            List<String> songs =  new ArrayList<>();
            songs.add("song1Id");
            return songs;
        };
    }
}
