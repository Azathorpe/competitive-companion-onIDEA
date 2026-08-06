package com.azathorpe.cci.model;

public class TestCase {
    String input;
    String output;

    public TestCase() {
    }

    public TestCase(String input, String output) {
        this.input = input;
        this.output = output;
    }

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