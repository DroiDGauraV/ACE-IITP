package com.ace.ace_iitp.DataItems;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class FeedStoriesData {

    @ServerTimestamp
    private Date date;
    private String title;
    private String op;
    private String pfp;
    private String img;
    private String fb;
    private String insta;
    private String desc;

    public FeedStoriesData () {
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getOp() {
        return op;
    }

    public String getPfp() {
        return pfp;
    }

    public String getImg() {
        return img;
    }

    public String getDesc() {
        return desc;
    }

    public String getFb() {
        return fb;
    }

    public String getInsta() {
        return insta;
    }
}