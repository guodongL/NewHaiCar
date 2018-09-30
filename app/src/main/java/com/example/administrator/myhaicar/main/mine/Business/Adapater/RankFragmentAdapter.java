package com.example.administrator.myhaicar.main.mine.Business.Adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.mine.Business.Model.BusinessBean;
import com.example.administrator.myhaicar.utils.LeanTextView;
import com.zhy.changeskin.SkinManager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 */

public class RankFragmentAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<BusinessBean> list;
    public RankFragmentAdapter(Context mContext,List<BusinessBean> list){
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
    public void reloadGridView(List<BusinessBean> _list, boolean isClear) {
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
            convertView=mInflater.inflate(R.layout.rankfragment_listview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        mHolder.leanText.setmDegrees(45);
        String discount = list.get(position).getDiscount();
        String result = discount.substring(2,3);
        if (discount.equals("0.0")){
            mHolder.leanText.setText("特惠");
        }else {
            if (result.equals("0")){
                String substring = discount.substring(0, 1);
                mHolder.leanText.setText(substring+"折");
            }else {
                mHolder.leanText.setText(discount+"折");
            }
        }
        mHolder.textView_adress.setText(list.get(position).getAddress());
        mHolder.textView_bussinesName.setText(list.get(position).getTitle());
        String hot = list.get(position).getHot();
        String video = list.get(position).getVideo();
        String auth = list.get(position).getAuth();
        if (hot.equals("1")){
            mHolder.image_hot.setVisibility(View.VISIBLE);
        }else {
            mHolder.image_hot.setVisibility(View.GONE);
        }
        if (video.equals("1")){
            mHolder.image_video.setVisibility(View.VISIBLE);
        }else {
            mHolder.image_video.setVisibility(View.GONE);
        }
        if (auth.equals("1")){
            mHolder.image_yes.setVisibility(View.VISIBLE);
        }else {
            mHolder.image_yes.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(list.get(position).getThumbnail()).into(mHolder.image_bussPhoto);
        String star = list.get(position).getStar();
        if (star.equals("4")){
            mHolder.image_bussinesStar5.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar4.setImageResource(R.drawable.stoplist_3);
            mHolder.image_bussinesStar3.setImageResource(R.drawable.stoplist_3);
            mHolder.image_bussinesStar2.setImageResource(R.drawable.stoplist_3);
            mHolder.image_bussinesStar1.setImageResource(R.drawable.stoplist_3);
        }else if (star.equals("4.5")){
            mHolder.image_bussinesStar5.setImageResource(R.drawable.stoplist_4);
            mHolder.image_bussinesStar4.setImageResource(R.drawable.stoplist_3);
            mHolder.image_bussinesStar3.setImageResource(R.drawable.stoplist_3);
            mHolder.image_bussinesStar2.setImageResource(R.drawable.stoplist_3);
            mHolder.image_bussinesStar1.setImageResource(R.drawable.stoplist_3);
        }else if (star.equals("3")){
            mHolder.image_bussinesStar5.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar4.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar3.setImageResource(R.drawable.stoplist_3);
            mHolder.image_bussinesStar2.setImageResource(R.drawable.stoplist_3);
            mHolder.image_bussinesStar1.setImageResource(R.drawable.stoplist_3);
        }else if (star.equals("3.5")){
            mHolder.image_bussinesStar5.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar4.setImageResource(R.drawable.stoplist_4);
            mHolder.image_bussinesStar3.setImageResource(R.drawable.stoplist_3);
            mHolder.image_bussinesStar2.setImageResource(R.drawable.stoplist_3);
            mHolder.image_bussinesStar1.setImageResource(R.drawable.stoplist_3);
        }else if (star.equals("2")){
            mHolder.image_bussinesStar5.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar4.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar3.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar2.setImageResource(R.drawable.stoplist_3);
            mHolder.image_bussinesStar1.setImageResource(R.drawable.stoplist_3);
        }else if (star.equals("2.5")){
            mHolder.image_bussinesStar5.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar4.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar3.setImageResource(R.drawable.stoplist_4);
            mHolder.image_bussinesStar2.setImageResource(R.drawable.stoplist_3);
            mHolder.image_bussinesStar1.setImageResource(R.drawable.stoplist_3);
        }else if (star.equals("1")){
            mHolder.image_bussinesStar5.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar4.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar3.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar2.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar1.setImageResource(R.drawable.stoplist_3);
        }else if (star.equals("1.5")){
            mHolder.image_bussinesStar5.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar4.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar3.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar2.setImageResource(R.drawable.stoplist_4);
            mHolder.image_bussinesStar1.setImageResource(R.drawable.stoplist_3);
        }else if (star.equals("0.5")){
            mHolder.image_bussinesStar5.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar4.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar3.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar2.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar1.setImageResource(R.drawable.stoplist_4);
        }else if (star.equals("0")){
            mHolder.image_bussinesStar5.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar4.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar3.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar2.setImageResource(R.drawable.stoplist_5);
            mHolder.image_bussinesStar1.setImageResource(R.drawable.stoplist_5);
        }else {
            mHolder.image_bussinesStar5.setImageResource(R.drawable.stoplist_3);
            mHolder.image_bussinesStar4.setImageResource(R.drawable.stoplist_3);
            mHolder.image_bussinesStar3.setImageResource(R.drawable.stoplist_3);
            mHolder.image_bussinesStar2.setImageResource(R.drawable.stoplist_3);
            mHolder.image_bussinesStar1.setImageResource(R.drawable.stoplist_3);
        }
        SkinManager.getInstance().injectSkin(convertView);
        return convertView;
    }
    class ViewHolder{
        private TextView textView_bussinesName;
        private TextView textView_adress;
        private ImageView image_hot;
        private ImageView image_bussPhoto;
        private ImageView image_yes;
        private ImageView image_video;
        private ImageView image_bussinesStar1,image_bussinesStar2,image_bussinesStar3,image_bussinesStar4,image_bussinesStar5;
        private LeanTextView leanText;
        public ViewHolder(View convertView){
            image_bussPhoto= (ImageView) convertView.findViewById(R.id.image_bussPhoto);
            leanText= (LeanTextView) convertView.findViewById(R.id.leanText);
            textView_adress= (TextView) convertView.findViewById(R.id.textView_adress);
            textView_bussinesName= (TextView) convertView.findViewById(R.id.textView_bussinesName);
            image_hot= (ImageView) convertView.findViewById(R.id.image_hot);
            image_yes= (ImageView) convertView.findViewById(R.id.image_yes);
            image_video= (ImageView) convertView.findViewById(R.id.image_video);
            image_bussinesStar1= (ImageView) convertView.findViewById(R.id.image_bussinesStar1);
            image_bussinesStar2= (ImageView) convertView.findViewById(R.id.image_bussinesStar2);
            image_bussinesStar3= (ImageView) convertView.findViewById(R.id.image_bussinesStar3);
            image_bussinesStar4= (ImageView) convertView.findViewById(R.id.image_bussinesStar4);
            image_bussinesStar5= (ImageView) convertView.findViewById(R.id.image_bussinesStar5);
        }
    }
}
