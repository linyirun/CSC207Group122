package use_case.home;

import java.util.List;
import java.util.Map;

public class HomeOutputData {
    String view_name;
    Map<String, List<String>> profileObjects;
    List<String> playlistNames;

    public HomeOutputData(String view_name) {
        this.view_name = view_name;
    }

    public HomeOutputData(String view_name, Map<String, List<String>> profileObject) {
        this.profileObjects = profileObject;
        this.view_name = view_name;
    }

    public HomeOutputData(String view_name, List<String> playlistNames) {
        this.playlistNames = playlistNames;
        this.view_name = view_name;
    }

    public String getViewName() {
        return this.view_name;
    }

    public Map<String, List<String>> getProfileObjects() {
        return profileObjects;
    }

    public List<String> getPlaylistNames() {
        return this.playlistNames;
    }
}
