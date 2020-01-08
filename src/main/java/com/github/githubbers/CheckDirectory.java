package com.github.githubbers;
import java.io.File;
public class CheckDirectory
{

    public static void checkDir()
    {
        File repoFolder = new File(Directory.getRepoPathFile());
        if (repoFolder.exists())
        {
            deleteDirFile(repoFolder);
        }

        makeDirFile(repoFolder);
        makeDirFile(new File(Directory.getLogPathFile()));
        makeDirFile(new File(Directory.getOutPathFile()));
        makeDirFile(new File(Directory.getTextPathFile()));

    }

    private static void makeDirFile(File Directory)
    {
        if (!Directory.exists())
        {
            Directory.mkdirs();
        }
    }

    private static boolean deleteDirFile(File dir)
    {
        if (dir.isDirectory())
        {
            String[] child1 = dir.list();
            if (child1 != null)
            {
                for (String child2 : child1)
                {
                    boolean success = deleteDirFile(new File(dir, child2));
                    if (!success)
                    {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }
}

