package interface_adapter.loginOAuth;
import java.net.URL;

public class LoginOAuthState {
    private String code = "";
    private URL url;


    public LoginOAuthState(interface_adapter.loginOAuth.LoginOAuthState copy) {
        code = copy.code;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public LoginOAuthState() {}

    public void setCode(String code) {
        this.code = code;
    }
    public void setURl(URL url) {this.url = url;}

    public String getCode() {
        return this.code;
    }
    public URL getURL() {return this.url;}

}

