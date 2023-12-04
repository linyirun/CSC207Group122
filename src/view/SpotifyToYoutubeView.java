package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.spotify_to_youtube.SpotifyToYoutubeController;
import interface_adapter.spotify_to_youtube.SpotifyToYoutubeState;
import interface_adapter.spotify_to_youtube.SpotifyToYoutubeViewModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class SpotifyToYoutubeView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "Spot To YT";
    private SpotifyToYoutubeViewModel spotifyToYoutubeViewModel;
    private ViewManagerModel viewManagerModel;
    private SpotifyToYoutubeController spotifyToYoutubeController;

    private JButton convertButton;
    private JButton homeButton;
    private JButton clearPlaylistsButton;
    private JButton deletePlaylistButton;
    private JButton connectYT;

    private JScrollPane playlistScrollPane;
    private String selectedPlaylistName;

    private DefaultListModel<String> playlistsModel;
    private JList<String> playlistsList;
    private DefaultListModel<String> selectedPlaylistsModel;
    private JList<String> selectedPlaylistsList;

    public SpotifyToYoutubeView(SpotifyToYoutubeViewModel spotifyToYoutubeViewModel, SpotifyToYoutubeController spotifyToYoutubeController, ViewManagerModel viewManagerModel) {
        initializeComponents(spotifyToYoutubeViewModel, spotifyToYoutubeController, viewManagerModel);
        attachEventListeners();
    }

    private void initializeComponents(SpotifyToYoutubeViewModel viewModel, SpotifyToYoutubeController controller, ViewManagerModel viewManagerModel) {
        this.spotifyToYoutubeViewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.spotifyToYoutubeController = controller;

        spotifyToYoutubeViewModel.addPropertyChangeListener(this);

        convertButton = new JButton(SpotifyToYoutubeViewModel.CONVERT_BUTTON_LABEL);
        homeButton = new JButton("Home");

        JLabel title = new JLabel(SpotifyToYoutubeViewModel.VIEW_TITLE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel actionsPanel = new JPanel();
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        clearPlaylistsButton = new JButton("Clear Selection");
        deletePlaylistButton = new JButton("Delete Playlist");
        connectYT = new JButton("Connect to YT");

        actionsPanel.add(convertButton);

        playlistsModel = new DefaultListModel<>();
        playlistsList = new JList<>(playlistsModel);
        JScrollPane playlistsScrollPane = new JScrollPane(playlistsList);
        playlistsScrollPane.setBorder(BorderFactory.createTitledBorder("Your Playlists"));

        selectedPlaylistsModel = new DefaultListModel<>();
        selectedPlaylistsList = new JList<>(selectedPlaylistsModel);
        JScrollPane selectedScrollPane = new JScrollPane(selectedPlaylistsList);
        selectedScrollPane.setBorder(BorderFactory.createTitledBorder("Selected Playlists"));
        createLayout(title, actionsPanel, playlistsScrollPane, selectedScrollPane);
    }

    private void createLayout(JLabel title, JPanel actionsPanel, JScrollPane playlistsScrollPane, JScrollPane selectedScrollPane) {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addGroup(layout.createSequentialGroup().addComponent(title).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE).addComponent(homeButton)).addComponent(actionsPanel).addGroup(layout.createSequentialGroup().addComponent(playlistsScrollPane).addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(selectedScrollPane).addComponent(clearPlaylistsButton).addComponent(deletePlaylistButton).addComponent(connectYT)

        )));

        layout.setVerticalGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(title).addComponent(homeButton)).addComponent(actionsPanel).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(playlistsScrollPane).addGroup(layout.createSequentialGroup().addComponent(selectedScrollPane).addComponent(clearPlaylistsButton).addComponent(deletePlaylistButton).addComponent(connectYT))));
    }

    private void attachEventListeners() {
        convertButton.addActionListener(e -> convertPlaylists());
        homeButton.addActionListener(e -> returnHome());
        connectYT.addActionListener(e -> connectToYT());

        playlistsList.addListSelectionListener(e -> handlePlaylistSelection(e));
        clearPlaylistsButton.addActionListener(e -> clearSelectedPlaylists());
        deletePlaylistButton.addActionListener(e -> deleteSelectedPlaylist());
    }

    private void convertPlaylists() {
        if (!spotifyToYoutubeViewModel.getState().getIsConnectedToYT()) {
            JOptionPane.showMessageDialog(this, "Please connect to Youtube first");
            return;
        }

        List<String> selectedPlaylists = new ArrayList<>();
        for (int i = 0; i < selectedPlaylistsModel.getSize(); i++) {
            selectedPlaylists.add(selectedPlaylistsModel.getElementAt(i));
        }

        if (selectedPlaylists.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one playlist to convert.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> newPlaylistNames = new ArrayList<>();
        for (String playlist : selectedPlaylists) {
            String givenName = JOptionPane.showInputDialog(this, "Enter the name for the playlist '" + playlist + "':", JOptionPane.QUESTION_MESSAGE);

            if (givenName == null || givenName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a non-empty name for the playlist '" + playlist + "'.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            newPlaylistNames.add(givenName);
        }

        selectedPlaylistsModel.clear();
        spotifyToYoutubeController.execute(selectedPlaylists, newPlaylistNames, true);
    }

    private void returnHome() {
        viewManagerModel.setActiveView("Home");
        viewManagerModel.firePropertyChanged();
    }

    private void connectToYT() {
        if (!spotifyToYoutubeViewModel.getState().getIsConnectedToYT()) {
            spotifyToYoutubeController.execute(null, null, false);
        }
    }

    private void handlePlaylistSelection(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String selectedPlaylist = playlistsList.getSelectedValue();
            if (selectedPlaylist != null && !selectedPlaylistsModel.contains(selectedPlaylist)) {
                selectedPlaylistsModel.addElement(selectedPlaylist);
            }
        }
    }

    private void clearSelectedPlaylists() {
        selectedPlaylistsModel.clear();
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
        } else if (evt.getPropertyName().equals("state")) {
            SpotifyToYoutubeState state = (SpotifyToYoutubeState) evt.getNewValue();
            JOptionPane.showMessageDialog(SpotifyToYoutubeView.this, state.getMsg());
        }

    }
}
