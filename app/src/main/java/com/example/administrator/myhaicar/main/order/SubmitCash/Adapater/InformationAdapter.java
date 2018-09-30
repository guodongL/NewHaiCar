package com.example.administrator.myhaicar.main.order.SubmitCash.Adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.order.SubmitCash.Model.InfoBean;
import com.zhy.changeskin.SkinManager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class InformationAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<InfoBean.NewslistinfoBean> list;
    public InformationAdapter(Context mContext,List<InfoBean.NewslistinfoBean> list){
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder=null;
        if (convertView==null) {
            convertView=mInflater.inflate(R.layout.information_listview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        mHolder.textView_news_created.setText(list.get(position).getNews_created());
        mHolder.textView_news_title.setText(list.get(position).getNews_title());
        String usr_isorno = list.get(position).getUsr_isorno();
        if (usr_isorno.equals("0")){
            mHolder.image_info.setImageResource(R.drawable.information);
        }else {
            mHolder.image_info.setImageResource(R.drawable.headview);
        }
   /*     String news_url = list.get(position).getNews_url();
        mHolder.linear_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, InforDetailActivity.class);
                mContext.startActivity(intent);
            }
        });*/
        SkinManager.getInstance().injectSkin(convertView);
        return convertView;
    }
    public void reloadGridView(List<InfoBean.NewslistinfoBean> _list, boolean isClear) {
        if (isClear) {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }
    class ViewHolder{
        private ImageView image_info;
        private TextView textView_news_created;
        private TextView textView_news_title;
        private LinearLayout linear_infor;
        public ViewHolder(View convertView){
            image_info= (ImageView) convertView.findViewById(R.id.image_info);
            linear_infor= (LinearLayout) convertView.findViewById(R.id.linear_infor);
            textView_news_title= (TextView) convertView.findViewById(R.id.textView_news_title);
            textView_news_created= (TextView) convertView.findViewById(R.id.textView_news_created);
        }
    }

}
