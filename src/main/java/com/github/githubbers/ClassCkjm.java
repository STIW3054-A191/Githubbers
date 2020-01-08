package com.github.githubbers;

import java.util.ArrayList;
import java.io.File;

public class ClassCkjm {
    public static ArrayList<String> getPath(String ReName) {
        return findClass(new File(Directory.getRepoPathFile() + ReName));
    }

    public static ArrayList<String> findClass(File dir) {
        ArrayList<String> path = new ArrayList<>();

        if (dir.isDirectory()) {
            String[] child1 = dir.list();
            if (child1 != null) {
                for (String child2 : child1) {
                    File mainChild = new File(dir, child2);
                    if (child2.endsWith(".class")) {
                        path.add(mainChild.getPath());
                    } else if (mainChild.isDirectory()) {
                        path.addAll(findClass(mainChild));
                    }
                }
            }
        }
        return path;
    }
}
