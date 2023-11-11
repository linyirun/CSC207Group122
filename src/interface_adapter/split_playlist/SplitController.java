package interface_adapter.split_playlist;
import use_case.split_playlist.*;
public class SplitController implements SplitInputBoundary{
    private final SplitInputBoundary splitUseCaseInteractor;

    public SplitController(SplitInputBoundary splitUseCaseInteractor){
        this.splitUseCaseInteractor = splitUseCaseInteractor;
    }

    public void execute(SplitInputData splitInputData){
        splitUseCaseInteractor.execute(splitInputData);
    }
}
