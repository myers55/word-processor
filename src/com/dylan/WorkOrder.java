package com.dylan;

import java.util.UUID;

public class WorkOrder {
    public  int iD;
    public  String description;
    public  String senderName;
    public  Status status;

    public WorkOrder() {
    }

    public  int getiD() {
        return iD;
    }

    public  void setiD(int iD) {
        this.iD = iD;
    }

    public  String getDescription() {
        return description;
    }

    public  void setDescription(String description) {
        this.description = description;
    }

    public  String getSenderName() {
        return senderName;
    }

    public  void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public  Status getStatus() {
        return status;
    }

    public  void setStatus(Status status) {
        this.status = status;
    }
}
