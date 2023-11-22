package use_case.merge_playlists;

import entity.Song;

import java.util.ArrayList;
import java.util.List;

public class MergeInteractor implements MergeInputBoundary {

    private MergeDataAccessInterface mergeDataAccessObject;
    private MergeOutputBoundary mergeOutputBoundary;

    public MergeInteractor(MergeDataAccessInterface mergeDataAccessObject, MergeOutputBoundary mergeOutputBoundary) {
        this.mergeDataAccessObject = mergeDataAccessObject;
        this.mergeOutputBoundary = mergeOutputBoundary;
    }
    @Override
    public void execute(MergeInputData mergeInputData) {
        List<String> playlistIds = mergeInputData.getPlaylistIds();

        ArrayList<String> newSongList = new ArrayList<>(); // this stores the merged songs
        for (String playlistId : playlistIds) {
            // For every playlist that's selected, get all the songs in this playlist and add it
            List<Song> songs = mergeDataAccessObject.getSongs(playlistId);
            for (Song song : songs) {
                newSongList.add(song.getId());  // add this song to the merged playlist
            }
        }

        MergeOutputData mergeOutputData = new MergeOutputData(mergeInputData.getPlaylistName());

    }
}
