package view;

import app.SpotifyAuth;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "log in";
    private final LoginViewModel loginViewModel;

    final JTextField tokenInputField = new JTextField(15);

    final JButton logInSpotify;
    final JButton loginToken
            ;
    private final LoginController loginController;

    public LoginView(LoginViewModel loginViewModel, LoginController controller) {

        this.loginController = controller;
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Login Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel tokenInfo = new LabelTextPanel(
                new JLabel("Token"), tokenInputField);

        JPanel buttons = new JPanel();
        logInSpotify = new JButton(loginViewModel.LOGIN_BUTTON_LABEL);
        buttons.add(logInSpotify);
        loginToken
                = new JButton(loginViewModel.TOKEN_BUTTON_LABEL);
        buttons.add(loginToken);

        logInSpotify.addActionListener(                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logInSpotify)) {
                            try {
                                SpotifyAuth.getAuthorization();
                            }
                            catch (IOException e) {
                                System.out.println("IOException");
                            }
                        }
                    }
                }
        );

        loginToken.addActionListener(this); // TODO

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        tokenInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        LoginState currentState = loginViewModel.getState();
                        currentState.setToken(tokenInputField.getText() + e.getKeyChar());
                        loginViewModel.setState(currentState);
                        System.out.println(loginViewModel.getState().getToken());
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                });

        this.add(title);
        this.add(buttons);
        this.add(tokenInfo);
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
