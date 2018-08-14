package com.skt.tmaphot.net.model.store;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Insta {

    @SerializedName("IDX")
    @Expose
    private String iDX;
    @SerializedName("STATUS")
    @Expose
    private String sTATUS;
    @SerializedName("ORDER_SEQ")
    @Expose
    private String oRDERSEQ;
    @SerializedName("LIKE_NUM")
    @Expose
    private String lIKENUM;
    @SerializedName("TAG_1")
    @Expose
    private String tAG1;
    @SerializedName("TAG_2")
    @Expose
    private String tAG2;
    @SerializedName("TAG_3")
    @Expose
    private String tAG3;
    @SerializedName("TAG_4")
    @Expose
    private String tAG4;
    @SerializedName("TAG_5")
    @Expose
    private String tAG5;
    @SerializedName("PARENT_CODE")
    @Expose
    private String pARENTCODE;
    @SerializedName("IMG_URL")
    @Expose
    private String iMGURL;
    @SerializedName("REG_DATE")
    @Expose
    private String rEGDATE;
    @SerializedName("IS_IMG")
    @Expose
    private String iSIMG;

    public String getIDX() {
        return iDX;
    }

    public void setIDX(String iDX) {
        this.iDX = iDX;
    }

    public String getSTATUS() {
        return sTATUS;
    }

    public void setSTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
    }

    public String getORDERSEQ() {
        return oRDERSEQ;
    }

    public void setORDERSEQ(String oRDERSEQ) {
        this.oRDERSEQ = oRDERSEQ;
    }

    public String getLIKENUM() {
        return lIKENUM;
    }

    public void setLIKENUM(String lIKENUM) {
        this.lIKENUM = lIKENUM;
    }

    public String getTAG1() {
        return tAG1;
    }

    public void setTAG1(String tAG1) {
        this.tAG1 = tAG1;
    }

    public String getTAG2() {
        return tAG2;
    }

    public void setTAG2(String tAG2) {
        this.tAG2 = tAG2;
    }

    public String getTAG3() {
        return tAG3;
    }

    public void setTAG3(String tAG3) {
        this.tAG3 = tAG3;
    }

    public String getTAG4() {
        return tAG4;
    }

    public void setTAG4(String tAG4) {
        this.tAG4 = tAG4;
    }

    public String getTAG5() {
        return tAG5;
    }

    public void setTAG5(String tAG5) {
        this.tAG5 = tAG5;
    }

    public String getPARENTCODE() {
        return pARENTCODE;
    }

    public void setPARENTCODE(String pARENTCODE) {
        this.pARENTCODE = pARENTCODE;
    }

    public String getIMGURL() {
        return iMGURL;
    }

    public void setIMGURL(String iMGURL) {
        this.iMGURL = iMGURL;
    }

    public String getREGDATE() {
        return rEGDATE;
    }

    public void setREGDATE(String rEGDATE) {
        this.rEGDATE = rEGDATE;
    }

    public String getISIMG() {
        return iSIMG;
    }

    public void setISIMG(String iSIMG) {
        this.iSIMG = iSIMG;
    }

}
