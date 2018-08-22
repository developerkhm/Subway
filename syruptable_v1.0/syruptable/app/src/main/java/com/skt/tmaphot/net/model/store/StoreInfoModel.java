
package com.skt.tmaphot.net.model.store;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreInfoModel {

    @SerializedName("reviewList")
    @Expose
    private List<ReviewList> reviewList = null;
    @SerializedName("store")
    @Expose
    private Store store;

    public List<ReviewList> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<ReviewList> reviewList) {
        this.reviewList = reviewList;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

}
