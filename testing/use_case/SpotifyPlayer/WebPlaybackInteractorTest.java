package use_case.SpotifyPlayer;

import com.sun.net.httpserver.*;
import entity.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WebPlaybackInteractorTest {
    private WebPlaybackInteractor webPlaybackInteractor;
    private MockWebPlaybackDAO mockDao;

    @BeforeEach
    void setUp() {
        mockDao = new MockWebPlaybackDAO();
        webPlaybackInteractor = new WebPlaybackInteractor(mockDao);
    }

    @Test
    void testStartClientServerSuccess() {
        // Set up mock data
        WebPlaybackInputData inputData = new WebPlaybackInputData("song1 | Richard", "");

        // Test the startClientServer method
        assertDoesNotThrow(() -> {
            webPlaybackInteractor.startClientServer(inputData);
            // Sleep for 3 seconds to simulate the process and server startup
            Thread.sleep(3000);
        });
        // Additional assertions based on the expected behavior of your method
    }

    @Test
    void testStartClientServerNoMatchingSong() {
        // Set up mock data
        WebPlaybackInputData inputData = new WebPlaybackInputData("NonMatchingSong | PlaylistId", "");

        // Test the startClientServer method
        assertThrows(NoSuchElementException.class, () -> {
            webPlaybackInteractor.startClientServer(inputData);
            // Sleep for 3 seconds to simulate the process and server startup
            Thread.sleep(3000);
        });
        // Additional assertions based on the expected behavior of your method
    }
//
    @Test
    void testStartServerSuccess() {

        // Test the startClientServer method
        assertDoesNotThrow(() -> {
            webPlaybackInteractor.StartServer();
            // Sleep for 3 seconds to simulate the process and server startup
            Thread.sleep(3000);
        });
        // Additional assertions based on the expected behavior of your method
    }
    @Test
    void testHandleSuccess() throws URISyntaxException {
        // Create a MockHttpExchange with appropriate values
        webPlaybackInteractor.StartServer();
        URI requestURI = new URI("http:localhost:8000/getAccessToken");
        String requestMethod = "GET";
        Map<String, List<String>> requestHeaders = new HashMap<>();
        InputStream requestBody = null; // You can provide a request body if needed

        MockHttpExchange mockHttpExchange = new MockHttpExchange(requestURI, requestMethod, requestHeaders, requestBody);
        assertDoesNotThrow(() -> {
            webPlaybackInteractor.new TokenHandler().handle(mockHttpExchange);
        });
    }
    @Test
    void testHandlePreFlight() throws URISyntaxException {
        // Create a MockHttpExchange with appropriate values
        webPlaybackInteractor.StartServer();
        URI requestURI = new URI("http:localhost:8000/getAccessToken");
        String requestMethod = "OPTIONS";
        Map<String, List<String>> requestHeaders = new HashMap<>();
        InputStream requestBody = null; // You can provide a request body if needed

        MockHttpExchange mockHttpExchange = new MockHttpExchange(requestURI, requestMethod, requestHeaders, requestBody);
        assertDoesNotThrow(() -> {
            webPlaybackInteractor.new TokenHandler().handle(mockHttpExchange);
        });
    }



//
//    @Test
//    void testStartServerSuccess() {
//        // Test the StartServer method
//        assertDoesNotThrow(() -> webPlaybackInteractor.StartServer());
//        // Additional assertions based on the expected behavior of your method
//}

    private class MockWebPlaybackDAO implements WebPlaybackDataAccessInterface {
        public Map<String, String> PlaylistMap;
        public Map<String, List<String>> Playlists;

        private boolean throwIOException;

        public MockWebPlaybackDAO() {
            PlaylistMap = new HashMap<>();
            PlaylistMap.put("playlist1", "idOfPlaylist1");
            //PlaylistMap.put("playlist2", "idOfPlaylist2");
            //PlaylistMap.put("playlist3", "idOfPlaylist3");
            List<String> songIds = new ArrayList<>();
            songIds.add("song1Id");
            songIds.add("song2Id");
            Playlists = new HashMap<>();
            Playlists.put("idOfPlaylist1", songIds);
        }

        public List<Song> getSongs(String playlistID) throws IOException{
            if (this.throwIOException) {
                throw new IOException();
            }
            HashMap<String, Long> artistsSong1 = new HashMap<>();
            artistsSong1.put("Raymond", (long) 90);
            artistsSong1.put("Richard", (long) 20);
            Song song1 = new Song("song1Id", "song1", artistsSong1);

            HashMap<String, Long> artistsSong2 = new HashMap<>();
            artistsSong2.put("Bill", (long) 90);
            Song song2 = new Song("song2Id", "song2", artistsSong2);

            List<Song> retured = new ArrayList<>();
            retured.add(song1);
            retured.add(song2);
            return retured;
        }

        public void setThrowIOException(boolean throwIOException) {
            this.throwIOException = throwIOException;
        };
    };
    public class MockHttpExchange extends HttpExchange {
        private URI requestURI;
        private String requestMethod;
        private Map<String, List<String>> requestHeaders;
        private InputStream requestBody;
        private Headers responseHeaders;
        private OutputStream responseBody;

        public MockHttpExchange(URI requestURI, String requestMethod, Map<String, List<String>> requestHeaders, InputStream requestBody) {
            this.requestURI = requestURI;
            this.requestMethod = requestMethod;
            this.requestHeaders = requestHeaders;
            this.requestBody = requestBody;
            this.responseHeaders = new Headers();
            this.responseBody = new ByteArrayOutputStream();
        }

        @Override
        public Headers getRequestHeaders() {
            Headers headers = new Headers();
            headers.putAll(requestHeaders);
            return headers;
        }

        @Override
        public URI getRequestURI() {
            return requestURI;
        }

        @Override
        public String getRequestMethod() {
            return requestMethod;
        }

        @Override
        public HttpContext getHttpContext() {
            return null;
        }

        @Override
        public void close() {

        }

        @Override
        public InputStream getRequestBody() {
            return requestBody;
        }

        @Override
        public Headers getResponseHeaders() {
            return responseHeaders;
        }

        @Override
        public OutputStream getResponseBody() {
            return responseBody;
        }

        @Override
        public void sendResponseHeaders(int rCode, long responseLength) throws IOException {

        }

        @Override
        public InetSocketAddress getRemoteAddress() {
            return null;
        }

        @Override
        public int getResponseCode() {
            return 0;
        }

        @Override
        public InetSocketAddress getLocalAddress() {
            return null;
        }

        @Override
        public String getProtocol() {
            return null;
        }

        @Override
        public Object getAttribute(String name) {
            return null;
        }

        @Override
        public void setAttribute(String name, Object value) {

        }

        @Override
        public void setStreams(InputStream i, OutputStream o) {

        }

        @Override
        public HttpPrincipal getPrincipal() {
            return null;
        }

        // Other methods from HttpExchange interface can be added as needed
    }
}




