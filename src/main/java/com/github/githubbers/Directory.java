package com.github.githubbers;
import java.net.URL;
public class Directory
{
    public static String getOutputPathFile()
    {
        URL location = Directory.class.getProtectionDomain().getCodeSource().getLocation();
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

    public static String getTextPathFile()
    {
        return getOutputPathFile() + "txt/";
    }
}
