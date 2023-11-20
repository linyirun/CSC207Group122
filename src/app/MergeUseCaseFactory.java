package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.home.HomeController;
import interface_adapter.home.HomePresenter;
import interface_adapter.home.HomeViewModel;
import use_case.home.HomeInputBoundary;
import use_case.home.HomeInteractor;
import use_case.home.HomeOutputBoundary;
import use_case.home.HomeUserDataAccessInterface;
import view.HomeView;
import view.MergeView;
import view.ViewManager;

import javax.swing.*;
import java.io.IOException;

public class MergeUseCaseFactory {
    private MergeUseCaseFactory() {
    }

//    public static MergeView create() {
//
//    }
}
