package com.example.constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Regex {

    public static final String CREDITOR = "creditor";
    public static final String DEBTOR = "debtor";


    public static boolean isCreditor(String input) {
        boolean result = false;
        Pattern pattern = Pattern.compile(CREDITOR, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        result = matcher.matches();
        return result;

    }

    public static boolean isDebtor(String input) {
        boolean result = false;
        Pattern pattern = Pattern.compile(DEBTOR, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        result = matcher.matches();
        return result;

    }

    public static boolean isClient(String input) {
        return isCreditor(input) || isDebtor(input);
    }



}
