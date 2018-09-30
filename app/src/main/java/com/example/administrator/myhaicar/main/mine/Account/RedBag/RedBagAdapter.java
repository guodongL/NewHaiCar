package com.example.administrator.myhaicar.main.mine.Account.RedBag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.myhaicar.R;
import com.zhy.changeskin.SkinManager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */

public class RedBagAdapter extends BaseAdapter {
    private Context mContext;
    private List<RedPacketBean.RedlistinfoBean> redList;
    private LayoutInflater mInflater;
    public RedBagAdapter(Context mContext,List<RedPacketBean.RedlistinfoBean> redList){
        this.mContext=mContext;
        this.redList=redList;
        mInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return redList.size();
    }

    @Override
    public Object getItem(int position) {
        return redList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void reloadGridView(List<RedPacketBean.RedlistinfoBean> _list, boolean isClear) {
        if (isClear) {
            redList.clear();
        }
        redList.addAll(_list);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=mInflater.inflate(R.layout.redbag_listview_item,parent,false);
        SkinManager.getInstance().injectSkin(convertView);
        return convertView;
    }
}
