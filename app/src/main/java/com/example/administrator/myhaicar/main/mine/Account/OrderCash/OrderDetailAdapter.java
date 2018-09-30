package com.example.administrator.myhaicar.main.mine.Account.OrderCash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;
import com.zhy.changeskin.SkinManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class OrderDetailAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mTitles=new ArrayList<>();
    private LayoutInflater mInflater;
    public OrderDetailAdapter(Context mContext){
        this.mContext=mContext;
        mInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mTitles=new ArrayList<String>();
        for (int i=0;i<4;i++){
            int index=i+1;
            mTitles.add(index+","+"我是问题我是问题我是问题");
        }
    }
    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public Object getItem(int position) {
        return mTitles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder mHolder=null;
        if (convertView==null) {
            convertView=mInflater.inflate(R.layout.haicarhint_listview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        SkinManager.getInstance().injectSkin(convertView);
        mHolder.textView_hint.setText(mTitles.get(position));
        return convertView;
    }
    class ViewHolder{
        private TextView textView_hint;
        public ViewHolder(View convertView){
            textView_hint= (TextView) convertView.findViewById(R.id.textView_hint);
        }
    }
}
