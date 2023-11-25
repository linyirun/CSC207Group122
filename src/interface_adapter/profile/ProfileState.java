package interface_adapter.profile;

import interface_adapter.home.HomeState;

import java.util.List;
import java.util.Map;

public class ProfileState {
    Map<String, List<String>> profileObjects;
    public ProfileState(ProfileState copy) {
        this.profileObjects = copy.profileObjects;
    }
    public ProfileState() {}

    public void setProfileObjects(Map<String, List<String>> profileObjects) {
        this.profileObjects = profileObjects;
    }

    public Map<String, List<String>> getProfileObjects() {
        return profileObjects;
    }
}
