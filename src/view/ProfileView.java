package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.profile.ProfileViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.List;

public class ProfileView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "Profile";
    public final ProfileViewModel profileViewModel;

    private final JButton back;
    private JList topTracks = new JList<>();
    private JList topArtists = new JList<>();

    private JLabel topTracksLabel;
    private JLabel topArtistsLabel;
    private JLabel titleLabel; // Added JLabel for the title

    public ProfileView(ProfileViewModel profileViewModel, ViewManagerModel viewManagerModel) {
        profileViewModel.addPropertyChangeListener(this);
        this.profileViewModel = profileViewModel;

        setLayout(new BorderLayout());

        titleLabel = new JLabel("Profile");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 16)); // Set font to bold
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topTracksLabel = new JLabel(ProfileViewModel.ALL_TIME_TOP_SONGS);
        topTracksLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topArtistsLabel = new JLabel(ProfileViewModel.ALL_TIME_TOP_ARTISTS);
        topArtistsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        back = new JButton("Home");
        back.setAlignmentY(Component.TOP_ALIGNMENT); // Align to the top
        back.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(back)) {
                            viewManagerModel.setActiveView("Home");
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        leftPanel.add(topTracksLabel);
        leftPanel.add(new JScrollPane(topTracks));
        leftPanel.add(topArtistsLabel);
        leftPanel.add(new JScrollPane(topArtists));
        add(leftPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(titleLabel);
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(back, BorderLayout.NORTH); // Align to the top
        add(buttonPanel, BorderLayout.EAST);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            Map<String, List<String>> profileObjects = profileViewModel.getState().getProfileObjects();
            List<String> tracks = profileObjects.get("tracks");
            List<String> artists = profileObjects.get("artists");

            DefaultListModel<String> listModel1 = new DefaultListModel<>();
            for (String item : tracks) {
                listModel1.addElement(item);
            }
            topTracks.setModel(listModel1);

            DefaultListModel<String> listModel2 = new DefaultListModel<>();
            for (String item : artists) {
                listModel2.addElement(item);
            }
            topArtists.setModel(listModel2);
        }
    }
}
