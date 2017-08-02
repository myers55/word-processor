package com.dylan;

public enum Status {
    INITIAL ("initial"),
    ASSIGNED ("assigned"),
    IN_PROGRESS ("in progress"),
    DONE  ("done");

    private String imReady;

    Status(String imReady){
        this.imReady = imReady;
    }
    public String getImReady() {
        return imReady;
    }
}
