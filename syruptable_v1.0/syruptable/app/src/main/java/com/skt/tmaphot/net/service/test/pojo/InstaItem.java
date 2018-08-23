package com.skt.tmaphot.net.service.test.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.google.gson.annotations.SerializedName;


@JsonObject
public class InstaItem implements Parcelable {

	@SerializedName("IS_IMG")
	@JsonField(name ="IS_IMG")
	private String iSIMG;

	@SerializedName("REG_DATE")
	@JsonField(name ="REG_DATE")
	private String rEGDATE;

	@SerializedName("LIKE_NUM")
	@JsonField(name ="LIKE_NUM")
	private String lIKENUM;

	@SerializedName("TAG_5")
	@JsonField(name ="TAG_5")
	private String tAG5;

	@SerializedName("TAG_3")
	@JsonField(name ="TAG_3")
	private String tAG3;

	@SerializedName("TAG_4")
	@JsonField(name ="TAG_4")
	private String tAG4;

	@SerializedName("STATUS")
	@JsonField(name ="STATUS")
	private String sTATUS;

	@SerializedName("TAG_1")
	@JsonField(name ="TAG_1")
	private String tAG1;

	@SerializedName("IMG_URL")
	@JsonField(name ="IMG_URL")
	private String iMGURL;

	@SerializedName("TAG_2")
	@JsonField(name ="TAG_2")
	private String tAG2;

	@SerializedName("PARENT_CODE")
	@JsonField(name ="PARENT_CODE")
	private String pARENTCODE;

	@SerializedName("ORDER_SEQ")
	@JsonField(name ="ORDER_SEQ")
	private String oRDERSEQ;

	@SerializedName("IDX")
	@JsonField(name ="IDX")
	private String iDX;

	public void setISIMG(String iSIMG){
		this.iSIMG = iSIMG;
	}

	public String getISIMG(){
		return iSIMG;
	}

	public void setREGDATE(String rEGDATE){
		this.rEGDATE = rEGDATE;
	}

	public String getREGDATE(){
		return rEGDATE;
	}

	public void setLIKENUM(String lIKENUM){
		this.lIKENUM = lIKENUM;
	}

	public String getLIKENUM(){
		return lIKENUM;
	}

	public void setTAG5(String tAG5){
		this.tAG5 = tAG5;
	}

	public String getTAG5(){
		return tAG5;
	}

	public void setTAG3(String tAG3){
		this.tAG3 = tAG3;
	}

	public String getTAG3(){
		return tAG3;
	}

	public void setTAG4(String tAG4){
		this.tAG4 = tAG4;
	}

	public String getTAG4(){
		return tAG4;
	}

	public void setSTATUS(String sTATUS){
		this.sTATUS = sTATUS;
	}

	public String getSTATUS(){
		return sTATUS;
	}

	public void setTAG1(String tAG1){
		this.tAG1 = tAG1;
	}

	public String getTAG1(){
		return tAG1;
	}

	public void setIMGURL(String iMGURL){
		this.iMGURL = iMGURL;
	}

	public String getIMGURL(){
		return iMGURL;
	}

	public void setTAG2(String tAG2){
		this.tAG2 = tAG2;
	}

	public String getTAG2(){
		return tAG2;
	}

	public void setPARENTCODE(String pARENTCODE){
		this.pARENTCODE = pARENTCODE;
	}

	public String getPARENTCODE(){
		return pARENTCODE;
	}

	public void setORDERSEQ(String oRDERSEQ){
		this.oRDERSEQ = oRDERSEQ;
	}

	public String getORDERSEQ(){
		return oRDERSEQ;
	}

	public void setIDX(String iDX){
		this.iDX = iDX;
	}

	public String getIDX(){
		return iDX;
	}

	@Override
 	public String toString(){
		return 
			"InstaItem{" + 
			"iS_IMG = '" + iSIMG + '\'' + 
			",rEG_DATE = '" + rEGDATE + '\'' + 
			",lIKE_NUM = '" + lIKENUM + '\'' + 
			",tAG_5 = '" + tAG5 + '\'' + 
			",tAG_3 = '" + tAG3 + '\'' + 
			",tAG_4 = '" + tAG4 + '\'' + 
			",sTATUS = '" + sTATUS + '\'' + 
			",tAG_1 = '" + tAG1 + '\'' + 
			",iMG_URL = '" + iMGURL + '\'' + 
			",tAG_2 = '" + tAG2 + '\'' + 
			",pARENT_CODE = '" + pARENTCODE + '\'' + 
			",oRDER_SEQ = '" + oRDERSEQ + '\'' + 
			",iDX = '" + iDX + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.iSIMG);
		dest.writeString(this.rEGDATE);
		dest.writeString(this.lIKENUM);
		dest.writeString(this.tAG5);
		dest.writeString(this.tAG3);
		dest.writeString(this.tAG4);
		dest.writeString(this.sTATUS);
		dest.writeString(this.tAG1);
		dest.writeString(this.iMGURL);
		dest.writeString(this.tAG2);
		dest.writeString(this.pARENTCODE);
		dest.writeString(this.oRDERSEQ);
		dest.writeString(this.iDX);
	}

	public InstaItem() {
	}

	protected InstaItem(Parcel in) {
		this.iSIMG = in.readString();
		this.rEGDATE = in.readString();
		this.lIKENUM = in.readString();
		this.tAG5 = in.readString();
		this.tAG3 = in.readString();
		this.tAG4 = in.readString();
		this.sTATUS = in.readString();
		this.tAG1 = in.readString();
		this.iMGURL = in.readString();
		this.tAG2 = in.readString();
		this.pARENTCODE = in.readString();
		this.oRDERSEQ = in.readString();
		this.iDX = in.readString();
	}

	public static final Parcelable.Creator<InstaItem> CREATOR = new Parcelable.Creator<InstaItem>() {
		@Override
		public InstaItem createFromParcel(Parcel source) {
			return new InstaItem(source);
		}

		@Override
		public InstaItem[] newArray(int size) {
			return new InstaItem[size];
		}
	};
}