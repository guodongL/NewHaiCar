package com.example.administrator.myhaicar.main.mine.MyOrder.Adapater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.mine.Account.OrderCash.OrderDetailActivity;
import com.example.administrator.myhaicar.main.mine.MyOrder.Model.HistoryBean;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.zhy.changeskin.SkinManager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class HistoryRecordAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private boolean skin;
    private List<HistoryBean.ListinfoBean> list;
    public HistoryRecordAdapter(Context mContext,List<HistoryBean.ListinfoBean> list){
        this.mContext=mContext;
        this.list=list;
        mInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void reloadGridView(List<HistoryBean.ListinfoBean> _list, boolean isClear) {
        if (isClear) {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        skin= PreferencesUtils.getBoolean(mContext,"skin");
        ViewHolder mHolder=null;
        if (convertView==null) {
            convertView=mInflater.inflate(R.layout.history_listview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        String order_type = list.get(position).getOrder_type();
        if (order_type.equals("头车")){
            if (skin){
                mHolder.image_carType.setImageResource(R.drawable.corner_night);
            }else {
                mHolder.image_carType.setImageResource(R.drawable.corner_day);
            }
        }else {
            mHolder.image_carType.setImageResource(R.drawable.corner1);
        }
        final String order_id = list.get(position).getOrder_id();
        Glide.with(mContext).load(list.get(position).getOrder_cartypelogo()).into(mHolder.image_carLogo);
        mHolder.textView_order_site.setText(list.get(position).getOrder_site());
        mHolder.textView_carNameColor.setText(list.get(position).getOrder_cartype()+"     "+list.get(position).getOrder_color());
        mHolder.textView_carTime.setText(list.get(position).getOrder_date()+list.get(position).getOrder_time());
        mHolder.textView_orderNumber.setText("订单编号"+list.get(position).getOrder_num());
        SkinManager.getInstance().injectSkin(convertView);
        mHolder.linear_detailCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("order_id",order_id);
                intent.setClass(mContext, OrderDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder{
        private TextView textView_orderNumber;
        private TextView textView_carNameColor;
        private ImageView image_carLogo;
        private ImageView image_carType;
        private TextView textView_carTime;
        private TextView textView_order_site;
        private LinearLayout linear_detailCount;
        public ViewHolder(View convertView){
            textView_orderNumber= (TextView) convertView.findViewById(R.id.textView_orderNumber);
            textView_carNameColor= (TextView) convertView.findViewById(R.id.textView_carNameColor);
            textView_carTime= (TextView) convertView.findViewById(R.id.textView_carTime);
            textView_order_site= (TextView) convertView.findViewById(R.id.textView_order_site);
            image_carLogo= (ImageView) convertView.findViewById(R.id.image_carLogo);
            image_carType= (ImageView) convertView.findViewById(R.id.image_carType);
            linear_detailCount= (LinearLayout) convertView.findViewById(R.id.linear_detailCount);
        }
    }
}
