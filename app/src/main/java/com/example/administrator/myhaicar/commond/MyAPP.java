package com.example.administrator.myhaicar.commond;

import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import com.example.administrator.myhaicar.tools.CrashHandler;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.changeskin.SkinManager;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/5/11.
 */

public class MyAPP extends Application {
    public static String token;
    @Override
    public void onCreate() {
        super.onCreate();
        /*CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());*/
        //全局初始化
        JPushInterface.init(this);
        //调试模式
        JPushInterface.setDebugMode(true);
        token = JPushInterface.getRegistrationID(this);
        if (token!=null){
            PreferencesUtils.putString(this,"token",token);
        }

        SkinManager.getInstance().init(this);
        Fresco.initialize(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx1876d6c050b3bb22", "b31d8f7ef04758eae91d9628c28ef680");
        PlatformConfig.setQQZone("1105824838", "wPMjdMGfzbhJLBtY");
        PlatformConfig.setSinaWeibo("3456395278", "2de4d6c97ac32630198e62461f47942e", "http://sns.whalecloud.com");

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
