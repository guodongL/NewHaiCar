package com.example.administrator.myhaicar.main.mine.Event.Adapater;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.mine.Event.Model.EvenBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.changeskin.SkinManager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class WelfareAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<EvenBean.ActilistinfoBean> list;
    private static final int TYPE1 = 0, TYPE2 = 1;
    public WelfareAdapter(Context mContext,List<EvenBean.ActilistinfoBean> list){
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
  /*  @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        String status = list.get(position).getActi_state();
        if (status.equals("0")){
            return TYPE1;
        }else if (status.equals("1")){
            return TYPE2;
        }
        return -1;
    }*/

    public void reloadGridView(List<EvenBean.ActilistinfoBean> _list, boolean isClear) {
        if (isClear) {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder=null;
        if (convertView==null) {
            convertView=mInflater.inflate(R.layout.mywel_listview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        String acti_vip = list.get(position).getActi_vip();
        if (acti_vip.equals("1")){
            mHolder.image_isornoVip.setVisibility(View.VISIBLE);
        }else {
            mHolder.image_isornoVip.setVisibility(View.GONE);
        }
        String acti_state = list.get(position).getActi_state();
        if (acti_state.equals("1")){
            mHolder.view1.setVisibility(View.VISIBLE);
            mHolder.textView_end.setVisibility(View.VISIBLE);
            mHolder.relative.setClickable(true);
            mHolder.relative.setFocusable(true);
        }else {
            mHolder.relative.setClickable(false);
            mHolder.relative.setFocusable(false);
            mHolder.view1.setVisibility(View.GONE);
            mHolder.textView_end.setVisibility(View.GONE);
        }
        mHolder.image_acti_pic.setImageURI(Uri.parse(list.get(position).getActi_pic()));
        mHolder.textView_acti_title.setText(list.get(position).getActi_title());
        mHolder.textView_acti_date.setText(list.get(position).getActi_date());
        mHolder.textView_acti_price.setText(list.get(position).getActi_price()+"元");
        SkinManager.getInstance().injectSkin(convertView);
        return convertView;
    }
    class ViewHolder{
        private ImageView image_isornoVip;
        private TextView textView_acti_title;
        private TextView textView_acti_date;
        private TextView textView_acti_price;
        private View view1;
        private SimpleDraweeView image_acti_pic;
        private TextView textView_end;
        private RelativeLayout relative;
        public ViewHolder(View convertView){
            image_acti_pic= (SimpleDraweeView) convertView.findViewById(R.id.image_acti_pic);
            relative= (RelativeLayout) convertView.findViewById(R.id.relative);
            view1=convertView.findViewById(R.id.view1);
            textView_end= (TextView) convertView.findViewById(R.id.textView_end);
            image_isornoVip= (ImageView) convertView.findViewById(R.id.image_isornoVip);
            textView_acti_price= (TextView) convertView.findViewById(R.id.textView_acti_price);
            textView_acti_date= (TextView) convertView.findViewById(R.id.textView_acti_date);
            textView_acti_title= (TextView) convertView.findViewById(R.id.textView_acti_title);
        }
    }
}
