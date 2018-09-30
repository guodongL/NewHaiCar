package com.example.administrator.myhaicar.main.order.SubmitCash.Adapater;

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

public class CityAdapter extends BaseAdapter {
    private List<CityAllBean> list;
   private Context context;
    private LayoutInflater mInflatr;
    public CityAdapter(Context context, List<CityAllBean> list){
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
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mholder = null;
        if (convertView == null) {
            convertView = mInflatr.inflate(R.layout.carbean_listview_item, parent, false);
            mholder = new ViewHolder(convertView);
            convertView.setTag(mholder);

        }else {
            mholder= (ViewHolder) convertView.getTag();
        }
        SkinManager.getInstance().injectSkin(convertView);
        mholder.textView_carBean_listView.setText(list.get(position).getName());
        return convertView;
    }
    public void reloadGridView(List<CityAllBean> _list, boolean isClear) {
        if (isClear) {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }
    class ViewHolder{
        private TextView textView_carBean_listView;
        public ViewHolder(View convertView){
            textView_carBean_listView= (TextView) convertView.findViewById(R.id.textView_carBean_listView);
        }
    }
}
