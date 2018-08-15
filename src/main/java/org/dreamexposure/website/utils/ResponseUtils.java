package org.dreamexposure.website.utils;

public class ResponseUtils {
    public static String getJsonResponseMessage(String msg) {
        return "{\"message\": \"" + msg + "\"}";
    }
}