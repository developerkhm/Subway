package com.skt.tmaphot.temp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.review.multiple.activities.AlbumSelectActivity;
import com.skt.tmaphot.activity.review.multiple.helpers.Constants;
import com.skt.tmaphot.activity.review.multiple.models.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TempReviewWriteActivity extends AppCompatActivity {
    ImageView img;
    Uri photoUri;
    String mCurrentPhotoPath;


    private LinearLayout tempLay;

    private static final int PICK_FROM_CAMERA = 1; //카메라 촬영으로 사진 가져오기
    private static final int PICK_FROM_ALBUM = 2; //앨범에서 사진 가져오기
    private static final int CROP_FROM_CAMERA = 3; //가져온 사진을 자르기 위한 변수
    private static final int PICTURE_REQUEST_CODE = -1; //가져온 사진을 자르기 위한 변수
    int PICK_IMAGE_MULTIPLE = 454545;
    int PICK_IMAGE_SAMSUNG = 454543335;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_activity_reviewwrite_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });

        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAlbum();

            }
        });


        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                goToAlbumMulti();
//                imageMultiPick();

                Intent intent = new Intent(TempReviewWriteActivity.this, AlbumSelectActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 3);
                startActivityForResult(intent, Constants.REQUEST_CODE);

            }
        });

        img = (ImageView) findViewById(R.id.img);
        tempLay = (LinearLayout) findViewById(R.id.temp_lay);

    } /////////////////END


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("UU", "ReviewActivity3 : onActivityResult requestCode :" + requestCode);
        switch (requestCode) {
            case PICK_FROM_CAMERA:

                try { //저는 bitmap 형태의 이미지로 가져오기 위해 아래와 같이 작업하였으며 Thumbnail을 추출하였습니다.
//                    BaseApplication.loadUriImage(this, photoUri, img);

                    return;


//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
//                    Bitmap thumbImage = ThumbnailUtils.extractThumbnail(bitmap, 256, 256);
//                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
//                    thumbImage.compress(Bitmap.CompressFormat.JPEG, 100, bs); //이미지가 클 경우 OutOfMemoryException 발생이 예상되어 압축
//
//
//                    //여기서는 ImageView에 setImageBitmap을 활용하여 해당 이미지에 그림을 띄우시면 됩니다.
//                    img.setImageBitmap(thumbImage);
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage().toString());
                }

                // 갤러리 이미지 갱신
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File f = new File(mCurrentPhotoPath);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                break;

            case PICK_FROM_ALBUM:
                photoUri = data.getData();

                try { //저는 bitmap 형태의 이미지로 가져오기 위해 아래와 같이 작업하였으며 Thumbnail을 추출하였습니다.
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    Bitmap thumbImage = ThumbnailUtils.extractThumbnail(bitmap, 256, 256);
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    thumbImage.compress(Bitmap.CompressFormat.JPEG, 100, bs); //이미지가 클 경우 OutOfMemoryException 발생이 예상되어 압축


                    //여기서는 ImageView에 setImageBitmap을 활용하여 해당 이미지에 그림을 띄우시면 됩니다.
                    img.setImageBitmap(thumbImage);
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage().toString());
                }

                break;
            case 2000:
                Log.d("UU", "ReviewActivity3 PICTURE_REQUEST_CODE ");

                ArrayList<Image> list = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);

                ImageView tt = new ImageView(this);
                                    tt.setImageURI(Uri.parse(list.get(0).path));
                                    tempLay.addView(tt);
//                                    image1.setImageURI(urione);

//                Uri uri = data.getData();


//                Uri uri = data.getData();
//                ClipData clipData = data.getClipData();
//
//                //이미지 URI 를 이용하여 이미지뷰에 순서대로 세팅한다.
//                if (clipData != null) {
//
//                    for (int i = 0; i < 3; i++) {
//                        if (i < clipData.getItemCount()) {
//                            Uri urione = clipData.getItemAt(i).getUri();
//                            Log.d("UU","ReviewActivity3 URI: " + urione.toString());
//                            switch (i) {
//                                case 0:
//                                    ImageView tt = new ImageView(this);
//                                    tt.setImageURI(urione);
//                                    tempLay.addView(tt);
////                                    image1.setImageURI(urione);
//                                    break;
//                                case 1:
////                                    image2.setImageURI(urione);
//                                    break;
//                                case 2:
////                                    image3.setImageURI(urione);
//                                    break;
//                            }
//                        }
//                    }
//                } else if (uri != null) {
////                    image1.setImageURI(uri);
//                }
                break;
        }

    }


    private void goToAlbumMulti() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        //사진을 여러개 선택할수 있도록 한다
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICTURE_REQUEST_CODE);
    }


    public void imageMultiPick() {
        // Undocumented way to get multiple photo selections from Android Gallery ( on Samsung )
        Intent intent = new Intent("android.intent.action.MULTIPLE_PICK");
        //("Intent.ACTION_GET_CONTENT);
        // intent.addCategory(Intent.CATEGORY_OPENABLE); // 생략해도 됨 - 삼성 갤럭시S3 테스트
        intent.setType("image/*");
        // 생략하면 아래 검사 무의미 > else { ... 구문으로 넘어감
        // Check to see if it can be handled...
        PackageManager manager = getApplicationContext().getPackageManager();
        List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);
        if (infos.size() > 0) {
            Log.e("FAT=", "삼성폰");
            // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // 삼성폰 - 생략해도 됨
            // createChooser 실행해도 Intent가 1개 뿐이면 통과 > 이미지 리스트가 바로 열림
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_SAMSUNG);
        } else {
            Log.e("FAT=", "일반폰");
            // intent.addCategory(Intent.CATEGORY_OPENABLE); // 없어도 됨 - 엘지 G2 테스트
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            // 일반폰 - 반드시 있어야 다중선택 가능
            intent.setAction(Intent.ACTION_PICK);
            // ACTION_GET_CONTENT 사용불가 - 엘지 G2 테스트
            startActivityForResult(intent, PICK_IMAGE_MULTIPLE);
        }
    }


    private void goToAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK); //ACTION_PICK 즉 사진을 고르겠다!
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }


    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //사진을 찍기 위하여 설정합니다.
        File photoFile = null;
        try {
            photoFile = createImageFile();
            mCurrentPhotoPath = photoFile.getAbsolutePath();

        } catch (IOException e) {
            Toast.makeText(TempReviewWriteActivity.this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (photoFile != null) {
            photoUri = FileProvider.getUriForFile(TempReviewWriteActivity.this,
                    "com.skt.tmaphot.provider", photoFile); //FileProvider의 경우 이전 포스트를 참고하세요.
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); //사진을 찍어 해당 Content uri를 photoUri에 적용시키기 위함
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
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


    /////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.store_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //        Button btn4 = (Button) findViewById(R.id.btn4);
//        btn4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent();
////                intent.setAction(Intent.ACTION_GET_CONTENT);
//
//                Intent intent = new Intent(Intent.ACTION_PICK); //ACTION_PICK 즉 사진을 고르겠다!
//                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//
//                intent.setType("video/*");
//                startActivityForResult(intent, VIDEO);
//
////                Uri uri = Uri.parse("file:///media/sdcard/DCIM/Camera");
////                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
////                intent.setAction(Intent.ACTION_GET_CONTENT);
////                intent.setType("video/*");
////                startActivityForResult(intent, VIDEO);
//////
////                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
////                i.setType("video/*");
////                startActivityForResult(i, VIDEO);
//            }
//        });
//
//        img = (ImageView) findViewById(R.id.img);
//        tempLay = (LinearLayout) findViewById(R.id.temp_lay);
}


