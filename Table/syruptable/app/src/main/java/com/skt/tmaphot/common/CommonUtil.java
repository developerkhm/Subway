package com.skt.tmaphot.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

    private static CommonUtil uniqueInstance;
    public Context mainContex;
    public String devicePhoneNumber;

    private CommonUtil() {
    }

    public static CommonUtil getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CommonUtil();
        }
        return uniqueInstance;
    }

    public String getCurrenTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
    }


    // 임시 mainContex 고쳐야됨
    @SuppressLint("MissingPermission")
    public String getPhoneNumber() {
        return ((TelephonyManager) mainContex.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
    }

    // 값 불러오기
    public int getPreferencesInt(Context context, String name, String key) {
        SharedPreferences pref = context.getSharedPreferences(name, context.MODE_PRIVATE);
        return pref.getInt(key, 0);
    }

    public String getPreferencesString(Context context, String name, String key) {
        SharedPreferences pref = context.getSharedPreferences(name, context.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    // 값 저장하기
    public void savePreferencesInt(Context context, String name, String key, int vlaue) {
        SharedPreferences pref = context.getSharedPreferences(name, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, vlaue);
        editor.commit();
    }

    // 값 저장하기
    public void savePreferencesString(Context context, String name, String key, String vlaue) {
        SharedPreferences pref = context.getSharedPreferences(name, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, vlaue);
        Log.d("DFDFDFFD", "savePreferencesString vlaue : " +  vlaue);
        editor.commit();
    }

    // 값(Key Data) 삭제하기
    public void removePreferences(Context context, String name, String key) {
        SharedPreferences pref = context.getSharedPreferences(name, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }

    // 값(ALL Data) 삭제하기
    public void removeAllPreferences(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(name, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int top;
        private int right;
        private int bottom;
        private int left;

        public SpacesItemDecoration(int top, int right, int bottom, int left) {
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.left = left;
        }

        public SpacesItemDecoration(int offset) {
            this.top = offset;
            this.right = offset;
            this.bottom = offset;
            this.left = offset;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = top;
            outRect.right = right;
            outRect.bottom = bottom;
            outRect.left = left;

            // Add top margin only for the first item to avoid double space between items
//            if(parent.getChildAdapterPosition(view) == 0) {
//                outRect.top = space;
//            }
        }
    }


    public void setColorRatingBar(RatingBar ratingbar) {
        LayerDrawable stars = (LayerDrawable) ratingbar.getProgressDrawable();
        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP); // for filled stars
//		stars.getDrawable(1).setColorFilter(Color.parseColor("#FFFF00"), PorterDuff.Mode.SRC_ATOP); // for half filled stars
        stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP); // for empty stars
    }


    public String bin2hex(String data) throws UnsupportedEncodingException {
        String bas64Data = getBase64encode(data);
        byte[] bas64DataArray = bas64Data.getBytes();
        return String.format("%0" + (bas64DataArray.length * 2) + 'x', new BigInteger(1, bas64DataArray));
    }


    public String pack(String hex){

        try {
            String input = hex.length() % 2 == 0 ? hex : hex + "0";
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < input.length(); i += 2) {
                String str = input.substring(i, i + 2);
                output.append((char) Integer.parseInt(str, 16));
            }
            return getBase64decode(output.toString());
        }catch(Exception e){
         e.printStackTrace();
        }
        return "";
    }

    /**
     * Base64 인코딩
     */
    public String getBase64encode(String content) throws UnsupportedEncodingException {
        byte[] data = content.getBytes("UTF-8");
        return Base64.encodeToString(data, Base64.DEFAULT);
//        return Base64.encodeToString(content.getBytes(), 0);
    }

    /**
     * Base64 디코딩
     */
    public String getBase64decode(String content) throws UnsupportedEncodingException {
        return new String(Base64.decode(content, Base64.DEFAULT), "UTF-8");
    }

    /**
     * getURLEncode
     */
    public String getURLEncode(String content) {
        try {
//          return URLEncoder.encode(content, "utf-8");   // UTF-8
            return URLEncoder.encode(content, "euc-kr");  // EUC-KR
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getURLDecode
     */
    public String getURLDecode(String content) {
        try {
//          return URLDecoder.decode(content, "utf-8");   // UTF-8
            return URLDecoder.decode(content, "euc-kr");  // EUC-KR
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}