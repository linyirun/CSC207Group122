package use_case.Lyrics;

public class LyricsInteractor implements LyricsInputBoundary {
    LyricsDataAccessInterface dao;
    LyricsOutputBoundary presenter;

    public LyricsInteractor(LyricsDataAccessInterface dao, LyricsOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    public String execute(LyricsInputData inputData) {
        try {
            String songName = inputData.getSongName();
            String lyrics = dao.getLyrics(songName);
            return lyrics;

        } catch (Exception e) {
            System.out.println(e);
            presenter.prepareFailView("Could not get lyrics");
            return null;

        }

    }
}
