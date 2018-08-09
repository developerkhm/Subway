package com.skt.tmaphot.net.service;

import com.skt.tmaphot.net.model.MultipleResource;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StackExchangeService {
    @GET("/2.2/users?order=desc&sort=reputation&site=stackoverflow")
    Flowable<UsersResponse> getMostPopularSOusers(@Query("pagesize") int howmany);

    @GET("/2.2/users?order=desc&sort=reputation&site=stackoverflow")
    Call<UsersResponse> doGetListResources(@Query("pagesize") int howmany);
}
