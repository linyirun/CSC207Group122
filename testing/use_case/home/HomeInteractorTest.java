package use_case.home;

import entity.Song;
import interface_adapter.home.HomeViewModel;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import use_case.loginOAuth.LoginOAuthInteractor;
import use_case.loginOAuth.LoginOAuthInteractorTest;
import view.SpotifyToYoutubeView;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HomeInteractorTest {
    HomeOutputBoundary mockPresenter;
    HomeUserDataAccessInterface mockDao;
    HomeInteractor homeInteractor;
    @BeforeEach
    void setUp() {
        mockPresenter = new MockHomePresenter();

        mockDao = new MockHomeDao();

        homeInteractor = new HomeInteractor(mockPresenter, mockDao);


    }

    @Test
    void testExecuteProfileTopArtistsAndTracks() {
        homeInteractor.execute(new HomeInputData("profile"));
        Map<String, List<String>> testMap = ((MockHomeDao)mockDao).mockTopTracksAndArtists();
        Map<String, List<String>> presenterMap = ((MockHomePresenter) mockPresenter).data.profileObjects;
        for (String key: testMap.keySet()) {
            assertEquals(new HashSet(testMap.get(key)), new HashSet(presenterMap.get(key)));
        }
    }

    @Test
    void testExecuteSpotToYTPlaylistNames() {
        homeInteractor.execute(new HomeInputData(HomeViewModel.SPOTIFY_TO_YT_NAME));
        Map<String, String> testMap = mockDao.getPlaylistMap();
        List<String> presenterMap = ((MockHomePresenter) mockPresenter).data.playlistNames;
        assertEquals(new HashSet(testMap.keySet()), new HashSet(presenterMap));
    }

    @Test
    void testGetPlaylistsMap() {
        assertTrue(mockDao.getPlaylistMap().equals(homeInteractor.getPlaylistsMap()));
    }
    @Test
    void testGetSongs() {
       List<Song> returnedSongs = homeInteractor.getSongs("12345");
       List<Song> expectedSongs = mockDao.getSongs("12345");
       List<String> returnedSongNames = new ArrayList<>();
       for (int i = 0; i < returnedSongs.size(); i++) {
           returnedSongNames.add(returnedSongs.get(i).getName());
       }
       List<String> expectedSongNames = new ArrayList<>();
       for (int i = 0; i < expectedSongs.size(); i++) {
           expectedSongNames.add(expectedSongs.get(i).getName());
       }
       assertEquals(new HashSet(expectedSongNames), new HashSet(returnedSongNames));
    }

    @Test
    void textExecuteProfileViewName() {
        homeInteractor.execute(new HomeInputData("profile"));
        assertEquals("Profile", ((MockHomePresenter) mockPresenter).data.getViewName());
    }
    @Test
    void textExecuteSplitViewName() {
        homeInteractor.execute(new HomeInputData(HomeViewModel.SPLIT_PLAYLIST_NAME));
        assertEquals("Split Playlist", ((MockHomePresenter) mockPresenter).data.getViewName());
    }
    @Test
    void textExecuteMergeViewName() {
        homeInteractor.execute(new HomeInputData(HomeViewModel.MERGE_PLAYLIST_NAME));
        assertEquals("Merge View", ((MockHomePresenter) mockPresenter).data.getViewName());
    }
    @Test
    void textExecuteArtistsViewName() {
        homeInteractor.execute(new HomeInputData(HomeViewModel.ARTISTS_PLAYLIST_MAKER_NAME));
        assertEquals("Artists Playlist Maker", ((MockHomePresenter) mockPresenter).data.getViewName());
    }
    @Test
    void textExecuteSpotToYTViewName() {
        homeInteractor.execute(new HomeInputData(HomeViewModel.SPOTIFY_TO_YT_NAME));
        assertEquals("Spot To YT", ((MockHomePresenter) mockPresenter).data.getViewName());
    }
    @Test
    void textExecuteFail() {
        homeInteractor.execute(new HomeInputData("LOLLIPOP"));
        assertEquals("Event source not defined", ((MockHomePresenter) mockPresenter).error);
    }

    public class MockHomePresenter implements HomeOutputBoundary{
        HomeOutputData data;
        String error;

        @Override
        public void prepareSuccessView(HomeOutputData data) {
            this.data = data;
        }

        @Override
        public void prepareFailView(String error) {
            this.error = error;
        }
    }
    public class MockHomeDao implements HomeUserDataAccessInterface {

        @Override
        public Map<String, List<String>> getUserTopTracksAndArtists(){
            return mockTopTracksAndArtists();
        }

        @Override
        public Map<String, String> getPlaylistMap() {
            Map<String, String> mockMap = new HashMap<>();
            mockMap.put("Playlist1", "id1");
            mockMap.put("Playlist2", "id2");
            return mockMap;
        }

        @Override
        public List<Song> getSongs(String playlistID) {
            List<Song> songs = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Map<String, Long> artists = new HashMap<>();
                artists.put("Artist" + i, (long)i);
                songs.add(new Song(Integer.toString(i), "Song" + i,artists));
            }
            return songs;
        }
        public Map<String, List<String>> mockTopTracksAndArtists() {
            Map mockMap = new HashMap<>();
            List<String> topTracks = new ArrayList<>();
            List<String> topArtists = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                topTracks.add(Integer.toString(i));
            }
            for (int i = 10; i < 20; i++) {
                topArtists.add(Integer.toString(i));
            }
            mockMap.put("tracks", topTracks);
            mockMap.put("artists", topArtists);
            return mockMap;
        }
    }
}