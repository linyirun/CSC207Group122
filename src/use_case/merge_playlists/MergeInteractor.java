package use_case.merge_playlists;

import entity.Song;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MergeInteractor implements MergeInputBoundary {
    private MergeDataAccessInterface mergeDataAccessObject;
    private MergeOutputBoundary mergePresenter;

    public MergeInteractor(MergeDataAccessInterface mergeDataAccessObject, MergeOutputBoundary mergePresenter) {
        this.mergeDataAccessObject = mergeDataAccessObject;
        this.mergePresenter = mergePresenter;
    }
    @Override
    public void mergePlaylists(MergeInputData mergeInputData) {
        List<String> playlistNames = mergeInputData.getSelectedPlaylistNames();

        Map<String, String> playlistNameToID = mergeDataAccessObject.getPlaylistMap();

        List<String> playlistIds = new ArrayList<String>();
        // Conver the playlist names to playlist IDs
        for (String playlistName : playlistNames) {
            playlistIds.add(playlistNameToID.get(playlistName));
        }

        List<String> newSongIDList = new ArrayList<>(); // this stores the merged songs ids
        for (String playlistId : playlistIds) {
            // For every playlist that's selected, get all the songs in this playlist and add it
            List<Song> songs = mergeDataAccessObject.getSongs(playlistId);
            for (Song song : songs) {
                newSongIDList.add(song.getId());  // add this song to the merged playlist
            }
        }

        try {
            String userId = mergeDataAccessObject.getUserId();

            // Create a new playlist
            // TODO: allow the user to choose the name of this new playlist
            String newPlaylistID = mergeDataAccessObject.createPlaylist(mergeInputData.getPlaylistName(), userId);
            mergeDataAccessObject.addSongsToPlaylist(newPlaylistID, newSongIDList);

            MergeOutputData mergeOutputData = new MergeOutputData(mergeInputData.getPlaylistName(), mergeInputData.isReturnHome());
            mergePresenter.prepareSuccessView(mergeOutputData);
        } catch (IOException | ParseException | InterruptedException e) {
            System.out.println("Could not read the userID from the data access object in mergeInteractor");
        }
    }

    /**
     *
     * @return a list of the names of the user's playlists
     */
    public List<String> getPlaylists() {
        Set<String> playlistNames = mergeDataAccessObject.getPlaylistMap().keySet();
        // Convert this to a list
        List<String> listNames = new ArrayList<>();
        listNames.addAll(playlistNames);
        return listNames;
    }

    // User wants to return to home view
    @Override
    public void returnHome() {
        MergeOutputData mergeOutputData = new MergeOutputData(null, true);
        mergePresenter.prepareSuccessView(mergeOutputData);
    }
}
