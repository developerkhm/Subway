
package com.skt.tmaphot.net.model.store;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Store {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("addr")
    @Expose
    private String addr;
    @SerializedName("pick_count")
    @Expose
    private String pickCount;
    @SerializedName("blog_review_count")
    @Expose
    private String blogReviewCount;
    @SerializedName("rater")
    @Expose
    private String rater;
    @SerializedName("star_rating")
    @Expose
    private String starRating;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("recent_image")
    @Expose
    private String recentImage;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("picks")
    @Expose
    private String picks;
    @SerializedName("blog_reviews")
    @Expose
    private String blogReviews;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("is_picked")
    @Expose
    private String isPicked;
    @SerializedName("is_wished")
    @Expose
    private String isWished;
    @SerializedName("is_closed")
    @Expose
    private String isClosed;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_l_name")
    @Expose
    private String categoryLName;
    @SerializedName("address_no")
    @Expose
    private String addressNo;
    @SerializedName("road_address")
    @Expose
    private String roadAddress;
    @SerializedName("l_area_nm")
    @Expose
    private String lAreaNm;
    @SerializedName("bld_no")
    @Expose
    private String bldNo;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("business_hours")
    @Expose
    private String businessHours;
    @SerializedName("day_off")
    @Expose
    private String dayOff;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("parking")
    @Expose
    private String parking;
    @SerializedName("valet_parking")
    @Expose
    private String valetParking;
    @SerializedName("wifi")
    @Expose
    private String wifi;
    @SerializedName("seat")
    @Expose
    private String seat;
    @SerializedName("reservation")
    @Expose
    private String reservation;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("tmap_id")
    @Expose
    private String tmapId;
    @SerializedName("comms_id")
    @Expose
    private String commsId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("aoi_id")
    @Expose
    private String aoiId;
    @SerializedName("aoi_name")
    @Expose
    private String aoiName;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("insta")
    @Expose
    private List<Instum> insta = null;
    @SerializedName("like")
    @Expose
    private String like;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNAME() {
        return nAME;
    }

    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPickCount() {
        return pickCount;
    }

    public void setPickCount(String pickCount) {
        this.pickCount = pickCount;
    }

    public String getBlogReviewCount() {
        return blogReviewCount;
    }

    public void setBlogReviewCount(String blogReviewCount) {
        this.blogReviewCount = blogReviewCount;
    }

    public String getRater() {
        return rater;
    }

    public void setRater(String rater) {
        this.rater = rater;
    }

    public String getStarRating() {
        return starRating;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRecentImage() {
        return recentImage;
    }

    public void setRecentImage(String recentImage) {
        this.recentImage = recentImage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPicks() {
        return picks;
    }

    public void setPicks(String picks) {
        this.picks = picks;
    }

    public String getBlogReviews() {
        return blogReviews;
    }

    public void setBlogReviews(String blogReviews) {
        this.blogReviews = blogReviews;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getIsPicked() {
        return isPicked;
    }

    public void setIsPicked(String isPicked) {
        this.isPicked = isPicked;
    }

    public String getIsWished() {
        return isWished;
    }

    public void setIsWished(String isWished) {
        this.isWished = isWished;
    }

    public String getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(String isClosed) {
        this.isClosed = isClosed;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryLName() {
        return categoryLName;
    }

    public void setCategoryLName(String categoryLName) {
        this.categoryLName = categoryLName;
    }

    public String getAddressNo() {
        return addressNo;
    }

    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public void setRoadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }

    public String getLAreaNm() {
        return lAreaNm;
    }

    public void setLAreaNm(String lAreaNm) {
        this.lAreaNm = lAreaNm;
    }

    public String getBldNo() {
        return bldNo;
    }

    public void setBldNo(String bldNo) {
        this.bldNo = bldNo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public String getDayOff() {
        return dayOff;
    }

    public void setDayOff(String dayOff) {
        this.dayOff = dayOff;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getValetParking() {
        return valetParking;
    }

    public void setValetParking(String valetParking) {
        this.valetParking = valetParking;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getReservation() {
        return reservation;
    }

    public void setReservation(String reservation) {
        this.reservation = reservation;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTmapId() {
        return tmapId;
    }

    public void setTmapId(String tmapId) {
        this.tmapId = tmapId;
    }

    public String getCommsId() {
        return commsId;
    }

    public void setCommsId(String commsId) {
        this.commsId = commsId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAoiId() {
        return aoiId;
    }

    public void setAoiId(String aoiId) {
        this.aoiId = aoiId;
    }

    public String getAoiName() {
        return aoiName;
    }

    public void setAoiName(String aoiName) {
        this.aoiName = aoiName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public List<Instum> getInsta() {
        return insta;
    }

    public void setInsta(List<Instum> insta) {
        this.insta = insta;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

}
