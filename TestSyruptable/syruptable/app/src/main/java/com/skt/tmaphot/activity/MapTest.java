package com.skt.tmaphot.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.skt.tmaphot.R;

public class MapTest extends NMapActivity {

    private NMapView mMapView;// 지도 화면 View
    private final String CLIENT_ID = "WXXAcA1gj_MhSZlxJkes";// 애플리케이션 클라이언트 아이디 값

    LinearLayout maptest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maptest_layout);

        mMapView = (NMapView)findViewById(R.id.mapTest);
        mMapView.setClientId(CLIENT_ID); // 클라이언트 아이디 값 설정
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();

        mMapView.setOnMapStateChangeListener(new NMapView.OnMapStateChangeListener() {
            @Override
            public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {

            }

            @Override
            public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {

            }

            @Override
            public void onMapCenterChangeFine(NMapView nMapView) {

            }

            @Override
            public void onZoomLevelChange(NMapView nMapView, int i) {

            }

            @Override
            public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

            }
        });


//        //내장카메라 호출
//        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//        //동영상 품질
//        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
//        //동영상 시간 제한
////                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 111);// 시간
//        //동영상 용량 제한
//        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, (long) (1024 * 1024 * 4));// 용량
//        //동영상 저장 경로
//        String mImageMovieUri = "/sdcard/Download/exam/";
////                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
////                        mImageMovieUri);
//        startActivityForResult(intent, 2);
    }
}
