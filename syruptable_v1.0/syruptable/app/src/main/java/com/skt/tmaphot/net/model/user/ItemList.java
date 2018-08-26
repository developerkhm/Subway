
package com.skt.tmaphot.net.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.google.gson.annotations.SerializedName;

@JsonObject
public class ItemList implements Parcelable {

    @SerializedName("idx")
    @JsonField(name ="idx")
    private String idx;
    @SerializedName("email")
    @JsonField(name ="email")
    private String email;
    @SerializedName("name")
    @JsonField(name ="name")
    private String name;
    @SerializedName("phone")
    @JsonField(name ="phone")
    private String phone;
    @SerializedName("tel")
    @JsonField(name ="tel")
    private String tel;
    @SerializedName("point")
    @JsonField(name ="point")
    private String point;
    @SerializedName("regdate")
    @JsonField(name ="regdate")
    private String regdate;
    @SerializedName("nameCheck_ci")
    @JsonField(name ="nameCheck_ci")
    private String nameCheckCi;
    @SerializedName("nameCheck_di")
    @JsonField(name ="nameCheck_di")
    private String nameCheckDi;
    @SerializedName("birth")
    @JsonField(name ="birth")
    private String birth;
    @SerializedName("gender")
    @JsonField(name ="gender")
    private String gender;
    @SerializedName("smsAgree")
    @JsonField(name ="smsAgree")
    private String smsAgree;
    @SerializedName("postcode")
    @JsonField(name ="postcode")
    private String postcode;
    @SerializedName("addr1")
    @JsonField(name ="addr1")
    private String addr1;
    @SerializedName("addr2")
    @JsonField(name ="addr2")
    private String addr2;
    @SerializedName("addr_island")
    @JsonField(name ="addr_island")
    private String addrIsland;
    @SerializedName("memo")
    @JsonField(name ="memo")
    private String memo;
    @SerializedName("level")
    @JsonField(name ="level")
    private String level;
    @SerializedName("fail_count")
    @JsonField(name ="fail_count")
    private String failCount;
    @SerializedName("lock_yn")
    @JsonField(name ="lock_yn")
    private String lockYn;
    @SerializedName("last_try_time")
    @JsonField(name ="last_try_time")
    private String lastTryTime;
    @SerializedName("lock_time")
    @JsonField(name ="lock_time")
    private String lockTime;
    @SerializedName("nickname")
    @JsonField(name ="nickname")
    private String nickname;
    @SerializedName("img")
    @JsonField(name ="img")
    private String img;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getNameCheckCi() {
        return nameCheckCi;
    }

    public void setNameCheckCi(String nameCheckCi) {
        this.nameCheckCi = nameCheckCi;
    }

    public String getNameCheckDi() {
        return nameCheckDi;
    }

    public void setNameCheckDi(String nameCheckDi) {
        this.nameCheckDi = nameCheckDi;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSmsAgree() {
        return smsAgree;
    }

    public void setSmsAgree(String smsAgree) {
        this.smsAgree = smsAgree;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getAddrIsland() {
        return addrIsland;
    }

    public void setAddrIsland(String addrIsland) {
        this.addrIsland = addrIsland;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFailCount() {
        return failCount;
    }

    public void setFailCount(String failCount) {
        this.failCount = failCount;
    }

    public String getLockYn() {
        return lockYn;
    }

    public void setLockYn(String lockYn) {
        this.lockYn = lockYn;
    }

    public String getLastTryTime() {
        return lastTryTime;
    }

    public void setLastTryTime(String lastTryTime) {
        this.lastTryTime = lastTryTime;
    }

    public String getLockTime() {
        return lockTime;
    }

    public void setLockTime(String lockTime) {
        this.lockTime = lockTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idx);
        dest.writeString(this.email);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.tel);
        dest.writeString(this.point);
        dest.writeString(this.regdate);
        dest.writeString(this.nameCheckCi);
        dest.writeString(this.nameCheckDi);
        dest.writeString(this.birth);
        dest.writeString(this.gender);
        dest.writeString(this.smsAgree);
        dest.writeString(this.postcode);
        dest.writeString(this.addr1);
        dest.writeString(this.addr2);
        dest.writeString(this.addrIsland);
        dest.writeString(this.memo);
        dest.writeString(this.level);
        dest.writeString(this.failCount);
        dest.writeString(this.lockYn);
        dest.writeString(this.lastTryTime);
        dest.writeString(this.lockTime);
        dest.writeString(this.nickname);
        dest.writeString(this.img);
    }

    public ItemList() {
    }

    protected ItemList(Parcel in) {
        this.idx = in.readString();
        this.email = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.tel = in.readString();
        this.point = in.readString();
        this.regdate = in.readString();
        this.nameCheckCi = in.readString();
        this.nameCheckDi = in.readString();
        this.birth = in.readString();
        this.gender = in.readString();
        this.smsAgree = in.readString();
        this.postcode = in.readString();
        this.addr1 = in.readString();
        this.addr2 = in.readString();
        this.addrIsland = in.readString();
        this.memo = in.readString();
        this.level = in.readString();
        this.failCount = in.readString();
        this.lockYn = in.readString();
        this.lastTryTime = in.readString();
        this.lockTime = in.readString();
        this.nickname = in.readString();
        this.img = in.readString();
    }

    public static final Parcelable.Creator<ItemList> CREATOR = new Parcelable.Creator<ItemList>() {
        @Override
        public ItemList createFromParcel(Parcel source) {
            return new ItemList(source);
        }

        @Override
        public ItemList[] newArray(int size) {
            return new ItemList[size];
        }
    };
}
