package com.azathorpe.cci.model;

/**
 * @author Azathorpe
 * @version 1.0
 */
public class Settings {
    String language;
    String autoFetchProblems;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAutoFetchProblems() {
        return autoFetchProblems;
    }

    public void setAutoFetchProblems(String autoFetchProblems) {
        this.autoFetchProblems = autoFetchProblems;
    }
}
