package com.skt.tmaphot.net.model.hotplace;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class HotplacePojo implements Parcelable {

	@SerializedName("per_page")
	@JsonField(name ="per_page")
	private int perPage;

	@SerializedName("lng")
	@JsonField(name ="lng")
	private String lng;

	@SerializedName("offset")
	@JsonField(name ="offset")
	private int offset;

	@SerializedName("storeList")
	@JsonField(name ="storeList")
	private List<StoreListItem> storeList;

	@SerializedName("page")
	@JsonField(name ="page")
	private String page;

	@SerializedName("lat")
	@JsonField(name ="lat")
	private String lat;

	public void setPerPage(int perPage){
		this.perPage = perPage;
	}

	public int getPerPage(){
		return perPage;
	}

	public void setLng(String lng){
		this.lng = lng;
	}

	public String getLng(){
		return lng;
	}

	public void setOffset(int offset){
		this.offset = offset;
	}

	public int getOffset(){
		return offset;
	}

	public void setStoreList(List<StoreListItem> storeList){
		this.storeList = storeList;
	}

	public List<StoreListItem> getStoreList(){
		return storeList;
	}

	public void setPage(String page){
		this.page = page;
	}

	public String getPage(){
		return page;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	@Override
 	public String toString(){
		return 
			"HotplacePojo{" + 
			"per_page = '" + perPage + '\'' + 
			",lng = '" + lng + '\'' + 
			",offset = '" + offset + '\'' + 
			",storeList = '" + storeList + '\'' + 
			",page = '" + page + '\'' + 
			",lat = '" + lat + '\'' + 
			"}";
		}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.perPage);
		dest.writeString(this.lng);
		dest.writeInt(this.offset);
		dest.writeList(this.storeList);
		dest.writeString(this.page);
		dest.writeString(this.lat);
	}

	public HotplacePojo() {
	}

	protected HotplacePojo(Parcel in) {
		this.perPage = in.readInt();
		this.lng = in.readString();
		this.offset = in.readInt();
		this.storeList = new ArrayList<StoreListItem>();
		in.readList(this.storeList, StoreListItem.class.getClassLoader());
		this.page = in.readString();
		this.lat = in.readString();
	}

	public static final Parcelable.Creator<HotplacePojo> CREATOR = new Parcelable.Creator<HotplacePojo>() {
		@Override
		public HotplacePojo createFromParcel(Parcel source) {
			return new HotplacePojo(source);
		}

		@Override
		public HotplacePojo[] newArray(int size) {
			return new HotplacePojo[size];
		}
	};
}