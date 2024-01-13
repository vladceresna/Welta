package com.rula.welta.obj;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Post {
    public String senderid;
    public String text;
    public String time;
    public String filters;

    public Post(){}
    public Post(String senderid, String text, String time, String filters) {
        this.senderid = senderid;
        this.text = text;
        this.time = time;
        this.filters = filters;
    }

    public String getSenderid() {
        return senderid;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public String getFilters(){return filters;}
}
