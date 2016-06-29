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

    FXMLLoader fxmlLoader;
    UIController uiController;

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
    }


    public static void main(String[] args) {
        launch(args);
    }
}