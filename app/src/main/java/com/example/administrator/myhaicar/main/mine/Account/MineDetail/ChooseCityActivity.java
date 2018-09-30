package com.example.administrator.myhaicar.main.mine.Account.MineDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.MyListView;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.order.SubmitCash.Model.CityAllBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ChooseCityActivity extends SwipeBackToolBarActivity {
    private Context mContext = this;
    private MyListView listView_chooseCityBean;
    private ImageView toolBar_back;
    private List<CityAllBean> list=new ArrayList<>();
    private ChooseCityAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        initView();
        initData();
    }
    private void initView() {
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        listView_chooseCityBean= (MyListView) findViewById(R.id.listView_chooseCityBean);
        adapter=new ChooseCityAdapter(mContext,list);
        listView_chooseCityBean.setAdapter(adapter);
        //adapter.reloadGridView(list,true);

    }
    private void initData() {
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        BufferedInputStream bis = null;
        ByteArrayOutputStream boas = null;
        try {
            InputStream is = getResources().getAssets().open("cityjson.txt");
            bis = new BufferedInputStream(is);
            boas = new ByteArrayOutputStream();
            int len = 0;
            byte[] bs = new byte[1024 * 8];
            while ((len = bis.read(bs)) != -1) {
                boas.write(bs, 0, len);
                boas.flush();
            }
            list = parseJsonToMessageBean(boas.toString());
            adapter.reloadGridView(list,true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (boas != null) {
                try {
                    boas.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        listView_chooseCityBean.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CityAllBean item = (CityAllBean) adapter.getItem(i);
                List<CityAllBean.CityBean> list=new ArrayList<>();
                list = item.getCity();
                String name = item.getName();
                Intent intent=new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("city",list.toArray());
                intent.putExtra("name",name);
                intent.putExtras(bundle);
                intent.setClass(mContext,ChooseNextCityActivity.class);
                startActivity(intent);
               

            }
        });

    }
    public List<CityAllBean> parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        List<CityAllBean> list = gson.fromJson(jsonString, new TypeToken<List<CityAllBean>>() {}.getType());
        return list;
    }
}
