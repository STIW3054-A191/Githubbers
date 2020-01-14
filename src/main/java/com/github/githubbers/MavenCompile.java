package com.github.githubbers;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import org.apache.maven.shared.invoker.*;

public class MavenCompile implements Runnable
{
     private String url;
    private int totalRepo;
    private CountDownLatch latch;
    private ArrayList<String[]> buildSuccessRepo;

    public MavenCompile(String RepoUrl, int TotalRepo, CountDownLatch Latch, ArrayList<String[]> BuildSuccessRepo)
    {
         this.url = RepoUrl;
        this.totalRepo = TotalRepo;
        this.latch = Latch;
        this.buildSuccessRepo = BuildSuccessRepo;
    }

    @Override
    public void run()
    {
        String repoPath = OutputPathFile.getRepoPathFile() + RepoDetails.getRepoName(url);
       String repoName = RepoDetails.getRepoName(url);

        String pomPath = PomDirectory.getPath(new File(repoPath));
        if (pomPath != null) {

            InvocationRequest request = new DefaultInvocationRequest();
            request.setPomFile(new File(pomPath));
            request.setGoals(Collections.singletonList("clean install"));

            try {

                Invoker invoker = new DefaultInvoker();
                invoker.setLogger(new PrintStreamLogger(System.out, InvokerLogger.ERROR));

                final StringBuilder output = new StringBuilder();
                output.append("\n");

                invoker.setOutputHandler(s -> output.append(s).append("\n"));

                invoker.setErrorHandler(s -> output.append(s).append("\n"));

                final InvocationResult invocationResult = invoker.execute(request);

                if (invocationResult.getExitCode() == 0) {
                    buildSuccessRepo.add(new String[]{pomPath, repoName, RepoDetails.getMatric(url)});
                    OutputResult.print(false, repoName, "Build Success !", latch, totalRepo);
                } else {
                    //Save error to log
                    LogOutput.saveFiles(RepoDetails.getMatric(url), repoName, output.toString());
                    OutputResult.print(true, repoName, "Build Failure !", latch, totalRepo);
                }

            } catch (MavenInvocationException e) {
                e.printStackTrace();
            }

        } else {
            //Save error to log
            LogOutput.saveFiles(RepoDetails.getMatric(url), repoName, repoName + " No pom.xml file !");
            OutputResult.print(true, repoName, "No pom.xml file !", latch, totalRepo);
        }
    }
}