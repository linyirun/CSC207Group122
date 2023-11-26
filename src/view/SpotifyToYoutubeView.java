package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.merge_playlists.MergeController;
import interface_adapter.merge_playlists.MergeViewModel;
import interface_adapter.split_playlist.SplitController;
import interface_adapter.spotfiy_to_youtube.SpotifyToYoutubeController;
import interface_adapter.spotfiy_to_youtube.SpotifyToYoutubeState;
import interface_adapter.spotfiy_to_youtube.SpotifyToYoutubeViewModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class SpotifyToYoutubeView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "Spot To YT";
    private final SpotifyToYoutubeViewModel spotifyToYoutubeViewModel;
    private final ViewManagerModel viewManagerModel;

    private final SpotifyToYoutubeController spotifyToYoutubeController;


    private final JButton convertButton;

    private final JButton homeButton;
    private final JButton clearPlaylistsButton;
    private final JButton deletePlaylistButton;
    private final JButton connectYT;

    private JScrollPane playlistScrollPane;
    private String selectedPlaylistName;

    private DefaultListModel<String> playlistsModel;
    private JList<String> playlistsList;
    private DefaultListModel<String> selectedPlaylistsModel;
    private JList<String> selectedPlaylistsList;


    public SpotifyToYoutubeView(SpotifyToYoutubeViewModel spotifyToYoutubeViewModel, SpotifyToYoutubeController spotifyToYoutubeController, ViewManagerModel viewManagerModel) {

        this.spotifyToYoutubeViewModel = spotifyToYoutubeViewModel;
        this.viewManagerModel = viewManagerModel;
        this.spotifyToYoutubeController = spotifyToYoutubeController;

        spotifyToYoutubeViewModel.addPropertyChangeListener(this);

        convertButton = new JButton(spotifyToYoutubeViewModel.CONVERT_BUTTON_LABEL);
        homeButton = new JButton("Home");


        JLabel title = new JLabel(spotifyToYoutubeViewModel.VIEW_TITLE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel actionsPanel = new JPanel();
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        clearPlaylistsButton = new JButton("Clear Selection");
        deletePlaylistButton = new JButton("Delete Playlist");
        connectYT = new JButton("Connect to YT");
//        inputPanel.add(new JLabel("Search:"));
//        inputPanel.add(searchField);
//        inputPanel.add(enterButton);

        actionsPanel.add(convertButton);
//        actionsPanel.add();

        playlistsModel = new DefaultListModel<>();
        playlistsList = new JList<>(playlistsModel);
        JScrollPane playlistsScrollPane = new JScrollPane(playlistsList);
        playlistsScrollPane.setBorder(BorderFactory.createTitledBorder("Your Playlists"));

        selectedPlaylistsModel = new DefaultListModel<>();
        selectedPlaylistsList = new JList<>(selectedPlaylistsModel);
        JScrollPane selectedScrollPane = new JScrollPane(selectedPlaylistsList);
        selectedScrollPane.setBorder(BorderFactory.createTitledBorder("Selected Playlists"));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(title)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                        GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE) // places this on the right
                                .addComponent(homeButton)
                        )
                        .addComponent(actionsPanel)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(playlistsScrollPane)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(selectedScrollPane)
                                        .addComponent(clearPlaylistsButton)
                                        .addComponent(deletePlaylistButton)
                                        .addComponent(connectYT)
                                )
                        )
        );


        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(title)
                                .addComponent(homeButton)
                        )
                        .addComponent(actionsPanel)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(playlistsScrollPane)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(selectedScrollPane)
                                        .addComponent(clearPlaylistsButton)
                                        .addComponent(deletePlaylistButton)
                                        .addComponent(connectYT)
                                )
                        )
        );


        convertButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(convertButton)) {
                            mergePlaylists();
                        }
                    }
                }
        );

        homeButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(homeButton)) {
                            viewManagerModel.setActiveView("Home");
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );
        connectYT.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if (evt.getSource().equals(connectYT) && !spotifyToYoutubeViewModel.getState().getIsConnectedToYT()) {
                        spotifyToYoutubeController.execute(null, null, false);
                    }
                }
            }
        );



        playlistsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedPlaylist = playlistsList.getSelectedValue();
                // TODO: this is very slow, checking if selectedPlaylistModel contains a playlist
                if (selectedPlaylist != null && !selectedPlaylistsModel.contains(selectedPlaylist)) {
                    selectedPlaylistsModel.addElement(selectedPlaylist);
                }
            }
        });



        clearPlaylistsButton.addActionListener(e -> {
            if (e.getSource().equals(clearPlaylistsButton)) {
                selectedPlaylistsModel.clear();
            }
        });

        deletePlaylistButton.addActionListener(e -> {
            if (e.getSource().equals(deletePlaylistButton)) {
                deleteSelectedPlaylist();
            }
        });
//

    }
    private void mergePlaylists() {
        if (!spotifyToYoutubeViewModel.getState().getIsConnectedToYT()) {
            JOptionPane.showMessageDialog(this, "Please connect to Youtube first");
            return;
        }
        List<String> selectedPlaylists = new ArrayList<>();
        for (int i = 0; i < selectedPlaylistsModel.getSize(); i++) {
            selectedPlaylists.add(selectedPlaylistsModel.getElementAt(i));
        }

        if (selectedPlaylists.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one playlist to merge.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String givenName = JOptionPane.showInputDialog(this, "Enter the name of the new playlist: ", JOptionPane.QUESTION_MESSAGE);

        if (givenName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a non-empty name.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            selectedPlaylistsModel.clear();
        }
        spotifyToYoutubeController.execute(selectedPlaylists, givenName, true);

    }

    private void deleteSelectedPlaylist() {
        int selectedIndex = selectedPlaylistsList.getSelectedIndex();
        if (selectedIndex != -1) {
            selectedPlaylistsModel.remove(selectedIndex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("refresh")) {
            List<String> playlistNames = spotifyToYoutubeViewModel.getState().getPlaylistNames();
            playlistsModel.clear();
            for (String playlistName : playlistNames) {
                playlistsModel.addElement(playlistName);
            }
        }
        else if (evt.getPropertyName().equals("state")) {
            SpotifyToYoutubeState state = (SpotifyToYoutubeState) evt.getNewValue();
            JOptionPane.showMessageDialog(SpotifyToYoutubeView.this, state.getMsg());
        }

    }
}
