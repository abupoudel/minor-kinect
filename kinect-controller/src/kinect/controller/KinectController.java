/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinect.controller;

import com.sun.webpane.webkit.JSObject;
import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
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
        File file = new File("web/main.html");
        System.out.println(file.toURI());
        we.load(file.toURI().toString());
        JSObject win = (JSObject) we.executeScript("window");
        win.setMember("app", new JavaApp());
        Scene scene = new Scene(wb, 300, 250);
        //scene.getStylesheets().add("web/css/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class JavaApp {

        public void exit() {
            //Platform.exit();
            System.out.println("Cool");
        }
    }
}
