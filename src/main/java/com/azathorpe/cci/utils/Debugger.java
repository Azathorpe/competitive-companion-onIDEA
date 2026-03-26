package com.azathorpe.cci.utils;

public class Debugger {
        public static void log(Object... args) {
            StringBuilder sb = new StringBuilder();
            for (Object arg : args) {
                sb.append(arg).append(" ");
            }
            System.out.println(sb.toString());
        }
}
