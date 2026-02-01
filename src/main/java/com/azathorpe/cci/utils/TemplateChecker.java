package com.azathorpe.cci.utils;

import org.jetbrains.annotations.Nullable;

public class TemplateChecker {
    public static enum TemplateType {
        PACKAGE_NAME,
        FILE_NAME
    }

    /**
     * Check which template is used in the given string.
     * @param template Template String
     * @return TemplateType or null if no template is found
     */
    public @Nullable TemplateType checkTemplate(String template) {
        if (template.contains("${PACKAGE_NAME}")) {
            return TemplateType.PACKAGE_NAME;
        } else if (template.contains("${FILE_NAME}")) {
            return TemplateType.FILE_NAME;
        } else {
            return null;
        }
    }
}
