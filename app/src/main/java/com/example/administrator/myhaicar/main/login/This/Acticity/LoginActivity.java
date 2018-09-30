package com.example.administrator.myhaicar.main.login.This.Acticity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.login.Guide.Acticity.StatementActivity;
import com.example.administrator.myhaicar.main.login.This.Model.LoginBean;
import com.example.administrator.myhaicar.main.login.This.Model.NoLoginBean;
import com.example.administrator.myhaicar.main.mine.Account.This.View.ConfirmPopWindow;
import com.example.administrator.myhaicar.commond.ConfirmPopWindowThree;
import com.example.administrator.myhaicar.main.order.This.Acticity.OrderActivity;
import com.example.administrator.myhaicar.utils.HTTP_GD;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

/*
* 界面为登录界面
* */
public class LoginActivity extends AppCompatActivity {
    private RelativeLayout activity_login;
    //登录按钮，点击跳转到主页面
    private Button button_login;
    private Context mContext=this;
    //点击button获取验证码
    private Button button_getVerify;
    private int countSeconds = 90;
    //电话号码的输入框，用于获取电话号
    private EditText edit_phoneNumber;
    //用于覆盖输入验证码的透明button
    private Button button_cover;
    private TextView textView_Statement;
    private EditText editText_authCode;
    private String token;
    //开启子线程进行倒计时90秒
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (countSeconds > 0) {
                button_getVerify.setClickable(false);
                countSeconds--;
                button_getVerify.setText("" + countSeconds + "秒后重发");
                mHandler.sendEmptyMessageDelayed(0,1000);
                button_cover.setVisibility(View.INVISIBLE);
            } else {
                countSeconds = 90;
                button_getVerify.setText("获取验证码");
                button_getVerify.setClickable(true);
                button_cover.setVisibility(View.VISIBLE);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView();
        initClick();
    }
    private void initView() {

        textView_Statement= (TextView) findViewById(R.id.textView_Statement);
        editText_authCode= (EditText) findViewById(R.id.editText_authCode);
        activity_login= (RelativeLayout) findViewById(R.id.activity_login);
        button_login= (Button) findViewById(R.id.button_login);
        button_getVerify= (Button) findViewById(R.id.button_getVerify);
        edit_phoneNumber= (EditText) findViewById(R.id.edit_phoneNumber);
        button_cover= (Button) findViewById(R.id.button_cover);
    }
    private void initClick() {
        textView_Statement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, StatementActivity.class);
                startActivity(intent);
            }
        });
        button_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmPopWindowThree confirmPopWindow = new ConfirmPopWindowThree(mContext,"请获取验证码");
                confirmPopWindow.showAtLocation(activity_login, Gravity.CENTER, 0, 0);
            }
        });
        //登录按钮点击跳转到主页面
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               login();
            }
        });
        //获取验证码按钮，点击进行倒计时，获取数据网络访问
        button_getVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = edit_phoneNumber.getText().toString().trim();
                //Toast.makeText(mContext,"同时网络访问",Toast.LENGTH_SHORT).show();
                getMobiile(phoneNumber);

            }
        });
    }

    private void getMobiile(String phoneNumber) {
        if ("".equals(phoneNumber)) {
            ConfirmPopWindow confirmPopWindow = new ConfirmPopWindow(mContext,"请您输入手机号","手机号不能为空");
            confirmPopWindow.showAtLocation(activity_login, Gravity.CENTER, 0, 0);
        } else if (isMobileNO(phoneNumber) == false) {
            ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext,"请您输入11位手机号");
            confirmPopWindowtwo.showAtLocation(activity_login, Gravity.CENTER, 0, 0);
        } else {
            startCountBack();
            requestVerifyCode(phoneNumber);
        }
    }
    private void requestVerifyCode(String phoneNumber) {
        OkHttpUtils.post().url(UrlConfig.Path.Phone_URL).addParams("act","smsobtain").addParams("phone",phoneNumber).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                    Toast.makeText(mContext,"网络连接失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String s) {


            }
        });
    }
    public void login() {
    /*    Intent intent=new Intent();
        intent.setClass(mContext, OrderActivity.class);
        startActivity(intent);*/
        //是不是已经登录过啦
        // PreferencesUtils.putString(mContext,"usr_flag","YES");
        final String phongNumber = edit_phoneNumber.getText().toString().trim();
        final String authCode = editText_authCode.getText().toString().trim();
        if ("".equals(phongNumber)) {
            ConfirmPopWindow confirmPopWindow = new ConfirmPopWindow(mContext, "请您输入手机号", "手机号不能为空");
            confirmPopWindow.showAtLocation(activity_login, Gravity.CENTER, 0, 0);
        } else if (isMobileNO(phongNumber) == false) {
            ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext, "请您输入11位手机号");
            confirmPopWindowtwo.showAtLocation(activity_login, Gravity.CENTER, 0, 0);
        } else if ("".equals(authCode)) {
            ConfirmPopWindow confirmPopWindow = new ConfirmPopWindow(mContext, "请您输入验证码", "验证码不能为空");
            confirmPopWindow.showAtLocation(activity_login, Gravity.CENTER, 0, 0);
        } else if (authCode.length() < 6) {
            ConfirmPopWindow confirmPopWindow = new ConfirmPopWindow(mContext, "请您检查验证码", "验证码为6位");
            confirmPopWindow.showAtLocation(activity_login, Gravity.CENTER, 0, 0);
        } else {
            OkHttpUtils.post().url(UrlConfig.Path.Phone_URL).addParams("act", "smsveri").addParams("smscode", authCode)
                    .addParams("phone", phongNumber).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    Toast.makeText(mContext, "网络异常，请稍后重试", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String s) {
                    String jsonString = s;
                    if (HTTP_GD.IsOrNot_Null(jsonString)) {
                        return;
                    }
                    LoginBean loginBeen = parseJsonToMessageBean(jsonString);
                    if (loginBeen.getRet_prompt().equals("0")) {
                        NoLoginBean noLoginBean = parseNoJsonToMessageBean(s);
                        String ret_info = noLoginBean.getRet_info();
                        ConfirmPopWindow confirmPopWindow = new ConfirmPopWindow(mContext, "提示", ret_info);
                        confirmPopWindow.showAtLocation(activity_login, Gravity.CENTER, 0, 0);
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(mContext, OrderActivity.class);
                        startActivity(intent);
                        if ((token= PreferencesUtils.getString(mContext,"token"))==null)
                        {
                            token="bucunzai";
                        }
                        Toast.makeText(mContext,token,Toast.LENGTH_LONG).show();
                        OkHttpUtils.post().url(UrlConfig.Path.JPush_URL).addParams("phone",phongNumber).addParams("md6",token).build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {

                            }

                            @Override
                            public void onResponse(String s) {

                            }
                        });
                        //所属城市
                    PreferencesUtils.putString(mContext,"usr_city",loginBeen.getUsr_city());
                    PreferencesUtils.putString(mContext,"usr_sex",loginBeen.getUsr_sex());
                        //年龄
                    PreferencesUtils.putString(mContext,"usr_ages",loginBeen.getUsr_ages());
                        //是否是vip
                    PreferencesUtils.putString(mContext,"usr_vip",loginBeen.getUsr_vip()+"");
                        //保证金额
                    PreferencesUtils.putString(mContext,"usr_bond_c",loginBeen.getUsr_bond_c());
                    PreferencesUtils.putString(mContext,"phone_number",phongNumber);
                    PreferencesUtils.putString(mContext,"usr_name",loginBeen.getUsr_name());
                        //版本号
                    PreferencesUtils.putString(mContext,"app_version",loginBeen.getApp_version());
                    PreferencesUtils.putString(mContext,"usr_addess",loginBeen.getUsr_addess());
                        //头像
                    PreferencesUtils.putString(mContext,"usr_headimgurl",loginBeen.getUsr_headimgurl());
                    PreferencesUtils.putString(mContext,"usr_id",loginBeen.getUsr_id());
                    PreferencesUtils.putString(mContext,"veri_code",authCode);
                        //版本号
                    PreferencesUtils.putString(mContext,"app_version",loginBeen.getApp_version());
                    //用户嗨车ID
                    PreferencesUtils.putString(mContext,"usr_mime_id",loginBeen.getUsr_mime_id());
                    //用户账户上的余额
                    PreferencesUtils.putString(mContext,"usr_money",loginBeen.getUsr_money());
                        //未读消息总数
                    PreferencesUtils.putString(mContext,"un_news",loginBeen.getUn_news());
                        PreferencesUtils.putString(mContext,"un_order",loginBeen.getUn_order());
                        //账户未读数
                        PreferencesUtils.putString(mContext,"un_account",loginBeen.getUn_account());
                        //商家未读
                        PreferencesUtils.putString(mContext,"un_busi",loginBeen.getUn_busi());
                        //福利未读
                        PreferencesUtils.putString(mContext,"un_acti",loginBeen.getUn_acti());
                        //车辆未读
                        PreferencesUtils.putString(mContext,"un_car",loginBeen.getUn_car());
                        //帮助未读
                        PreferencesUtils.putString(mContext,"un_help",loginBeen.getUn_help());
                        PreferencesUtils.putBoolean(mContext,"firstLogin",false);
                        PreferencesUtils.putBoolean(mContext,"firstAddCar",false);
                        //是不是已经登录过啦
                    PreferencesUtils.putString(mContext, "usr_flag", "YES");
                        String usr_sex = loginBeen.getUsr_sex();
                    PreferencesUtils.putString(mContext, "flag", usr_sex);
                    }
                }
            });
        }
    }
    //获取验证码，倒计时方法
    private void startCountBack() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button_getVerify.setText("获取验证码");
                mHandler.sendEmptyMessage(0);
            }
        });
    }
    //下面方法是解决防止进入界面键盘自动弹出
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_login:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;

        }
    }
    public static boolean isMobileNO(String tel) {
        Pattern p = Pattern.compile("^(13[0-9]|15([0-3]|[5-9])|14[5,7,9]|17[1,3,5,6,7,8]|18[0-9])\\d{8}$");
        Matcher m = p.matcher(tel);
        System.out.println(m.matches() + "---");
        return m.matches();
    }
    public LoginBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        LoginBean bean = gson.fromJson(jsonString, new TypeToken<LoginBean>() {
        }.getType());

        return bean;
    }
    public NoLoginBean parseNoJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        NoLoginBean bean = gson.fromJson(jsonString, new TypeToken<NoLoginBean>() {
        }.getType());

        return bean;
    }

}
