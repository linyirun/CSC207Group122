package interface_adapter.playlists;
import use_case.playlists.*;
import use_case.split_playlist.SplitInputData;

public class PlaylistsController implements PlaylistsInputBoundary{
    final PlaylistsInputBoundary playlistsUseCaseInteractor;

    public PlaylistsController(PlaylistsInputBoundary playlistsUseCaseInteractor){
        this.playlistsUseCaseInteractor = playlistsUseCaseInteractor;
    }

    public void execute(){
        playlistsUseCaseInteractor.execute();
    }
}
