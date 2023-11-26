package entity;

public final class YoutubeAuth {

    private static String accessToken = "";
    private static String refreshToken = "";
    private static final String scope = "https://www.googleapis.com/auth/youtube.force-ssl";
    private static final String CLIENT_ID = "301610688418-im7k6ap2sjnffbr54neqhj6n8thi8jvp.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "";
    private static final String redirectURI = "http://localhost:8000/callback";
    private static final String API_KEY = "";

    private YoutubeAuth() {}

    public static String getAccessToken() {
        return accessToken;
    }
    public static String getRefreshToken() {
        return refreshToken;
    }
    public static String getScope() {
        return scope;
    }

    public static String getRedirectURI() {
        return redirectURI;
    }

    public static String getClientId() {
        return CLIENT_ID;
    }
    public static String getClientSecret() {
        return CLIENT_SECRET;
    }
    public static void setAccessToken(String newAccessToken) {
        accessToken = newAccessToken;
    }
    public static void setRefreshToken(String newRefreshToken) {
        refreshToken = newRefreshToken;
    }

    public static String getApiKey() {
        return API_KEY;
    }
}
