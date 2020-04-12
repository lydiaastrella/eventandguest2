package com.example.eventandguest;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    private int id;
    private String name, date, image;

    Event() {
    }

    private Event(Parcel in) {
        id = in.readInt();
        name = in.readString();
        date = in.readString();
        image = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getDate() {
        return date;
    }

    void setDate(String date) {
        this.date = date;
    }

    String getImage() {
        return image;
    }

    void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(date);
        parcel.writeString(image);
    }
}
