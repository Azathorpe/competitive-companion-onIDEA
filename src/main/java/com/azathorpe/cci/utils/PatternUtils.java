package com.azathorpe.cci.utils;

import java.util.regex.Pattern;

public class PatternUtils {
    public static Pattern pattern = Pattern.compile("\\$\\{[^}]*}");

    public static final String PACKAGE_NAME_TEMPLATE = "${PACKAGE_NAME}";
    public static final String CLASS_NAME_TEMPLATE = "${CLASS_NAME}";
    public static final String METHOD_NAME_TEMPLATE = "${METHOD_NAME}";
    public static final String FILE_NAME_TEMPLATE = "${FILE_NAME}";
    public static final String FILE_PATH_TEMPLATE = "${FILE_PATH}";
    public static final String PROJECT_NAME_TEMPLATE = "${PROJECT_NAME}";
    public static final String PROJECT_PATH_TEMPLATE = "${PROJECT_PATH}";

    /**
     * Check if the template contains any template variable like ${VARIABLE_NAME}
     * @param template Template String
     * @return True if contains template variable, false otherwise
     */
    public static boolean containsTemplateVariable(String template) {
        return pattern.matcher(template).find();
    }

}
