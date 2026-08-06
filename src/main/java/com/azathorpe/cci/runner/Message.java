package com.azathorpe.cci.runner;

public class Message {
    String output;
    String error;
    boolean status;

    public Message() {
    }

    public Message(String output, String error) {
        this.output = output;
        this.error = error;
    }

    public Message(String output, String error, boolean status) {
        this.output = output;
        this.error = error;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Message{" +
                "output='" + output + '\'' +
                ", error='" + error + '\'' +
                ", status=" + status +
                '}';
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
