package com.xcompany.jhonline.utils;


public class NullCheck {

    public static String check(String str) {
        if (str == null) return "";
        return str;
    }

    public static String check(String prefix, String str) {
        if (str == null) return prefix;
        return prefix + str;
    }
}
