package com.github.githubbers;
public class RepoDetails
{
    public static String getRepoName(String url)
    {
        String[] splitUrl = url.split("/");
        return splitUrl[splitUrl.length - 1];
    }

    public static String getMatric(String url)
    {
        String repoName = getRepoName(url);
        String[] splitRepoName = repoName.split("-");
        return splitRepoName[0];
    }
}
