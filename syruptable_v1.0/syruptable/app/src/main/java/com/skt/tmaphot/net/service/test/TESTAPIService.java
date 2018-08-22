package com.skt.tmaphot.net.service.test;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TESTAPIService {
//    @GET("/2.2/users?order=desc&sort=reputation&site=stackoverflow")
//    Flowable<UsersResponse> getMostPopularSOusers(@Query("pagesize") int howmany);
//
    @GET("posts")
    Observable<List<PostDatum>> getPosts();

    @GET("albums")
    Observable<List<Album>> getAlbums();


//    @POST("hotplace/getList")
//    @FormUrlEncoded
//    Observable<List<HotplaceModel>> getHotplaceList(
//            @Field("page") int page,
//            @Field("per_page") int per_page,
//            @Field("lat") double lat,
//            @Field("lng") double lng,
//            @Field("sort") int sort,
//            @Field("cate") String cate
//    );
//
//
////    @POST("hotplace/getList")
////    @FormUrlEncoded
////    Call<List<HotplaceModel>> getHotplaceList(
////            @Field("page") int page,
////            @Field("per_page") int per_page,
////            @Field("lat") double lat,
////            @Field("lng") double lng,
////            @Field("sort") int sort
////    );
//
//    @POST("users/info")
//    @FormUrlEncoded
//    Observable<UserInfoModel> getUserInfo(@Field("id") String id);
//
//    @POST("{subpath}/{id}")
//    Observable<StoreInfoModel> getStoreInfo(
//            @Path("subpath") String subpath,
//            @Path("id") String id);
//
//
////    @POST("users/logout")
////    Call<ResponseBody> logout(
////            @Field("id") String id
////    );
//
//    @POST("logout")
//    Call<ResponseBody> logout();
}
