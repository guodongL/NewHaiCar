package com.example.administrator.myhaicar.main.mine.MyCar.Adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.mine.MyCar.Model.CarTypeBean;
import com.zhy.changeskin.SkinManager;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public class CarTypeAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<CarTypeBean.BrandListBean> list;
    public CarTypeAdapter(Context mContext,List<CarTypeBean.BrandListBean> list){
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
    public void reloadGridView(List<CarTypeBean.BrandListBean> _list, boolean isClear) {
        if (isClear) {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder mholder = null;
        if (convertView == null) {
            convertView=mInflater.inflate(R.layout.carname_listview_item,parent,false);
            mholder = new ViewHolder(convertView);
            convertView.setTag(mholder);
        }else {
            mholder= (ViewHolder) convertView.getTag();
        }
        mholder.textView_carName.setText(list.get(position).getBrand_name());
        SkinManager.getInstance().injectSkin(convertView);
        return convertView;
    }
    class ViewHolder{
        private TextView textView_carName;
        public ViewHolder(View convertView){
            textView_carName= (TextView) convertView.findViewById(R.id.textView_carName);
        }
    }
}
