package com.baktajivan;
//directory part 1
import java.io.File;
//check directory is exist or not
public class CheckDirectory {

    public static void checkDir() {
        File repoFolder = new File(Directory.getRepoFolderPath());
        if (repoFolder.exists()) {
            deleteDir(repoFolder);
        }

        makeDir(repoFolder);
        makeDir(new File(Directory.getLogFolderPath()));
        makeDir(new File(Directory.getOutFolderPath()));
        makeDir(new File(Directory.getTxtFolderPath()));

    }

    private static void makeDir(File Directory) {
        if (!Directory.exists()) {
            Directory.mkdirs();
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // Delete all child
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDir(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        // Delete directory and return result
        return dir.delete();
    }
}

