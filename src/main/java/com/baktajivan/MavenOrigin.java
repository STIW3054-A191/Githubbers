package com.baktajivan;
//maven part 1
import java.io.File;
//check if maven exist
public class MavenOrigin{

    public static void setHome() {
        if (System.getProperty("maven.home") == null) {
            System.setProperty("maven.home", getPath());
        }
    }

    private static String getPath() {

        if (System.getenv("M2_HOME") != null) {
            return System.getenv("M2_HOME") + "/bin/mvn";
        }

        for (String dirname : System.getenv("PATH").split(File.pathSeparator)) {
            File file = new File(dirname, "mvn");
            if (file.isFile() && file.canExecute()) {
                return file.getAbsolutePath();
            }
        }
        throw new RuntimeException("No mvn found, please install mvn by 'conda install maven' or setup M2_HOME");
    }
}


