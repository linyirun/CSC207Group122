package use_case.loginOAuth;

import entity.SpotifyAuth;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class LoginOAuthInteractorTest {
    LoginOAuthOutputBoundary mockPresenter;
    LoginOAuthUserDataAccessInterface mockDao;
    LoginOAuthInputBoundary loginOAuthInteractor;
    @BeforeEach
    void setUp() {
       mockPresenter = new MockLoginOAuthPresenter();

       mockDao = new MockLoginOAuthDao();

       loginOAuthInteractor = new LoginOAuthInteractor(mockDao, mockPresenter);

    }

    @Test
    void testExecuteSuccessView() {
        // test that the correct display name is returned
        try {
            loginOAuthInteractor.execute();
            assertEquals("Richard Chen", ((MockLoginOAuthPresenter) mockPresenter).data.getDisplayName());
        }
        catch(InterruptedException | IOException e) {
            fail("Not supposed to fail here, Spotify client secret possibly invalid");
        }
    }

    @Test
    void testExecuteInvalidClientSecretFailView() {
        // test that invalid client secret returns http error 400
        String clientSecret = SpotifyAuth.getClientSecret();
        SpotifyAuth.setClientSecret("LOL");
        try {
            loginOAuthInteractor.execute();
            assertEquals("400", ((MockLoginOAuthPresenter) mockPresenter).httpErrorCode);
            assertEquals("Bad Request", ((MockLoginOAuthPresenter) mockPresenter).httpErrorMessage);
        }
        catch(InterruptedException | IOException e) {
            fail("Not supposed to fail here");
        }
        finally {
            SpotifyAuth.setClientSecret(clientSecret);
        }

    }

    public class MockLoginOAuthPresenter implements LoginOAuthOutputBoundary{
        public LoginOAuthOutputData data;
        public String httpErrorCode;
        public String httpErrorMessage;
        public String desktopLinkNotSupported;
        @Override
        public void prepareSuccessView(LoginOAuthOutputData data) {
            this.data = data;
        }

        @Override
        public void prepareFailViewHTTP(String httpErrorCode, String httpErrorMessage) {
            this.httpErrorCode = httpErrorCode;
            this.httpErrorMessage = httpErrorMessage;
        }

        @Override
        public void prepareFailViewDesktop(String error) {
            this.desktopLinkNotSupported = error;
        }
    }

    public class MockLoginOAuthDao implements LoginOAuthUserDataAccessInterface {
        @Override
        public String getAccountName() {
            return "Richard Chen";
        }
    }
}
