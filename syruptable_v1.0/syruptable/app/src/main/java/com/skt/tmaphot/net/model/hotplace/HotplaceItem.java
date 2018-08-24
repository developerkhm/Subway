package com.skt.tmaphot.net.model.hotplace;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class HotplaceItem{

	@SerializedName("address_no")
	@JsonField(name ="address_no")
	private String addressNo;

	@SerializedName("category_name")
	@JsonField(name ="category_name")
	private String categoryName;

	@SerializedName("dk")
	@JsonField(name ="dk")
	private double dk;

	@SerializedName("dm")
	@JsonField(name ="dm")
	private String dm;

	@SerializedName("review_str")
	@JsonField(name ="review_str")
	private String reviewStr;

	@SerializedName("recent_image")
	@JsonField(name ="recent_image")
	private String recentImage;

	@SerializedName("category_id")
	@JsonField(name ="category_id")
	private String categoryId;

	@SerializedName("blog_review_count")
	@JsonField(name ="blog_review_count")
	private String blogReviewCount;

	@SerializedName("star_rating")
	@JsonField(name ="star_rating")
	private String starRating;

	@SerializedName("category_l_name")
	@JsonField(name ="category_l_name")
	private String categoryLName;

	@SerializedName("id")
	@JsonField(name ="id")
	private String id;

	@SerializedName("addr")
	@JsonField(name ="addr")
	private String addr;

	@SerializedName("bld_no")
	@JsonField(name ="bld_no")
	private String bldNo;

	@SerializedName("lat")
	@JsonField(name ="lat")
	private String lat;

	@SerializedName("is_closed")
	@JsonField(name ="is_closed")
	private String isClosed;

	@SerializedName("images")
	@JsonField(name ="images")
	private String images;

	@SerializedName("d")
	@JsonField(name ="d")
	private String D;

	@SerializedName("lng")
	@JsonField(name ="lng")
	private String lng;

	@SerializedName("rater")
	@JsonField(name ="rater")
	private String rater;

	@SerializedName("image_url")
	@JsonField(name ="image_url")
	private String imageUrl;

	@SerializedName("total_count")
	@JsonField(name ="total_count")
	private String totalCount;

	@SerializedName("road_address")
	@JsonField(name ="road_address")
	private String roadAddress;

	@SerializedName("l_area_nm")
	@JsonField(name ="l_area_nm")
	private String lAreaNm;

	@SerializedName("NAME")
	@JsonField(name ="NAME")
	private String nAME;

	@SerializedName("pick_count")
	@JsonField(name ="pick_count")
	private String pickCount;

	@SerializedName("location")
	@JsonField(name ="location")
	private String location;

	public void setAddressNo(String addressNo){
		this.addressNo = addressNo;
	}

	public String getAddressNo(){
		return addressNo;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setDk(double dk){
		this.dk = dk;
	}

	public double getDk(){
		return dk;
	}

	public void setDm(String dm){
		this.dm = dm;
	}

	public String getDm(){
		return dm;
	}

	public void setReviewStr(String reviewStr){
		this.reviewStr = reviewStr;
	}

	public String getReviewStr(){
		return reviewStr;
	}

	public void setRecentImage(String recentImage){
		this.recentImage = recentImage;
	}

	public String getRecentImage(){
		return recentImage;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public void setBlogReviewCount(String blogReviewCount){
		this.blogReviewCount = blogReviewCount;
	}

	public String getBlogReviewCount(){
		return blogReviewCount;
	}

	public void setStarRating(String starRating){
		this.starRating = starRating;
	}

	public String getStarRating(){
		return starRating;
	}

	public void setCategoryLName(String categoryLName){
		this.categoryLName = categoryLName;
	}

	public String getCategoryLName(){
		return categoryLName;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setAddr(String addr){
		this.addr = addr;
	}

	public String getAddr(){
		return addr;
	}

	public void setBldNo(String bldNo){
		this.bldNo = bldNo;
	}

	public String getBldNo(){
		return bldNo;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	public void setIsClosed(String isClosed){
		this.isClosed = isClosed;
	}

	public String getIsClosed(){
		return isClosed;
	}

	public void setImages(String images){
		this.images = images;
	}

	public String getImages(){
		return images;
	}

	public void setD(String D){
		this.D = D;
	}

	public String getD(){
		return D;
	}

	public void setLng(String lng){
		this.lng = lng;
	}

	public String getLng(){
		return lng;
	}

	public void setRater(String rater){
		this.rater = rater;
	}

	public String getRater(){
		return rater;
	}

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public void setTotalCount(String totalCount){
		this.totalCount = totalCount;
	}

	public String getTotalCount(){
		return totalCount;
	}

	public void setRoadAddress(String roadAddress){
		this.roadAddress = roadAddress;
	}

	public String getRoadAddress(){
		return roadAddress;
	}

	public void setLAreaNm(String lAreaNm){
		this.lAreaNm = lAreaNm;
	}

	public String getLAreaNm(){
		return lAreaNm;
	}

	public void setNAME(String nAME){
		this.nAME = nAME;
	}

	public String getNAME(){
		return nAME;
	}

	public void setPickCount(String pickCount){
		this.pickCount = pickCount;
	}

	public String getPickCount(){
		return pickCount;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	@Override
 	public String toString(){
		return 
			"HotplaceItem{" + 
			"address_no = '" + addressNo + '\'' + 
			",category_name = '" + categoryName + '\'' + 
			",dk = '" + dk + '\'' + 
			",dm = '" + dm + '\'' + 
			",review_str = '" + reviewStr + '\'' + 
			",recent_image = '" + recentImage + '\'' + 
			",category_id = '" + categoryId + '\'' + 
			",blog_review_count = '" + blogReviewCount + '\'' + 
			",star_rating = '" + starRating + '\'' + 
			",category_l_name = '" + categoryLName + '\'' + 
			",id = '" + id + '\'' + 
			",addr = '" + addr + '\'' + 
			",bld_no = '" + bldNo + '\'' + 
			",lat = '" + lat + '\'' + 
			",is_closed = '" + isClosed + '\'' + 
			",images = '" + images + '\'' + 
			",d = '" + D + '\'' + 
			",lng = '" + lng + '\'' + 
			",rater = '" + rater + '\'' + 
			",image_url = '" + imageUrl + '\'' + 
			",total_count = '" + totalCount + '\'' + 
			",road_address = '" + roadAddress + '\'' + 
			",l_area_nm = '" + lAreaNm + '\'' + 
			",nAME = '" + nAME + '\'' + 
			",pick_count = '" + pickCount + '\'' + 
			",location = '" + location + '\'' + 
			"}";
		}
}