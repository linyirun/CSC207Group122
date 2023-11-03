package use_case.loginOAuth;

public class LoginOAuthInputData {
    String code = "";
    public LoginOAuthInputData(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
