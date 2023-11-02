package entity;

public final class SpotifyAuth {

    private static String accessToken = "";
    private static String refreshToken = "";
    private final static String scope = "playlist-modify-public%20playlist-modify-private";
    private final static String CLIENT_ID = "84e604e1f851429db4c89831cf8d03a4";
    private final static String CLIENT_SECRET = "c15d3e66db08456d98487002aab0fa49";
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
