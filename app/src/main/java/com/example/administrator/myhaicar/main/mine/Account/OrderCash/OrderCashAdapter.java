package com.example.administrator.myhaicar.main.mine.Account.OrderCash;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;
import com.zhy.changeskin.SkinManager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */

public class OrderCashAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<CashBean.BondlistBean> list;
    public OrderCashAdapter(Context mContext,List<CashBean.BondlistBean> list){
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
    public void reloadGridView(List<CashBean.BondlistBean> _list, boolean isClear) {
        if (isClear) {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder=null;
        if (convertView==null) {
            convertView=mInflater.inflate(R.layout.ordercash_listview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        mHolder.textView_created.setText(list.get(position).getCreated()+"");
        mHolder.textView_out_trade_no.setText(list.get(position).getOut_trade_no());
        mHolder.textView_paytype.setText(list.get(position).getPaytype());
        mHolder.textView_payyon.setText(list.get(position).getPayyon());
        mHolder.textView_refund.setText(list.get(position).getRefund());
        mHolder.textView_total_amount.setText("+"+list.get(position).getTotal_amount());
        final String order_id = list.get(position).getOrder_id();
        SkinManager.getInstance().injectSkin(convertView);
        mHolder.linear_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("order_id",order_id);
                intent.setClass(mContext,OrderDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder{
        private TextView textView_created;
        private TextView textView_total_amount;
        private TextView textView_refund;
        private TextView textView_out_trade_no;
        private TextView textView_paytype;
        private TextView textView_payyon;
        private LinearLayout linear_details;
        public ViewHolder(View convertView){
            linear_details= (LinearLayout) convertView.findViewById(R.id.linear_details);
            textView_created= (TextView) convertView.findViewById(R.id.textView_created);
            textView_total_amount= (TextView) convertView.findViewById(R.id.textView_total_amount);
            textView_refund= (TextView) convertView.findViewById(R.id.textView_refund);
            textView_out_trade_no= (TextView) convertView.findViewById(R.id.textView_out_trade_no);
            textView_paytype= (TextView) convertView.findViewById(R.id.textView_paytype);
            textView_payyon= (TextView) convertView.findViewById(R.id.textView_payyon);
        }
    }
}
