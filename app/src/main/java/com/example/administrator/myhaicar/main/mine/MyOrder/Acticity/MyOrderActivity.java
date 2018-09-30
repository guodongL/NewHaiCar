package com.example.administrator.myhaicar.main.mine.MyOrder.Acticity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.Account.Transaction.TransactionPagerAdapter;
import com.example.administrator.myhaicar.main.mine.MyOrder.View.NoTravelFragment;
import com.example.administrator.myhaicar.main.mine.MyOrder.View.TravelToEndFragment;
import com.example.administrator.myhaicar.tools.TabLayoutUtils;
import com.example.administrator.myhaicar.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private ImageView image_noOrder;
    private RelativeLayout relative_noOrder;
    private TabLayout tablayout_myOrder;
    private ViewPager myOrder_viewPager;
    private TransactionPagerAdapter adapter;
    private String[] arrMyOrderTitles = null;
    private ImageView toolBar_back;
    private ImageView imaeView_history;
    private List<Fragment> list=new ArrayList<>();
    private boolean skin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        initView();
        initClick();
    }
    private void initView() {
        skin = PreferencesUtils.getBoolean(mContext, "skin");
        imaeView_history= (ImageView) findViewById(R.id.imaeView_history);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        myOrder_viewPager= (ViewPager) findViewById(R.id.myOrder_viewPager);
        tablayout_myOrder= (TabLayout) findViewById(R.id.tablayout_myOrder);
        image_noOrder= (ImageView) findViewById(R.id.image_noOrder);
        relative_noOrder= (RelativeLayout) findViewById(R.id.relative_noOrder);
    }

    private void initClick() {
        imaeView_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,HistoryRecordActivity.class);
                startActivity(intent);
            }
        });
        tablayout_myOrder.post(new Runnable() {
            @Override
            public void run() {
                TabLayoutUtils.setIndicator(tablayout_myOrder,50,50);
            }
        });
        arrMyOrderTitles=getResources().getStringArray(R.array.arrMyOrderTitles);
        if (skin){
            tablayout_myOrder.setSelectedTabIndicatorColor(Color.parseColor("#FFAAAAAA"));
            tablayout_myOrder.setTabTextColors(Color.parseColor("#FFAAAAAA"),Color.parseColor("#ffffff"));
        }else {
            tablayout_myOrder.setSelectedTabIndicatorColor(Color.parseColor("#a67c33"));
            tablayout_myOrder.setTabTextColors(Color.parseColor("#605e5e"),Color.parseColor("#a67c33"));
        }
        NoTravelFragment noTravelFragment=new NoTravelFragment();
        TravelToEndFragment travelToEndFragment=new TravelToEndFragment();
        list.add(noTravelFragment);
        list.add(travelToEndFragment);
        adapter=new TransactionPagerAdapter(this.getSupportFragmentManager(),list,arrMyOrderTitles);
        myOrder_viewPager.setAdapter(adapter);
        tablayout_myOrder.setupWithViewPager(myOrder_viewPager);
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        image_noOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relative_noOrder.setVisibility(View.INVISIBLE);
            }
        });


    }
}
