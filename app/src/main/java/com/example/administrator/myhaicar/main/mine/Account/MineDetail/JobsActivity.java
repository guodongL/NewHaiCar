package com.example.administrator.myhaicar.main.mine.Account.MineDetail;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class JobsActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private EditText editText_jobs;
    private TextView textView_save;
    private ImageView toolBar_back;
    private String  usr_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        initView();
        initClick();
    }

    private void initView() {
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        textView_save= (TextView) findViewById(R.id.textView_save);
        editText_jobs= (EditText) findViewById(R.id.editText_jobs);
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
                OkHttpUtils.post().url(UrlConfig.Path.BondID_URL).addParams("acts","usr_ooc").addParams("usr_id",usr_id).addParams("upcont",editText_jobs.getText()+"").build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext,"网络获取失败",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onResponse(String s) {
                        MineSucceedBean mineSucceedBean = parseJsonToMessageBean(s);
                        String treatedok = mineSucceedBean.getTreatedok();
                        if (treatedok.equals("1")) {
                            PreferencesUtils.putString(mContext, "usr_occupation", editText_jobs.getText() + "");
                            finish();
                        }
                    }
                });
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
