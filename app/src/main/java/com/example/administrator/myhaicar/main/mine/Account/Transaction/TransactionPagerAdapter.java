package com.example.administrator.myhaicar.main.mine.Account.Transaction;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class TransactionPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list = null;
    private String[] arrRedTitles = null;

    public TransactionPagerAdapter(FragmentManager fm, List<Fragment> list, String[] arrRedTitles) {
        super(fm);
        this.list = list;
        this.arrRedTitles = arrRedTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return arrRedTitles[position];
    }
}

