package com.sun.kh_android_mvvm_databinding.utils;

public class StringUtils {
    public static String append(String... strings) {
        StringBuilder builder = new StringBuilder();
        for (String string: strings)
            builder.append(string);
        return builder.toString();
    }
}
