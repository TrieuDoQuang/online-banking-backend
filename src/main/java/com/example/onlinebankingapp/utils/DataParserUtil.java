package com.example.onlinebankingapp.utils;

import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataParserUtil {
    public static boolean isValidPhonenumber(String phonenumber){
        //Check if phone number is 9 or 10 digits long
        Pattern pattern = Pattern.compile("^\\d{10}$|^\\d{9}$");
        Matcher matcher = pattern.matcher(phonenumber);
        return matcher.matches();
    }
}
