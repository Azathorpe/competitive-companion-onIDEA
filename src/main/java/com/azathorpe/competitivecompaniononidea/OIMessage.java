package com.azathorpe.competitivecompaniononidea;

@SuppressWarnings("unused")
public class OIMessage {
    String name;
    String group;
    String url;
    String interactive;
    int memoryLimit;
    int timeLimit;
    TestCase[] tests;
    String testType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.replaceAll(" ", "_");
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group.replace(" ", "_");
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
        for (TestCase test : tests) {
            builder.append("Input:\n").append(test.getInput());
            builder.append("Output:\n").append(test.getOutput());
        }
        return builder.toString();
    }

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
}

class TestCase {
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

    @Override
    public String toString() {
        return "TestCase{" +
                "input='" + input + '\'' +
                ", output='" + output + '\'' +
                '}';
    }
}
