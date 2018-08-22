package com.skt.tmaphot.activity.review;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.MapWebViewActivity;
import com.skt.tmaphot.R;
import com.skt.tmaphot.VideoActivity;
import com.skt.tmaphot.activity.review.multiple.activities.AlbumSelectActivity;
import com.skt.tmaphot.activity.review.multiple.helpers.Constants;
import com.skt.tmaphot.activity.review.multiple.models.Image;
import com.skt.tmaphot.common.CommonUtil;
import com.skt.tmaphot.net.service.APIClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ReviewWriteActivity extends BaseActivity {

    private String storeId;

    private final int TAKE_FROM_CAMERA = 1000; //카메라 촬영으로 사진 가져오기
    private final int SELECT_FROM_ALBUM = 2000; //앨범에서 사진 가져오기
    private final int FROM_VIDEO = 3000; //가져온 사진을 자르기

    private final int REQUEST_ID_MULTIPLE_PERMISSIONS = 777;    //권한체크
    private final long MAX_VIDEO_SIZE = (long) (1024 * 1024 * 20);  // 20MB?

    private final int MAX_ADD_IMAGE_COUNT = 5;
    private final int MAX_ADD_VEDIO_COUNT = 2;

    private Uri takePhotoUri;
    private String mCurrentPhotoPath;
    private int displayWidth;

    private LinearLayout mainLinearLayout;
    private LinearLayout addImageArea;
    private LinearLayout addVideoArea;
    private TextView addImageView, addVideoView;
    private RatingBar ratingBar;
    private EditText editText;

    private List<UplaodItem> uplaodImageItems, uploadVideoItems;
    private List<String> tempImageItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewwrite_layout);
        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();
        storeId = intent.getStringExtra("storeId");

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        displayWidth = metrics.widthPixels;

        mainLinearLayout = (LinearLayout) findViewById(R.id.reviewwrite_main);
        addImageArea = (LinearLayout) findViewById(R.id.activity_reviewwrite_upload_image_area);

        ratingBar = (RatingBar) findViewById(R.id.reviewwrite_rating);
        ratingBar.setStepSize(1f);
        CommonUtil.getInstance().setColorRatingBar(ratingBar);


        editText = (EditText) findViewById(R.id.reviewwrite_edit);


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

        addImageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addImageView.getLayoutParams().width = displayWidth / 3;
        addImageView.getLayoutParams().height = displayWidth / 3;
        addImageView.setPadding(0, 0, 2, 0);
        addImageView.setGravity(Gravity.CENTER);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            if (baceContext instanceof Activity) {
                ((Activity) baceContext).finish();
            }
            return true;
        }

        if (item.getItemId() == R.id.action_map) {
            ActivityStart(new Intent(baceContext, MapWebViewActivity.class), null);
            return true;
        }

        if (item.getItemId() == R.id.action_review) {

            completeReviewWrite(storeId);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void completeReviewWrite(String storeId) {

        String content = editText.getText().toString();
        if (content.length() == 0) {
            // 내용 작성
            alertContentPopup(editText);
            return;
        }

        progressON();

        File file = null;
        RequestBody requestFile;
        ArrayList<MultipartBody.Part> imageParts = null;
        ArrayList<MultipartBody.Part> videoParts = null;

        String store_id = storeId;
        int state = (int) ratingBar.getRating();
        Log.d("review12", "[state] : " + state);

        RequestBody contentBody = RequestBody.create(MediaType.parse("text/plain"), content);

        if (uplaodImageItems != null && uplaodImageItems.size() > 0) {
            imageParts = new ArrayList<>();

            for (UplaodItem uplaodItem : uplaodImageItems) {
                Log.d("review12", "[IMAGE] getpath : " + uplaodItem.getPath());
                file = new File(uplaodItem.getPath());
                requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                imageParts.add(MultipartBody.Part.createFormData("image[]", file.getName(), requestFile));
            }
        }

        if (uploadVideoItems != null && uploadVideoItems.size() > 0) {
            videoParts = new ArrayList<>();

            for (UplaodItem uplaodItem : uploadVideoItems) {
                Log.d("review12", "[Video] getpath : " + uplaodItem.getPath());
                file = new File(uplaodItem.getPath());
                requestFile = RequestBody.create(MediaType.parse("video/*"), file);
                videoParts.add(MultipartBody.Part.createFormData("video[]", file.getName(), requestFile));
            }
        }

        Observable<ResponseBody> observable = APIClient.getInstance().getClient("http://api.ordertable.co.kr/v1/review/set/").uploadReview(store_id, imageParts, videoParts, contentBody, state);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        progressOFF();
                    }

                    @Override
                    public void onComplete() {
                        progressOFF();
                        finish();
                    }
                });

//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });

    }

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
                    Log.d("AAAB", "TAKE_FROM_CAMERA getPath " + takePhotoUri.getPath());
                    Log.d("AAAB", "getId" + getId(takePhotoUri.getPath()));
                    Log.d("AAAB", "getFileName " + getFileName(takePhotoUri.getPath()));
                    Log.d("AAAB", "mCurrentPhotoPath " + mCurrentPhotoPath);

                    compressImage(mCurrentPhotoPath, null);

                    String path = takePhotoUri.getPath();
                    String tagId = getId(path);
                    Uri uri = takePhotoUri;
                    String fileName = getFileName(path);

                    ImageView imageView = creatImageView(false, tagId);
                    loadImage(this, takePhotoUri, imageView, false, BaseApplication.getInstance().LIST_HORIZONTAL);

                    uplaodImageItems.add(new UplaodItem(tagId, mCurrentPhotoPath, uri, fileName));

                    // 갤러리 이미지 갱신
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(new File(mCurrentPhotoPath));
                    mediaScanIntent.setData(contentUri);
                    this.sendBroadcast(mediaScanIntent);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                addImageArea.addView(addImageView);

                break;
            case SELECT_FROM_ALBUM:

                ArrayList<Image> imageList = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
//                Log.d("AAAB","SELECT_FROM_ALBUM" + imageList.get(0).path);

                for (Image image : imageList) {
                    try {
                        String path = image.path;
                        String tagId = getId(path);
                        Uri uri = Uri.fromFile(new File(path));
                        String fileName = getFileName(path);

                        ImageView imageView = creatImageView(false, tagId);
                        loadImage(this, Uri.fromFile(new File(image.path)), imageView, false, BaseApplication.getInstance().LIST_HORIZONTAL);

                        // 암축한 이미지 경로 넣기
                        String tempFileName = "temp_" + fileName;
                        String tempFilePath = getOnlyFilePath(image.path) + tempFileName;

                        compressImage(path, tempFilePath);

                        uplaodImageItems.add(new UplaodItem(tagId, tempFilePath, uri, tempFileName));
                        if (tempImageItems == null)
                            tempImageItems = new ArrayList<>();

                        tempImageItems.add(tempFilePath);

                        Log.d("AAAB", "SELECT_FROM_ALBUM path :" + image.path);
                        Log.d("AAAB", "SELECT_FROM_ALBUM path[Only] :" + getOnlyFilePath(image.path));
                        Log.d("AAAB", "SELECT_FROM_ALBUM id :" + image.id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                addImageArea.addView(addImageView);


                break;

            case FROM_VIDEO:
                Uri videoUri = data.getData();

                String path = getPathFromMediaUri(this, videoUri);
                String tagId = getId(path);
                Uri uri = videoUri;
                String fileName = getFileName(path);

                ImageView imageView = creatImageView(true, tagId);
                loadImage(this, videoUri, imageView, false, BaseApplication.getInstance().LIST_HORIZONTAL);

                uploadVideoItems.add(new UplaodItem(tagId, path, uri, fileName));

                Log.d("AAAB", "FROM_VIDEO getPath" + videoUri.getPath());
                Log.d("AAAB", "Function getPathFromURI" + getPathFromMediaUri(this, videoUri));
                addVideoArea.addView(addVideoView);

                break;
        }
    }

    private void takePhoto() {

        if (uplaodImageItems != null && uplaodImageItems.size() >= MAX_ADD_IMAGE_COUNT) {
            //  최대 사이즈 카운터 경고
            alertPopup(MAX_ADD_IMAGE_COUNT);
            return;
        }

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
        int setCount = 0;
        if (uplaodImageItems != null && uplaodImageItems.size() >= MAX_ADD_IMAGE_COUNT) {
            //  최대 사이즈 카운터 경고
            alertPopup(MAX_ADD_IMAGE_COUNT);
            return;
        } else {
            if (uplaodImageItems != null) {
                setCount = MAX_ADD_IMAGE_COUNT - uplaodImageItems.size();
            } else {
                setCount = MAX_ADD_IMAGE_COUNT;
            }
        }

        Intent intent = new Intent(this, AlbumSelectActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, setCount);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    private void takeVideo() {

        if (uploadVideoItems != null && uploadVideoItems.size() >= MAX_ADD_VEDIO_COUNT) {
            alertPopup(MAX_ADD_VEDIO_COUNT);
            return;
        }

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

        if (uploadVideoItems != null && uploadVideoItems.size() >= MAX_ADD_VEDIO_COUNT) {
            alertPopup(MAX_ADD_VEDIO_COUNT);
            return;
        }


        Intent intent = new Intent(Intent.ACTION_PICK); //ACTION_PICK 즉 사진을 고르겠다!
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("video/*");
        startActivityForResult(intent, FROM_VIDEO);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyymmdd").format(new Date());
        String imageFileName = timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/DCIM/syruptable/");

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
        deleteButton.getLayoutParams().width = displayWidth / 10;
        deleteButton.getLayoutParams().height = displayWidth / 10;
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
        rootview.getLayoutParams().width = displayWidth / 3 - 10;
        rootview.getLayoutParams().height = displayWidth / 3 - 10;

        if (!isVidio) {
            if (addImageView.getParent() != null) {
                ((ViewGroup) addImageView.getParent()).removeView(addImageView);
            }

            addImageArea.addView(rootview);
        } else {
            if (addVideoView.getParent() != null) {
                ((ViewGroup) addVideoView.getParent()).removeView(addVideoView);
            }

            rootview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String tag = (String) view.findViewById(R.id.reviewwrite_delete).getTag();

                    for (UplaodItem uplaodItem : uploadVideoItems) {
                        String id = uplaodItem.getId();
                        if (tag.equals(id)) {
                            // 동영상 재생
                            Intent intent = new Intent(baceContext, VideoActivity.class);
                            intent.putExtra("videoPath", uplaodItem.getPath());
                            ActivityStart(intent, null);
                        }
                    }
                }
            });

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

    private String getOnlyFilePath(String path) {
        return path.substring(0, path.lastIndexOf("/") + 1);
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
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                finish();
                return;
            }
        }
    }

    /*
     * This method is fetching the absolute path of the image file
     * if you want to upload other kind of files like .pdf, .docx
     * you need to make changes on this method only
     * Rest part will be the same
     * */
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public String getPathFromUri(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString(cursor.getColumnIndex("_data"));
        cursor.close();

        return path;
    }


    private void compressImage(String path, String tempFilePath) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap imageBitmap = null;
        FileOutputStream outFile = null;
        try {
            //이미지를 불러올때 고용량의 경우 OutOfMemory가 발생할 수 있으므로 이미지 크기를 줄여서 호출함

            imageBitmap = BitmapFactory.decodeFile(path, options);

            // 기본 카메라 모듈을 이용해 촬영할 경우 가끔씩 이미지가 회전되어 출력되는 경우가 존재하여 이미지를 상황에 맞게 회전시킨다
            ExifInterface exif = new ExifInterface(path);
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int exifDegree = exifOrientationToDegrees(exifOrientation);

            //회전된 이미지를 다시 회전시켜 정상 출력
            imageBitmap = rotate(imageBitmap, exifDegree);

            File f = null;
            if (tempFilePath != null) {
                f = new File(tempFilePath);
            } else {
                f = new File(path);
            }
            outFile = new FileOutputStream(f);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outFile);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //비트맵 메모리 반환
            imageBitmap.recycle();
            outFile.close();
        }
    }

    /**
     * EXIF정보를 회전각도로 변환하는 메서드
     *
     * @param exifOrientation EXIF 회전각
     * @return 실제 각도
     */
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    /**
     * 이미지를 회전시킵니다.
     *
     * @param bitmap  비트맵 이미지
     * @param degrees 회전 각도
     * @return 회전된 이미지
     */
    private Bitmap rotate(Bitmap bitmap, int degrees) {
        Bitmap retBitmap = bitmap;

        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

            try {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != converted) {
                    retBitmap = converted;
                    bitmap.recycle();
                    bitmap = null;
                }
            } catch (OutOfMemoryError ex) {
                // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
            }
        }
        return retBitmap;
    }

    private void tempImageFileDelete(List<String> tempImageItems) {

        if (tempImageItems == null)
            return;

        for (String temp : tempImageItems) {
            File file = new File(temp);

            if (file.exists()) {
                if (file.delete()) {

                } else {

                }
            } else {

            }
        }
    }


    private void alertPopup(int max_count) {

        new MaterialStyledDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setDescription("최대 총 " + max_count + "개 까지 첨부 가능합니다.")
                .setStyle(Style.HEADER_WITH_TITLE)
                .setHeaderColor(R.color.colorBlack)
                .setPositiveText("확인")
                .withDialogAnimation(true)
                .show();

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(
//                "최대 총 " + max_count + "개 까지 첨부 가능합니다.")
//                .setPositiveButton("확인",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//        AlertDialog alert = builder.create();
//        alert.show();
//
//        final Button neutralButton = alert.getButton(AlertDialog.BUTTON_NEUTRAL);
//        LinearLayout.LayoutParams neutralButtonLL = (LinearLayout.LayoutParams) neutralButton.getLayoutParams();
//        neutralButtonLL.gravity = Gravity.CENTER;
//        neutralButton.setLayoutParams(neutralButtonLL);
    }

    private void alertContentPopup(EditText editText) {

        new MaterialStyledDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setDescription("내용을 입력해주세요.")
                .setStyle(Style.HEADER_WITH_TITLE)
                .setHeaderColor(R.color.colorBlack)
                .setPositiveText("확인")
                .withDialogAnimation(true)
                .show();

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(
//                "내용을 입력해주세요.")
//                .setPositiveButton("확인",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                                editText.setFocusable(true);
//                                editText.requestFocus();
//                            }
//                        });
//        AlertDialog alert = builder.create();
//        alert.show();
//        final Button neutralButton = alert.getButton(AlertDialog.BUTTON_NEUTRAL);
//        LinearLayout.LayoutParams neutralButtonLL = (LinearLayout.LayoutParams) neutralButton.getLayoutParams();
//        neutralButtonLL.gravity = Gravity.CENTER;
//        neutralButton.setLayoutParams(neutralButtonLL);
    }

    @Override
    protected void onDestroy() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    tempImageFileDelete(tempImageItems);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        super.onDestroy();
    }
} //class End


