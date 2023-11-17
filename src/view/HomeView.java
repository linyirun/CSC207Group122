package view;

import interface_adapter.home.HomeController;
import interface_adapter.home.HomeViewModel;
import interface_adapter.home.HomeState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;


public class HomeView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "Home";

    private final HomeViewModel homeViewModel;

    private final HomeController homeController;

    private final JButton splitPlaylist;

    private final JLabel profile;

    public HomeView(HomeController homeController, HomeViewModel homeViewModel){
        this.homeController = homeController;
        this.homeViewModel = homeViewModel;
        homeViewModel.addPropertyChangeListener(this);

        JPanel buttons =  new JPanel();

        profile = new JLabel();
        profile.setAlignmentX(Component.RIGHT_ALIGNMENT);

        splitPlaylist= new JButton(homeViewModel.SPLIT_PLAYLIST_NAME);
        splitPlaylist.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.add(splitPlaylist);

        splitPlaylist.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(splitPlaylist)) {
                            homeController.execute("split");
                        }
                    }
                }
        );
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(profile);
        this.add(buttons);

    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            profile.setText("Welcome " + homeViewModel.getState().getDisplayName());
        }
    }
}
