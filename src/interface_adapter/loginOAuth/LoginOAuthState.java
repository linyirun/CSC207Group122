package interface_adapter.loginOAuth;

import java.net.URL;

public class LoginOAuthState {
    private final String code = "";
    private URL url;
    private String OAuthError = "";

    public LoginOAuthState() {
    }

    public String getOAuthError() {
        return this.OAuthError;
    }

    public void setOAuthError(String error) {
        this.OAuthError = error;
    }

}

