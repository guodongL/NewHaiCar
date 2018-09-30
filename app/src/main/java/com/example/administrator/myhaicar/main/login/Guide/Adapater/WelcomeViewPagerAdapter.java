package com.example.administrator.myhaicar.main.login.Guide.Adapater;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */

public class WelcomeViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<ImageView> list = null;
    private LayoutInflater mInflater = null;

    public WelcomeViewPagerAdapter(Context mContext, List<ImageView> list) {
        this.mContext=mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(list.get(position));
    }
}
