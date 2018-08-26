package com.skt.tmaphot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.skt.tmaphot.location.GPSData;
import com.skt.tmaphot.net.model.hotplace.HotplacePojo;
import com.skt.tmaphot.net.model.user.UserInfoPojo;
import com.skt.tmaphot.net.service.APIClient;
import com.skt.tmaphot.net.service.APIService;
import com.skt.tmaphot.net.service.LoginInfo;
import com.skt.tmaphot.net.service.test.MainData;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoadingActivity extends AppCompatActivity {

    public final static String MAINACTIVITY = MainActivity.class.getName();

    private APIService APIService = APIClient.getInstance().getClient2(null);

    private int hotplace_curruntPage = 0;
    private int hotplace_per_page = 20;
    private int hotplace_sortType = 1;
    private String large_cd = "";
    private String middle_cd = "";
    private String small_cd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

//        if (getCallingActivity().getClassName().equals(MainActivity.class.getName())) {
//        if (getCallingActivity().getClassName().equals("MainActivity")) {
//            initMainGetData();
//        }

        Intent intent = getIntent();
        String key = intent.getStringExtra(BaseApplication.ACTIVITY_KEY);

        if(key.equals(LoadingActivity.MAINACTIVITY))
        {
            initMainGetData();
        }
    }

    public void initMainGetData() {

        //        List<Observable<?>> requests = new ArrayList<>();

        Observable<UserInfoPojo> userInfoModelObservable = APIService.getUserInfo(
                LoginInfo.getInstance().getUserId()
        );

        Observable<HotplacePojo> hotplacePojoObservable = APIService.getHotplaceList(
                hotplace_curruntPage,
                hotplace_per_page,
                GPSData.getInstance().getLatitude(),
                GPSData.getInstance().getLongitude(),
                hotplace_sortType,
                large_cd,
                middle_cd,
                small_cd
        ).subscribeOn(Schedulers.io());


        Observable.zip(userInfoModelObservable, hotplacePojoObservable, (userInfo, hotplace) ->{
            return new MainData(userInfo, hotplace);
        })
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MainData>() {

                    MainData mainData = null;

                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        Log.d("test126734","onSubscribe");
                    }

                    @Override
                    public void onNext(MainData dataTest) {
                        mainData = dataTest;
                        Log.d("test126734","onNext");
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.d("test126734","onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("test126734","onComplete");
                        Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                        intent.putExtra(MAINACTIVITY, mainData);
                        BaseApplication.getInstance().startActivity(intent);
                    }
                });
    }


//        Observable<List<PostDatum>> postsObservable = TESTAPIClient.getInstance().getClient("").getPosts()
//                .subscribeOn(Schedulers.io());
//
//        Observable<List<Album>> albumObservable = TESTAPIClient.getInstance().getClient("").getAlbums()
//                .subscribeOn(Schedulers.io());
//
//
//        Observable.zip(postsObservable, albumObservable, (v, v2) -> {
//            // if there is any need to modify the two response or combined response object.
//            return new DataTest(v, v2);
//        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<DataTest>() {
//            @Override
//            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(DataTest dataTest) {
//                Log.d("Test", "onNext");
//                Log.d("Test", dataTest.getAlbums().get(0).getTitle());
//
//            }
//
//            @Override
//            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//    }

}
