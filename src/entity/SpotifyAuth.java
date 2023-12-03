package entity;

public final class SpotifyAuth {

    private static String accessToken = "";
    private static String refreshToken = "";
    private final static String SCOPE = "playlist-modify-public%20playlist-modify-private%20user-read-email%20user-read-private%20user-top-read";
    private static String clientId = "500e6bb5329243e484a3aa96f28b043f";
    private static String clientSecret = "76770e4fd3fa49518986be674847a8bc";

    private SpotifyAuth() {}

    public static String getAccessToken() {
        return accessToken;
    }
    public static String getRefreshToken() {
        return refreshToken;
    }
    public static String getScope() {
        return SCOPE;
    }
    public static String getClientId() {
        return clientId;
    }
    public static String getClientSecret() {
        return clientSecret;
    }
    public static void setClientSecret(String newClientSecret) {clientSecret = newClientSecret;}
    public static void setAccessToken(String newAccessToken) {
        accessToken = newAccessToken;
    }
    public static void setRefreshToken(String newRefreshToken) {
        refreshToken = newRefreshToken;
    }

}
