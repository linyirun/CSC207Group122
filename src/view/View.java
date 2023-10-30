package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class View extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "sign up";

    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private final JButton signUp;
    private final JButton cancel;

    public View() {

//        signupViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Title");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        String test = "Test";

        LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel(test), usernameInputField);
        LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(test), passwordInputField);
        LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
                new JLabel(test), repeatPasswordInputField);

        JPanel buttons = new JPanel();
        signUp = new JButton("Sign Up");
        buttons.add(signUp);
        cancel = new JButton("Cancel");
        buttons.add(cancel);

//        clear = new JButton(SignupViewModel.CLEAR_BUTTON_LABEL);
//        buttons.add(clear);

        signUp.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        // Test
                    }
                }
        );

//        SignupView thisObject = this;
//        clear.addActionListener(
//                new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        clearController.execute();
//
//                        ArrayList<String> usernames = clearViewModel.getState().getNames();
//
//                        String names = String.join("\n", usernames);
//                        System.out.println(names);
//
//                        JOptionPane.showMessageDialog(thisObject, names);
//                    }
//                }
//        );

//        cancel.addActionListener(this);

        // This makes a new KeyListener implementing class, instantiates it, and
        // makes it listen to keystrokes in the usernameInputField.
        //
        // Notice how it has access to instance variables in the enclosing class!
//        usernameInputField.addKeyListener(
//                new KeyListener() {
//                    @Override
//                    public void keyTyped(KeyEvent e) {
//                        SignupState currentState = signupViewModel.getState();
//                        String text = usernameInputField.getText() + e.getKeyChar();
//                        currentState.setUsername(text);
//                        signupViewModel.setState(currentState);
//                    }
//
//                    @Override
//                    public void keyPressed(KeyEvent e) {
//                    }
//
//                    @Override
//                    public void keyReleased(KeyEvent e) {
//                    }
//                });

//        passwordInputField.addKeyListener(
//                new KeyListener() {
//                    @Override
//                    public void keyTyped(KeyEvent e) {
//                        SignupState currentState = signupViewModel.getState();
//                        currentState.setPassword(passwordInputField.getText() + e.getKeyChar());
//                        signupViewModel.setState(currentState);
//                    }
//
//                    @Override
//                    public void keyPressed(KeyEvent e) {
//
//                    }
//
//                    @Override
//                    public void keyReleased(KeyEvent e) {
//
//                    }
//                }
//        );

//        repeatPasswordInputField.addKeyListener(
//                new KeyListener() {
//                    @Override
//                    public void keyTyped(KeyEvent e) {
//                        SignupState currentState = signupViewModel.getState();
//                        currentState.setRepeatPassword(repeatPasswordInputField.getText() + e.getKeyChar());
//                        signupViewModel.setState(currentState); // Hmm, is this necessary?
//                    }
//
//                    @Override
//                    public void keyPressed(KeyEvent e) {
//
//                    }
//
//                    @Override
//                    public void keyReleased(KeyEvent e) {
//
//                    }
//                }
//        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernameInfo);
        this.add(passwordInfo);
        this.add(repeatPasswordInfo);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showConfirmDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        SignupState state = (SignupState) evt.getNewValue();
//        if (state.getUsernameError() != null) {
//            JOptionPane.showMessageDialog(this, state.getUsernameError());
//        }
    }
}