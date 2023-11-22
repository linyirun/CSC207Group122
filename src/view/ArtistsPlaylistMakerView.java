package view;

import interface_adapter.artists_playlist_maker.ArtistsPmController;
import interface_adapter.artists_playlist_maker.ArtistsPmState;
import interface_adapter.artists_playlist_maker.ArtistsPmViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.playlists.PlaylistsViewModel;

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
    private final ArtistsPmViewModel artistsPmViewModel;

    final JButton enterButton;
    final JButton createPlaylistButton;  // New button for creating the playlist
    final JTextField searchField;
    final JList<String> searchResultsList;
    final DefaultListModel<String> searchResultsModel;

    final JList<String> selectedArtistsList;
    final DefaultListModel<String> selectedArtistsModel;

    private final ArtistsPmController artistsPmController;

    public ArtistsPlaylistMakerView(ArtistsPmViewModel artistsPmViewModel, ArtistsPmController controller) {

        this.artistsPmController = controller;
        this.artistsPmViewModel = artistsPmViewModel;
        this.artistsPmViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Artists Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel inputPanel = new JPanel();
        searchField = new JTextField(20);
        enterButton = new JButton(ArtistsPmViewModel.ARTISTS_BUTTON_LABEL);
        createPlaylistButton = new JButton(ArtistsPmViewModel.CREATE_PLAYLIST_BUTTON_LABEL);  // New button
        inputPanel.add(searchField);
        inputPanel.add(enterButton);
        inputPanel.add(createPlaylistButton);  // Add the new button

        searchResultsModel = new DefaultListModel<>();
        searchResultsList = new JList<>(searchResultsModel);

        selectedArtistsModel = new DefaultListModel<>();
        selectedArtistsList = new JList<>(selectedArtistsModel);

        JScrollPane searchScrollPane = new JScrollPane(searchResultsList);
        JScrollPane selectedScrollPane = new JScrollPane(selectedArtistsList);

        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(enterButton)) {
                    String searchTerm = searchField.getText();
                    List<String> searchResults = artistsPmController.showTopArtists(searchTerm);
                    displaySearchResults(searchResults);
                }
            }
        });

        // Add a selection listener to the search results list
        searchResultsList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Add the selected artist to the list of selected artists
                    String selectedArtist = searchResultsList.getSelectedValue();
                    if (selectedArtist != null) {
                        selectedArtistsModel.addElement(selectedArtist);
                    }
                }
            }
        });

        createPlaylistButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(createPlaylistButton)) {
                    // Call the function to create the playlist
                    createPlaylist();
                }
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(inputPanel);
        this.add(searchScrollPane);
        this.add(selectedScrollPane);
    }

    private void displaySearchResults(List<String> searchResults) {
        searchResultsModel.clear();
        for (String result : searchResults) {
            searchResultsModel.addElement(result);
        }
    }

    private void createPlaylist() {
        List<String> selectedArtists = new ArrayList<>();
        // Get the selected artists from the list
        for (int i = 0; i < selectedArtistsModel.getSize(); i++) {
            selectedArtists.add(selectedArtistsModel.getElementAt(i));
        }

        // Call the function to create the playlist with the selected artists
        // Replace the following line with the actual function call
        artistsPmController.createPlaylist(selectedArtists);

        // Optionally, you can clear the selected artists list after creating the playlist
        selectedArtistsModel.clear();
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    public void propertyChange(PropertyChangeEvent evt) {
        ArtistsPmState state = (ArtistsPmState) evt.getNewValue();

        // Check if the playlist was created successfully
        if ("playlistCreated".equals(evt.getPropertyName()) && state.isPlaylistCreated()) {
            JOptionPane.showMessageDialog(this, "Playlist created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
