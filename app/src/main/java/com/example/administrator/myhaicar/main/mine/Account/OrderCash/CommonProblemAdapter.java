package com.example.administrator.myhaicar.main.mine.Account.OrderCash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;

import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class CommonProblemAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<CommonProblemBean.HelplistBean> list;
    public CommonProblemAdapter(Context mContext,List<CommonProblemBean.HelplistBean> list){
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
    public void reloadGridView(List<CommonProblemBean.HelplistBean> _list, boolean isClear) {
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
            convertView=mInflater.inflate(R.layout.haicarhint_listview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        mHolder.textView_hint.setText(list.get(position).getNews_title());
        return convertView;
    }
    class ViewHolder{
        private TextView textView_hint;
        public ViewHolder(View convertView){
            textView_hint= (TextView) convertView.findViewById(R.id.textView_hint);
        }
    }
}
