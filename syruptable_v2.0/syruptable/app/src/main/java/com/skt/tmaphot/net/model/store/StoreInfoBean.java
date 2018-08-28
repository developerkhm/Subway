package com.skt.tmaphot.net.model.store;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class StoreInfoBean{

	@SerializedName("reviewList")
	@JsonField(name ="reviewList")
	private List<Object> reviewList;

	@SerializedName("store")
	@JsonField(name ="store")
	private Store store;

	@SerializedName("social_list")
	@JsonField(name ="social_list")
	private List<SocialListItem> socialList;

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

	public void setSocialList(List<SocialListItem> socialList){
		this.socialList = socialList;
	}

	public List<SocialListItem> getSocialList(){
		return socialList;
	}

	@Override
 	public String toString(){
		return 
			"StoreInfoBean{" + 
			"reviewList = '" + reviewList + '\'' + 
			",store = '" + store + '\'' + 
			",social_list = '" + socialList + '\'' + 
			"}";
		}
}