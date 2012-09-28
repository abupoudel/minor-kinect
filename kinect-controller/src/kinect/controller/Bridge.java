/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinect.controller;

import java.io.File;
import javafx.application.Platform;

/**
 *
 * @author fr3ak
 */
public class Bridge {

    public void exit() {
        Platform.exit();
    }

    public void debug(String msg) {
        System.out.println(msg);
    }

    public void fileHandle(String filePath) {
        String fileExt = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
        System.out.println(fileExt);
        String fileOperation = "file://" + filePath;
        String output;
        switch (fileExt) {
            case "mp3":
                //output = "<embed height='100' width='100' src='"+fileOperation+"'>";
                output = "<audio autoplay='autoplay'><source src='"+fileOperation+"' type='audio/mpeg' />Your browser does not support the audio element.</audio>";
                break;
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
                output = "<img src='"+fileOperation+"'>";
                break;
            default:
                output = "Error specifying the file.";
        }
        global.we.executeScript("setContent(\""+output+"\");");
    }

    public void listFolder(String path) {
        File folder = new File(path);
        String[] fileList = folder.list();
        String parentDir = folder.getParent();
        String currentDir = folder.getPath();

        Boolean first = true;
        if (global.showHiddenFileFolder == false) {
            global.hiddenFolder = ".";
        }
        String detailDir = "{\"currentDir\":\"" + currentDir + "\", \"parentDir\":\"" + parentDir + "\"}";
        String jsonFolder = "{";
        System.out.println(folder.getPath());
        for (int i = 0; i < fileList.length; i++) {
            String fileType = "";
            String fileName = fileList[i];
            if (fileName.startsWith(global.hiddenFolder)) {
                continue;
            }
            if (new File(currentDir + "/" + fileName).isDirectory()) {
                fileType = "[DIR] ";
            } else {
                int j;
                for (j = 0; j < global.allowedTypes.length; j++) {
                    if (fileName.toLowerCase().endsWith(global.allowedTypes[j])) {
                        break;
                    }
                }
                if (j == global.allowedTypes.length) {
                    continue;
                }
            }
            if (first) {
                jsonFolder += "\"" + i + "\"" + ":" + "\"" + fileType + fileName + "\"";
                first = false;
            } else {
                jsonFolder += ", \"" + i + "\"" + ":" + "\"" + fileType + fileName + "\"";
            }
        }
        jsonFolder += "}";
        global.we.executeScript("fileList('" + detailDir + "', '" + jsonFolder + "');");
    }
}
