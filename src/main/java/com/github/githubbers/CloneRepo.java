package com.github.githubbers;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import java.io.File;
import java.util.concurrent.CountDownLatch;

public class CloneRepo implements Runnable
{
    private String url;
    private int justRepo;
    private CountDownLatch latch;

    public CloneRepo(String Url, int JustRepo, CountDownLatch Latch)
    {
        this.url = Url;
        this.justRepo = JustRepo;
        this.latch = Latch;
    }

    @Override
    public void run()
    {
        try
        {
            Git.cloneRepository()
                    .setURI(url + ".git")
                    .setDirectory(new File(OutputPathFile.getRepoPathFile() + RepoDetails.getRepoName(url)))
                    .call();
        } catch (GitAPIException e)
        {
            e.printStackTrace();
        }

        OutputResult.print(false, RepoDetails.getRepoName(url), "Cloning successfully completed.", latch, justRepo);
    }
}