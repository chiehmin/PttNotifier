package com.fatminmin.pttnotifier;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by fatminmin on 6/28/16.
 */

public class Main extends Application {

    public static Crawler crawler = null;
    public static Application app = null;
    public static Stage mStage;

    FXMLLoader fxmlLoader;
    UIController uiController;


    static public void moveToFront() {
        if(mStage != null) {
            mStage.setAlwaysOnTop(true);
            mStage.toFront();
            mStage.setAlwaysOnTop(false);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 700, 300));
        primaryStage.show();

        uiController = fxmlLoader.getController();
        crawler = new Crawler(uiController);
        app = this;
        mStage = primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}