package interface_adapter.loginOAuth;
import java.net.URL;
import java.util.ArrayList;

public class LoginOAuthState {
    private String code = "";
    private URL url;
    private String OAuthError = "";

    public LoginOAuthState(interface_adapter.loginOAuth.LoginOAuthState copy) {
        code = copy.code;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public LoginOAuthState() {}

    public void setCode(String code) {
        this.code = code;
    }
    public void setURl(URL url) {this.url = url;}
    public void setOAuthError(String error) {
        this.OAuthError = error;
    }

    public String getCode() {
        return this.code;
    }
    public URL getURL() {return this.url;}
    public String getOAuthError() {return this.OAuthError;}

}

