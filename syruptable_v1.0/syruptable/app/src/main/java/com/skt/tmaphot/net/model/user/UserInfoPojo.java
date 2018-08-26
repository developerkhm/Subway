
package com.skt.tmaphot.net.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.google.gson.annotations.SerializedName;

@JsonObject
public class UserInfoPojo implements Parcelable {

    @SerializedName("result")
    @JsonField(name ="result")
    private String result;
    @SerializedName("itemList")
    @JsonField(name ="itemList")
    private ItemList itemList;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ItemList getItemList() {
        return itemList;
    }

    public void setItemList(ItemList itemList) {
        this.itemList = itemList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.result);
        dest.writeParcelable(this.itemList, flags);
    }

    public UserInfoPojo() {
    }

    protected UserInfoPojo(Parcel in) {
        this.result = in.readString();
        this.itemList = in.readParcelable(ItemList.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserInfoPojo> CREATOR = new Parcelable.Creator<UserInfoPojo>() {
        @Override
        public UserInfoPojo createFromParcel(Parcel source) {
            return new UserInfoPojo(source);
        }

        @Override
        public UserInfoPojo[] newArray(int size) {
            return new UserInfoPojo[size];
        }
    };
}
