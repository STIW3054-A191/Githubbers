package com.baktajivan;
//clone part 2 [link details split]
public class RepoDetails {
    //getting information from the github link
    //split the unnecessary info just getting the matric no
    public static String getName(String url) {
        String[] splitUrl = url.split("/");
        return splitUrl[splitUrl.length - 1];
    }

    public static String getMatricNo(String url) {
        String repoName = getName(url);
        String[] splitName = repoName.split("-");
        return splitName[0];
    }

}
