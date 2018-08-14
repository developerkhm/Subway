package com.skt.tmaphot.net.service;

import com.skt.tmaphot.net.model.hotplace.HotplaceModel;
import com.skt.tmaphot.net.model.store.StoreInfoModel;
import com.skt.tmaphot.net.model.user.UserInfoModel;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
//    @GET("/2.2/users?order=desc&sort=reputation&site=stackoverflow")
//    Flowable<UsersResponse> getMostPopularSOusers(@Query("pagesize") int howmany);
//
//    @GET("/2.2/users?order=desc&sort=reputation&site=stackoverflow")
//    Observable<UsersResponse> getPosts(@Query("pagesize") int howmany);

    @POST("hotplace/getList")
    @FormUrlEncoded
    Observable<List<HotplaceModel>> getHotplaceList(
            @Field("page") int page,
            @Field("per_page") int per_page,
            @Field("lat") double lat,
            @Field("lng") double lng,
            @Field("sort") int sort
    );

    @POST("users/info")
    @FormUrlEncoded
    Observable<UserInfoModel> getUserInfo(@Field("id") String id);

    @POST("{subpath}/{id}")
    Observable<StoreInfoModel> getStoreInfo(
            @Path("subpath") String subpath,
            @Path("id") String id);


    @POST("users/logout")
    @FormUrlEncoded
    Call<ResponseBody> logout(
            @Field("id") String id
    );
}
