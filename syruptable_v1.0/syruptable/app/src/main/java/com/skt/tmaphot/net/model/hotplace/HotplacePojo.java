package com.skt.tmaphot.net.model.hotplace;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class HotplacePojo{

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
}