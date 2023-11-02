package view;

import interface_adapter.loginOAuth.LoginOAuthController;
import interface_adapter.loginOAuth.LoginOAuthState;
import interface_adapter.loginOAuth.LoginOAuthViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.IOException;

public class LoginOAuthView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "login OAuth";
    private URL url;
    private final LoginOAuthViewModel loginOAuthViewModel;

    final JTextField codeInputField = new JTextField(15);

    final JButton enterToken;

    JButton url_link;
    private final LoginOAuthController loginOAuthController;

    public LoginOAuthView(LoginOAuthViewModel loginOAuthViewModel, LoginOAuthController controller) {

        this.loginOAuthController = controller;
        this.loginOAuthViewModel = loginOAuthViewModel;
        this.loginOAuthViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Login OAuth");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel codeInfo = new LabelTextPanel(
                new JLabel("Code"), codeInputField);
        url_link = new JButton("Link Here");

        JPanel buttons = new JPanel();
        enterToken = new JButton(loginOAuthViewModel.ENTER_CODE_LABEL);
        buttons.add(enterToken);

        enterToken.addActionListener(                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(enterToken)) {
                            loginOAuthController.execute();
                        }
                    }
                }
        );
        url_link.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if (evt.getSource().equals(url_link)) {
                        if (Desktop.isDesktopSupported()) {
                            try {
                                Desktop.getDesktop().browse(url.toURI());
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
        url = state.getURL();
    }

}
