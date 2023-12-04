package entity;

public final class SpotifyAuth {

    private final static String SCOPE = "playlist-modify-public%20playlist-modify-private%20user-read-email%20user-read-private%20user-top-read%20streaming%20user-modify-playback-state";;
    private static String accessToken = "";
    private static String refreshToken = "";
    private static final String clientId = "4d0a07bc89114f82a9e2df1d6550f90b";
    private static String clientSecret = "72722d072c45411c818fc18136ae0358";

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
