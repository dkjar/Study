package com.dkjar.server;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable {
    private int userId;
    private String userName;
    private String userNick;

    protected UserModel(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        userNick = in.readString();
    }

    public UserModel(){

    }

    public UserModel(int userId, String userName, String userNick) {
        this.userId = userId;
        this.userName = userName;
        this.userNick = userNick;
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeString(userNick);
    }
}
