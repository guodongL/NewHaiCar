package com.example.administrator.myhaicar.main.mine.Account.Transaction;

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
import com.example.administrator.myhaicar.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private TabLayout tablayout_transaction;
    private ViewPager trading_viewPager;
    private TransactionPagerAdapter adapter;
    private String[] arrRedTitles = null;
    private ImageView toolBar_back;
    private boolean skin;
    private List<Fragment> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        initView();
        initData();

    }
    private void initView() {
         skin = PreferencesUtils.getBoolean(mContext, "skin");
        tablayout_transaction= (TabLayout) findViewById(R.id.tablayout_transaction);
        trading_viewPager= (ViewPager) findViewById(R.id.trading_viewPager);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
       // trading_viewPager.setOffscreenPageLimit(5);
    }

    private void initData() {
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        arrRedTitles=getResources().getStringArray(R.array.arrRedTitles);
        if (skin){
            tablayout_transaction.setSelectedTabIndicatorColor(Color.parseColor("#FFAAAAAA"));
            tablayout_transaction.setTabTextColors(Color.parseColor("#FFAAAAAA"),Color.parseColor("#ffffff"));
        }else {
            tablayout_transaction.setSelectedTabIndicatorColor(Color.parseColor("#a67c33"));
            tablayout_transaction.setTabTextColors(Color.parseColor("#605e5e"),Color.parseColor("#a67c33"));
        }
        AllFragment allFragment=new AllFragment();
        WeddingCarFragment wdFragment=new WeddingCarFragment();
        DueFeeFragment dueOrderFragment=new DueFeeFragment();
        RedPageFragment redOrderFragment=new RedPageFragment();
        WithDrawFragment withDrawFragment=new WithDrawFragment();
        WelfareFragment welfOrderFragment=new WelfareFragment();
        list.add(allFragment);
        list.add(wdFragment);
        list.add(dueOrderFragment);
        list.add(redOrderFragment);
        list.add(withDrawFragment);
        list.add(welfOrderFragment);
        adapter=new TransactionPagerAdapter(this.getSupportFragmentManager(),list,arrRedTitles);
        trading_viewPager.setAdapter(adapter);
        tablayout_transaction.setupWithViewPager(trading_viewPager);
    }
/*    @Subscribe
    public void onEventMainThread(BankName event) {
        String msg =event.getBankName();
        if (msg.equals("1")){
            tablayout_transaction.setSelectedTabIndicatorColor(Color.parseColor("#FFAAAAAA"));
            tablayout_transaction.setTabTextColors(Color.parseColor("#FFAAAAAA"),Color.parseColor("#ffffff"));
            SkinManager.getInstance().changeSkin("night");


        }else if (msg.equals("2")){
            tablayout_transaction.setSelectedTabIndicatorColor(Color.parseColor("#a67c33"));
            tablayout_transaction.setTabTextColors(Color.parseColor("#FFAAAAAA"),Color.parseColor("#a67c33"));
            SkinManager.getInstance().changeSkin("day");
        }

    }*/
   /* @Override
    protected void onDestroy()
    {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }*/
}
