package com.azathorpe.cci.model;

import com.azathorpe.cci.utils.anno.RememberDELIT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Many get&et methods are required for fastjson to work properly
 *
 * @author Azathorpe
 * @version 1.0
 */
@SuppressWarnings("unused")
public class Question {
    String name;
    String group;
    String url;
    String interactive;
    int memoryLimit;
    int timeLimit;
    TestCase[] tests;
    String testType;

    private static final HashSet<Character> invalidChars = new HashSet<>();

    static{
        for(char c : "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_".toCharArray())
            invalidChars.add(c);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        StringBuilder sb = new StringBuilder();

        for(char c : name.toCharArray()){
            if(invalidChars.contains(c))
                sb.append(c);
            else
                sb.append("_");
        }

        this.name = sb.toString();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        StringBuilder sb = new StringBuilder();

        for(char c : group.toCharArray()){
            if(invalidChars.contains(c))
                sb.append(c);
            else
                sb.append("_");
        }

        this.group = sb.toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInteractive() {
        return interactive;
    }

    public void setInteractive(String interactive) {
        this.interactive = interactive;
    }

    public TestCase[] getTests() {
        return tests;
    }

    public void setTests(TestCase[] tests) {
        this.tests = tests;
    }

    public int getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(int memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getTestCases() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"tests\":[");
        for (TestCase test : tests) {
            builder.append("{\"input\":\"").append(test.getInput().replace("\n", "\\n").replace("\"", "\\\"")).append("\",");
            builder.append("\"output\":\"").append(test.getOutput().replace("\n", "\\n").replace("\"", "\\\"")).append("\"},");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("]}");
        return builder.toString();
    }

    @RememberDELIT
    @Override
    public String toString() {
        return "OIMessage{" +
                "name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", url='" + url + '\'' +
                ", interactive='" + interactive + '\'' +
                ", memoryLimit=" + memoryLimit +
                ", timeLimit=" + timeLimit +
                ", testType='" + testType + '\'' +
                '}';
    }


    public class TestCase {
        String input;
        String output;

        public String getInput() {
            return input;
        }

        public void setInput(String input) {
            this.input = input;
        }

        public String getOutput() {
            return output;
        }

        public void setOutput(String output) {
            this.output = output;
        }

        @RememberDELIT
        @Override
        public String toString() {
            return "TestCase{" +
                    "input='" + input + '\'' +
                    ", output='" + output + '\'' +
                    '}';
        }
    }

}