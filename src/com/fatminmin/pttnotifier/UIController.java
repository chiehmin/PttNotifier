package com.fatminmin.pttnotifier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Created by fatminmin on 6/28/16.
 */
public class UIController {
    @FXML private TextField tfKeyword;
    @FXML private Button btnControl;
    @FXML private TextArea taLog;
    @FXML private Text txKeywords;
    @FXML private Button btnAddKeyword;
    @FXML private VBox vbResult;

    @FXML protected void handleControl(ActionEvent event) {
        if(!Main.crawler.isRunning()) {
            if(Main.crawler.start()) {
                btnControl.setText("Stop");
            }
        } else {
            Main.crawler.stop();
            btnControl.setText("Start");
        }
    }

    @FXML protected void handleAddKeyword(ActionEvent event) {
        String text = txKeywords.getText();
        String keyword = tfKeyword.getText();
        if(keyword.trim().length() == 0) return;

        Main.crawler.addKeyword(keyword);

        txKeywords.setText(text + keyword + " ");
        tfKeyword.clear();
    }

    public void log(String msg) {
        while(taLog.getText().length() > 1000) {
            String curText = taLog.getText();
            taLog.setText(curText.substring(curText.indexOf("\n") + 1));
        }
        taLog.appendText(msg + "\n");
        taLog.setScrollTop(Double.MAX_VALUE);
    }

    public void notifyPostAppeared(String title, String url) {

        Hyperlink link = new Hyperlink(title);
        link.setOnAction(t -> {
            Main.app.getHostServices().showDocument(url);
        });
        vbResult.getChildren().add(link);
    }
}
