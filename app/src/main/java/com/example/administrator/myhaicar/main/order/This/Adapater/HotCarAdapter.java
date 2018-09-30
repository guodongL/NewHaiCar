package com.example.administrator.myhaicar.main.order.This.Adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.order.This.Model.CarGridViewBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/4.
 */

public class HotCarAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private Button ObjectButton;
    private List<CarGridViewBean.CarsortBean> list;
    private String s;
    public HotCarAdapter(Context mContext,List<CarGridViewBean.CarsortBean> list){
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
    public void reloadGridView(List<CarGridViewBean.CarsortBean> _list, boolean isClear) {
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
            convertView = mInflater.inflate(R.layout.hotcar_confirm_item, parent, false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        mHolder.button_car_sortname.setTag(list.get(position).getCar_sortid());
        mHolder.button_car_sortname.setText(list.get(position).getCar_sortname());
        mHolder.button_car_sortname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick((Button)v);
            }
        });
        return convertView;
    }
    public void  buttonClick(Button button) {
        if (ObjectButton ==null ?false:(!ObjectButton.equals(button)))
        {
            ObjectButton.setSelected(!ObjectButton.isSelected());
            button.setSelected(!button.isSelected());
            ObjectButton=button;
            s = ObjectButton.getTag()+ "";
            mItemOnClickListener.itemOnClickListener(s);
            return;
        }
        if (!button.isSelected())
        {
            button.setSelected(!button.isSelected());
            ObjectButton=button;
            s = ObjectButton.getTag() + "";
          mItemOnClickListener.itemOnClickListener(s);
            return;
        }
    }
    private ItemOnClickListener mItemOnClickListener;
    public void setmItemOnClickListener(ItemOnClickListener listener){
        this.mItemOnClickListener = listener;
    }
    public interface ItemOnClickListener{
        public void itemOnClickListener(String carTypeID);
    }
    class ViewHolder{
        private Button button_car_sortname;
        public ViewHolder(View convertView){
            button_car_sortname= (Button) convertView.findViewById(R.id.button_car_sortname);
        }
    }
}
