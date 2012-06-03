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
    
    public void debug(String msg) {
        System.out.println(msg);
    }
    
    public void fileHandle(){
        System.out.println("Perform certain file action");
    }

    public void listFolder(String path) {
        File folder = new File(path);
        String[] fileList = folder.list();
        String parentDir = folder.getParent();
        String currentDir = folder.getPath();
        String hiddenFolder = "THISISNOTAFILENAMEPLEASEDONOTCREATETHISASFILENAME";
        Boolean first = true;
        if(global.showHiddenFileFolder == false) hiddenFolder = ".";
        String detailDir = "{\"currentDir\":\""+currentDir+"\", \"parentDir\":\""+parentDir+"\"}";
        String jsonFolder = "{";
        for(int i=0; i<fileList.length; i++){
            String fileType = "";
            String fileName = fileList[i];
            if(fileName.startsWith(hiddenFolder)) continue;
            if(new File(currentDir+"/"+fileName).isDirectory()){
                fileType = "[DIR] ";
            } else {
                int j;
                for(j=0; j<global.allowedTypes.length; j++){
                    if(fileName.endsWith(global.allowedTypes[j])) break;
                }
                if(j == global.allowedTypes.length) continue;
            }
            if(first){
                jsonFolder += "\""+i +"\""+ ":" + "\""+fileType+fileName+"\"";
                first = false;
            } else {
                jsonFolder += ", \""+i +"\""+ ":" + "\""+fileType+fileName+"\"";
            }
        }
        jsonFolder += "}";
        global.we.executeScript("fileList('" + detailDir + "', '" + jsonFolder + "');");
    }
}
