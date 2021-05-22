package com.ace.ace_iitp.DataItems;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class EventsDataItems implements Parcelable {

    @ServerTimestamp
    private Date date;
    @DocumentId
    private String event_id;
    private boolean active = false;
    private String img;
    private boolean prob = false;
    private String name;
    private String desc;
    private String pay;
    private String rulebook;
    private String reglink;
    private String extra = "null";
    private String extraLink;

    public EventsDataItems () {}

    protected EventsDataItems(Parcel in) {
        event_id = in.readString();
        active = in.readByte() != 0;
        img = in.readString();
        prob = in.readByte() != 0;
        name = in.readString();
        desc = in.readString();
        pay = in.readString();
        rulebook = in.readString();
        reglink = in.readString();
        extra = in.readString();
        extraLink = in.readString();
    }

    public static final Creator<EventsDataItems> CREATOR = new Creator<EventsDataItems>() {
        @Override
        public EventsDataItems createFromParcel(Parcel in) {
            return new EventsDataItems(in);
        }

        @Override
        public EventsDataItems[] newArray(int size) {
            return new EventsDataItems[size];
        }
    };

    public String getRulebook() {
        return rulebook;
    }

    public String getReglink() {
        return reglink;
    }

    public Date getDate() {
        return date;
    }

    public boolean isActive() {
        return active;
    }

    public String getEvent_id() {
        return event_id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getImg() {
        return img;
    }

    public String getPay() {
        return pay;
    }

    public boolean isProb() {
        return prob;
    }

    public String getExtra() {
        return extra;
    }

    public String getExtraLink() {
        return extraLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(event_id);
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeString(img);
        dest.writeByte((byte) (prob ? 1 : 0));
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(pay);
        dest.writeString(rulebook);
        dest.writeString(reglink);
        dest.writeString(extra);
        dest.writeString(extraLink);
    }
}
