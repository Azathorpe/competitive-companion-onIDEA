package com.azathorpe.cci.utils;

import java.util.HashMap;

/**
 * @author Azathorpe
 * @version 1.0
 */
public class InfosUtils {
    public static HashMap<String, String> env = new HashMap<>(System.getenv());

    public static final String PATTERN_PACKAGE = "PACKAGE";
    public static final String PATTERN_CLASS = "CLASS";
    public static final String PATTERN_METHOD = "METHOD";
    public static final String PATTERN_FILE_NAME = "FILE_NAME";
    public static final String PATTERN_FILE_PATH = "FILE_PATH";
    public static final String PATTERN_PROJECT_NAME = "PROJECT_NAME";
    public static final String PATTERN_PROJECT_PATH = "PROJECT_PATH";

    public static final String PATTERN_NOT_DEFINED = "NOT_DEFINED";

    static {
        env.putIfAbsent(PATTERN_PACKAGE, PATTERN_NOT_DEFINED);
        env.putIfAbsent(PATTERN_CLASS, PATTERN_NOT_DEFINED);
        env.putIfAbsent(PATTERN_METHOD, PATTERN_NOT_DEFINED);
        env.putIfAbsent(PATTERN_FILE_NAME, PATTERN_NOT_DEFINED);
        env.putIfAbsent(PATTERN_FILE_PATH, PATTERN_NOT_DEFINED);
        env.putIfAbsent(PATTERN_PROJECT_NAME, PATTERN_NOT_DEFINED);
        env.putIfAbsent(PATTERN_PROJECT_PATH, PATTERN_NOT_DEFINED);
    }

    public static void console(){
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n",
                    envName,
                    env.get(envName));
        }
    }

    public static void modifyEnv(String key, String value) {
        env.put(key, value);
        System.out.println("Environment variable modified: " + key + " = " + value);
    }

    public static String getEnv(String key) {
        return env.getOrDefault(key, PATTERN_NOT_DEFINED);
    }


}
