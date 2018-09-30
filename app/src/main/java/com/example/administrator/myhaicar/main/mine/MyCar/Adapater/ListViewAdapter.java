package com.example.administrator.myhaicar.main.mine.MyCar.Adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.myhaicar.R;

/**
 * Created by Administrator on 2017/5/16.
 */

public class ListViewAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    public ListViewAdapter(Context mContext){
        this.mContext=mContext;
        mInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=mInflater.inflate(R.layout.carteam_listview_item,parent,false);
        return convertView;
    }
}
