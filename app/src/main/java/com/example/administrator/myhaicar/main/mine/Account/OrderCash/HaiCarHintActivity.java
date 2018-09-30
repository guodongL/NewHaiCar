package com.example.administrator.myhaicar.main.mine.Account.OrderCash;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class HaiCarHintActivity extends SwipeBackToolBarActivity {
private Context mContext=this;
    private PullToRefreshListView listview_haiCarhint;
    private HaiCarHintAdapter hintAdapter;
    private ImageView toolBar_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hai_car_hint);
        initView();
        initClick();
    }
    private void initView() {
        listview_haiCarhint= (PullToRefreshListView) findViewById(R.id.listview_haiCarhint);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        hintAdapter=new HaiCarHintAdapter(mContext);
        listview_haiCarhint.setAdapter(hintAdapter);
    }
    private void initClick() {
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listview_haiCarhint.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                listview_haiCarhint.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listview_haiCarhint.onRefreshComplete();
                    }
                },1500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

}
