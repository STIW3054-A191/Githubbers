package com.github.githubbers;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogOutput
{

    public static void saveFile(String MatricNum, String ReName, String MessageDetail)
    {

        String logFilePath = Directory.getLogPathFile() + MatricNum + ".log";
        try
        {
            FileHandler fileHandler = new FileHandler(logFilePath, true);
            fileHandler.setFormatter(new SimpleFormatter());
            Logger logger = Logger.getLogger(ReName);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
            logger.warning(MessageDetail);
            fileHandler.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}


