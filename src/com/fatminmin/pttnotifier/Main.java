package com.fatminmin.pttnotifier;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * Created by fatminmin on 6/28/16.
 */

public class Main extends Application {

    public static Crawler crawler = null;
    public static Application app = null;
    public static Stage mStage;

    FXMLLoader fxmlLoader;
    UIController uiController;

    public static TrayNotification notification;


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

        primaryStage.setOnCloseRequest(e -> Platform.exit());

        notification = new TrayNotification();
        notification.setTitle("A new post is detected");
        notification.setNotificationType(NotificationType.SUCCESS);
        notification.setAnimationType(AnimationType.POPUP);
        notification.setOnDismiss(actionEvent -> {
            Main.moveToFront();
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}