package com.github.githubbers;
public class RepoDetails
{
    public static String getName(String url)
    {
        String[] splitUrl = url.split("/");
        return splitUrl[splitUrl.length - 1];
    }

    public static String getMatricNo(String url)
    {
        String repoName = getName(url);
        String[] splitName = repoName.split("-");
        return splitName[0];
    }
}
