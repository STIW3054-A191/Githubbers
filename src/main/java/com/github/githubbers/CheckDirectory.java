package com.github.githubbers;
import java.io.File;
public class CheckDirectory
{

    public static void checkDir()
    {
        File repoFolder = new File(OutputPathFile.getRepoPathFile());
        if (repoFolder.exists())
        {
            deleteDir(repoFolder);
        }

        makeDirFile(repoFolder);
        makeDirFile(new File(OutputPathFile.getLogPathFile()));
        makeDirFile(new File(OutputPathFile.getOutPathFile()));
        makeDirFile(new File(OutputPathFile.getTxtPathFile()));

    }

    private static void makeDirFile(File Directory)
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
            String[] child1 = dir.list();
            if (child1 != null)
            {
                for (String child2 : child1)
                {
                    boolean success = deleteDir(new File(dir, child2));
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

