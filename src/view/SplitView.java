package view;

import interface_adapter.login.LoginState;
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


public class SplitView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "Split Playlist";

    private final SplitViewModel splitViewModel;

    private final SplitController splitController;

    private final PlaylistsViewModel playlistsViewModel;

    private final PlaylistsController playlistsController;

    private final JButton getPlaylist;

    private JScrollPane playlistScrollPane;

    private DefaultListModel<String> playlistModel;

    private JList<String> playlistList;

    private String selectedPlaylistName;

    private final JButton splitBymonth;

    private final JButton splitByDay;

    private final JButton splitByYear;

    private final JButton splitByArtists;

    public SplitView(SplitController splitController, SplitViewModel splitViewModel,
                     PlaylistsController playlistsController, PlaylistsViewModel playlistsViewModel){
        this.splitController = splitController;
        this.splitViewModel = splitViewModel;
        this.playlistsViewModel = playlistsViewModel;
        this.playlistsController = playlistsController;
        playlistsViewModel.addPropertyChangeListener(this);

        playlistModel = new DefaultListModel<>();
        // Create the list and put it in a scroll pane.
        playlistModel.addElement("Inicial"); // for testing only
        playlistList = new JList<>(playlistModel);
        playlistList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        playlistList.setSelectedIndex(0);
        playlistList.setVisibleRowCount(10);
        playlistScrollPane = new JScrollPane(playlistList);

        playlistModel.addElement("Inicial");//for testing purpose only
        JPanel scrollPlaylist = new JPanel();
        playlistScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        playlistScrollPane.setPreferredSize(new Dimension(220, 200));
        scrollPlaylist.add(playlistScrollPane);

        playlistList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // This line prevents double events
                    String selectedValue = playlistList.getSelectedValue();
                    System.out.println("Selected Playlist: " + selectedValue);
                    // Perform actions here when an item is selected
                    selectedPlaylistName = selectedValue;
                }
            }
        });

        JPanel buttons =  new JPanel();

        splitByArtists = new JButton(splitViewModel.SPLIT_BY_ARTISTS);
        splitByArtists.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.add(splitByArtists);

        getPlaylist = new JButton(PlaylistsViewModel.GET_PLAYLIST_BUTTON_LABLE);
        getPlaylist.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttons.add(getPlaylist);

        splitBymonth = new JButton(splitViewModel.SPLIT_BY_MONTH);
        splitBymonth.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttons.add(splitBymonth);

        splitByDay = new JButton(splitViewModel.SPLIT_BY_DAY);
        splitByDay.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttons.add(splitByDay);

        splitByYear = new JButton(splitViewModel.SPLIT_BY_YEAR);
        splitByYear.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttons.add(splitByYear);

        getPlaylist.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(getPlaylist)) {
                            playlistsController.execute();
                        }
                    }
                }
        );

        splitByArtists.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(splitByArtists)) {
                            SplitInputData inputData = new SplitInputData(selectedPlaylistName, splitViewModel.SPLIT_BY_ARTISTS);
                            splitController.execute(inputData);
                            playlistsController.execute();
                            String splitedPlaylists = splitViewModel.toString();
                            if(splitedPlaylists.isEmpty()){
                                JOptionPane.showMessageDialog(null, "You need to choose a playlist first or the playlist you chose is empty!");
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Playlist is split to: " + splitedPlaylists);
                            }
                        }
                    }
                }
        );

        splitBymonth.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(splitBymonth)) {
                            SplitInputData inputData = new SplitInputData(selectedPlaylistName, splitViewModel.SPLIT_BY_MONTH);
                            splitController.execute(inputData);
                        }
                    }
                }
        );

        splitByDay.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(splitByDay)) {
                            SplitInputData inputData = new SplitInputData(selectedPlaylistName, splitViewModel.SPLIT_BY_DAY);
                            splitController.execute(inputData);
                        }
                    }
                }
        );

        splitByYear.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(splitByYear)) {
                            SplitInputData inputData = new SplitInputData(selectedPlaylistName, splitViewModel.SPLIT_BY_YEAR);
                            splitController.execute(inputData);
                        }
                    }
                }
        );

        this.add(scrollPlaylist);
        this.add(buttons);

    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        PlaylistsState state = (PlaylistsState) evt.getNewValue();
        Set<String> currentPlaylists;
        currentPlaylists = state.getPlaylistMap().keySet();
        playlistModel.clear(); // Clear the model
        System.out.println("view property change"+currentPlaylists.toString());
        for (String newName : currentPlaylists) {
            playlistModel.addElement(newName); // Add new elements
        }
    }

    public PlaylistsController getPlaylistsController() {
        return playlistsController;
    }

    public static void main(String[] args) {
        //only for testing purpose

    }
}
