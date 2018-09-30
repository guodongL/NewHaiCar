package com.example.administrator.myhaicar.main.mine.Account.MineDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.MyListView;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

public class DistrictActivity extends SwipeBackToolBarActivity {
    private MyListView listView_cityNextBean;
    private ImageView toolBar_back;
    private String name;
    private Context mContext=this;
    private String text;
    private DistrictAdapter disAdapter;
    private TextView textView_city;
    private ArrayList<String> area=new ArrayList<>();
    private String usr_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        initView();
        initData();
    }
    private void initView() {
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        listView_cityNextBean= (MyListView) findViewById(R.id.listView_cityNextBean);
        textView_city= (TextView) findViewById(R.id.textView_city);
        disAdapter=new DistrictAdapter(mContext,area);
        listView_cityNextBean.setAdapter(disAdapter);
    }
    private void initData() {
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Intent intent=this.getIntent();
        text = intent.getStringExtra("text");
        textView_city.setText(text);
        name = intent.getStringExtra("name");
        area = intent.getStringArrayListExtra("area");
        disAdapter.reloadGridView(area,true);
        listView_cityNextBean.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final String item=(String)disAdapter.getItem(position);
           // Toast.makeText(mContext,name+text+item,Toast.LENGTH_LONG).show();
         OkHttpUtils.post().url(UrlConfig.Path.BondID_URL).addParams("acts","usr_ads").addParams("usr_id",usr_id).addParams("upcont",name+"|"+text+"|"+item).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    Toast.makeText(mContext,"网络获取失败",Toast.LENGTH_LONG).show();
                }
                @Override
                public void onResponse(String s) {
                    MineSucceedBean mineSucceedBean = parseJsonToMessageBean(s);
                    String treatedok = mineSucceedBean.getTreatedok();
                    if (treatedok.equals("1")) {
                        PreferencesUtils.putString(mContext, "usr_province", name);
                        PreferencesUtils.putString(mContext, "usr_city", text);
                        PreferencesUtils.putString(mContext, "usr_region", item);
                        Intent intent = new Intent();
                        intent.setClass(mContext, MineDetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
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
