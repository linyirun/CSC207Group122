package interface_adapter.home;


public class HomeState {
    String button_error_msg = "";
    String display_name = "";

    public HomeState(HomeState copy) {
        button_error_msg = copy.button_error_msg;
    }
    public HomeState() {}

    public void setButtonErrorMsg(String msg) {
        this.button_error_msg = msg;
    }

    public String getButtonErrorMsg() {
        return this.button_error_msg;
    }
    public void setDisplayName(String display_name) {
        if (display_name != null) {
            this.display_name = display_name;
        }
    }
    public String getDisplayName() {
        return this.display_name;
    }
}
