package use_case.playlists;

import java.util.Map;

public class PlaylistsInteractor implements PlaylistsInputBoundary{
    final PlaylistsUserDataAccessInterface userDataAccessObject;
    final PlaylistsOutputBoundary playlistsPresenter;

    public PlaylistsInteractor(PlaylistsUserDataAccessInterface userDataAccessInterface,
                           PlaylistsOutputBoundary playlistsPresenter) {
        this.userDataAccessObject = userDataAccessInterface;
        this.playlistsPresenter = playlistsPresenter;
    }

    @Override
    public void execute() {
        Map<String,String> playlistMap;
        playlistMap = userDataAccessObject.getPlaylistMap();
        System.out.println("interactor DAO" + playlistMap);
        PlaylistsOutputData outputData = new PlaylistsOutputData(playlistMap);
        System.out.println("interactor out put data" + outputData.getPlaylistMap());
        playlistsPresenter.prepareSuccessView(outputData);
    }
}
