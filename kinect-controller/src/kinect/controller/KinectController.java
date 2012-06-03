/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinect.controller;

import com.sun.webpane.webkit.JSObject;
import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
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
        final WebEngine we = wb.getEngine();
        File file = new File("web/main.html");
        we.load(file.toURI().toString());
        we.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {

                    @Override
                    public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
                        if (newState == State.SUCCEEDED) {
                            JSObject jsobj = (JSObject) we.executeScript("window");
                            jsobj.setMember("java", new Bridge());
                        }
                    }
                });
        Scene scene = new Scene(wb, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.fullScreenProperty();
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    class Bridge {

        public void exit() {
            Platform.exit();
        }
    }
}
