package com.example.administrator.myhaicar.main.mine.Account.MineDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.order.SubmitCash.Model.CityAllBean;
import com.zhy.changeskin.SkinManager;

import java.util.List;

/**
 * Created by 果冻 on 2017/3/12.
 */

public class NextCityAdapter extends BaseAdapter {
    private List<CityAllBean.CityBean> list;
    private Context mContext;
    private LayoutInflater mInflater;
    public NextCityAdapter(Context mContext, List<CityAllBean.CityBean> list){
        this.mContext=mContext;
        this.list=list;
        mInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mholder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.city_listview_item, viewGroup, false);
            mholder = new ViewHolder(view);
            view.setTag(mholder);

        }else {
            mholder= (ViewHolder) view.getTag();
        }
        SkinManager.getInstance().injectSkin(view);
        mholder.textView_cityBean_listView.setText(list.get(i).getName());
        return view;
    }
    class ViewHolder{
        private TextView textView_cityBean_listView;
        public ViewHolder(View convertView){
            textView_cityBean_listView= (TextView) convertView.findViewById(R.id.textView_cityBean_listView);
        }
    }
}
