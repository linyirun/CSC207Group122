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

    public final String viewName = "login OAuth";
    private final LoginOAuthViewModel loginOAuthViewModel;

    private JButton url_link;

    public LoginOAuthView(LoginOAuthViewModel loginOAuthViewModel, LoginOAuthController controller) {

        this.loginOAuthViewModel = loginOAuthViewModel;
        this.loginOAuthViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Spotify Login");
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(title);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        url_link = new JButton("Login to Spotify");
        url_link.setAlignmentX(Component.CENTER_ALIGNMENT);
        url_link.setFocusPainted(false);
        url_link.setBackground(new Color(29, 185, 84));
        url_link.setForeground(Color.BLACK); // Set text color to black
        url_link.setCursor(new Cursor(Cursor.HAND_CURSOR));

        url_link.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(url_link)) {
                    controller.execute();
                }
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(url_link);
        contentPanel.add(buttonPanel);

        add(contentPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(400, 200));
    }
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginOAuthState state = (LoginOAuthState) evt.getNewValue();
        if (evt.getPropertyName().equals("error")) {
            JOptionPane.showMessageDialog(LoginOAuthView.this, state.getOAuthError());
        }
    }
}
