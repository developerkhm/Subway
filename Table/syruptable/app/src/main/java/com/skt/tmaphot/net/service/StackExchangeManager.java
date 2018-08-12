package com.skt.tmaphot.net.service;

import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class StackExchangeManager {



    private final StackExchangeService mStackExchangeService;

    public StackExchangeManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mStackExchangeService = retrofit.create(StackExchangeService.class);
    }

    public Flowable<List<Item>> getMostPopularSQysers(int howmany){
        return mStackExchangeService
                .getMostPopularSOusers(howmany)
                .map(new Function<UsersResponse, List<Item>>() {
                    @Override
                    public List<Item> apply(@NonNull UsersResponse usersResponse) throws Exception {
                        Log.d("debug","map");
                        return usersResponse.getItems();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getService(){

    }
}

