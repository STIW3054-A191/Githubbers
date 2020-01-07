package com.github.githubbers;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import java.io.File;
import java.util.concurrent.CountDownLatch;

public class CloneRepo implements Runnable
{

    private String url;
    private int repoTotal;
    private CountDownLatch latch;

    public CloneRepo(String Url, int RepoTotal, CountDownLatch Latch)
    {
        this.url = Url;
        this.repoTotal = RepoTotal;
        this.latch = Latch;
    }

    @Override
    public void run()
    {

        try
        {
            Git.cloneRepository()
                    .setURI(url + ".git")
                    .setDirectory(new File(Directory.getOutputPathFile() + RepoDetails.getName(url)))
                    .call();
        } catch (GitAPIException e)
        {
            e.printStackTrace();
        }

        OutputResult.print(false, RepoDetails.getName(url), "Cloning successfully completed.", latch, repoTotal);
    }
}