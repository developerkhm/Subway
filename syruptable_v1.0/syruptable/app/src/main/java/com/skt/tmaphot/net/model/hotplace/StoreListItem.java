package com.skt.tmaphot.net.model.hotplace;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class StoreListItem{

	@SerializedName("new_addr_1")
	@JsonField(name ="new_addr_1")
	private String newAddr1;

	@SerializedName("new_addr_2")
	@JsonField(name ="new_addr_2")
	private String newAddr2;

	@SerializedName("category_name")
	@JsonField(name ="category_name")
	private String categoryName;

	@SerializedName("dk")
	@JsonField(name ="dk")
	private String dk;

	@SerializedName("open_time")
	@JsonField(name ="open_time")
	private String openTime;

	@SerializedName("dm")
	@JsonField(name ="dm")
	private String dm;

	@SerializedName("zip_code")
	@JsonField(name ="zip_code")
	private String zipCode;

	@SerializedName("thumb_url")
	@JsonField(name ="thumb_url")
	private String thumbUrl;

	@SerializedName("home_page")
	@JsonField(name ="home_page")
	private String homePage;

	@SerializedName("category_id")
	@JsonField(name ="category_id")
	private String categoryId;

	@SerializedName("holidays")
	@JsonField(name ="holidays")
	private String holidays;

	@SerializedName("blog_review_count")
	@JsonField(name ="blog_review_count")
	private String blogReviewCount;

	@SerializedName("star_rating")
	@JsonField(name ="star_rating")
	private String starRating;

	@SerializedName("id")
	@JsonField(name ="id")
	private String id;

	@SerializedName("menus")
	@JsonField(name ="menus")
	private String menus;

	@SerializedName("addr")
	@JsonField(name ="addr")
	private String addr;

	@SerializedName("lat")
	@JsonField(name ="lat")
	private String lat;

	@SerializedName("addr_txt_info")
	@JsonField(name ="addr_txt_info")
	private String addrTxtInfo;

	@SerializedName("lng")
	@JsonField(name ="lng")
	private String lng;

	@SerializedName("d")
	@JsonField(name ="d")
	private String D;

	@SerializedName("rater")
	@JsonField(name ="rater")
	private String rater;

	@SerializedName("image_url")
	@JsonField(name ="image_url")
	private String imageUrl;

	@SerializedName("NAME")
	@JsonField(name ="NAME")
	private String nAME;

	@SerializedName("pick_count")
	@JsonField(name ="pick_count")
	private String pickCount;

	@SerializedName("info_txt")
	@JsonField(name ="info_txt")
	private String infoTxt;

	public void setNewAddr1(String newAddr1){
		this.newAddr1 = newAddr1;
	}

	public String getNewAddr1(){
		return newAddr1;
	}

	public void setNewAddr2(String newAddr2){
		this.newAddr2 = newAddr2;
	}

	public String getNewAddr2(){
		return newAddr2;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setDk(String dk){
		this.dk = dk;
	}

	public String getDk(){
		return dk;
	}

	public void setOpenTime(String openTime){
		this.openTime = openTime;
	}

	public String getOpenTime(){
		return openTime;
	}

	public void setDm(String dm){
		this.dm = dm;
	}

	public String getDm(){
		return dm;
	}

	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
	}

	public String getZipCode(){
		return zipCode;
	}

	public void setthumbUrl(String thumbUrl){
		this.thumbUrl = thumbUrl;
	}

	public String getthumbUrl(){
		return thumbUrl;
	}

	public void setHomePage(String homePage){
		this.homePage = homePage;
	}

	public String getHomePage(){
		return homePage;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public void setHolidays(String holidays){
		this.holidays = holidays;
	}

	public String getHolidays(){
		return holidays;
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

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setMenus(String menus){
		this.menus = menus;
	}

	public String getMenus(){
		return menus;
	}

	public void setAddr(String addr){
		this.addr = addr;
	}

	public String getAddr(){
		return addr;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	public void setAddrTxtInfo(String addrTxtInfo){
		this.addrTxtInfo = addrTxtInfo;
	}

	public String getAddrTxtInfo(){
		return addrTxtInfo;
	}

	public void setLng(String lng){
		this.lng = lng;
	}

	public String getLng(){
		return lng;
	}

	public void setD(String D){
		this.D = D;
	}

	public String getD(){
		return D;
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

	public void setInfoTxt(String infoTxt){
		this.infoTxt = infoTxt;
	}

	public String getInfoTxt(){
		return infoTxt;
	}

	@Override
 	public String toString(){
		return 
			"StoreListItem{" + 
			"new_addr_1 = '" + newAddr1 + '\'' + 
			",new_addr_2 = '" + newAddr2 + '\'' + 
			",category_name = '" + categoryName + '\'' + 
			",dk = '" + dk + '\'' + 
			",open_time = '" + openTime + '\'' + 
			",dm = '" + dm + '\'' + 
			",zip_code = '" + zipCode + '\'' + 
			",thumb_url = '" + thumbUrl + '\'' +
			",home_page = '" + homePage + '\'' + 
			",category_id = '" + categoryId + '\'' + 
			",holidays = '" + holidays + '\'' + 
			",blog_review_count = '" + blogReviewCount + '\'' + 
			",star_rating = '" + starRating + '\'' + 
			",id = '" + id + '\'' + 
			",menus = '" + menus + '\'' + 
			",addr = '" + addr + '\'' + 
			",lat = '" + lat + '\'' + 
			",addr_txt_info = '" + addrTxtInfo + '\'' + 
			",lng = '" + lng + '\'' + 
			",d = '" + D + '\'' + 
			",rater = '" + rater + '\'' + 
			",image_url = '" + imageUrl + '\'' + 
			",nAME = '" + nAME + '\'' + 
			",pick_count = '" + pickCount + '\'' + 
			",info_txt = '" + infoTxt + '\'' + 
			"}";
		}
}