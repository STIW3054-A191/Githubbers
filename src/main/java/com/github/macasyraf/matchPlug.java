package com.github.macasyraf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class matchPlug {

    boolean isMatch(String pattern, String link) {

        Pattern justPat = Pattern.compile(pattern);
        Matcher justMatch = justPat.matcher(link);
        return justMatch.find();
    }

}
