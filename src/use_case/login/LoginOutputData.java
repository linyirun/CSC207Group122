package use_case.login;

import java.net.URL;
public class LoginOutputData {

    private final URL url;
    private boolean useCaseFailed;

    public LoginOutputData(URL url, boolean useCaseFailed) {
        this.url = url;
        this.useCaseFailed = useCaseFailed;
    }

    public URL getURL(){
        return this.url;
    }
    public boolean getUseCaseFailed() {
        return this.useCaseFailed;
    }

}
