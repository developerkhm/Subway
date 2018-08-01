package com.skt.tmaphot.activity.review;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.MainApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.review.multiple.activities.AlbumSelectActivity;
import com.skt.tmaphot.activity.review.multiple.helpers.Constants;
import com.skt.tmaphot.activity.review.multiple.models.Image;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReviewWriteActivity extends BaseActivity {
    ImageView img;
    Uri takePhotoUri;
    String mCurrentPhotoPath;


    private LinearLayout tempLay;

    private static final int TAKE_FROM_CAMERA = 1000; //카메라 촬영으로 사진 가져오기
    private static final int SELECT_FROM_ALBUM = 2000; //앨범에서 사진 가져오기
    private static final int FROM_VIDEO = 3000; //가져온 사진을 자르기 위한 변수


    private LinearLayout addImageArea;
    private LinearLayout addVideoArea;
    private ImageView addImageView, addVideoView;
    private int displayWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewwrite_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        displayWidth = metrics.widthPixels;

        addImageArea = (LinearLayout) findViewById(R.id.activity_reviewwrite_upload_image_area);
        addVideoArea = (LinearLayout) findViewById(R.id.activity_reviewwrite_upload_video_area);
        Button takePhotoBtn = (Button) findViewById(R.id.activity_reviewwrite_take_capture);
        takePhotoBtn.setOnClickListener(onClickListener);
        Button takeVideoBtn = (Button) findViewById(R.id.activity_reviewwrite_take_video);
        takeVideoBtn.setOnClickListener(onClickListener);

        addImageView = new ImageView(this);
        addImageView.setBackgroundColor(Color.BLACK);
        addImageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addImageView.getLayoutParams().width = displayWidth / 3;
        addImageView.getLayoutParams().height = displayWidth / 3;
        addImageView.requestLayout();
        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });

        addImageArea.addView(addImageView);

        addVideoView = new ImageView(this);
        addVideoView.setBackgroundColor(Color.BLACK);
        addVideoView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addVideoView.getLayoutParams().width = displayWidth / 3;
        addVideoView.getLayoutParams().height = displayWidth / 3;
        addVideoView.requestLayout();
        addVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectVideo();
            }
        });

        addVideoArea.addView(addVideoView);

    } /////////////////END

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.activity_reviewwrite_take_capture:
                    takePhoto();
                    break;

                case R.id.activity_reviewwrite_take_video:
                    takeVideo();
                    break;
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("UU", "ReviewActivity3 : onActivityResult requestCode :" + requestCode);
        switch (requestCode) {
            case TAKE_FROM_CAMERA:

                try {

//                    Log.d("AAAB","TAKE_FROM_CAMERA" + takePhotoUri.toString());
//                    Log.d("AAAB","TAKE_FROM_CAMERA" + takePhotoUri.getPath());

                    MainApplication.loadUriImage(this, takePhotoUri, creatImageView(false));

                    // 갤러리 이미지 갱신
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    File f = new File(mCurrentPhotoPath);
                    Uri contentUri = Uri.fromFile(f);
                    mediaScanIntent.setData(contentUri);
                    this.sendBroadcast(mediaScanIntent);
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage().toString());
                }
                addImageArea.addView(addImageView);
                break;
            case SELECT_FROM_ALBUM:

                ArrayList<Image> imageList = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
//                Log.d("AAAB","SELECT_FROM_ALBUM" + imageList.get(0).path);
//                Log.d("AAAB","SELECT_FROM_ALBUM" + Uri.parse(imageList.get(0).path));

                for (Image image : imageList) {
                    MainApplication.loadUriImage(this, Uri.fromFile(new File(image.path)), creatImageView(false));
                }
                addImageArea.addView(addImageView);
                break;

            case FROM_VIDEO:
                Uri videoUri = data.getData();
//                Log.d("AAAB","FROM_VIDEO" + videoUri.toString());
//                Log.d("AAAB","FROM_VIDEO" + videoUri.getPath());
                MainApplication.loadUriImage(this, videoUri, creatImageView(true));
                addVideoArea.addView(addVideoView);
                break;


        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
            mCurrentPhotoPath = photoFile.getAbsolutePath();

        } catch (IOException e) {
            Toast.makeText(ReviewWriteActivity.this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (photoFile != null) {
            takePhotoUri = FileProvider.getUriForFile(ReviewWriteActivity.this, "com.skt.tmaphot.provider", photoFile); //FileProvider의 경우 이전 포스트를 참고하세요.
            intent.putExtra(MediaStore.EXTRA_OUTPUT, takePhotoUri); //사진을 찍어 해당 Content uri를 photoUri에 적용시키기 위함
            startActivityForResult(intent, TAKE_FROM_CAMERA);
        }
    }

    private void selectPhoto() {
        Intent intent = new Intent(this, AlbumSelectActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 10);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    private void takeVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); //동영상 품질
//      intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 111); //동영상 시간 제한
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, (long) (1024 * 1024 * 4)); //동영상 용량 제한
        //동영상 저장 경로
//      String mImageMovieUri = "/sdcard/Download/exam/";
//      intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,mImageMovieUri);
        startActivityForResult(intent, FROM_VIDEO);
    }

    private void selectVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK); //ACTION_PICK 즉 사진을 고르겠다!
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("video/*");
        startActivityForResult(intent, FROM_VIDEO);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "IP" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/test/"); //test라는 경로에 이미지를 저장하기 위함
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }

    private ImageView creatImageView(boolean isVidio) {
        ImageView newImageView = new ImageView(this);
        newImageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        newImageView.getLayoutParams().width = displayWidth / 3;
        newImageView.getLayoutParams().height = displayWidth / 3;
        newImageView.requestLayout();
        if (!isVidio) {
            addImageArea.removeView(addImageView);
            addImageArea.addView(newImageView);
        } else {
            addVideoArea.removeView(addVideoView);
            addVideoArea.addView(newImageView);
        }

        return newImageView;
    }

//    private void goToAlbum() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//        startActivityForResult(intent, PICK_FROM_ALBUM);
//    }

}


