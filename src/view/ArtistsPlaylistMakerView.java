package view;

import interface_adapter.artists_playlist_maker.ArtistsPmController;
import interface_adapter.artists_playlist_maker.ArtistsPmViewModel;
import use_case.artists_playlist_maker.ArtistsPmInputData;

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

public class ArtistsPlaylistMakerView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "Artists Playlist Maker";
    final JButton enterButton;
    final JButton createPlaylistButton;
    final JButton clearSelectionButton;
    final JButton deleteArtistButton;
    final JTextField searchField;
    final JList<String> searchResultsList;
    final DefaultListModel<String> searchResultsModel;
    final JList<String> selectedArtistsList;
    final DefaultListModel<String> selectedArtistsModel;
    private final ArtistsPmViewModel artistsPmViewModel;
    private final ArtistsPmController artistsPmController;

    public ArtistsPlaylistMakerView(ArtistsPmViewModel artistsPmViewModel, ArtistsPmController controller) {
        this.artistsPmController = controller;
        this.artistsPmViewModel = artistsPmViewModel;
        this.artistsPmViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Artists Playlist Maker");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder("Search and Actions"));
        searchField = new JTextField(20);
        enterButton = new JButton(ArtistsPmViewModel.ARTISTS_BUTTON_LABEL);
        createPlaylistButton = new JButton(ArtistsPmViewModel.CREATE_PLAYLIST_BUTTON_LABEL);
        clearSelectionButton = new JButton("Clear Selection");
        deleteArtistButton = new JButton("Delete Artist");
        inputPanel.add(new JLabel("Search:"));
        inputPanel.add(searchField);
        inputPanel.add(enterButton);
        inputPanel.add(createPlaylistButton);
        inputPanel.add(clearSelectionButton);
        inputPanel.add(deleteArtistButton);

        searchResultsModel = new DefaultListModel<>();
        searchResultsList = new JList<>(searchResultsModel);
        JScrollPane searchScrollPane = new JScrollPane(searchResultsList);
        searchScrollPane.setBorder(BorderFactory.createTitledBorder("Search Results"));

        selectedArtistsModel = new DefaultListModel<>();
        selectedArtistsList = new JList<>(selectedArtistsModel);
        JScrollPane selectedScrollPane = new JScrollPane(selectedArtistsList);
        selectedScrollPane.setBorder(BorderFactory.createTitledBorder("Selected Artists"));
        enterButton.addActionListener(e -> {
            if (e.getSource().equals(enterButton)) {
                String searchTerm = searchField.getText();
                ArtistsPmInputData inputData = new ArtistsPmInputData(searchTerm, null, 0);
                List<String> searchResults = artistsPmController.showTopArtists(inputData);
                displaySearchResults(searchResults);
            }
        });

        searchResultsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedArtist = searchResultsList.getSelectedValue();
                if (selectedArtist != null) {
                    selectedArtistsModel.addElement(selectedArtist);
                }
            }
        });

        createPlaylistButton.addActionListener(e -> {
            if (e.getSource().equals(createPlaylistButton)) {
                createPlaylist();
            }
        });

        clearSelectionButton.addActionListener(e -> {
            if (e.getSource().equals(clearSelectionButton)) {
                selectedArtistsModel.clear();
            }
        });

        deleteArtistButton.addActionListener(e -> {
            if (e.getSource().equals(deleteArtistButton)) {
                deleteSelectedArtist();
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(title).addComponent(inputPanel).addGroup(layout.createSequentialGroup().addComponent(searchScrollPane).addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(selectedScrollPane).addComponent(clearSelectionButton).addComponent(deleteArtistButton))));

        layout.setVerticalGroup(layout.createSequentialGroup().addComponent(title).addComponent(inputPanel).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(searchScrollPane).addGroup(layout.createSequentialGroup().addComponent(selectedScrollPane).addComponent(clearSelectionButton).addComponent(deleteArtistButton))));
    }

    private void displaySearchResults(List<String> searchResults) {
        searchResultsModel.clear();
        for (String result : searchResults) {
            searchResultsModel.addElement(result);
        }
    }

    private void createPlaylist() {
        List<String> selectedArtists = new ArrayList<>();
        for (int i = 0; i < selectedArtistsModel.getSize(); i++) {
            selectedArtists.add(selectedArtistsModel.getElementAt(i));
        }

        if (selectedArtists.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one artist to create a playlist.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String numberOfSongsStr = JOptionPane.showInputDialog(this, "Enter the number of songs you want from each artist:", "Number of Songs", JOptionPane.QUESTION_MESSAGE);

        if (numberOfSongsStr == null || numberOfSongsStr.trim().isEmpty()) {
            return;
        }

        try {
            int numberOfSongs = Integer.parseInt(numberOfSongsStr);

            if (numberOfSongs <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number of songs greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ArtistsPmInputData inputData = new ArtistsPmInputData(null, selectedArtists, numberOfSongs);
            artistsPmController.createPlaylist(inputData);

            selectedArtistsModel.clear();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedArtist() {
        int selectedIndex = selectedArtistsList.getSelectedIndex();
        if (selectedIndex != -1) {
            selectedArtistsModel.remove(selectedIndex);
        }
    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if ("playlistCreated".equals(evt.getPropertyName()) && (Boolean) evt.getNewValue()) {
            JOptionPane.showMessageDialog(this, "Playlist created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
