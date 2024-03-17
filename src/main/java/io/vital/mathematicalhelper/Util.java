package io.vital.mathematicalhelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static final String realNumberRegex = "-?[0-9]+\\.?[0-9]*";

    private static final Pattern numberPattern = Pattern.compile(realNumberRegex);
    private static final Pattern expressionPattern = Pattern.compile("\\w{2,}");

    public static boolean isStringNumber(String variable) {
        Matcher numberMatcher = numberPattern.matcher(variable);
        return numberMatcher.matches();
    }

    public static boolean isExpressionBadlyFormatted(String expression){
        return expressionPattern.matcher(expression).find();
    }
}
