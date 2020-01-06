package com.baktajivan;
//maven part 2
import java.io.File;

public class PomDirectory{

    public static String getPath(File dir) {
        String path = null;
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    File aChild = new File(dir, child);
                    if (child.equals("pom.xml")) {
                        path = aChild.getPath();
                        break;
                    }
                }

                if (path == null) {
                    for (String child : children) {
                        File aChild = new File(dir, child);
                        if (aChild.isDirectory()) {
                            path = getPath(aChild);
                            if (path != null) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return path;
    }
}

