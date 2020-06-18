package com.archi.architecture.server;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String mName;
    private String mAuthority;
    private float mPrice;
    private int mNum;

    public Book() {
    }

    public Book(Parcel in) {
        this.mName = in.readString();
        this.mAuthority = in.readString();
        this.mPrice = in.readFloat();
        this.mNum = in.readInt();
    }

    public Book(String name) {
        mName = name;
        mAuthority = "龙文章";
        mPrice = 12.5f;
        mNum = 100;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAuthority() {
        return mAuthority;
    }

    public void setAuthority(String authority) {
        mAuthority = authority;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        mPrice = price;
    }

    public int getNum() {
        return mNum;
    }

    public void setNum(int num) {
        mNum = num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mAuthority);
        dest.writeFloat(mPrice);
        dest.writeInt(mNum);
    }

    public void readFromParcel(Parcel dest) {
        this.mName = dest.readString();
        this.mAuthority = dest.readString();
        this.mPrice = dest.readFloat();
        this.mNum = dest.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
