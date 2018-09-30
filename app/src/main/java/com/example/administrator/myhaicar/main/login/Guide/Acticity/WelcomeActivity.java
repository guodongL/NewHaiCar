package com.example.administrator.myhaicar.main.login.Guide.Acticity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.order.This.Acticity.OrderActivity;
import com.example.administrator.myhaicar.utils.PreferencesUtils;

/*
* 界面为欢迎页
* */
public class WelcomeActivity extends AppCompatActivity {
    private  Context mContext=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        Handler handler=new Handler();
        //开启子线程延时2秒跳转页面
        handler.postDelayed(new NewHandler(),1000);
    }
    class NewHandler implements Runnable{

        @Override
        public void run() {

            String usr_flag = PreferencesUtils.getString(mContext, "usr_flag");
            if (usr_flag==null||usr_flag.equals("NO"))
            {
                //进行页面跳转
                Intent intent = new Intent();
                intent.setClass(getApplication(), GuideActivity.class);
                startActivity(intent);
                finish();
                //我的订单未读数

            }
            else
            {
                //进行页面跳转
                Intent intent = new Intent();
                intent.setClass(getApplication(), OrderActivity.class);
                startActivity(intent);
                finish();

            }
        }
    }
}
