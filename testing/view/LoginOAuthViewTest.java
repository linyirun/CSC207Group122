package view;

import interface_adapter.loginOAuth.LoginOAuthController;
import interface_adapter.loginOAuth.LoginOAuthState;
import interface_adapter.loginOAuth.LoginOAuthViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.loginOAuth.LoginOAuthInputBoundary;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LoginOAuthViewTest {

    private LoginOAuthViewModelStub viewModelStub;
    private MockController mockController;
    private LoginOAuthView loginOAuthView;

    @BeforeEach
    void setUp() {
        viewModelStub = new LoginOAuthViewModelStub();
        mockController = new MockController();
        loginOAuthView = new LoginOAuthView(viewModelStub, mockController);
    }

//    @Test
//    void testButtonClickCallsController() {
//        // When
//        loginOAuthView.onButtonClick();
//
//        // Then
//        assertTrue(mockController.executeCalled);
//    }
    @Test
    void testErrorPropertyChange(){
        ActionEvent event = new ActionEvent(loginOAuthView, 20, "Test");
        loginOAuthView.actionPerformed(event);

    }

    private static class MockController extends LoginOAuthController {

        boolean executeCalled = false;
        String errorMessage;

        public MockController() {
            super(null);
        }

        @Override
        public void execute() {
            executeCalled = true;
        }
    }

    private static class LoginOAuthViewModelStub extends LoginOAuthViewModel {

        private final PropertyChangeSupport support = new PropertyChangeSupport(this);
        private String error;

        public void setError(String error) {
            this.error = error;
        }

        public void firePropertyChanged() {
            support.firePropertyChange("error", null, this.error);
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {
            support.addPropertyChangeListener(listener);
        }

    }
}
