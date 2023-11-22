package view;

import interface_adapter.home.HomeController;
import interface_adapter.home.HomeViewModel;
import interface_adapter.home.HomeState;
import interface_adapter.playlists.PlaylistsController;
import interface_adapter.playlists.PlaylistsViewModel;

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
    private final PlaylistsController playlistsController;
    private final PlaylistsViewModel playlistsViewModel;

    private final JButton splitPlaylist;

    private final JButton artistsPlaylistMaker;

    private final JLabel profile;

    public HomeView(HomeController homeController, HomeViewModel homeViewModel, PlaylistsController playlistsController, PlaylistsViewModel playlistsViewModel){
        this.homeController = homeController;
        this.homeViewModel = homeViewModel;
        this.playlistsController = playlistsController;
        this.playlistsViewModel = playlistsViewModel;
        homeViewModel.addPropertyChangeListener(this);

        JPanel buttons =  new JPanel();

        profile = new JLabel();
        profile.setAlignmentX(Component.RIGHT_ALIGNMENT);

        splitPlaylist = new JButton(homeViewModel.SPLIT_PLAYLIST_NAME);
        splitPlaylist.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.add(splitPlaylist);

        artistsPlaylistMaker= new JButton(homeViewModel.ARTISTS_PLAYLIST_MAKER_NAME);
        artistsPlaylistMaker.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.add(artistsPlaylistMaker);

        splitPlaylist.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(splitPlaylist)) {
                            playlistsController.execute();
                            homeController.execute("split");
                        }
                    }
                }
        );

        artistsPlaylistMaker.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(artistsPlaylistMaker)) {
                            homeController.execute("apm");
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
