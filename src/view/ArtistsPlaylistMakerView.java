package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.artists_playlist_maker.ArtistsPmController;
import interface_adapter.artists_playlist_maker.ArtistsPmViewModel;
import use_case.artists_playlist_maker.ArtistsPmInputData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * View class for the Artists Playlist Maker use case.
 */
public class ArtistsPlaylistMakerView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "Artists Playlist Maker";

    private JCheckBox includeInPlaylistCheckBox;
    private JButton enterButton, createPlaylistButton, clearSelectionButton, deleteArtistButton;
    private JTextField searchField;
    private JList<String> searchResultsList, selectedArtistsList;
    private DefaultListModel<String> searchResultsModel, selectedArtistsModel;
    private final ArtistsPmViewModel artistsPmViewModel;

    private JButton backButton;

    private final ArtistsPmController artistsPmController;

    private final ViewManagerModel viewManagerModel;

    /**
     * Constructs an instance of ArtistsPlaylistMakerView.
     *
     * @param artistsPmViewModel The view model for the Artists Playlist Maker.
     * @param controller          The controller for the Artists Playlist Maker.
     */
    public ArtistsPlaylistMakerView(ArtistsPmViewModel artistsPmViewModel, ArtistsPmController controller, ViewManagerModel viewManagerModel) {
        this.artistsPmController = controller;
        this.artistsPmViewModel = artistsPmViewModel;
        this.viewManagerModel = viewManagerModel;
        this.artistsPmViewModel.addPropertyChangeListener(this);

        setupUI();
        addListeners();
    }

    private void setupUI() {
        JLabel title = new JLabel("Artists Playlist Maker");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel inputPanel = createInputPanel();

        searchResultsModel = new DefaultListModel<>();
        searchResultsList = new JList<>(searchResultsModel);
        JScrollPane searchScrollPane = createScrollPane(searchResultsList, "Search Results");

        selectedArtistsModel = new DefaultListModel<>();
        selectedArtistsList = new JList<>(selectedArtistsModel);
        JScrollPane selectedScrollPane = createScrollPane(selectedArtistsList, "Selected Artists");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Add back button
        backButton = new JButton("Home");
        // Center align the title and right-align the back button
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup().addComponent(title)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backButton))
                .addComponent(inputPanel)
                .addGroup(layout.createSequentialGroup().addComponent(searchScrollPane)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(selectedScrollPane)
                                .addComponent(clearSelectionButton)
                                .addComponent(deleteArtistButton))));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(title)
                        .addComponent(backButton))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputPanel)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(searchScrollPane)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(selectedScrollPane)
                                .addComponent(clearSelectionButton)
                                .addComponent(deleteArtistButton))));
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder("Search and Actions"));
        searchField = new JTextField(20);
        enterButton = new JButton(ArtistsPmViewModel.ARTISTS_BUTTON_LABEL);
        createPlaylistButton = new JButton(ArtistsPmViewModel.CREATE_PLAYLIST_BUTTON_LABEL);
        clearSelectionButton = new JButton("Clear Selection");
        deleteArtistButton = new JButton("Delete Artist");

        includeInPlaylistCheckBox = new JCheckBox("Include songs already in playlists");

        inputPanel.add(new JLabel("Search:"));
        inputPanel.add(searchField);
        inputPanel.add(enterButton);
        inputPanel.add(createPlaylistButton);
        inputPanel.add(clearSelectionButton);
        inputPanel.add(deleteArtistButton);
        inputPanel.add(includeInPlaylistCheckBox);


        return inputPanel;
    }

    private JScrollPane createScrollPane(JList<String> list, String title) {
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createTitledBorder(title));
        return scrollPane;
    }

    private void addListeners() {
        enterButton.addActionListener(e -> searchButtonClicked());
        searchResultsList.addListSelectionListener(e -> handleSearchResultsSelection());
        createPlaylistButton.addActionListener(e -> createPlaylistButtonClicked());
        clearSelectionButton.addActionListener(e -> clearSelectionButtonClicked());
        deleteArtistButton.addActionListener(e -> deleteArtistButtonClicked());
        backButton.addActionListener(e -> backButtonClicked());
    }

    private void backButtonClicked() {
        viewManagerModel.setActiveView("Home");
        viewManagerModel.firePropertyChanged();
    }

    private void searchButtonClicked() {
        String searchTerm = searchField.getText();
        List<String> searchResults = artistsPmController.showTopArtists(searchTerm);
        displaySearchResults(searchResults);
    }

    private void handleSearchResultsSelection() {
        if (!searchResultsList.getValueIsAdjusting()) {
            String selectedArtist = searchResultsList.getSelectedValue();

            if (selectedArtist != null && !selectedArtistsModel.contains(selectedArtist)) {
                selectedArtistsModel.addElement(selectedArtist);
            }
        }
    }


    private void createPlaylistButtonClicked() {
        List<String> selectedArtists = getSelectedArtists();

        if (selectedArtists.isEmpty()) {
            showErrorMessage("Please select at least one artist to create a playlist.");
            return;
        }

        int numberOfSongs = getNumberOfSongs();

        boolean includeInPlaylist = includeInPlaylistCheckBox.isSelected();

        if (numberOfSongs <= 0) {
            showErrorMessage("Please enter a valid number of songs greater than zero.");
            return;
        }

        artistsPmController.createPlaylist(selectedArtists, numberOfSongs, includeInPlaylist);

        clearSelectionButtonClicked();
    }

    private List<String> getSelectedArtists() {
        List<String> selectedArtists = new ArrayList<>();
        for (int i = 0; i < selectedArtistsModel.getSize(); i++) {
            selectedArtists.add(selectedArtistsModel.getElementAt(i));
        }
        return selectedArtists;
    }

    private int getNumberOfSongs() {
        String numberOfSongsStr = showInputPrompt("Enter the number of songs you want from each artist:", "Number of Songs");
        if (numberOfSongsStr == null || numberOfSongsStr.trim().isEmpty()) {
            return 0; // User canceled or entered an empty string
        }

        try {
            return Integer.parseInt(numberOfSongsStr);
        } catch (NumberFormatException e) {
            showErrorMessage("Please enter a valid number.");
            return 0;
        }
    }

    private void clearSelectionButtonClicked() {
        selectedArtistsModel.clear();
    }

    private void deleteArtistButtonClicked() {
        int selectedIndex = selectedArtistsList.getSelectedIndex();
        if (selectedIndex != -1) {
            selectedArtistsModel.remove(selectedIndex);
        }
    }

    private void displaySearchResults(List<String> searchResults) {
        searchResultsModel.clear();
        for (String result : searchResults) {
            searchResultsModel.addElement(result);
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("playlistCreated".equals(evt.getPropertyName()) && (Boolean) evt.getNewValue()) {
            JOptionPane.showMessageDialog(this, "Playlist created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    private String showInputPrompt(String message, String title) {
        return JOptionPane.showInputDialog(this, message, title, JOptionPane.QUESTION_MESSAGE);
    }
}
