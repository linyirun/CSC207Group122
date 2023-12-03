package interface_adapter.profile;

import java.util.List;
import java.util.Map;

public class ProfileState {
    Map<String, List<String>> profileObjects;

    public ProfileState() {
    }

    public Map<String, List<String>> getProfileObjects() {
        return profileObjects;
    }

    public void setProfileObjects(Map<String, List<String>> profileObjects) {
        this.profileObjects = profileObjects;
    }
}
