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
 * Created by Administrator on 2017/5/25.
 */

public class ChooseCityAdapter extends BaseAdapter {
    private List<CityAllBean> list;
    private Context context;
    private LayoutInflater mInflatr;
    public ChooseCityAdapter(Context context, List<CityAllBean> list){
        this.context=context;
        this.list=list;
        mInflatr= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        if (list.equals(null))
            return 0;
        if (list.size()<=0)
            return 0;
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
    public void reloadGridView(List<CityAllBean> _list, boolean isClear) {
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
            convertView = mInflatr.inflate(R.layout.choosecity_listview_item, parent, false);
            mholder = new ViewHolder(convertView);
            convertView.setTag(mholder);

        }else {
            mholder= (ViewHolder) convertView.getTag();
        }
        SkinManager.getInstance().injectSkin(convertView);
        mholder.textView_cityBean_listView.setText(list.get(position).getName());
        return convertView;
    }
    class ViewHolder{
        private TextView textView_cityBean_listView;
        public ViewHolder(View convertView){
            textView_cityBean_listView= (TextView) convertView.findViewById(R.id.textView_cityBean_listView);
        }
    }
}
