package use_case.merge_playlists;

import entity.Song;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MergeInteractor implements MergeInputBoundary {
    private MergeDataAccessInterface mergeDataAccessObject;
    private MergeOutputBoundary mergePresenter;

    public MergeInteractor(MergeDataAccessInterface mergeDataAccessObject, MergeOutputBoundary mergePresenter) {
        this.mergeDataAccessObject = mergeDataAccessObject;
        this.mergePresenter = mergePresenter;
    }
    @Override
    public void mergePlaylists(MergeInputData mergeInputData) {
        List<String> playlistIds = mergeInputData.getSelectedPlaylistIds();

        ArrayList<String> newSongIDList = new ArrayList<>(); // this stores the merged songs ids
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
            String newPlaylistID = mergeDataAccessObject.createPlaylist("Merged Playlist", userId);
            mergeDataAccessObject.addSongsToPlaylist(newPlaylistID, newSongIDList);

            MergeOutputData mergeOutputData = new MergeOutputData(mergeInputData.getPlaylistName(), mergeInputData.isReturnHome());
            mergePresenter.prepareSuccessView(mergeOutputData);
        } catch (IOException | ParseException | InterruptedException e) {
            System.out.println("Could not read the userID from the data access object in mergeInteractor");
        }
    }

    // User wants to return to home view
    @Override
    public void returnHome() {
        MergeOutputData mergeOutputData = new MergeOutputData(null, true);
        mergePresenter.prepareSuccessView(mergeOutputData);
    }
}
