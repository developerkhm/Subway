
package com.skt.tmaphot.net.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemList {

    @SerializedName("idx")
    @Expose
    private String idx;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("tel")
    @Expose
    private Object tel;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("regdate")
    @Expose
    private String regdate;
    @SerializedName("nameCheck_ci")
    @Expose
    private String nameCheckCi;
    @SerializedName("nameCheck_di")
    @Expose
    private String nameCheckDi;
    @SerializedName("birth")
    @Expose
    private String birth;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("smsAgree")
    @Expose
    private String smsAgree;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("addr1")
    @Expose
    private String addr1;
    @SerializedName("addr2")
    @Expose
    private String addr2;
    @SerializedName("addr_island")
    @Expose
    private String addrIsland;
    @SerializedName("memo")
    @Expose
    private String memo;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("fail_count")
    @Expose
    private String failCount;
    @SerializedName("lock_yn")
    @Expose
    private String lockYn;
    @SerializedName("last_try_time")
    @Expose
    private String lastTryTime;
    @SerializedName("lock_time")
    @Expose
    private String lockTime;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("img")
    @Expose
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

    public Object getTel() {
        return tel;
    }

    public void setTel(Object tel) {
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

}
