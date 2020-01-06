package com.baktajivan;
//directory part 2
import java.net.URL;
//class for directory
public class Directory {
//directory path where to store it
    public static String getOutputFolderPath(){
        URL location = Directory.class.getProtectionDomain().getCodeSource().getLocation();
        return location.getFile().substring(1).split("target")[0]+"target/output/";
    }
//creates repo file
    public static String getRepoFolderPath(){
        return getOutputFolderPath()+"repo/";
    }
//create log message file
    public static String getLogFolderPath(){
        return getOutputFolderPath()+"log/";
    }
//create output file
    public static String getOutFolderPath(){
        return getOutputFolderPath()+"out/";
    }
//create txt message file
    public static String getTxtFolderPath(){
        return getOutputFolderPath()+"txt/";
    }
}
