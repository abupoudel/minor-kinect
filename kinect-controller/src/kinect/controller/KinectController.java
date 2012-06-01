/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinect.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author navin
 */
public class KinectController extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        WebView wb = new WebView();
        WebEngine we = wb.getEngine();
        we.load("http://localhost/");
        primaryStage.setScene(new Scene(wb, 300, 250));
        primaryStage.show();
    }
}
