package com.ambientdigitalgroup.ambchat.notification;

public class Sender {
    public DataNotification dataNotification;
    public String to;

    public Sender(DataNotification dataNotification, String to) {
        this.dataNotification = dataNotification;
        this.to = to;
    }

    public DataNotification getDataNotification() {
        return dataNotification;
    }

    public void setDataNotification(DataNotification dataNotification) {
        this.dataNotification = dataNotification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
