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
        // Convert the playlist names to playlist IDs
        for (String playlistName : playlistNames) {
            playlistIds.add(playlistNameToID.get(playlistName));
        }

        List<String> newSongIDList = new ArrayList<>(); // this stores the merged songs ids
        for (String playlistId : playlistIds) {
            // For every playlist that's selected, get all the songs in this playlist and add it
            List<Song> songs = mergeDataAccessObject.getSongs(playlistId);

            // Create a list of song IDs so we can get its audio features
            List<String> songIds = new ArrayList<>();
            for (Song song : songs) songIds.add(song.getId());

            List<Map<String, String>> listOfAudioFeaturesMap = mergeDataAccessObject.getSongsAudioFeatures(songIds);


            for (int i = 0; i < songs.size(); i++) {
                Song song = songs.get(i);
                if (checkFilters(listOfAudioFeaturesMap.get(i), mergeInputData)) {
                    newSongIDList.add(song.getId());  // add this song to the merged playlist
                }
            }
        }

        try {
            String userId = mergeDataAccessObject.getUserId();

            // Create a new playlist
            String newPlaylistID = mergeDataAccessObject.createPlaylist(mergeInputData.getPlaylistName(), userId);
            mergeDataAccessObject.addSongsToPlaylist(newPlaylistID, newSongIDList);

            MergeOutputData mergeOutputData = new MergeOutputData(mergeInputData.getPlaylistName(), mergeInputData.isReturnHome());
            mergePresenter.prepareSuccessView(mergeOutputData);
        } catch (IOException | ParseException | InterruptedException e) {
            System.out.println("Could not read the userID from the data access object in mergeInteractor");
        }
    }

    private boolean checkFilters(Map<String, String> audioFeaturesMap, MergeInputData mergeInputData) {

        Double instrumentalness = Double.parseDouble(audioFeaturesMap.get("instrumentalness"));
        Double valence = Double.parseDouble(audioFeaturesMap.get("valence"));
        Double tempo = Double.parseDouble(audioFeaturesMap.get("tempo"));

        // Check if it satisfies the instrumental filter
        if (mergeInputData.getInstrumentalChoice() == MergeInputData.INSTRUMENTAL_CHOICE) {
            if (instrumentalness <= MergeInputData.INSTRUMENTAL_THRESHOLD) return false;
        }
        if (mergeInputData.getInstrumentalChoice() == MergeInputData.VOCAL_CHOICE) {
            if (instrumentalness >= MergeInputData.VOCAL_THRESHOLD) return false;
        }

        // Check if it satisfies the valence filter
        if (mergeInputData.getValenceChoice() == MergeInputData.SAD_CHOICE) {
            if (valence >= MergeInputData.SAD_THRESHOLD) return false;
        }

        if (mergeInputData.getValenceChoice() == MergeInputData.NEUTRAL_CHOICE) {
            if (valence <= MergeInputData.SAD_THRESHOLD || valence >= MergeInputData.HAPPY_THRESHOLD) return false;
        }

        if (mergeInputData.getValenceChoice() == MergeInputData.HAPPY_CHOICE) {
            if (valence <= MergeInputData.HAPPY_THRESHOLD) return false;
        }

        // Check if it satisfies the tempo filter
        if (mergeInputData.getTempoChoice() == MergeInputData.SLOW_CHOICE) {
            if (tempo >= MergeInputData.SLOW_THRESHOLD) return false;
        }

        if (mergeInputData.getTempoChoice() == MergeInputData.NORMAL_CHOICE) {
            if (tempo <= MergeInputData.SLOW_THRESHOLD || tempo >= MergeInputData.FAST_THRESHOLD) return false;
        }

        if (mergeInputData.getTempoChoice() == MergeInputData.FAST_CHOICE) {
            if (tempo <= MergeInputData.FAST_THRESHOLD) return false;
        }

        return true;
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
