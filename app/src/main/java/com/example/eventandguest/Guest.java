package com.example.eventandguest;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Guest implements Parcelable {
    private int id;
    private String email, first_name, last_name, avatar;

    Guest(JSONObject object){
        try{
            id = object.getInt("id");
            email = object.getString("email");
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            avatar = object.getString("avatar");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Guest(Parcel in) {
        id = in.readInt();
        email = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        avatar = in.readString();
    }

    public static final Creator<Guest> CREATOR = new Creator<Guest>() {
        @Override
        public Guest createFromParcel(Parcel in) {
            return new Guest(in);
        }

        @Override
        public Guest[] newArray(int size) {
            return new Guest[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(email);
        parcel.writeString(first_name);
        parcel.writeString(last_name);
        parcel.writeString(avatar);
    }

}
