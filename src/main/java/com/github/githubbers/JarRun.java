package com.github.githubbers;
import java.io.*;
import java.util.concurrent.*;

public class JarRun implements Runnable
{

    private String pathPom, reName, matricNum;
    private CountDownLatch latch;
    private int latchAll;

    public JarRun(String PomDirectory, String RepoName, String MatricNum, CountDownLatch Latch, int LatchAll)
    {
        this.pathPom = PomDirectory;
        this.reName = RepoName;
        this.matricNum = MatricNum;
        this.latch = Latch;
        this.latchAll = LatchAll;
    }

    @Override
    public void run()
    {

        try
        {
            Runtime rTime = Runtime.getRuntime();
            Process process = rTime.exec("java -jar " + JarDirectory.getPath(pathPom), null, new File(OutputPathFile.getRepoPathFile() + reName));

            FutureTask<String> futureTask = new FutureTask<>(new JarCallable(matricNum, reName, process.getInputStream(), process.getErrorStream()));
            new Thread(futureTask).start();

            try
            {
                String result = futureTask.get(1, TimeUnit.MINUTES);
                if (result.equals("complete"))
                {
                    OutputResult.print(false, reName, "Jar file successfully completed.", latch, latchAll);
                } else if (result.equals("error"))
                {
                    OutputResult.print(true, reName, "Jar file failed to run.", latch, latchAll);
                }
            } catch (TimeoutException e)
            {
                process.destroy();
                OutputResult.print(true, reName, "Timeout.", latch, latchAll);
            }

        } catch (IOException | InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
    }
}

