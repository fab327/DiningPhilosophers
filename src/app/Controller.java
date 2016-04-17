package app;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class Controller {

    public Label runningStateLabel;

    public void startApplication(ActionEvent actionEvent) {
        runningStateLabel.setText("Application started");
    }

}
