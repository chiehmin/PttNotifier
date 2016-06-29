package com.fatminmin.pttnotifier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Created by fatminmin on 6/28/16.
 */
public class UIController {
    @FXML private TextField tfKeyword;
    @FXML private Button btnControl;
    @FXML private TextArea taLog;
    @FXML protected void handleControl(ActionEvent event) {
        if(!Main.crawler.isRunning()) {
            Main.crawler.setKeyword(tfKeyword.getText());
            if(Main.crawler.start()) {
                btnControl.setText("Stop");
            }
        } else {
            Main.crawler.stop();
            btnControl.setText("Start");
        }
    }

    public void notifyPostAppeared(String title, String url) {
        taLog.appendText(title + " " + url + "\n");

    }
}
