package com.github.githubbers;
import java.io.File;
public class CheckDirectory
{

    public static void checkDir()
    {
        File repoFolder = new File(Directory.getRepoFolderPath());
        if (repoFolder.exists())
        {
            deleteDir(repoFolder);
        }

        makeDir(repoFolder);
        makeDir(new File(Directory.getLogFolderPath()));
        makeDir(new File(Directory.getOutFolderPath()));
        makeDir(new File(Directory.getTxtFolderPath()));

    }

    private static void makeDir(File Directory)
    {
        if (!Directory.exists())
        {
            Directory.mkdirs();
        }
    }

    private static boolean deleteDir(File dir)
    {
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            if (children != null)
            {
                for (String child : children)
                {
                    boolean success = deleteDir(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }
}

