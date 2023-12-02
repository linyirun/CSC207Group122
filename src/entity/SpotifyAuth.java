package entity;

public final class SpotifyAuth {

    private static String accessToken = "";
    private static String refreshToken = "";
    private final static String scope = "playlist-modify-public%20playlist-modify-private%20user-read-email%20user-read-private%20user-top-read";
    private final static String CLIENT_ID = "84e604e1f851429db4c89831cf8d03a4";
    private final static String CLIENT_SECRET = "7883896d9ed04f1f819020d83fa1e43c";

    private SpotifyAuth() {}

    public static String getAccessToken() {
        return accessToken;
    }
    public static String getRefreshToken() {
        return refreshToken;
    }
    public static String getScope() {
        return scope;
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
