package use_case.split_playlist;

import java.awt.event.HierarchyBoundsAdapter;
import java.util.*;

import entity.Song;

public class SplitInteractor implements SplitInputBoundary{
    final SplitUserDataAccessInterface userDataAccessObject;
    final SplitOutputBoundary splitPresenter;

    public SplitInteractor(SplitUserDataAccessInterface userDataAccessInterface,
                           SplitOutputBoundary splitOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.splitPresenter = splitOutputBoundary;
    }

    @Override
    public void execute(SplitInputData splitInputData) {
        String givenPlaylistName = splitInputData.getPlaylistName();
        String givePlaylistID = userDataAccessObject.getPlaylistMap().get(givenPlaylistName);
        List<Song> songs = userDataAccessObject.getSongs(givePlaylistID);
        Map<String, List<String>> artistPlaylists = new HashMap<>();
        for(Song song : songs){
            String mostPopularArtist = null;
            Map<String, Long> artists = song.getArtists();
            for(String artistName : artists.keySet()){
                if(mostPopularArtist == null){
                    mostPopularArtist = artistName;
                }
                if(artists.get(artistName) > artists.get(mostPopularArtist)){
                    mostPopularArtist = artistName;
                }
            }
            if(artistPlaylists.containsKey(mostPopularArtist)){
                artistPlaylists.get(mostPopularArtist).add(song.getId());
            }
            else{
                List<String> songIds = new ArrayList<>();
                songIds.add(song.getId());
                artistPlaylists.put(mostPopularArtist, songIds);
            }
        }

        //create playlists
        //get userid
        String userid = "";
        try{
            userid = userDataAccessObject.getUserId();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("unable to get userid");
        }

        StringBuffer createdPlaylistNames = new StringBuffer();

        for(String artistName : artistPlaylists.keySet()){
            String createdPlaylistName = artistName +  " from " + givenPlaylistName;
            createdPlaylistNames.append(createdPlaylistName + "\n");
            createPlaylistWithSongs(createdPlaylistName, artistPlaylists.get(artistName), userid);
        }

        SplitOutputData outputData = new SplitOutputData(createdPlaylistNames.toString());
        splitPresenter.prepareSuccessView(outputData);
    }

    public void createPlaylistWithSongs(String name, List<String> songIds, String userid){
        try{
            String createdPlaylistID = userDataAccessObject.createPlaylist(name, userid);
            userDataAccessObject.addSongsToPlaylist(createdPlaylistID, songIds);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("unable to create playlist");
        }
    }
}
