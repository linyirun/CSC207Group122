package data_access;

import data_access.LyricsDataAccessObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LyricsDataAccessObjectTest {

    // Replace this with your actual Genius API access token

    @Test
    void getLyrics_shouldNotReturnLyrics() {
        LyricsDataAccessObject lyricsDAO = new LyricsDataAccessObject();


        // Replace with your actual song name (in the format "Song Name | Artist 1, Artist 2")
        String songName = "YOUR_SONG_NAME | Artist 1, Artist 2";


            String lyrics = lyricsDAO.getLyrics(songName);
            assertNull(lyrics);

    }

    // Add more tests as needed based on different scenarios (e.g., no lyrics found, invalid song name, etc.)

}
