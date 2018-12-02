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
}
