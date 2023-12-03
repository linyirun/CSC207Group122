package use_case.GeniusAuth;

import entity.GeniusAuth;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class GeniusInteractor {

    public static void execute() {
        String clientSecret = GeniusAuth.getClientSecret();
        String clientId = GeniusAuth.getClientId();
        try {
            String tokenUrl = "https://api.genius.com/oauth/token";
            String clientIdAndSecret = clientId + ":" + clientSecret;

            String clientBase64 = Base64.getEncoder().encodeToString(clientIdAndSecret.getBytes());

            HttpURLConnection connection = (HttpURLConnection) new URL(tokenUrl).openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + clientBase64);
            connection.getOutputStream().write("grant_type=client_credentials".getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject;
                try {
                    jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                    System.out.println(jsonObject.toJSONString());
                    GeniusAuth.setAccessToken((String) jsonObject.get("access_token"));
                    System.out.println(GeniusAuth.getAccessToken());
                } catch (ParseException e) {
                    System.out.println("InputStream could not be parsed into JSON object");
                }

            } else {
                System.out.println("Token request failed: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
