package interface_adapter.login;

public class LoginState {
    private String token = "";

    public LoginState(LoginState copy) {
        token = copy.token;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public LoginState() {}

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

}
