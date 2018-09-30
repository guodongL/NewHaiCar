package com.example.administrator.myhaicar.main.mine.Account.MineDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.commond.ConfirmPopWindowThree;
import com.example.administrator.myhaicar.main.mine.Setting.BoundBean;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class NewPhoneNextActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private ImageView toolBar_back;
    private Button button_nextNewphone;
    private String phone;
    private EditText edit_veri_code;
    private TextView textView_phoneNumber;
    private  String phone_number;
    private String usr_id;
    private RelativeLayout activity_new_phone_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_phone_next);
        initView();
        initClick();


    }
    private void initView() {
        Intent intent=this.getIntent();
        phone = intent.getStringExtra("phone");
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        phone_number = PreferencesUtils.getString(mContext, "phone_number");
        activity_new_phone_next= (RelativeLayout) findViewById(R.id.activity_new_phone_next);
        edit_veri_code= (EditText) findViewById(R.id.edit_veri_code);
        textView_phoneNumber= (TextView) findViewById(R.id.textView_phoneNumber);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        button_nextNewphone= (Button) findViewById(R.id.button_nextNewphone);
    }
    private void initClick() {
        textView_phoneNumber.setText(phone_number);
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_nextNewphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String veri_code = edit_veri_code.getText().toString();
                if (veri_code.equals("")) {
                    ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext, "请您输入11位手机号");
                    confirmPopWindowtwo.showAtLocation(activity_new_phone_next, Gravity.CENTER, 0, 0);
                } else {
                    OkHttpUtils.post().url(UrlConfig.Path.BondID_URL).addParams("acts", "usr_phone").addParams("usr_id", usr_id).addParams("upcont", phone)
                            .addParams("veri_code",veri_code).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            Toast.makeText(mContext, "网络连接失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String s) {
                            BoundBean boundBean = MyparseJsonToMessageBean(s);
                            String treatedok = boundBean.getTreatedok();
                            if (treatedok.equals("1")){
                                Intent intent = new Intent();
                                intent.setClass(mContext, NewPhoneSucceedActivity.class);
                                startActivity(intent);
                            }else {
                                ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext, treatedok);
                                confirmPopWindowtwo.showAtLocation(activity_new_phone_next, Gravity.CENTER, 0, 0);
                            }
                        }
                    });

                }
            }
        });
    }
    public BoundBean MyparseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        BoundBean bean = gson.fromJson(jsonString, new TypeToken<BoundBean>() {
        }.getType());
        return bean;
    }
}
