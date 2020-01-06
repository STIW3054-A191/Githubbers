package com.baktajivan;
import java.io.*;
import java.util.concurrent.*;
//jar part 4
public class JarRun implements Runnable {

    private String pomPath, repoName, matricNo;
    private CountDownLatch latch;
    private int totalLatch;

    public JarRun(String PomPath, String RepoName, String MatricNo, CountDownLatch Latch, int TotalLatch) {
        this.pomPath = PomPath;
        this.repoName = RepoName;
        this.matricNo = MatricNo;
        this.latch = Latch;
        this.totalLatch = TotalLatch;
    }

    @Override
    public void run() {

        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("java -jar " + JarDirectory.getPath(pomPath), null, new File(Directory.getRepoFolderPath() + repoName));

            FutureTask<String> futureTask = new FutureTask<>(new JarCallable(matricNo, repoName, proc.getInputStream(), proc.getErrorStream()));
            new Thread(futureTask).start();

            try {
                String result = futureTask.get(1, TimeUnit.MINUTES);
                if (result.equals("complete")) {
                    OutputResult.print(false, repoName, "Run Jar Completed !", latch, totalLatch);
                } else if (result.equals("error")) {
                    OutputResult.print(true, repoName, "Jar Error !", latch, totalLatch);
                }
            } catch (TimeoutException e) {
                proc.destroy();
                OutputResult.print(true, repoName, "Over waiting time !", latch, totalLatch);
            }

        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}

