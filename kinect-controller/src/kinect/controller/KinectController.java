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
        File file = new File("web/main.html");
        global.we.load(file.toURI().toString());
        global.we.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {

                    @Override
                    public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
                        if (newState == State.SUCCEEDED) {
                            JSObject jsobj = (JSObject) global.we.executeScript("window");
                            Bridge myB = new Bridge();
                            jsobj.setMember("java", myB);
                        }
                    }
                });
        Scene scene = new Scene(global.wb, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.fullScreenProperty();
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}

class Bridge {

    public void exit() {
        Platform.exit();
    }

    public void listFolder(String path) {
        System.out.println(path);
        File folder = new File(path);
        String[] fileList = folder.list();
        //System.out.println(folder.getParent()); < Gives the parent directory else null
        String jsonFolder = "{";
        for(int i=0; i<fileList.length; i++){
            if(i == fileList.length - 1){
                jsonFolder += "\""+i +"\""+ ":" + "\""+fileList[i].toString()+"\"";
            } else {
                jsonFolder += "\""+i +"\""+ ":" + "\""+fileList[i].toString()+"\", ";
            }
        }
        jsonFolder += "}";
        System.out.println(jsonFolder);
        global.we.executeScript("fileList('" + jsonFolder + "');");
    }
}
