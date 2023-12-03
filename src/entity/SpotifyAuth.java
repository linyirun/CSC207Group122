package entity;

public final class SpotifyAuth {

    private final static String SCOPE = "playlist-modify-public%20playlist-modify-private%20user-read-email%20user-read-private%20user-top-read";
    private static String accessToken = "";
    private static String refreshToken = "";
    private static final String clientId = "84e604e1f851429db4c89831cf8d03a4";
    private static String clientSecret = "";

    private SpotifyAuth() {
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String newAccessToken) {
        accessToken = newAccessToken;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }

    public static void setRefreshToken(String newRefreshToken) {
        refreshToken = newRefreshToken;
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

    public static void setClientSecret(String newClientSecret) {
        clientSecret = newClientSecret;
    }

}
