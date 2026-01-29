package com.azathorpe.cci.actions.utils;

import java.util.regex.Pattern;

public class PatternUtils {
    public static Pattern pattern = Pattern.compile("\\$\\{[^}]*}");

    /**
     * Check if the template contains any template variable like ${VARIABLE_NAME}
     * @param template Template String
     * @return True if contains template variable, false otherwise
     */
    public static boolean containsTemplateVariable(String template) {
        return pattern.matcher(template).find();
    }

}
