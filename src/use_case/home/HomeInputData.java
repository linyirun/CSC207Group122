package use_case.home;

public class HomeInputData {
    String button_name = "";

    public HomeInputData(String name) {
        this.button_name = name;
    }
    public String getButtonName() {
        return this.button_name;
    }
}
