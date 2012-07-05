/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * This may be used later so I just have created this thing :)
 */
package kinect.controller;


/**
 *
 * @author fr3ak
 */
public class json {

    public String encode(String[] list) {
        if (list.length == 0) {
            return "{}";
        } else {
            String jsonFolder = "{";
            Boolean first = true;
            for (int i = 0; i < list.length; i++) {
                if (first) {
                    jsonFolder += "\"" + i + "\"" + ":" + "\"" + list[i] + "\"";
                    first = false;
                } else {
                    jsonFolder += ", \"" + i + "\"" + ":" + "\"" + list[i] + "\"";
                }
            }
            jsonFolder += "}";
            return jsonFolder;
        }
    }
}
