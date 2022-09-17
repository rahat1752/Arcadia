package com.example.arcadia;

import java.util.Date;

public class MessageChatModel {
    private String text;
    private String time;
    private int viewType;

    public MessageChatModel(String text, String time, int viewType) {
        this.text = text;
        this.time = time;
        this.viewType = viewType;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        //String dateToString = time.toString();
        //return dateToString;
        return time;
    }

    public int getViewType() {
        return viewType;
    }
}
