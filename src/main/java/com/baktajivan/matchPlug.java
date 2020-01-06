package com.baktajivan;
//clone part 1.5 [helps in split link]
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class matchPlug {

    boolean isMatch(String pattern, String link) {

        Pattern justPat = Pattern.compile(pattern);
        Matcher justMatch = justPat.matcher(link);
        return justMatch.find();
    }

}
