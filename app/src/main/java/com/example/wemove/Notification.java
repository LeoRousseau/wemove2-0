package com.example.wemove;

import java.util.Date;

public class Notification {

    private String type;
    private String message;
    private String contentID;
    private boolean seen = false;
    private Date date;

    public Notification(String type, String message, String contentID, Date date) {
        this.type = type;
        this.message = message;
        this.contentID = contentID;
        this.date = date;
    }

    public Notification() {

    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getContentID() {
        return contentID;
    }

    public boolean isSeen() {
        return seen;
    }

    public Date getDate() {
        return date;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    @Override
    public String toString() {
        return "Notif{" +
                "date'" + date + '\'' +
                ", contentID'" + contentID + '\'' +
                ", type " + type + '\'' +
                ", message " + message + '\'' +
                ", seen " + seen + '\'' +
                '}';
    }
}
