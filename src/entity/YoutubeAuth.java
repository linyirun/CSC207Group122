package entity;

public final class YoutubeAuth {

    private static String accessToken = "";
    private static String refreshToken = "";
    private final static String scope = "https://www.googleapis.com/auth/youtube.force-ssl";
    private final static String CLIENT_ID = "1016234743626-p2864temak6gu1l6auk5h1uag4ran75j.apps.googleusercontent.com";
    private final static String CLIENT_SECRET = "";
    private final static String redirectURI = "http://localhost:8000/callback";

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

}
