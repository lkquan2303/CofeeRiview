package com.example.coffereview.Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckAll {
    public boolean checkmail(String mail)
    {
        // return Patterns.EMAIL_ADDRESS.matcher(mail).matches();
        String emailPattern =  "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern regex = Pattern.compile(emailPattern);
        Matcher matcher = regex.matcher(mail);
        if(matcher.find())
        {
            return true;
        }else
        {
            return false;
        }
    }
    public static boolean isNum(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

