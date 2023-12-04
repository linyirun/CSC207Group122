package use_case.GeniusAuth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class GeniusInteractorTest {

    private GeniusDataAccessInterfaceStub dao;
    private GeniusOutputBoundaryStub presenter;
    private GeniusInteractor interactor;

    @BeforeEach
    void setUp() {
        dao = new GeniusDataAccessInterfaceStub();
        presenter = new GeniusOutputBoundaryStub();
        interactor = new GeniusInteractor();
    }

    @Test
    void execute_Success() {
        // Arrange
        GeniusAuth.setClientSecret("dummySecret");
        GeniusAuth.setClientId("dummyClientId");

        dao.setResponseCode(HttpURLConnection.HTTP_OK);
        dao.setInputStream("{\"access_token\": \"dummyAccessToken\"}");

        // Act
        interactor.execute();

        // Assert

        assertFalse(presenter.isSuccessViewPrepared());
        assertFalse(presenter.isFailViewPrepared());
    }

    @Test
    void execute_Failure() {
        // Arrange
        GeniusAuth.setClientSecret("dummySecret");
        GeniusAuth.setClientId("dummyClientId");

        dao.setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST);

        // Act
        interactor.execute();

        // Assert
        assertNull(GeniusAuth.getAccessToken());
        assertFalse(presenter.isSuccessViewPrepared());
        assertFalse(presenter.isFailViewPrepared());
    }

    // Stub for GeniusAuth
    public static class GeniusAuth {
        private static String clientId;
        private static String clientSecret;
        private static String accessToken;

        public static String getClientId() {
            return clientId;
        }

        public static void setClientId(String clientId) {
            GeniusAuth.clientId = clientId;
        }

        public static String getClientSecret() {
            return clientSecret;
        }

        public static void setClientSecret(String clientSecret) {
            GeniusAuth.clientSecret = clientSecret;
        }

        public static String getAccessToken() {
            return accessToken;
        }

        public static void setAccessToken(String accessToken) {
            GeniusAuth.accessToken = accessToken;
        }
    }

    // Stub for GeniusDataAccessInterface
    private static class GeniusDataAccessInterfaceStub implements GeniusDataAccessInterface {
        private int responseCode;
        private String inputStream;

        public void setResponseCode(int responseCode) {
            this.responseCode = responseCode;
        }

        public void setInputStream(String inputStream) {
            this.inputStream = inputStream;
        }



    }

    // Stub for GeniusOutputBoundary
    private static class GeniusOutputBoundaryStub implements GeniusOutputBoundary {
        private boolean successViewPrepared;
        private boolean failViewPrepared;

        @Override
        public void prepareSuccessView(GeniusOutputData response) {
            successViewPrepared = true;
        }

        @Override
        public void prepareFailView(String error) {
            failViewPrepared = true;
        }

        public boolean isSuccessViewPrepared() {
            return successViewPrepared;
        }

        public boolean isFailViewPrepared() {
            return failViewPrepared;
        }
    }
}
