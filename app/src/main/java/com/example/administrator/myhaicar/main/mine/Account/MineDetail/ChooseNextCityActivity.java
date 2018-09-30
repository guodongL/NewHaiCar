package com.example.administrator.myhaicar.main.mine.Account.MineDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.MyListView;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.order.SubmitCash.Model.CityAllBean;

import java.util.ArrayList;
import java.util.List;

public class ChooseNextCityActivity extends SwipeBackToolBarActivity {
    private ImageView toolBar_back;
    private String name;
    private TextView textView_city;
    private Context mContext=this;
    private List<CityAllBean.CityBean> totlist=new ArrayList<>();
    private MyListView listView_cityNextBean;
    private NextCityAdapter cityAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_next_city);
        initView();
        initData();

    }
    private void initView() {
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        textView_city= (TextView) findViewById(R.id.textView_city);
        listView_cityNextBean= (MyListView) findViewById(R.id.listView_cityNextBean);
        cityAdapter=new NextCityAdapter(mContext,totlist);
        listView_cityNextBean.setAdapter(cityAdapter);
    }

    private void initData() {
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent=this.getIntent();
        name = intent.getStringExtra("name");
        textView_city.setText(name);
        Object[] cobjs = (Object[])intent.getSerializableExtra("city");
        for (int i = 0; i < cobjs.length; i++) {
            CityAllBean.CityBean user = (CityAllBean.CityBean) cobjs[i];
            totlist.add(user);
        }
        listView_cityNextBean.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CityAllBean.CityBean item = (CityAllBean.CityBean) cityAdapter.getItem(i);
                ArrayList<String> area = (ArrayList<String>) item.getArea();
                String text = item.getName();
                Intent intent=new Intent();
                intent.putStringArrayListExtra("area",area);
                intent.putExtra("text",text);
                intent.putExtra("name",name);
                intent.setClass(mContext,DistrictActivity.class);
                startActivity(intent);
            }
        });
    }
}
