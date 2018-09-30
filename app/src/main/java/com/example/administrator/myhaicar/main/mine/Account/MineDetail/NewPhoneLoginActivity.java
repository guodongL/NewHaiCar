package com.example.administrator.myhaicar.main.mine.Account.MineDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.Account.This.View.ConfirmPopWindow;
import com.example.administrator.myhaicar.commond.ConfirmPopWindowThree;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

public class NewPhoneLoginActivity extends SwipeBackToolBarActivity {
private Context mContext=this;
    private RelativeLayout activity_new_phone_login;
    private ImageView toolBar_back;
    private Button button_nextNewphone;
    private String phoneNumber;
    private EditText edit_inPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_phone_login);
        initView();
        initClick();
    }
    private void initView() {
        phoneNumber = PreferencesUtils.getString(mContext, "phone_number");
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        button_nextNewphone= (Button) findViewById(R.id.button_nextNewphone);
        edit_inPhone= (EditText) findViewById(R.id.edit_inPhone);
        activity_new_phone_login= (RelativeLayout) findViewById(R.id.activity_new_phone_login);
    }
    private void initClick() {
         toolBar_back.setOnClickListener(new View.OnClickListener() {
              @Override
                public void onClick(View v) {
                    finish();
                }
          });
        button_nextNewphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = edit_inPhone.getText().toString();
                if ("".equals(phone)) {
                    ConfirmPopWindow confirmPopWindow = new ConfirmPopWindow(mContext,"请您输入手机号","手机号不能为空");
                    confirmPopWindow.showAtLocation(activity_new_phone_login, Gravity.CENTER, 0, 0);
                } else if (isMobileNO(phone) == false) {
                    ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext,"请您输入11位手机号");
                    confirmPopWindowtwo.showAtLocation(activity_new_phone_login, Gravity.CENTER, 0, 0);
                }else {
                    OkHttpUtils.post().url(UrlConfig.Path.Phone_URL).addParams("act", "smsobtain").addParams("phone", phoneNumber).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            Toast.makeText(mContext, "网络连接失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String s) {
                            NewPhoneBean newPhoneBean = MyparseJsonToMessageBean(s);
                            String ret_prompt = newPhoneBean.getRet_prompt();
                            String ret_info = newPhoneBean.getRet_info();
                            if (ret_prompt.equals("1")) {
                                Intent intent = new Intent();
                                intent.putExtra("phone", phone);
                                intent.setClass(mContext, NewPhoneNextActivity.class);
                                startActivity(intent);
                            }else {
                                ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext,ret_info);
                                confirmPopWindowtwo.showAtLocation(activity_new_phone_login, Gravity.CENTER, 0, 0);
                            }
                        }
                    });
                }
            }
        });
    }
    //下面方法是解决防止进入界面键盘自动弹出
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_new_phone_login:
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
    public NewPhoneBean MyparseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        NewPhoneBean bean = gson.fromJson(jsonString, new TypeToken<NewPhoneBean>() {
        }.getType());
        return bean;
    }
}
