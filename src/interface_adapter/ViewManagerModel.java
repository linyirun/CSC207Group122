package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewManagerModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String activeViewName;

    public void setActiveView(String activeView) {
        this.activeViewName = activeView;
    }

    public String getActiveViewName() {
        return activeViewName;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("view", null, this.activeViewName);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }


}
