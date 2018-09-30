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
import com.example.administrator.myhaicar.main.mine.MyOrder.Model.NoTravelBean;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.zhy.changeskin.SkinManager;

import java.util.List;


/**
 * Created by Administrator on 2017/5/15.
 */

public class NoTravelAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private boolean skin;
    private List<NoTravelBean.ListinfoBean> list;
    public NoTravelAdapter(Context mContext,List<NoTravelBean.ListinfoBean> list){
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
    public void reloadGridView(List<NoTravelBean.ListinfoBean> _list, boolean isClear) {
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
            convertView=mInflater.inflate(R.layout.notravel_listview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        String order_type = list.get(position).getOrder_type();
        if (order_type.equals("头车")){
            if (skin){
                mHolder.image_order_type.setImageResource(R.drawable.corner_night);
            }else {
                mHolder.image_order_type.setImageResource(R.drawable.corner_day);
            }
        }else {
            mHolder.image_order_type.setImageResource(R.drawable.corner1);
        }
        final String order_id = list.get(position).getOrder_id();
        mHolder.textView_order_cartype.setText(list.get(position).getOrder_cartype()+"   "+list.get(position).getOrder_color());
        mHolder.textView_order_countdown.setText(+list.get(position).getOrder_countdown()+"");
        mHolder.textView_order_update.setText(list.get(position).getOrder_update());
        Glide.with(mContext).load(list.get(position).getOrder_cartypelogo()).into(mHolder.image_carLogo);
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
        private ImageView image_carLogo;
        private ImageView image_order_type;
        private TextView textView_order_cartype;
        private TextView textView_order_countdown;
        private TextView textView_order_update;
        private LinearLayout linear_detailCount;
        public ViewHolder(View convertView){
            linear_detailCount= (LinearLayout) convertView.findViewById(R.id.linear_detailCount);
            textView_order_cartype= (TextView) convertView.findViewById(R.id.textView_order_cartype);
            textView_order_countdown= (TextView) convertView.findViewById(R.id.textView_order_countdown);
            textView_order_update= (TextView) convertView.findViewById(R.id.textView_order_update);
            image_order_type= (ImageView) convertView.findViewById(R.id.image_order_type);
            image_carLogo= (ImageView) convertView.findViewById(R.id.image_carLogo);


        }
    }
}
