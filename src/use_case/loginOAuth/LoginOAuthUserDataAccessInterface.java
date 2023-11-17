package use_case.loginOAuth;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface LoginOAuthUserDataAccessInterface {
    String getAccountName() throws IOException, InterruptedException, ParseException;;
}
