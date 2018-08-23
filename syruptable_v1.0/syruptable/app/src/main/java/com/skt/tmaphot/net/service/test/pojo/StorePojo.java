package com.skt.tmaphot.net.service.test.pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class StorePojo{

	@SerializedName("reviewList")
	@JsonField(name ="reviewList")
	private List<Object> reviewList;

	@SerializedName("store")
	@JsonField(name ="store")
	private Store store;

	public void setReviewList(List<Object> reviewList){
		this.reviewList = reviewList;
	}

	public List<Object> getReviewList(){
		return reviewList;
	}

	public void setStore(Store store){
		this.store = store;
	}

	public Store getStore(){
		return store;
	}

	@Override
 	public String toString(){
		return 
			"StorePojo{" + 
			"reviewList = '" + reviewList + '\'' + 
			",store = '" + store + '\'' + 
			"}";
		}
}