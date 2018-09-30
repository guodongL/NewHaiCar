package com.example.administrator.myhaicar.main.mine.Business.Acticity;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.Account.Transaction.TransactionPagerAdapter;
import com.example.administrator.myhaicar.main.mine.Business.View.CarWashFragment;
import com.example.administrator.myhaicar.main.mine.Business.View.ComprehenRankFragment;
import com.example.administrator.myhaicar.main.mine.Business.View.GoldJewelryFragment;
import com.example.administrator.myhaicar.main.mine.Business.View.HaiPlayerFragment;
import com.example.administrator.myhaicar.main.mine.Business.View.MaintenanceFragment;
import com.example.administrator.myhaicar.main.mine.Business.View.TravelAroundFragment;
import com.example.administrator.myhaicar.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class BusinessActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private TabLayout tabLaout_business;
    private ViewPager business_viewPager;
    private TransactionPagerAdapter adapter;
    private String[] arrBusiness = null;
    private ImageView toolBar_back;
    private boolean skin;
    private List<Fragment> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        initView();
        initClick();
    }
    private void initView() {
        skin = PreferencesUtils.getBoolean(mContext, "skin");
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        business_viewPager= (ViewPager) findViewById(R.id.business_viewPager);
        tabLaout_business= (TabLayout) findViewById(R.id.tabLaout_business);
    }

    private void initClick() {
        arrBusiness=getResources().getStringArray(R.array.arrBusiness);
        if (skin){
            tabLaout_business.setSelectedTabIndicatorColor(Color.parseColor("#FFAAAAAA"));
            tabLaout_business.setTabTextColors(Color.parseColor("#FFAAAAAA"),Color.parseColor("#ffffff"));
        }else {
            tabLaout_business.setSelectedTabIndicatorColor(Color.parseColor("#a67c33"));
            tabLaout_business.setTabTextColors(Color.parseColor("#605e5e"),Color.parseColor("#a67c33"));
        }
          ComprehenRankFragment rankFragment=new ComprehenRankFragment();
        GoldJewelryFragment jewelryFragment=new GoldJewelryFragment();
        TravelAroundFragment aroundFragment=new TravelAroundFragment();
        CarWashFragment washFragment=new CarWashFragment();
        MaintenanceFragment maintenanceFragment=new MaintenanceFragment();
        HaiPlayerFragment playerFragment=new HaiPlayerFragment();
          list.add(rankFragment);
          list.add(jewelryFragment);
          list.add(aroundFragment);
          list.add(washFragment);
          list.add(maintenanceFragment);
          list.add(playerFragment);
        adapter=new TransactionPagerAdapter(this.getSupportFragmentManager(),list,arrBusiness);
        business_viewPager.setAdapter(adapter);
        tabLaout_business.setupWithViewPager(business_viewPager);
        tabLaout_business.setTabMode(TabLayout.MODE_SCROLLABLE);
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
