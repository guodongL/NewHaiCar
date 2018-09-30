package com.example.administrator.myhaicar.main.mine.MyCar.Adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.mine.MyCar.Model.CarTeamBean;
import com.zhy.changeskin.SkinManager;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */

public class CarTeamAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<CarTeamBean.CarlistinfoBean> list;
    public CarTeamAdapter(Context mContext,List<CarTeamBean.CarlistinfoBean> list){
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
    public void reloadGridView(List<CarTeamBean.CarlistinfoBean>  _list, boolean isClear) {
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
            convertView=mInflater.inflate(R.layout.team_listview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(list.get(position).getCar_logo()).into(mHolder.image_carLogo);
        mHolder.textView_carColor.setText(list.get(position).getCar_color());
        mHolder.textView_carName.setText(list.get(position).getCar_name());
        mHolder.textView_carPeople.setText("队员"+list.get(position).getCar_peonum()+"人");
        SkinManager.getInstance().injectSkin(convertView);
        return convertView;
    }
    class ViewHolder{
        private TextView textView_carName;
        private TextView textView_carColor;
        private TextView textView_carPeople;
        private ImageView image_carLogo;
        public ViewHolder(View convertView){
            textView_carName= (TextView) convertView.findViewById(R.id.textView_carName);
            textView_carColor= (TextView) convertView.findViewById(R.id.textView_carColor);
            textView_carPeople= (TextView) convertView.findViewById(R.id.textView_carPeople);
            image_carLogo= (ImageView) convertView.findViewById(R.id.image_carLogo);

        }
    }
}
