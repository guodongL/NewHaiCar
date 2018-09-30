package com.example.administrator.myhaicar.main.mine.Account.This.Adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.mine.Account.This.Model.UserBankBean;
import com.zhy.changeskin.SkinManager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class BankAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<UserBankBean> list;
    public BankAdapter(Context mContext, List<UserBankBean> list){
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
    public void reloadGridView(List<UserBankBean> _list, boolean isClear) {
        if (isClear) {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder mHolder=null;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.withdraw_listview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        mHolder.withDraw_name.setText(list.get(position).getName());
        mHolder.withDraw_bank.setText(list.get(position).getBank());
        mHolder.withDraw_bankNumber.setText(list.get(position).getBankNumber());
        SkinManager.getInstance().injectSkin(convertView);
        mHolder.radio_button.setButtonDrawable(android.R.color.transparent);
        final ViewHolder finalMHolder = mHolder;
        mHolder.relative_withDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String withDraw_bank = finalMHolder.withDraw_bank.getText().toString();
                String withDraw_name = finalMHolder.withDraw_name.getText().toString();
                String withDraw_bankNumber = finalMHolder.withDraw_bankNumber.getText().toString();
                mItemOnClickListener.itemOnClickListener(finalMHolder.relative_withDraw,withDraw_bank,withDraw_name,withDraw_bankNumber);
            }
        });
        return convertView;
    }
    //点击选择银行卡与用户信息
    private ItemOnClickListener mItemOnClickListener;

    public void setmItemOnClickListener(ItemOnClickListener listener){
        this.mItemOnClickListener = listener;
    }
    public interface ItemOnClickListener{
        public void itemOnClickListener(RelativeLayout selectView,String withDraw_bank,String withDraw_name,String withDraw_bankNumber);
    }
    class ViewHolder{
        private RadioButton radio_button;
        private RelativeLayout relative_withDraw;
        private TextView withDraw_name;
        private TextView withDraw_bank;
        private TextView withDraw_bankNumber;
        public ViewHolder(View convertView){
            relative_withDraw= (RelativeLayout) convertView.findViewById(R.id.relative_withDraw);
            withDraw_name= (TextView) convertView.findViewById(R.id.withDraw_name);
            withDraw_bank= (TextView) convertView.findViewById(R.id.withDraw_bank);
            withDraw_bankNumber= (TextView) convertView.findViewById(R.id.withDraw_bankNumber);
            radio_button= (RadioButton) convertView.findViewById(R.id.radio_button);
        }
    }
}
