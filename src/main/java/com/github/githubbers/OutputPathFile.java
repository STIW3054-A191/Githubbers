package com.github.githubbers;
import java.net.URL;
public class OutputPathFile
{
    public static String getOutputPathFile()
    {
        URL location = OutputPathFile.class.getProtectionDomain().getCodeSource().getLocation();
        return location.getFile().substring(1).split("target")[0]+"target/output/";
    }

    public static String getRepoPathFile()
    {
        return getOutputPathFile() + "repo/";
    }

    public static String getLogPathFile()
    {
        return getOutputPathFile() + "log/";
    }

    public static String getOutPathFile()
    {
        return getOutputPathFile() + "out/";
    }

    public static String getTxtPathFile()
    {
        return getOutputPathFile() + "txt/";
    }
}
