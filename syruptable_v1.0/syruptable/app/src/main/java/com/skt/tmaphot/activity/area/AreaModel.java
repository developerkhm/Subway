
package com.skt.tmaphot.activity.area;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AreaModel {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("area_type")
    @Expose
    private String areaType;
    @SerializedName("do_code")
    @Expose
    private int doCode;
    @SerializedName("do_txt")
    @Expose
    private String doTxt;
    @SerializedName("dong_code")
    @Expose
    private int dongCode;
    @SerializedName("dong_txt")
    @Expose
    private String dongTxt;
    @SerializedName("lati")
    @Expose
    private String lati;
    @SerializedName("longti")
    @Expose
    private String longti;
    @SerializedName("num1")
    @Expose
    private String num1;
    @SerializedName("num2")
    @Expose
    private String num2;
    @SerializedName("ri_code")
    @Expose
    private int riCode;
    @SerializedName("ri_txt")
    @Expose
    private String riTxt;
    @SerializedName("sigun_code")
    @Expose
    private int sigunCode;
    @SerializedName("sigun_txt")
    @Expose
    private String sigunTxt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public int getDoCode() {
        return doCode;
    }

    public void setDoCode(int doCode) {
        this.doCode = doCode;
    }

    public String getDoTxt() {
        return doTxt;
    }

    public void setDoTxt(String doTxt) {
        this.doTxt = doTxt;
    }

    public int getDongCode() {
        return dongCode;
    }

    public void setDongCode(int dongCode) {
        this.dongCode = dongCode;
    }

    public String getDongTxt() {
        return dongTxt;
    }

    public void setDongTxt(String dongTxt) {
        this.dongTxt = dongTxt;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getLongti() {
        return longti;
    }

    public void setLongti(String longti) {
        this.longti = longti;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNum2() {
        return num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public int getRiCode() {
        return riCode;
    }

    public void setRiCode(int riCode) {
        this.riCode = riCode;
    }

    public String getRiTxt() {
        return riTxt;
    }

    public void setRiTxt(String riTxt) {
        this.riTxt = riTxt;
    }

    public int getSigunCode() {
        return sigunCode;
    }

    public void setSigunCode(int sigunCode) {
        this.sigunCode = sigunCode;
    }

    public String getSigunTxt() {
        return sigunTxt;
    }

    public void setSigunTxt(String sigunTxt) {
        this.sigunTxt = sigunTxt;
    }

}
