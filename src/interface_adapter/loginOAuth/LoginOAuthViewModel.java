package interface_adapter.loginOAuth;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginOAuthViewModel extends ViewModel {

    public static final String ENTER_CODE_LABEL = "Enter Authorization Code";

    private LoginOAuthState state = new LoginOAuthState();

    public LoginOAuthViewModel() {
        super("login OAuth");
    }

    public void setState(LoginOAuthState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Signup Presenter will call to let the ViewModel know
    // to alert the View
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }
    public void firePropertyChangedError() {support.firePropertyChange("error", null, this.state);}

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public LoginOAuthState getState() {
        return state;
    }

}

