package com.example.administrator.myhaicar.main.mine.MyOrder.Adapater;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.mine.Account.OrderCash.OrderDetailActivity;
import com.example.administrator.myhaicar.main.mine.Account.This.View.ConfirmPopWindow;
import com.example.administrator.myhaicar.main.mine.MyOrder.Model.EndButtonBean;
import com.example.administrator.myhaicar.main.mine.MyOrder.Model.TravelToEndBean;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.changeskin.SkinManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/15.
 */

public class TravelToEndAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private boolean skin;
    private String usr_id;
    private List<TravelToEndBean.ListinfoBean> list;
    public TravelToEndAdapter(Context mContext,List<TravelToEndBean.ListinfoBean> list){
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
    public void reloadGridView(List<TravelToEndBean.ListinfoBean> _list, boolean isClear) {
        if (isClear) {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        skin= PreferencesUtils.getBoolean(mContext,"skin");
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        ViewHolder mHolder=null;
        if (convertView==null) {
            convertView=mInflater.inflate(R.layout.travelnoend_listview_item,parent,false);
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
        Glide.with(mContext).load(list.get(position).getOrder_cartypelogo()).into(mHolder.image_carLogo);
        mHolder.textView_order_addess.setText(list.get(position).getOrder_addess());
        mHolder.textView_order_cartype.setText(list.get(position).getOrder_cartype()+"  "+list.get(position).getOrder_color());
        mHolder.textView_order_date.setText(list.get(position).getOrder_date());
        final String order_id = list.get(position).getOrder_id();
        SkinManager.getInstance().injectSkin(convertView);
        final ViewHolder finalMHolder = mHolder;
        mHolder.button_travelToEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.post().url(UrlConfig.Path.MyOrder_URL).addParams("act","listend").addParams("user_id",usr_id).addParams("order_id",order_id)
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String s) {
                        EndButtonBean endButtonBean = parseJsonToMessageBean(s);
                        String ret_info = endButtonBean.getRet_info();
                        ConfirmPopWindow confirmPopWindow = new ConfirmPopWindow(mContext, "提示", ret_info);
                        confirmPopWindow.showAtLocation(finalMHolder.relative, Gravity.CENTER, 0, 0);
                    }
                });
            }
        });
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
        private LinearLayout linear_detailCount;
        private TextView textView_order_cartype;
        private TextView textView_order_date;
        private TextView textView_order_addess;
        private Button button_travelToEnd;
        private ImageView image_order_type;
        private RelativeLayout relative;
        private ImageView image_carLogo;
        public ViewHolder(View convertView){
            image_carLogo= (ImageView) convertView.findViewById(R.id.image_carLogo);
            relative= (RelativeLayout) convertView.findViewById(R.id.relative);
            image_order_type= (ImageView) convertView.findViewById(R.id.image_order_type);
            button_travelToEnd= (Button) convertView.findViewById(R.id.button_travelToEnd);
            textView_order_addess= (TextView) convertView.findViewById(R.id.textView_order_addess);
            textView_order_date= (TextView) convertView.findViewById(R.id.textView_order_date);
            textView_order_cartype= (TextView) convertView.findViewById(R.id.textView_order_cartype);
            linear_detailCount= (LinearLayout) convertView.findViewById(R.id.linear_detailCount);
        }
    }
    public EndButtonBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        EndButtonBean bean = gson.fromJson(jsonString, new TypeToken<EndButtonBean>() {
        }.getType());
        return bean;
    }
}
