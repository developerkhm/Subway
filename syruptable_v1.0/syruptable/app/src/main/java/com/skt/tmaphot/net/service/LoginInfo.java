package com.skt.tmaphot.net.service;

import android.util.Log;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.common.CommonUtil;
import com.skt.tmaphot.net.model.user.UserInfoModel;

public class LoginInfo {

    private static LoginInfo instance;
    private UserInfoModel userInfo;


    public static LoginInfo getInstance(){
        if(instance == null){
            instance = new LoginInfo();
        }
        return instance;
    }

    private LoginInfo() { }

    public boolean isLogin() {
        if(userInfo != null)
        {
            Log.d("KKD","isLogin() != null: " + userInfo.getResult());
            return  Boolean.valueOf(userInfo.getResult());
        }
        return false;
    }


    public String getUserId() {
//        return userId;
        return CommonUtil.getInstance().getPreferencesString(BaseApplication.getInstance(),"login","userid");
    }

    public void setUserId(String userId) {
//        this.userId = userId;
        CommonUtil.getInstance().savePreferencesString(BaseApplication.getInstance(),"login","userid", userId);
    }

    public UserInfoModel getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoModel userInfo) {
        this.userInfo = userInfo;
    }
}
