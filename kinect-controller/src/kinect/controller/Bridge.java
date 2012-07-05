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
    
    public void fileHandle(String filePath){
        System.out.println("Perform certain file action");
    }

    public void listFolder(String path) {
        File folder = new File(path);
        String[] fileList = folder.list();
        String parentDir = folder.getParent();
        String currentDir = folder.getPath();
        
        Boolean first = true;
        if(global.showHiddenFileFolder == false) global.hiddenFolder = ".";
        String detailDir = "{\"currentDir\":\""+currentDir+"\", \"parentDir\":\""+parentDir+"\"}";
        String jsonFolder = "{";
        for(int i=0; i<fileList.length; i++){
            String fileType = "";
            String fileName = fileList[i];
            if(fileName.startsWith(global.hiddenFolder)) continue;
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
