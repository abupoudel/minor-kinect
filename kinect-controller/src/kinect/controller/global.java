/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinect.controller;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author fr3ak
 */
public class global {
    public static WebView wb = new WebView();
    public static WebEngine we = wb.getEngine();
    public static String[] allowedTypes = {".jpg",".mp3",".png",".gif",".jpeg"};
    public static Boolean showHiddenFileFolder = false;
    public static String hiddenFolder = "THISISNOTAFILENAMEPLEASEDONOTCREATETHISASFILENAME";
}
