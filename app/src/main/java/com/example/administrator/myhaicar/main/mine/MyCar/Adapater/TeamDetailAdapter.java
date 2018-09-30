package com.example.administrator.myhaicar.main.mine.MyCar.Adapater;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.mine.MyCar.Model.TeamDetailBean;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.changeskin.SkinManager;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */

public class TeamDetailAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private boolean skin;
    private List<TeamDetailBean.CarlistinfoBean> list;
    public TeamDetailAdapter(Context mContext,List<TeamDetailBean.CarlistinfoBean> list){
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
    public void reloadGridView(List<TeamDetailBean.CarlistinfoBean>  _list, boolean isClear) {
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
            convertView=mInflater.inflate(R.layout.teamdetail_listview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        mHolder.image_usr_headimg.setImageURI(Uri.parse(list.get(position).getUsr_headimg()));
        mHolder.textView_usr_name.setText(list.get(position).getUsr_name());
        mHolder.textView_usr_lev.setText(list.get(position).getUsr_lev());
        int usr_vip = list.get(position).getUsr_vip();
        if (usr_vip==0){
            if (skin) {
                mHolder.textView_usr_vip.setText("未开通VIP会员");
                mHolder.textView_usr_vip.setTextColor(Color.parseColor("#fffefe"));
            }else {
                mHolder.textView_usr_vip.setText("未开通VIP会员");
                mHolder.textView_usr_vip.setTextColor(Color.parseColor("#666666"));
            }
        }else {
            if (skin){
                mHolder.textView_usr_vip.setText("VIP会员");
                mHolder.textView_usr_vip.setTextColor(Color.parseColor("#ca9865"));
            }else {
                mHolder.textView_usr_vip.setText("VIP会员");
                mHolder.textView_usr_vip.setTextColor(Color.parseColor("#ca9865"));
            }
        }
        SkinManager.getInstance().injectSkin(convertView);
        return convertView;
    }
    class ViewHolder{
        private TextView textView_usr_name;
        private TextView textView_usr_vip;
        private TextView textView_usr_lev;
        private SimpleDraweeView image_usr_headimg;
        public ViewHolder(View convertView){
            textView_usr_name= (TextView) convertView.findViewById(R.id.textView_usr_name);
            textView_usr_vip= (TextView) convertView.findViewById(R.id.textView_usr_vip);
            textView_usr_lev= (TextView) convertView.findViewById(R.id.textView_usr_lev);
            image_usr_headimg= (SimpleDraweeView) convertView.findViewById(R.id.image_usr_headimg);

        }
    }
}
