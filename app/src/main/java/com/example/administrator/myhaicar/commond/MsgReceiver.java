package com.example.administrator.myhaicar.commond;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.login.This.Model.LoginBean;
import com.example.administrator.myhaicar.main.order.This.Acticity.OrderActivity;
import com.example.administrator.myhaicar.utils.HTTP_GD;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import me.leolin.shortcutbadger.ShortcutBadger;
import okhttp3.Call;

import static cn.jpush.android.api.JPushInterface.EXTRA_REGISTRATION_ID;


public class MsgReceiver extends BroadcastReceiver {
    private  String usr_id;
    //极光推送的消息没有UI,只有消息内容.
    //要想展示内容,就自己去写UI.
    @Override
    public void onReceive(final Context context, Intent intent) {
       String action = intent.getAction();
       if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(action)) {
           Bundle bundle = intent.getExtras();
           String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
           Log.i("TAG", "msg=" + message);
     /*      usr_id = PreferencesUtils.getString(context, "usr_id");
           OkHttpUtils.post().url(UrlConfig.Path.Phone_URL).addParams("act","re_load").addParams("usr_id",usr_id).build().execute(new StringCallback() {
               @Override
               public void onError(Call call, Exception e) {
                   Toast.makeText(context, "网络获取失败", Toast.LENGTH_LONG).show();
               }

               @Override
               public void onResponse(String s) {
                   if (HTTP_GD.IsOrNot_Null(s)) {
                       return;
                   }
                   LoginBean loginBean = MyparseJsonToMessageBean(s);
                   String un_news = loginBean.getUn_news();
                   int badgeCount = Integer.parseInt(un_news);
                   ShortcutBadger.applyCount(context, badgeCount); //1.1.17版本添加数字“徽章”的方法
               }
           });*/
       }
       else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
           //打开自定义的Activity
           Intent i = new Intent(context, OrderActivity.class);
           i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           context.startActivity(i);
      }

        String phone_number = PreferencesUtils.getString(context, "phone_number");
        Bundle bundle = intent.getExtras();
        String token = bundle.getString(EXTRA_REGISTRATION_ID);
        if (token!=null){
            PreferencesUtils.putString(context,"token",token);
        }
        if (phone_number!=null&&token!=null){
            OkHttpUtils.post().url(UrlConfig.Path.JPush_URL).addParams("phone",phone_number).addParams("md6",token).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {

                }

                @Override
                public void onResponse(String s) {

                }
            });
        }


    }
    public LoginBean MyparseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        LoginBean bean = gson.fromJson(jsonString, new TypeToken<LoginBean>() {
        }.getType());

        return bean;
    }

}
