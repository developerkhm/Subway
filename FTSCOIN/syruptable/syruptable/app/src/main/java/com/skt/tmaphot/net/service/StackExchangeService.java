package com.skt.tmaphot.net.service;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StackExchangeService {
    @GET("/2.2/users?order=desc&sort=reputation&site=stackoverflow")
    Flowable<UsersResponse> getMostPopularSOusers(@Query("pagesize") int howmany);
}
