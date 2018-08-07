package com.skt.tmaphot.activity.review;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.MapWebViewActivity;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.review.multiple.activities.AlbumSelectActivity;
import com.skt.tmaphot.activity.review.multiple.helpers.Constants;
import com.skt.tmaphot.activity.review.multiple.models.Image;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewWriteActivity extends BaseActivity {

    private final int TAKE_FROM_CAMERA = 1000; //카메라 촬영으로 사진 가져오기
    private final int SELECT_FROM_ALBUM = 2000; //앨범에서 사진 가져오기
    private final int FROM_VIDEO = 3000; //가져온 사진을 자르기

    private final int REQUEST_ID_MULTIPLE_PERMISSIONS = 777;    //권한체크
    private final long MAX_VIDEO_SIZE = (long) (1024 * 1024 * 20);  // 20MB?

    private Uri takePhotoUri;
    private String mCurrentPhotoPath;
    private int displayWidth;

    private LinearLayout mainLinearLayout;
    private LinearLayout addImageArea;
    private LinearLayout addVideoArea;
    private TextView addImageView, addVideoView;

    private List<UplaodItem> uplaodImageItems, uploadVideoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewwrite_layout);
        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        displayWidth = metrics.widthPixels;

        mainLinearLayout = (LinearLayout) findViewById(R.id.reviewwrite_main);
        addImageArea = (LinearLayout) findViewById(R.id.activity_reviewwrite_upload_image_area);

//        addImageArea.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        addImageArea.getLayoutParams().width = displayWidth;

        addVideoArea = (LinearLayout) findViewById(R.id.activity_reviewwrite_upload_video_area);
        LinearLayout takePhotoBtn = (LinearLayout) findViewById(R.id.activity_reviewwrite_take_capture);
        takePhotoBtn.setOnClickListener(onClickListener);
        LinearLayout takeVideoBtn = (LinearLayout) findViewById(R.id.activity_reviewwrite_take_video);
        takeVideoBtn.setOnClickListener(onClickListener);

        addImageView = new TextView(this);
        addImageView.setBackgroundColor(getResources().getColor(R.color.text_gray_d4));
        addImageView.setText("사진 + ");
//        addImageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        addImageView.setGravity(Gravity.CENTER);
        addImageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addImageView.getLayoutParams().width = displayWidth / 3;
        addImageView.getLayoutParams().height = displayWidth / 3;
        addImageView.setPadding(0, 0, 2, 0);
//        addImageView.requestLayout();
        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });

        addImageArea.addView(addImageView);

        addVideoView = new TextView(this);
        addVideoView.setBackgroundColor(getResources().getColor(R.color.text_gray_d4));
        addVideoView.setText("동영상 + ");
        addVideoView.setGravity(Gravity.CENTER);
        addVideoView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addVideoView.getLayoutParams().width = displayWidth / 3;
        addVideoView.getLayoutParams().height = displayWidth / 3;
        addVideoView.setPadding(0, 0, 2, 0);
//        addVideoView.requestLayout();
        addVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectVideo();
            }
        });

        addVideoArea.addView(addVideoView);

        checkAndRequestPermissions();


    } //////////////////////////////////////////////////////////////////END


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

    private class UplaodItem {
        private String id;
        private String path;
        private Uri uri;
        private String fileName;

        public UplaodItem(String id, String path, Uri uri, String fileName) {
            this.id = id;
            this.path = path;
            uri = uri;
            this.fileName = fileName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Uri getUri() {
            return uri;
        }

        public void setUri(Uri uri) {
            uri = uri;
        }


        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("UUA", "resultCode :" + resultCode);

        if (resultCode == 0) //not action
            return;

        if (uplaodImageItems == null)
            uplaodImageItems = new ArrayList<>();

        if (uploadVideoItems == null)
            uploadVideoItems = new ArrayList<>();

        switch (requestCode) {
            case TAKE_FROM_CAMERA:
                try {
                    Log.d("AAAB", "TAKE_FROM_CAMERA getPath" + takePhotoUri.getPath());
                    Log.d("AAAB", "getId" + getId(takePhotoUri.getPath()));
                    Log.d("AAAB", "getFileName" + getFileName(takePhotoUri.getPath()));
//                    Log.d("AAAB", "Function getPathFromURI" + getPathFromMediaUri(takePhotoUri));

                    String path = takePhotoUri.getPath();
                    String tagId = getId(path);
                    Uri uri = takePhotoUri;
                    String fileName = getFileName(path);

                    ImageView imageView = creatImageView(false, tagId);
                    loadImage(this, takePhotoUri, imageView, false);

                    uplaodImageItems.add(new UplaodItem(tagId, path, uri, fileName));

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

                for (Image image : imageList) {

                    String path = image.path;
                    String tagId = getId(path);
                    Uri uri = Uri.fromFile(new File(path));
                    String fileName = getFileName(path);

                    ImageView imageView = creatImageView(false, tagId);
                    loadImage(this, Uri.fromFile(new File(image.path)), imageView, false);

                    uplaodImageItems.add(new UplaodItem(tagId, path, uri, fileName));

                    Log.d("AAAB", "SELECT_FROM_ALBUM path" + image.path);
                    Log.d("AAAB", "SELECT_FROM_ALBUM id" + image.id);
                }
                addImageArea.addView(addImageView);
//                addImageArea.requestLayout();

                break;

            case FROM_VIDEO:
                Uri videoUri = data.getData();

                String path = getPathFromMediaUri(this, videoUri);
                String tagId = getId(path);
                Uri uri = videoUri;
                String fileName = getFileName(path);

                ImageView imageView = creatImageView(true, tagId);
                loadImage(this, videoUri, imageView, false);

                uploadVideoItems.add(new UplaodItem(tagId, path, uri, fileName));

                Log.d("AAAB", "FROM_VIDEO getPath" + videoUri.getPath());
                Log.d("AAAB", "Function getPathFromURI" + getPathFromMediaUri(this, videoUri));
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
            takePhotoUri = FileProvider.getUriForFile(ReviewWriteActivity.this, "com.skt.tmaphot.provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, takePhotoUri);
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
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, MAX_VIDEO_SIZE); //동영상 용량 제한
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
        String timeStamp = new SimpleDateFormat("yyyymmdd").format(new Date());
        String imageFileName = timeStamp + "_";
//        File storageDir = new File(Environment.getExternalStorageDirectory() + "/test/"); //test라는 경로에 이미지를 저장하기 위함
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/DCIM/syruptable/"); //test라는 경로에 이미지를 저장하기 위함
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

    private ImageView creatImageView(final boolean isVidio, String tagid) {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rootview = layoutInflater.inflate(R.layout.reviewwrite_image_layout, mainLinearLayout, false);
        ImageView newImageView = (ImageView) rootview.findViewById(R.id.reviewwrite_image);
        final Button deleteButton = (Button) rootview.findViewById(R.id.reviewwrite_delete);
        deleteButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        deleteButton.getLayoutParams().width = displayWidth / 9;
        deleteButton.getLayoutParams().height = displayWidth / 9;
        deleteButton.setTextColor(Color.WHITE);
        deleteButton.setTag(tagid);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootview.setVisibility(View.GONE);
                deleteItem(isVidio, String.valueOf(deleteButton.getTag()));
            }
        });

        rootview.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rootview.getLayoutParams().width = displayWidth / 3;
        rootview.getLayoutParams().height = displayWidth / 3;

        if (!isVidio) {
            addImageArea.removeView(addImageView);
            addImageArea.addView(rootview);
        } else {
            addVideoArea.removeView(addVideoView);
            addVideoArea.addView(rootview);
        }
        return newImageView;
    }

    private String getPathFromMediaUri(Context context, Uri uri) {
        String result = null;

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        int col = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        if (col >= 0 && cursor.moveToFirst())
            result = cursor.getString(col);
        cursor.close();

        return result;
    }

    private String getId(String path) {
        return path.substring(path.lastIndexOf(".") - 6, path.lastIndexOf("."));
    }

    private String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    private synchronized void deleteItem(final boolean isVideo, final String tagId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!isVideo) {
                    if (uplaodImageItems != null) {
                        for (int i = 0; i < uplaodImageItems.size(); i++) {
                            if ((uplaodImageItems.get(i).getId()).equals(tagId)) {
                                uplaodImageItems.remove(i);
                            }
                        }
                    }
                } else {
                    if (uploadVideoItems != null) {
                        for (int i = 0; i < uploadVideoItems.size(); i++) {

                            if ((uploadVideoItems.get(i).getId()).equals(tagId)) {
                                uploadVideoItems.remove(i);
                            }
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            if (baceContext instanceof Activity) {
                ((Activity) baceContext).finish();
            }
            return true;
        }

        if (item.getItemId() == R.id.action_review) {
            // 서버에 저장 처리
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void checkAndRequestPermissions() {
        int permissionWriteExternalStroage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionReadExternalStroage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionWriteExternalStroage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionReadExternalStroage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
            {
                finish();
                return;
            }
        }
    }
} //class End


