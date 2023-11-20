package view;

import entity.SpotifyAuth;
import interface_adapter.ViewManagerModel;
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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

public class LoginOAuthView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "login OAuth"; //random line
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

        // We will need to delete this
        getPlaylist = new JButton("Get Playlist");
        getPlaylist.setAlignmentX(Component.LEFT_ALIGNMENT);
//        buttons.add(getPlaylist);


        // getPlaylist ActionListener
//        getPlaylist.addActionListener(
//                new ActionListener() {//add a comment
//                    public void actionPerformed(ActionEvent evt) {//add another comment
//                        if (evt.getSource().equals(getPlaylist)) {
//                            try {
//                                // Use your pre-existing access token
//                                String accessToken = SpotifyAuth.getAccessToken();
//
//                                // Use the access token to make a request to get the user's playlists
//                                String endpoint = "https://api.spotify.com/v1/me/playlists";
//
//                                URL playlistsUrl = new URL(endpoint);
//                                HttpURLConnection playlistsConnection = (HttpURLConnection) playlistsUrl.openConnection();
//                                playlistsConnection.setRequestMethod("GET");
//                                playlistsConnection.setRequestProperty("Authorization", "Bearer " + accessToken);
//                                int playlistsResponseCode = playlistsConnection.getResponseCode();
//
//                                if (playlistsResponseCode == 200) {
//                                    InputStream inputStream = playlistsConnection.getInputStream();
//                                    JSONParser jsonParser = new JSONParser();
//                                    JSONObject jsonObject = (JSONObject)jsonParser.parse(
//                                            new InputStreamReader(inputStream, "UTF-8"));
//
//                                    JSONArray playlists = (JSONArray) jsonObject.get("items");
//                                    for (Object playlist : playlists) {
//                                        JSONObject playlistObj = (JSONObject) playlist;
//                                        String playlistName = (String) playlistObj.get("name");
//                                        System.out.println(playlistName); // Prints the name of each playlist
//                                        String playlistitems = (String) playlistObj.get("items");
//                                        System.out.println(playlistitems);
//                                    }
//
//                                    playlistsConnection.disconnect();
//                                } else {
//                                    System.err.println("Error: Unable to retrieve playlists. Response code: " + playlistsResponseCode);
//                                }
//                            } catch (Exception e) {
//                                System.err.println("Error: " + e.getMessage());
//                            }
//
//                        }
//                    }
//                }
//        );


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
