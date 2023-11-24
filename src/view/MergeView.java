package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.merge_playlists.MergeController;
import interface_adapter.merge_playlists.MergeViewModel;
import interface_adapter.playlists.PlaylistsController;
import interface_adapter.playlists.PlaylistsViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MergeView extends JPanel implements ActionListener, PropertyChangeListener {
    public final static String viewName = "Merge View";
    private final MergeViewModel mergeViewModel;
    private final ViewManagerModel viewManagerModel;

    private final MergeController mergeController;

    private final JButton getPlaylistButton;

    private final JButton mergeButton;

    private JScrollPane playlistScrollPane;
    private String selectedPlaylistName;


    public MergeView(MergeViewModel mergeViewModel, MergeController mergeController, ViewManagerModel viewManagerModel) {

        this.mergeViewModel = mergeViewModel;
        this.viewManagerModel = viewManagerModel;
        this.mergeController = mergeController;

        viewManagerModel.addPropertyChangeListener(this);
        getPlaylistButton = new JButton(mergeViewModel.GET_PLAYLISTS_LABEL);

        mergeButton = new JButton(mergeViewModel.MERGE_BUTTON_LABEL);



    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Update displayed list for the user if this event is state (i.e. playlists changed)


    }
}
