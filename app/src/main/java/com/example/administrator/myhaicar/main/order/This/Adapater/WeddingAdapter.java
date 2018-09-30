package com.example.administrator.myhaicar.main.order.This.Adapater;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.ConfirmPopWindowThree;
import com.example.administrator.myhaicar.main.order.SubmitCash.Acticity.MarginActivity;
import com.example.administrator.myhaicar.main.order.This.Model.GrabOrderBean;
import com.example.administrator.myhaicar.main.order.This.Model.OrderBean;
import com.example.administrator.myhaicar.main.order.This.View.OrderDrfulPopu;
import com.example.administrator.myhaicar.main.order.This.View.OrderPopuwindow;
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
 * Created by Administrator on 2017/4/27.
 */

public class WeddingAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<OrderBean.HotlistinfoBean> list;
    private boolean skin;
    private String usr_id;
    public WeddingAdapter(Context mContext,List<OrderBean.HotlistinfoBean> list,String usr_id){
        this.mContext=mContext;
        this.list=list;
        this.usr_id=usr_id;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        skin=PreferencesUtils.getBoolean(mContext,"skin");
        ViewHolder mHolder=null;
        if (convertView==null) {
        convertView=mInflater.inflate(R.layout.weeding_listview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        String order_carclass = list.get(position).getOrder_carclass();
        if (order_carclass.equals("头车")){
            if (skin){
                mHolder.image_order_carclass.setImageResource(R.drawable.corner_night);
            }else {
                mHolder.image_order_carclass.setImageResource(R.drawable.corner_day);
            }
        }else {
            mHolder.image_order_carclass.setImageResource(R.drawable.corner1);
        }
            if (skin){

                    mHolder.image_grabOrder.setImageResource(R.drawable.grabbutton_night);

            }else {
                mHolder.image_grabOrder.setImageResource(R.drawable.grabbutton_day);
            }

        final String order_date = list.get(position).getOrder_date();
       String  date=order_date.substring(5,order_date.length()-1);
        mHolder.textView_order_date.setText(date);
        String order_km = list.get(position).getOrder_km();
        String substring = order_km.substring(0, order_km.indexOf("."));
        mHolder.textView_order_km.setText(substring+"Km");
        mHolder.textView_order_price.setText(list.get(position).getOrder_price()+"元");
        mHolder.textView_order_site.setText(list.get(position).getOrder_site());
        Glide.with(mContext).load(list.get(position).getOrder_cartypelogo()).into(mHolder.image_carLogo);
        final String order_time = list.get(position).getOrder_time();
        if (order_time.equals("上午")){
            if (skin) {
                mHolder.image_order_time.setImageResource(R.drawable.travelam_night);
            }else {
                mHolder.image_order_time.setImageResource(R.drawable.travelam_day);
            }
        }else {
            if (skin){
                mHolder.image_order_time.setImageResource(R.drawable.travelam1_night);
            }else {
                mHolder.image_order_time.setImageResource(R.drawable.travelpm_day);
            }

        }
        final String order_id = list.get(position).getOrder_id();
        final String order_price = list.get(position).getOrder_price();
        final String order_cartype = list.get(position).getOrder_cartype();
        mHolder.textView_order_cartype.setText(list.get(position).getOrder_cartype()+" "+list.get(position).getOrder_color());
        final ViewHolder finalMHolder = mHolder;
        mHolder.image_grabOrder.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              OkHttpUtils.post().url(UrlConfig.Path.Order_URL).addParams("act","uporder").addParams("order_city_true","0").addParams("order_id",order_id).addParams("usr_id",usr_id)
                      .build().execute(new StringCallback() {
                  @Override
                  public void onError(Call call, Exception e) {
                      Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                  }

                  @Override
                  public void onResponse(String s) {
                      GrabOrderBean grabOrderBean = parseJsonToMessageBean(s);
                      String ret_prompt = grabOrderBean.getRet_prompt();
                      String ret_info = grabOrderBean.getRet_info();
                     /* Intent intent=new Intent();
                      intent.putExtra("order_id",order_id);
                      intent.putExtra("order_data",order_date);
                      intent.putExtra("order_time",order_time);
                      intent.putExtra("order_cartype",order_cartype);
                      intent.putExtra("order_price",order_price);
                      intent.setClass(mContext, MarginActivity.class);
                      mContext.startActivity(intent);*/
                   if (ret_prompt.equals("0")){
                          OrderDrfulPopu confirmPopWindow = new OrderDrfulPopu(mContext,ret_info);
                          confirmPopWindow.showAtLocation(finalMHolder.relative_order, Gravity.CENTER, 0, 0);
                       if (skin){

                           finalMHolder.image_grabOrder.setImageResource(R.drawable.grabnobutton_night);

                       }else {
                           finalMHolder.image_grabOrder.setImageResource(R.drawable.grabnobutton);
                       }
                      }else if (ret_prompt.equals("1")){
                          OrderPopuwindow confirmPopWindow = new OrderPopuwindow(mContext,ret_info);
                          confirmPopWindow.showAtLocation(finalMHolder.relative_order, Gravity.CENTER, 0, 0);
                          if (skin) {
                              finalMHolder.image_grabOrder.setImageResource(R.drawable.grabnobutton_night);
                          }else {
                              finalMHolder.image_grabOrder.setImageResource(R.drawable.grabnobutton);
                          }

                      }else if (ret_prompt.equals("2")){
                          Intent intent=new Intent();
                          intent.putExtra("order_id",order_id);
                          intent.putExtra("order_data",order_date);
                          intent.putExtra("order_time",order_time);
                          intent.putExtra("order_cartype",order_cartype);
                          intent.putExtra("order_price",order_price);
                          intent.setClass(mContext, MarginActivity.class);
                          mContext.startActivity(intent);
                      }else {
                          ConfirmPopWindowThree confirmPopWindow = new ConfirmPopWindowThree(mContext,ret_info);
                          confirmPopWindow.showAtLocation(finalMHolder.relative_order, Gravity.CENTER, 0, 0);
                      }
                  }
              });
          }
      });
        SkinManager.getInstance().injectSkin(convertView);
        return convertView;
    }
    public void reloadGridView(List<OrderBean.HotlistinfoBean> _list, boolean isClear) {
        if (isClear) {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }
    class ViewHolder{
        private ImageView image_grabOrder;
        private ImageView image_carLogo;
        private ImageView image_order_carclass;
        private ImageView image_order_time;
        private TextView textView_order_date;
        private TextView textView_order_km;
        private TextView textView_order_price;
        private TextView textView_order_site;
        private TextView textView_order_cartype;
        private RelativeLayout relative_order;
        public ViewHolder(View convertView){
            image_carLogo= (ImageView) convertView.findViewById(R.id.image_carLogo);
            relative_order= (RelativeLayout) convertView.findViewById(R.id.relative_order);
            image_grabOrder= (ImageView) convertView.findViewById(R.id.image_grabOrder);
            image_order_carclass= (ImageView) convertView.findViewById(R.id.image_order_carclass);
            image_order_time= (ImageView) convertView.findViewById(R.id.image_order_time);
            textView_order_date= (TextView) convertView.findViewById(R.id.textView_order_date);
            textView_order_km= (TextView) convertView.findViewById(R.id.textView_order_km);
            textView_order_price= (TextView) convertView.findViewById(R.id.textView_order_price);
            textView_order_site= (TextView) convertView.findViewById(R.id.textView_order_site);
            textView_order_cartype= (TextView) convertView.findViewById(R.id.textView_order_cartype);
        }
    }
    public GrabOrderBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        GrabOrderBean bean = gson.fromJson(jsonString, new TypeToken<GrabOrderBean>() {
        }.getType());
        return bean;
    }
}
