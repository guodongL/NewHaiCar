package com.example.administrator.myhaicar.main.mine.Account.Transaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;
import com.zhy.changeskin.SkinManager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class TransactionAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<TradingBean.RecolistinfoBean> list;
    public TransactionAdapter(Context mContext,List<TradingBean.RecolistinfoBean> list){
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
    public void reloadGridView(List<TradingBean.RecolistinfoBean> _list, boolean isClear) {
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
            convertView=mInflater.inflate(R.layout.trading_listview_item,parent,false);
            mholder = new ViewHolder(convertView);
            convertView.setTag(mholder);
        }else {
            mholder= (ViewHolder) convertView.getTag();
        }
         mholder.textView_time.setText(list.get(position).getReco_created());
        if (list.get(position).getReco_type().equals("红包")||list.get(position).getReco_type().equals("迎亲赚钱")||list.get(position).getReco_type().equals("退还保证金")){
            mholder.textView__mount.setText("+"+list.get(position).getReco_mount());
        }
        else
        {
            mholder.textView__mount.setText("-"+list.get(position).getReco_mount());
        }
        mholder.textView__remarks.setText(list.get(position).getReco_remarks());
        mholder.textView_orderNumber.setText(list.get(position).getReco_num());
        mholder.textView_payment.setText(list.get(position).getReco_payment());
        mholder.textView_typeTitle.setText(list.get(position).getReco_type()+"(元)");
        mholder.textView_type.setText(list.get(position).getReco_type());
        SkinManager.getInstance().injectSkin(convertView);
        return convertView;
    }
    class ViewHolder{
        private TextView textView__mount;
        private TextView textView_time;
        private TextView textView_type;
        private TextView textView_orderNumber;
        private TextView textView_payment;
        private TextView textView__remarks;
        private TextView textView_typeTitle;
        public ViewHolder(View convertView){
            textView_typeTitle= (TextView) convertView.findViewById(R.id.textView_typeTitle);
            textView__mount= (TextView) convertView.findViewById(R.id.textView__mount);
            textView_time= (TextView) convertView.findViewById(R.id.textView_time);
            textView_type= (TextView) convertView.findViewById(R.id.textView_type);
            textView_orderNumber= (TextView) convertView.findViewById(R.id.textView_orderNumber);
            textView_payment= (TextView) convertView.findViewById(R.id.textView_payment);
            textView__remarks= (TextView) convertView.findViewById(R.id.textView__remarks);
        }
    }
}
