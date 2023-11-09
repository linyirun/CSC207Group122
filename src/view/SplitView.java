package view;

import interface_adapter.login.LoginState;
import interface_adapter.split_playlist.SplitController;
import interface_adapter.split_playlist.SplitViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SplitView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "Split Playlist";

    private final SplitViewModel splitViewModel;

    private final SplitController splitController;

    public SplitView(SplitController controller, SplitViewModel viewModel){
        this.splitController = controller;
        this.splitViewModel = viewModel;

    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();
    }

    public static void main(String[] args) {
        //only for testing purpose

    }
}
