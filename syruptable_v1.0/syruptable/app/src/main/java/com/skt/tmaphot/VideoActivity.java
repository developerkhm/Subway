package com.skt.tmaphot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_layout);

        Intent intent = getIntent();
        String videoPath = intent.getStringExtra("videoPath");

        // VideoView : 동영상을 재생하는 뷰
        VideoView videoView = (VideoView) findViewById(R.id.videoView1);

        // MediaController : 특정 View 위에서 작동하는 미디어 컨트롤러 객체
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc); // Video View 에 사용할 컨트롤러 지정

//        String path = Environment.getExternalStorageDirectory()
//                .getAbsolutePath(); // 기본적인 절대경로 얻어오기


//        videoView.setVideoPath(path + "/video/kakaotalk_1458998519582.3gp");
        videoView.setVideoPath(videoPath);
        // VideoView 로 재생할 영상
        // 아까 동영상 [상세정보] 에서 확인한 경로

        videoView.requestFocus(); // 포커스 얻어오기
        videoView.start(); // 동영상 재생
        mc.show(2);
    } // end of onCreate
} // end of class




