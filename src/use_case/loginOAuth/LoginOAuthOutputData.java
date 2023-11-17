package use_case.loginOAuth;

public class LoginOAuthOutputData {
    String display_name = "";
    public LoginOAuthOutputData(String display_name) {
        this.display_name = display_name;
    }
    public String getDisplayName() {
        return this.display_name;
    }
}
