package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.merge_playlists.MergeController;
import interface_adapter.merge_playlists.MergeViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MergeView extends JPanel implements ActionListener, PropertyChangeListener {
    public final static String viewName = "Merge View";
    private final MergeViewModel mergeViewModel;
    private final ViewManagerModel viewManagerModel;

    private final MergeController mergeController;

    private final JButton getPlaylistButton;

    private final JButton mergeButton;

    private final JButton homeButton;
    private final JButton refreshButton;

    private JScrollPane playlistScrollPane;
    private String selectedPlaylistName;

    private DefaultListModel<String> playlistModel;
    private JList<String> playlistList;


    public MergeView(MergeViewModel mergeViewModel, MergeController mergeController, ViewManagerModel viewManagerModel) {

        this.mergeViewModel = mergeViewModel;
        this.viewManagerModel = viewManagerModel;
        this.mergeController = mergeController;

        viewManagerModel.addPropertyChangeListener(this);
        getPlaylistButton = new JButton(mergeViewModel.GET_PLAYLISTS_LABEL);

        mergeButton = new JButton(mergeViewModel.MERGE_BUTTON_LABEL);
        homeButton = new JButton(mergeViewModel.HOME_BUTTON_LABEL);

        refreshButton = new JButton(mergeViewModel.REFRESH_BUTTON_LABEL);

        JLabel title = new JLabel(mergeViewModel.VIEW_TITLE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


//        JPanel inputPanel = new JPanel();
//        inputPanel.setBorder(BorderFactory.createTitledBorder("Search and Actions"));
//        searchField = new JTextField(20);
//        enterButton = new JButton(ArtistsPmViewModel.ARTISTS_BUTTON_LABEL);

//        clearSelectionButton = new JButton("Clear Selection");
//        deleteArtistButton = new JButton("Delete Artist");
//        inputPanel.add(new JLabel("Search:"));
//        inputPanel.add(searchField);
//        inputPanel.add(enterButton);
//        inputPanel.add(createPlaylistButton);
//        inputPanel.add(clearSelectionButton);
//        inputPanel.add(deleteArtistButton);
//
//        searchResultsModel = new DefaultListModel<>();
//        searchResultsList = new JList<>(searchResultsModel);
//        JScrollPane searchScrollPane = new JScrollPane(searchResultsList);
//        searchScrollPane.setBorder(BorderFactory.createTitledBorder("Search Results"));
//
//        selectedArtistsModel = new DefaultListModel<>();
//        selectedArtistsList = new JList<>(selectedArtistsModel);
//        JScrollPane selectedScrollPane = new JScrollPane(selectedArtistsList);



        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(title)
                        )
                        .addComponent(mergeButton)


        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(title)
                        )
                        .addComponent(mergeButton)

        );



//        this.setLayout(layout);
//        layout.setAutoCreateGaps(true);
//        layout.setAutoCreateContainerGaps(true);
//
//        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
//                .addComponent(title)
//                .addComponent(inputPanel)
//                .addGroup(layout.createSequentialGroup()
//                        .addComponent(searchScrollPane)
//                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
//                                .addComponent(selectedScrollPane)
//                                .addComponent(clearSelectionButton)
//                                .addComponent(deleteArtistButton))));
//
//        layout.setVerticalGroup(layout.createSequentialGroup()
//                .addComponent(title)
//                .addComponent(inputPanel)
//                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                        .addComponent(searchScrollPane)
//                        .addGroup(layout.createSequentialGroup()
//                                .addComponent(selectedScrollPane)
//                                .addComponent(clearSelectionButton)
//                                .addComponent(deleteArtistButton))));


//        selectedScrollPane.setBorder(BorderFactory.createTitledBorder("Selected Artists"));
//        enterButton.addActionListener(e -> {
//            if (e.getSource().equals(enterButton)) {
//                String searchTerm = searchField.getText();
//                ArtistsPmInputData inputData = new ArtistsPmInputData(searchTerm, null, 0);
//                List<String> searchResults = artistsPmController.showTopArtists(inputData);
//                displaySearchResults(searchResults);
//            }
//        });
//
//        searchResultsList.addListSelectionListener(e -> {
//            if (!e.getValueIsAdjusting()) {
//                String selectedArtist = searchResultsList.getSelectedValue();
//                if (selectedArtist != null) {
//                    selectedArtistsModel.addElement(selectedArtist);
//                }
//            }
//        });
//
//        createPlaylistButton.addActionListener(e -> {
//            if (e.getSource().equals(createPlaylistButton)) {
//                createPlaylist();
//            }
//        });
//
//        clearSelectionButton.addActionListener(e -> {
//            if (e.getSource().equals(clearSelectionButton)) {
//                selectedArtistsModel.clear();
//            }
//        });
//
//        deleteArtistButton.addActionListener(e -> {
//            if (e.getSource().equals(deleteArtistButton)) {
//                deleteSelectedArtist();
//            }
//        });
//

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Update displayed list for the user if this event is state (i.e. playlists changed)


    }
}
