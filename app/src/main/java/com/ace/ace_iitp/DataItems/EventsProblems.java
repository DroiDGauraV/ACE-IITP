package com.ace.ace_iitp.DataItems;

import android.os.Parcel;
import android.os.Parcelable;

public class EventsProblems implements Parcelable {

    private String img_prob;
    private String name;
    private String info;
    private String rules;
    private String link;

    public EventsProblems() {
    }

    protected EventsProblems(Parcel in) {
        img_prob = in.readString();
        name = in.readString();
        info = in.readString();
        rules = in.readString();
        link = in.readString();
    }

    public static final Creator<EventsProblems> CREATOR = new Creator<EventsProblems>() {
        @Override
        public EventsProblems createFromParcel(Parcel in) {
            return new EventsProblems(in);
        }

        @Override
        public EventsProblems[] newArray(int size) {
            return new EventsProblems[size];
        }
    };

    public String getImg_prob() {
        return img_prob;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getRules() {
        return rules;
    }

    public String getLink() {
        return link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img_prob);
        dest.writeString(name);
        dest.writeString(info);
        dest.writeString(rules);
        dest.writeString(link);
    }
}
