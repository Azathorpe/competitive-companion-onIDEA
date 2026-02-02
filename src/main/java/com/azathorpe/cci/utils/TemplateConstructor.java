package com.azathorpe.cci.utils;

public class TemplateConstructor {
    public static String constructProjectName(String line) {
        //TODO: implement project name construction logic for diff languages
        String[] split = line.split("/");
        int index = 0;
        for (String s : split) {
            index++;
            if (s.equals("src"))
                break;
        }

        StringBuilder sb = new StringBuilder();
        for (; index < split.length; index++) {
            //TODO: implement project name construction logic for diff languages
            if(split[index].contains(".java"))
                break;
            sb.append(split[index]);
            if (index <= split.length - 2) {
                sb.append(".");
            }
        }

        if (sb.isEmpty())
            return "";

        return "package " + sb.toString();
    }

    public static String constructClassName(String line) {
        //TODO: implement class name construction logic for diff languages
        return line.replace(".java", "");
    }
}
