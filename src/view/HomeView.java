package view;

import interface_adapter.home.HomeController;
import interface_adapter.home.HomeViewModel;
import interface_adapter.home.HomeState;
import interface_adapter.playlists.PlaylistsController;
import interface_adapter.playlists.PlaylistsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;


public class HomeView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "Home";

    private final HomeViewModel homeViewModel;
    private final HomeController homeController;

    private final JButton splitPlaylist;

    private final JButton mergePlaylist;

    private final JButton artistsPlaylistMaker;

    private final JButton spotifyToYoutube;
    private final JLabel profile;
    private final JLabel profileText;
    private final JLabel welcome;

    private final DefaultListModel<String> playlistsModel;
    private final JList<String> playlistsList;
    private DefaultListModel<String> selectedSongsModel;
    private JList<String> selectedSongsList;

    private JLabel titleLabel;

    public HomeView(HomeController homeController, HomeViewModel homeViewModel) {
        this.homeController = homeController;
        this.homeViewModel = homeViewModel;
        homeViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        titleLabel = new JLabel("Home");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 16)); // Set font to bold
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.add(titleLabel);

        add(titlePanel, BorderLayout.NORTH);

        // Create a panel for the buttons and use BoxLayout to stack them vertically
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBorder(BorderFactory.createTitledBorder("Buttons")); // Add a titled border

        splitPlaylist = createStyledButton(HomeViewModel.SPLIT_PLAYLIST_NAME);
        artistsPlaylistMaker = createStyledButton(HomeViewModel.ARTISTS_PLAYLIST_MAKER_NAME);
        mergePlaylist = createStyledButton(HomeViewModel.MERGE_PLAYLIST_NAME);
        spotifyToYoutube = createStyledButton(HomeViewModel.SPOTIFY_TO_YT_NAME);
     
        buttonsPanel.add(mergePlaylist);
        buttonsPanel.add(splitPlaylist);
        buttonsPanel.add(artistsPlaylistMaker);
        buttonsPanel.add(spotifyToYoutube);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some spacing

        add(buttonsPanel, BorderLayout.WEST);

        // Create a panel for the profile information
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createTitledBorder("Profile")); // Add a titled border

        ImageIcon profileIcon = new ImageIcon("src/images/profile_icon_2.png");
        Image scaledImage = profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon smallerProfileIcon = new ImageIcon(scaledImage);

        profile = new JLabel(smallerProfileIcon);
        profile.setAlignmentX(Component.CENTER_ALIGNMENT);
        profile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        profile.setIconTextGap(10);

        profileText = new JLabel("Profile");
        profileText.setAlignmentX(Component.CENTER_ALIGNMENT);

        profile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                profile.setBorder(BorderFactory.createLineBorder(Color.RED, 1)); // Change border color
            }

            @Override
            public void mouseExited(MouseEvent e) {
                profile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Restore border color
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                homeController.execute("profile");
            }
        });

        profilePanel.add(profile);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some spacing
        profilePanel.add(profileText);

        // Add the profile panel to the EAST of the main panel
        add(profilePanel, BorderLayout.EAST);

        // Create a panel for the welcome label
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBorder(BorderFactory.createTitledBorder("Playlists")); // Add a titled border

        welcome = new JLabel();
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

//        welcomePanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some spacing
        welcomePanel.add(welcome);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some spacing

        JPanel scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout(new GridLayout(1, 2));

        // add ScrollPanes displaying the user's playlists and songs
        playlistsModel = new DefaultListModel<>();
        playlistsList = new JList<>(playlistsModel);
        JScrollPane playlistsScrollPane = new JScrollPane(playlistsList);
        playlistsScrollPane.setBorder(BorderFactory.createTitledBorder("Your Playlists"));

        scrollPanePanel.add(playlistsScrollPane);

        selectedSongsModel = new DefaultListModel<>();
        selectedSongsList = new JList<>(selectedSongsModel);
        JScrollPane songsScrollPane = new JScrollPane(selectedSongsList);
        songsScrollPane.setBorder(BorderFactory.createTitledBorder("Songs"));

        scrollPanePanel.add(songsScrollPane);

        welcomePanel.add(scrollPanePanel);

        // Add the welcome label to the CENTER of the main panel
        add(welcomePanel, BorderLayout.CENTER);

    }
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFocusPainted(false);
        button.setBackground(new Color(240, 240, 240));
        button.setMaximumSize(new Dimension(150, 40));

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(button)) {
                    homeController.execute(text);
                }
            }
        });

        return button;
    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            welcome.setText("Welcome " + homeViewModel.getState().getDisplayName());
        }


    }
}
