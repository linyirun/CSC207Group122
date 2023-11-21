package view;

import interface_adapter.artists_playlist_maker.ArtistsPmController;
import interface_adapter.artists_playlist_maker.ArtistsPmViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.playlists.PlaylistsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ArtistsPlaylistMakerView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "Artists Playlist Maker";
    private final ArtistsPmViewModel artistsPmViewModel;

    final JButton enterButton;

    private final ArtistsPmController artistsPmController;

    public ArtistsPlaylistMakerView(ArtistsPmViewModel artistsPmViewModel, ArtistsPmController controller) {

        this.artistsPmController = controller;
        this.artistsPmViewModel = artistsPmViewModel;
        this.artistsPmViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Artists Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttons = new JPanel();
        enterButton = new JButton(ArtistsPmViewModel.ARTISTS_BUTTON_LABEL);
        buttons.add(enterButton);



        enterButton.addActionListener(                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(enterButton)) {
                            artistsPmController.execute();
                        }
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();
    }

}
