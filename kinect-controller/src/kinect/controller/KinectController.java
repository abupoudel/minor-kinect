/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinect.controller;

import com.sun.webpane.webkit.JSObject;
import java.io.File;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
        
        EventDispatcher originalDispatcher = primaryStage.getEventDispatcher();
        primaryStage.setEventDispatcher((new MyEventDispatcher(originalDispatcher)));
        primaryStage.setScene(scene);
        primaryStage.fullScreenProperty();
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}

class MyEventDispatcher implements EventDispatcher {

    private EventDispatcher originalDispatcher;

    public MyEventDispatcher(EventDispatcher originalDispatcher) {
        this.originalDispatcher = originalDispatcher;
    }

    @Override
    public Event dispatchEvent(Event event, EventDispatchChain tail) {
        if (event instanceof MouseEvent) {
            MouseEvent mouseEvent = (MouseEvent) event;
            if (MouseButton.SECONDARY == mouseEvent.getButton()) {
                mouseEvent.consume();
            }
        }
        return originalDispatcher.dispatchEvent(event, tail);
    }
}
