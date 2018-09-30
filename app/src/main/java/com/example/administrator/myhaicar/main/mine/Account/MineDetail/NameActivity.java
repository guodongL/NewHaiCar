package com.example.administrator.myhaicar.main.mine.Account.MineDetail;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.Account.This.View.ConfirmPopWindow;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class NameActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private EditText editText_name;
    private TextView textView_save;
    private ImageView toolBar_back;
    private   String usr_id;
    private RelativeLayout activity_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        initView();
        initClick();
    }

    private void initView() {
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        activity_name= (RelativeLayout) findViewById(R.id.activity_name);
        textView_save= (TextView) findViewById(R.id.textView_save);
        editText_name= (EditText) findViewById(R.id.editText_name);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
    }

    private void initClick() {
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
      textView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_name.getText().length()>4||editText_name.getText().length()==0){
                    ConfirmPopWindow confirmPopWindow = new ConfirmPopWindow(mContext,"请修改您的名字","名称限制为4位");
                    confirmPopWindow.showAtLocation(activity_name, Gravity.CENTER, 0, 0);
                }else {
                    OkHttpUtils.post().url(UrlConfig.Path.BondID_URL).addParams("acts","usr_name").addParams("usr_id",usr_id).addParams("upcont",editText_name.getText()+"").build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            //Toast.makeText(mContext,"网络获取失败",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponse(String s) {
                            MineSucceedBean mineSucceedBean = parseJsonToMessageBean(s);
                            String treatedok = mineSucceedBean.getTreatedok();
                            if (treatedok.equals("1")) {
                                PreferencesUtils.putString(mContext, "usr_name", editText_name.getText() + "");
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }
    public MineSucceedBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        MineSucceedBean bean = gson.fromJson(jsonString, new TypeToken<MineSucceedBean>() {
        }.getType());
        return bean;
    }
}
