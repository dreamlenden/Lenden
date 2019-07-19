package com.example.lenden.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactModel implements Parcelable {
    private String name,phoneNumber;

    public ContactModel(String name, String phonenNumber) {
        this.name = name;
        this.phoneNumber = phonenNumber;
    }

    protected ContactModel(Parcel in) {
        name = in.readString();
        phoneNumber = in.readString();
    }

    public static final Creator<ContactModel> CREATOR = new Creator<ContactModel>() {
        @Override
        public ContactModel createFromParcel(Parcel in) {
            return new ContactModel(in);
        }

        @Override
        public ContactModel[] newArray(int size) {
            return new ContactModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phonenNumber) {
        this.phoneNumber = phonenNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phoneNumber);
    }
}
