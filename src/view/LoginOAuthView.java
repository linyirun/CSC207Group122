package view;

import entity.SpotifyAuth;
import interface_adapter.loginOAuth.LoginOAuthController;
import interface_adapter.loginOAuth.LoginOAuthState;
import interface_adapter.loginOAuth.LoginOAuthViewModel;

import use_case.loginOAuth.LoginOAuthInteractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.IOException;
import java.util.Scanner;

public class LoginOAuthView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "login OAuth";
    private URL url;
    private final LoginOAuthViewModel loginOAuthViewModel;

    final JTextField codeInputField = new JTextField(15);


    final JButton getPlaylist;

    JButton url_link;
    private final LoginOAuthController loginOAuthController;

    public LoginOAuthView(LoginOAuthViewModel loginOAuthViewModel, LoginOAuthController controller) {

        this.loginOAuthController = controller;
        this.loginOAuthViewModel = loginOAuthViewModel;
        this.loginOAuthViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Login OAuth");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel codeInfo = new LabelTextPanel(new JLabel("Code"), codeInputField);
        url_link = new JButton("Link Here");
        url_link.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttons = new JPanel();

        getPlaylist = new JButton("Get Playlist");
        getPlaylist.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttons.add(getPlaylist);

        // getPlaylist ActionListener
        getPlaylist.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(getPlaylist)) {
                            try {
                                // todo: get user id here
                                URL url = new URL("https://api.spotify.com/v1/users/smedjan/playlists");
                                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                                httpConn.setRequestMethod("GET");

                                httpConn.setRequestProperty("Authorization", "Bearer " + SpotifyAuth.getAccessToken());

                                int result = httpConn.getResponseCode();
                                if (result == 200) {
                                    InputStream responseStream  = httpConn.getInputStream();
                                    Scanner s = new Scanner(responseStream).useDelimiter("\\A");
                                    String response = s.hasNext() ? s.next() : "";
                                    System.out.println(response);
                                } else {
                                    System.out.println("Request failed.");
                                }



                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );


        url_link.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if (evt.getSource().equals(url_link)) {
                        System.out.println("here");
                        if (Desktop.isDesktopSupported()) {
                            try {
                                Desktop.getDesktop().browse(url.toURI());
                                controller.execute();
                            } catch (IOException | URISyntaxException e) {
                                System.out.println("Problem with URL");
                            }
                        } else {
                            JOptionPane.showMessageDialog(LoginOAuthView.this, "Automatic browser link opening not supported");
                        }
                    }
                }
            }
        );
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        codeInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        LoginOAuthState currentState = loginOAuthViewModel.getState();
                        currentState.setCode(codeInputField.getText() + e.getKeyChar());
                        loginOAuthViewModel.setState(currentState);
                        System.out.println(loginOAuthViewModel.getState().getCode());
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                });

        this.add(title);
        this.add(url_link);
        this.add(buttons);
        this.add(codeInfo);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginOAuthState state = (LoginOAuthState) evt.getNewValue();
        if (evt.getPropertyName().equals("state")) {
            url = state.getURL();
        }
        else if (evt.getPropertyName().equals("error")) {


            JOptionPane.showMessageDialog(LoginOAuthView.this, state.getOAuthError());
        }
    }

}
