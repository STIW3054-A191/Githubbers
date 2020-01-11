package com.github.githubbers;
import java.io.File;
public class JarDirectory
{
    public static String getPath(String pathPom)
    {

        String pathFile = null;
        File dir = new File(pathPom.replaceAll("pom.xml", "target"));
        String[] child1 = dir.list();

        if (child1 != null)
        {
            for (String child2 : child1)
            {
                File mainChild = new File(dir, child2);
                if (!child2.startsWith("original") && child2.endsWith(".jar"))
                {
                    pathFile = mainChild.getPath();
                }
            }
        }
        return pathFile;
    }
}
