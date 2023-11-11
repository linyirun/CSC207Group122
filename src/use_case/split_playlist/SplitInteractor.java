package use_case.split_playlist;

import entity.User;
import use_case.login.LoginInputData;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;
import use_case.login.LoginUserDataAccessInterface;

public class SplitInteractor implements SplitInputBoundary{
    final SplitUserDataAccessInterface userDataAccessObject;
    final SplitOutputBoundary splitPresenter;

    public SplitInteractor(SplitUserDataAccessInterface userDataAccessInterface,
                           SplitOutputBoundary splitOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.splitPresenter = splitOutputBoundary;
    }

    @Override
    public void execute(SplitInputData splitInputData) {
        
    }
}
