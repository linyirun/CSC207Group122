package use_case.Lyrics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LyricsInteractorTest {

    private LyricsDataAccessInterfaceStub dao;
    private LyricsOutputBoundaryStub presenter;
    private LyricsInteractor interactor;

    @BeforeEach
    void setUp() {
        dao = new LyricsDataAccessInterfaceStub();
        presenter = new LyricsOutputBoundaryStub();
        interactor = new LyricsInteractor(dao, presenter);
    }

    @Test
    void execute_Success() {
        // Arrange
        dao.setLyrics("Dummy lyrics");
        LyricsInputData inputData = new LyricsInputData("SongName");

        // Act
        String result = interactor.execute(inputData);

        // Assert
        assertEquals("Dummy lyrics", result);
        assertFalse(presenter.isFailViewPrepared());
    }

    @Test
    void execute_Failure() {
        // Arrange
        dao.setException(new RuntimeException("Some error"));
        LyricsInputData inputData = new LyricsInputData("SongName");

        // Act
        String result = interactor.execute(inputData);

        // Assert
        assertNull(result);
        assertTrue(presenter.isFailViewPrepared());
    }

    @Test
    void outputData_getLyrics() {
        // Arrange
        String lyrics = "Test lyrics";
        LyricsOutputData outputData = new LyricsOutputData(lyrics);

        // Act
        String result = outputData.getLyrics();

        // Assert
        assertEquals(lyrics, result);
    }

    // Stub for LyricsDataAccessInterface
    private static class LyricsDataAccessInterfaceStub implements LyricsDataAccessInterface {
        private String lyrics;
        private Exception exception;

        public void setLyrics(String lyrics) {
            this.lyrics = lyrics;
        }

        public void setException(Exception exception) {
            this.exception = exception;
        }

        @Override
        public String getLyrics(String name) {
            if (exception != null) {
                throw new RuntimeException(exception);
            }
            return lyrics;
        }
    }

    // Stub for LyricsOutputBoundary
    private static class LyricsOutputBoundaryStub implements LyricsOutputBoundary {
        private boolean failViewPrepared;

        @Override
        public void prepareFailView(String error) {
            failViewPrepared = true;
        }

        public boolean isFailViewPrepared() {
            return failViewPrepared;
        }
    }
}
