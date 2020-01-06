package com.github.githubbers;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogOutput
{

    public static void save(String MatricNo, String RepoName, String Message)
    {

        String logFilePath = Directory.getLogFolderPath() + MatricNo + ".log";
        try
        {
            FileHandler fileHandler = new FileHandler(logFilePath, true);
            fileHandler.setFormatter(new SimpleFormatter());
            Logger logger = Logger.getLogger(RepoName);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
            logger.warning(Message);
            fileHandler.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}


