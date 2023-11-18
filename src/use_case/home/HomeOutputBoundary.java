package use_case.home;

public interface HomeOutputBoundary {
    void prepareSuccessView(HomeOutputData data);

    void prepareFailView(String error);
}
