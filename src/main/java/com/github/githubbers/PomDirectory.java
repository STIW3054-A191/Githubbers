package com.github.githubbers;
import java.io.File;

public class PomDirectory
{

    public static String getPath(File dir)
    {
        String path = null;
        if (dir.isDirectory())
        {
            String[] child1 = dir.list();
            if (child1 != null)
            {
                for (String child2 : child1)
                {
                    File mainChild = new File(dir, child2);
                    if (child2.equals("pom.xml"))
                    {
                        path = mainChild.getPath();
                        break;
                    }
                }

                if (path == null)
                {
                    for (String child2 : child1)
                    {
                        File mainChild = new File(dir, child2);
                        if (mainChild.isDirectory())
                        {
                            path = getPath(mainChild);
                            if (path != null)
                            {
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

