package com.azathorpe.cci.model;

import com.alibaba.fastjson2.JSON;

/**
 * @author Azathorpe
 * @version 1.0
 */
public class Settings {
    String language = "Java";
    String autoFetchProblems = "true";

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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
