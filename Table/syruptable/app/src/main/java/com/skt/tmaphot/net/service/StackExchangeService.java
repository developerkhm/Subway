package com.skt.tmaphot.net.service;

import com.skt.tmaphot.net.model.HotplaceModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface StackExchangeService {
    @GET("/2.2/users?order=desc&sort=reputation&site=stackoverflow")
    Flowable<UsersResponse> getMostPopularSOusers(@Query("pagesize") int howmany);

    @GET("/2.2/users?order=desc&sort=reputation&site=stackoverflow")
    Observable<UsersResponse> getPosts(@Query("pagesize") int howmany);

    @POST("hotplace/getList")
    @FormUrlEncoded
    Observable <List<HotplaceModel>> getHotplaceList(
            @Field("page") int page,
            @Field("lat") double lat,
            @Field("lng") double lng,
            @Field("sort") int sort
    );
}
