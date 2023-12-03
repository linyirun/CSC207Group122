package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.split_playlist.SplitController;
import interface_adapter.split_playlist.SplitViewModel;
import interface_adapter.playlists.*;
import use_case.split_playlist.SplitInputData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.text.*;




public class SplitView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "Split Playlist";

    private final SplitViewModel splitViewModel;
    private final SplitController splitController;
    private final PlaylistsViewModel playlistsViewModel;
    private final PlaylistsController playlistsController;
    private final ViewManagerModel viewManagerModel;

    private JButton getPlaylist;
    private JScrollPane playlistScrollPane;
    private DefaultListModel<String> playlistModel;
    private JList<String> playlistList;
    private String selectedPlaylistName;
    private JButton splitByLength;
    private JButton splitByArtists;

    private String endTime;

    private String startTime;

    public SplitView(SplitController splitController, SplitViewModel splitViewModel,
                     PlaylistsController playlistsController, PlaylistsViewModel playlistsViewModel, ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        this.splitController = splitController;
        this.splitViewModel = splitViewModel;
        this.playlistsViewModel = playlistsViewModel;
        this.playlistsController = playlistsController;
        playlistsViewModel.addPropertyChangeListener(this);
        viewManagerModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Split Playlist");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font to bold and adjust size as needed
        add(titleLabel, BorderLayout.PAGE_START);

        // Playlist Scroll Pane on the left
        add(createPlaylistPanel(), BorderLayout.WEST);

        // Interval in the middle
        add(createSongIntervalPanel(), BorderLayout.CENTER);

        // Buttons on the right
        add(createButtonsPanel(), BorderLayout.EAST);
    }

    private JPanel createPlaylistPanel() {
        JPanel playlistPanel = new JPanel(new BorderLayout());
        playlistPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        playlistModel = new DefaultListModel<>();

        playlistList = new JList<>(playlistModel);
        playlistList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        playlistList.setSelectedIndex(0);
        playlistList.setVisibleRowCount(10);

        playlistScrollPane = new JScrollPane(playlistList);
        // Add the scroll pane to the center of the playlist panel
        playlistPanel.add(playlistScrollPane, BorderLayout.CENTER);

        // Set preferred size for the playlist panel
        playlistPanel.setPreferredSize(new Dimension(250, 400)); // Adjust values as needed

        playlistList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedValue = playlistList.getSelectedValue();
                    System.out.println("Selected Playlist: " + selectedValue);
                    selectedPlaylistName = selectedValue;
                }
            }
        });

        return playlistPanel;
    }

    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Back Button
        JButton backButton = new JButton("Home");
        buttonsPanel.add(backButton, BorderLayout.NORTH);

        // Grouped Split Buttons
        JPanel splitButtonsPanel = new JPanel(new GridLayout(0, 1, 0, 5));
        splitButtonsPanel.setBorder(BorderFactory.createTitledBorder("Split Options"));

        splitByArtists = new JButton(splitViewModel.SPLIT_BY_ARTISTS);
        splitByArtists.setAlignmentX(Component.CENTER_ALIGNMENT);

        splitButtonsPanel.add(splitByArtists);

        splitByLength = new JButton(splitViewModel.SPLIT_BY_LENGTH);
        splitByLength.setAlignmentX(Component.CENTER_ALIGNMENT);

        splitButtonsPanel.add(splitByLength);

        buttonsPanel.add(splitButtonsPanel, BorderLayout.CENTER);

        // Get Playlist Button
        getPlaylist = new JButton(PlaylistsViewModel.GET_PLAYLIST_BUTTON_LABEL);
        buttonsPanel.add(getPlaylist, BorderLayout.SOUTH);

        // Action Listeners for Buttons
        ActionListener buttonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(backButton)) {
                    // Handle back button action here
                    // For example, you can navigate back to the previous view
                    viewManagerModel.setActiveView("Home");
                    viewManagerModel.firePropertyChanged();
                } else if (evt.getSource().equals(getPlaylist)) {
                    playlistsController.execute();
                } else if (evt.getSource().equals(splitByArtists)) {
                    SplitInputData inputData = new SplitInputData(selectedPlaylistName);
                    splitController.execute(inputData);
                    playlistsController.execute();
                    String splitPlaylists = splitViewModel.toString();
                    showMessage(splitPlaylists);
                } else if (evt.getSource().equals(splitByLength)){
                    if(endTime != null && startTime != null){
                        splitController.splitByLength(selectedPlaylistName, Integer.parseInt(startTime), Integer.parseInt(endTime));
                        playlistsController.execute();
                        String splitPlaylists = splitViewModel.toString();
                        showMessage(splitPlaylists);
                    }
                    else{
                        showMessage("input error");
                    }
                }
            }
        };

        backButton.addActionListener(buttonActionListener);
        getPlaylist.addActionListener(buttonActionListener);
        splitByArtists.addActionListener(buttonActionListener);
        splitByLength.addActionListener(buttonActionListener);

        return buttonsPanel;
    }

    public JPanel createSongIntervalPanel() {
        // Create a JPanel
        JPanel panel = new JPanel();

        // Set the layout
        panel.setLayout(new FlowLayout());

        // Create labels
        JLabel startLabel = new JLabel("Start Time (seconds): ");
        JLabel endLabel = new JLabel("End Time (seconds): ");

        // Create text fields for input
        JTextField startTextField = new JTextField(5);
        JTextField endTextField = new JTextField(5);

        DocumentFilter filter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) {
                    return;
                }

                if (string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) {
                    return;
                }

                if (text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };

        ((AbstractDocument) startTextField.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) endTextField.getDocument()).setDocumentFilter(filter);


        // Add DocumentListeners to text fields
        DocumentListener startTextFieldListener = new DocumentListener() {
            public void update(DocumentEvent e) {
                // Handle start time input here
                System.out.println("Start Time Updated: " + startTextField.getText());
                startTime = startTextField.getText();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update(e);
            }
        };
        startTextField.getDocument().addDocumentListener(startTextFieldListener);

        DocumentListener endTextFieldListener = new DocumentListener() {
            public void update(DocumentEvent e) {
                // Handle end time input here
                System.out.println("End Time Updated: " + endTextField.getText());
                endTime = endTextField.getText();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update(e);
            }
        };
        endTextField.getDocument().addDocumentListener(endTextFieldListener);

        // Add components to the panel
        panel.add(startLabel);
        panel.add(startTextField);
        panel.add(endLabel);
        panel.add(endTextField);

        return panel;
    }


    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue().equals("Split Playlist")) {
            // This will only be reached when being called when HomeInteractor calls the presenter to switch to this page
            playlistsController.execute();  // Call this to initially gather the user's playlists
        }

        if (evt.getNewValue() instanceof PlaylistsState) {
            // This branch will be reached whenever SplitViewModel calls firePropertyChanged, as event's new value will be of type PlaylistsState

            PlaylistsState state = (PlaylistsState) evt.getNewValue();
            Set<String> currentPlaylists;
            currentPlaylists = state.getPlaylistMap().keySet();
            playlistModel.clear(); // Clear the model
            System.out.println("view property change" + currentPlaylists.toString());
            for (String newName : currentPlaylists) {
                playlistModel.addElement(newName); // Add new elements
            }
        }
        else if(evt.getNewValue().equals("fail")){
            showMessage("");
        }
    }
    private void showMessage(String message) {
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You need to choose a playlist first or the playlist you chose is empty!", "Error",JOptionPane.ERROR_MESSAGE);
        }
        else if(message.equals("input error")){
            JOptionPane.showMessageDialog(this, "You need to enter the interval!", "Error",JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Playlist is split to: " + message, "Success", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
