package com.skt.tmaphot.net.model.store;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class Store{

	@SerializedName("business_hours")
	@JsonField(name ="business_hours")
	private String businessHours;

	@SerializedName("parking")
	@JsonField(name ="parking")
	private String parking;

	@SerializedName("address_no")
	@JsonField(name ="address_no")
	private String addressNo;

	@SerializedName("blog_reviews")
	@JsonField(name ="blog_reviews")
	private String blogReviews;

	@SerializedName("category_name")
	@JsonField(name ="category_name")
	private String categoryName;

	@SerializedName("day_off")
	@JsonField(name ="day_off")
	private String dayOff;

	@SerializedName("is_picked")
	@JsonField(name ="is_picked")
	private String isPicked;

	@SerializedName("tmap_id")
	@JsonField(name ="tmap_id")
	private String tmapId;

	@SerializedName("aoi_id")
	@JsonField(name ="aoi_id")
	private String aoiId;

	@SerializedName("is_wished")
	@JsonField(name ="is_wished")
	private String isWished;

	@SerializedName("aoi_name")
	@JsonField(name ="aoi_name")
	private String aoiName;

	@SerializedName("recent_image")
	@JsonField(name ="recent_image")
	private String recentImage;

	@SerializedName("category_id")
	@JsonField(name ="category_id")
	private String categoryId;

	@SerializedName("blog_review_count")
	@JsonField(name ="blog_review_count")
	private String blogReviewCount;

	@SerializedName("price")
	@JsonField(name ="price")
	private String price;

	@SerializedName("star_rating")
	@JsonField(name ="star_rating")
	private String starRating;

	@SerializedName("reservation")
	@JsonField(name ="reservation")
	private String reservation;

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

	@SerializedName("insta")
	@JsonField(name ="insta")
	private List<InstaItem> insta;

	@SerializedName("images")
	@JsonField(name ="images")
	private String images;

	@SerializedName("wifi")
	@JsonField(name ="wifi")
	private String wifi;

	@SerializedName("lng")
	@JsonField(name ="lng")
	private String lng;

	@SerializedName("like")
	@JsonField(name ="like")
	private int like;

	@SerializedName("rater")
	@JsonField(name ="rater")
	private String rater;

	@SerializedName("image_url")
	@JsonField(name ="image_url")
	private String imageUrl;

	@SerializedName("picks")
	@JsonField(name ="picks")
	private String picks;

	@SerializedName("comms_id")
	@JsonField(name ="comms_id")
	private String commsId;

	@SerializedName("road_address")
	@JsonField(name ="road_address")
	private String roadAddress;

	@SerializedName("l_area_nm")
	@JsonField(name ="l_area_nm")
	private String lAreaNm;

	@SerializedName("NAME")
	@JsonField(name ="NAME")
	private String nAME;

	@SerializedName("seat")
	@JsonField(name ="seat")
	private String seat;

	@SerializedName("pick_count")
	@JsonField(name ="pick_count")
	private String pickCount;

	@SerializedName("phone")
	@JsonField(name ="phone")
	private String phone;

	@SerializedName("location")
	@JsonField(name ="location")
	private String location;

	@SerializedName("comment")
	@JsonField(name ="comment")
	private String comment;

	@SerializedName("valet_parking")
	@JsonField(name ="valet_parking")
	private String valetParking;

	@SerializedName("homepage")
	@JsonField(name ="homepage")
	private String homepage;

	public void setBusinessHours(String businessHours){
		this.businessHours = businessHours;
	}

	public String getBusinessHours(){
		return businessHours;
	}

	public void setParking(String parking){
		this.parking = parking;
	}

	public String getParking(){
		return parking;
	}

	public void setAddressNo(String addressNo){
		this.addressNo = addressNo;
	}

	public String getAddressNo(){
		return addressNo;
	}

	public void setBlogReviews(String blogReviews){
		this.blogReviews = blogReviews;
	}

	public String getBlogReviews(){
		return blogReviews;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setDayOff(String dayOff){
		this.dayOff = dayOff;
	}

	public String getDayOff(){
		return dayOff;
	}

	public void setIsPicked(String isPicked){
		this.isPicked = isPicked;
	}

	public String getIsPicked(){
		return isPicked;
	}

	public void setTmapId(String tmapId){
		this.tmapId = tmapId;
	}

	public String getTmapId(){
		return tmapId;
	}

	public void setAoiId(String aoiId){
		this.aoiId = aoiId;
	}

	public String getAoiId(){
		return aoiId;
	}

	public void setIsWished(String isWished){
		this.isWished = isWished;
	}

	public String getIsWished(){
		return isWished;
	}

	public void setAoiName(String aoiName){
		this.aoiName = aoiName;
	}

	public String getAoiName(){
		return aoiName;
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

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setStarRating(String starRating){
		this.starRating = starRating;
	}

	public String getStarRating(){
		return starRating;
	}

	public void setReservation(String reservation){
		this.reservation = reservation;
	}

	public String getReservation(){
		return reservation;
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

	public void setInsta(List<InstaItem> insta){
		this.insta = insta;
	}

	public List<InstaItem> getInsta(){
		return insta;
	}

	public void setImages(String images){
		this.images = images;
	}

	public String getImages(){
		return images;
	}

	public void setWifi(String wifi){
		this.wifi = wifi;
	}

	public String getWifi(){
		return wifi;
	}

	public void setLng(String lng){
		this.lng = lng;
	}

	public String getLng(){
		return lng;
	}

	public void setLike(int like){
		this.like = like;
	}

	public int getLike(){
		return like;
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

	public void setPicks(String picks){
		this.picks = picks;
	}

	public String getPicks(){
		return picks;
	}

	public void setCommsId(String commsId){
		this.commsId = commsId;
	}

	public String getCommsId(){
		return commsId;
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

	public void setSeat(String seat){
		this.seat = seat;
	}

	public String getSeat(){
		return seat;
	}

	public void setPickCount(String pickCount){
		this.pickCount = pickCount;
	}

	public String getPickCount(){
		return pickCount;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return comment;
	}

	public void setValetParking(String valetParking){
		this.valetParking = valetParking;
	}

	public String getValetParking(){
		return valetParking;
	}

	public void setHomepage(String homepage){
		this.homepage = homepage;
	}

	public String getHomepage(){
		return homepage;
	}

	@Override
 	public String toString(){
		return 
			"Store{" + 
			"business_hours = '" + businessHours + '\'' + 
			",parking = '" + parking + '\'' + 
			",address_no = '" + addressNo + '\'' + 
			",blog_reviews = '" + blogReviews + '\'' + 
			",category_name = '" + categoryName + '\'' + 
			",day_off = '" + dayOff + '\'' + 
			",is_picked = '" + isPicked + '\'' + 
			",tmap_id = '" + tmapId + '\'' + 
			",aoi_id = '" + aoiId + '\'' + 
			",is_wished = '" + isWished + '\'' + 
			",aoi_name = '" + aoiName + '\'' + 
			",recent_image = '" + recentImage + '\'' + 
			",category_id = '" + categoryId + '\'' + 
			",blog_review_count = '" + blogReviewCount + '\'' + 
			",price = '" + price + '\'' + 
			",star_rating = '" + starRating + '\'' + 
			",reservation = '" + reservation + '\'' + 
			",category_l_name = '" + categoryLName + '\'' + 
			",id = '" + id + '\'' + 
			",addr = '" + addr + '\'' + 
			",bld_no = '" + bldNo + '\'' + 
			",lat = '" + lat + '\'' + 
			",is_closed = '" + isClosed + '\'' + 
			",insta = '" + insta + '\'' + 
			",images = '" + images + '\'' + 
			",wifi = '" + wifi + '\'' + 
			",lng = '" + lng + '\'' + 
			",like = '" + like + '\'' + 
			",rater = '" + rater + '\'' + 
			",image_url = '" + imageUrl + '\'' + 
			",picks = '" + picks + '\'' + 
			",comms_id = '" + commsId + '\'' + 
			",road_address = '" + roadAddress + '\'' + 
			",l_area_nm = '" + lAreaNm + '\'' + 
			",nAME = '" + nAME + '\'' + 
			",seat = '" + seat + '\'' + 
			",pick_count = '" + pickCount + '\'' + 
			",phone = '" + phone + '\'' + 
			",location = '" + location + '\'' + 
			",comment = '" + comment + '\'' + 
			",valet_parking = '" + valetParking + '\'' + 
			",homepage = '" + homepage + '\'' + 
			"}";
		}
}