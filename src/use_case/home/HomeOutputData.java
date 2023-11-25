package use_case.home;

import java.util.List;
import java.util.Map;

public class HomeOutputData {
    String view_name;
    Map<String, List<String>> profileObjects;
    public HomeOutputData(String view_name) {
        this.view_name = view_name;
    }
    public HomeOutputData(String view_name, Map<String, List<String>> profileObject) {
        this.profileObjects = profileObject;
        this.view_name = view_name;
    }
    public String getViewName() {
        return this.view_name;
    }

    public Map<String, List<String>> getProfileObjects() {
        return profileObjects;
    }
}
