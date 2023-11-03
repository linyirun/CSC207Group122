package use_case.loginOAuth;

import java.io.IOException;

public interface LoginOAuthInputBoundary {
    void execute(LoginOAuthInputData data) throws IOException;
}
