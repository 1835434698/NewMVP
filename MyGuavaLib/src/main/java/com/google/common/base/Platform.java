package com.google.common.base;


public class Platform {

    static boolean stringIsNullOrEmpty( String string) {
        return string == null || string.isEmpty();
    }

    static String nullToEmpty( String string) {
        return (string == null) ? "" : string;
    }
}

