package view;

import interface_adapter.merge_playlists.MergeViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MergeView extends JPanel implements ActionListener, PropertyChangeListener {
    public final static String viewName = "Merge View";
    public final MergeViewModel mergeViewModel;

    private final JButton getPlaylistButton;

    private final JButton mergeButton;

    private JScrollPane playlistScrollPane;
    private String selectedPlaylistName;


    public MergeView(MergeViewModel mergeViewModel) {

        this.mergeViewModel = mergeViewModel;

        getPlaylistButton = new JButton(mergeViewModel.GET_PLAYLISTS_LABEL);

        mergeButton = new JButton(mergeViewModel.MERGE_BUTTON_LABEL);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
