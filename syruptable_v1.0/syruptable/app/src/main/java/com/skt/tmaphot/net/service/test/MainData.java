package com.skt.tmaphot.net.service.test;

import android.os.Parcel;
import android.os.Parcelable;

import com.skt.tmaphot.net.model.hotplace.HotplacePojo;
import com.skt.tmaphot.net.model.user.UserInfoPojo;

import java.util.List;

public class MainData implements Parcelable {
    private UserInfoPojo userInfoPojo;
    private HotplacePojo hotplacePojo;

    public MainData(UserInfoPojo userInfoPojo, HotplacePojo hotplacePojo) {
        this.userInfoPojo = userInfoPojo;
        this.hotplacePojo = hotplacePojo;
    }

    public UserInfoPojo getUserInfoPojo() {
        return userInfoPojo;
    }

    public void setUserInfoPojo(UserInfoPojo userInfoPojo) {
        this.userInfoPojo = userInfoPojo;
    }

    public HotplacePojo getHotplacePojo() {
        return hotplacePojo;
    }

    public void setHotplacePojo(HotplacePojo hotplacePojo) {
        this.hotplacePojo = hotplacePojo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.userInfoPojo, flags);
        dest.writeParcelable(this.hotplacePojo, flags);
    }

    protected MainData(Parcel in) {
        this.userInfoPojo = in.readParcelable(UserInfoPojo.class.getClassLoader());
        this.hotplacePojo = in.readParcelable(HotplacePojo.class.getClassLoader());
    }

    public static final Parcelable.Creator<MainData> CREATOR = new Parcelable.Creator<MainData>() {
        @Override
        public MainData createFromParcel(Parcel source) {
            return new MainData(source);
        }

        @Override
        public MainData[] newArray(int size) {
            return new MainData[size];
        }
    };
}