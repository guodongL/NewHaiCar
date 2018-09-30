package com.example.administrator.myhaicar.main.mine.MyCar.Adapater;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.mine.Account.This.View.ConfirmPopWindow;
import com.example.administrator.myhaicar.main.mine.MyCar.Acticity.CarNameActivity;
import com.example.administrator.myhaicar.main.mine.MyCar.Model.CarManagerBean;
import com.example.administrator.myhaicar.main.mine.MyCar.Model.DelateOrAmendBean;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.changeskin.SkinManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/16.
 */

public class CarManagerAdapter extends BaseAdapter {
    private List<CarManagerBean.CarlistinfoBean> list;
    private Context mContext;
    private LayoutInflater mInflater;
    public CarManagerAdapter(Context mContext,List<CarManagerBean.CarlistinfoBean> list){
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
    public void reloadGridView(List<CarManagerBean.CarlistinfoBean>  _list, boolean isClear) {
        if (isClear) {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String usr_id = PreferencesUtils.getString(mContext, "usr_id");
        ViewHolder mHolder=null;
        if (convertView==null) {
            convertView=mInflater.inflate(R.layout.car_swiplistview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        mHolder.textView_car_name.setText(list.get(position).getCar_name()+list.get(position).getCar_color());
        mHolder.textView_car_sate.setText(list.get(position).getCar_sate());
        mHolder.textView_car_info.setText(list.get(position).getCar_sate_info());
        final String car_id = list.get(position).getCar_id();
        Glide.with(mContext).load(list.get(position).getCar_logo()).into(mHolder.image_MycarLogo);
        Glide.with(mContext).load(list.get(position).getCar_driv()).into(mHolder.imageView_driv);
        Glide.with(mContext).load(list.get(position).getCar_peop()).into(mHolder.imageView_peop);
        final String car_srt_id = list.get(position).getCar_srt_id();
        final String car_color = list.get(position).getCar_color();
        final ViewHolder finalMHolder = mHolder;
        mHolder.image_amend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.post().url(UrlConfig.Path.CarManager_URL).addParams("acts","dupture").addParams("usr_id",usr_id).addParams("car_srt_id",car_srt_id)
                        .addParams("car_color",car_color).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String s) {
                        DelateOrAmendBean delateOrAmendBean = parseJsonToMessageBean(s);
                        String treatedok = delateOrAmendBean.getTreatedok();
                        String treatedok_info = delateOrAmendBean.getTreatedok_info();
                        if (treatedok.equals("1")){
                            Intent intent=new Intent();
                            intent.putExtra("car_id",car_id);
                            intent.setClass(mContext, CarNameActivity.class);
                            mContext.startActivity(intent);
                        }else {
                            ConfirmPopWindow confirmPopWindow = new ConfirmPopWindow(mContext, "提示", treatedok_info);
                            confirmPopWindow.showAtLocation(finalMHolder.relative, Gravity.CENTER, 0, 0);
                        }
                    }
                });

            }
        });
        SkinManager.getInstance().injectSkin(convertView);
        return convertView;
    }
    class ViewHolder{
        private TextView textView_car_name;
        private TextView textView_car_sate;
        private TextView textView_car_info;
        private ImageView imageView_driv;
        private ImageView imageView_peop;
        private ImageView image_amend;
        private RelativeLayout relative;
        private ImageView image_MycarLogo;
        public ViewHolder(View convertView){
            relative= (RelativeLayout) convertView.findViewById(R.id.relative);
            image_MycarLogo= (ImageView) convertView.findViewById(R.id.image_MycarLogo);
            image_amend= (ImageView) convertView.findViewById(R.id.image_amend);
            textView_car_name= (TextView) convertView.findViewById(R.id.textView_car_name);
            textView_car_sate= (TextView) convertView.findViewById(R.id.textView_car_sate);
            textView_car_info= (TextView) convertView.findViewById(R.id.textView_car_info);
            imageView_driv= (ImageView) convertView.findViewById(R.id.imageView_driv);
            imageView_peop= (ImageView) convertView.findViewById(R.id.imageView_peop);
        }
    }
    public DelateOrAmendBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        DelateOrAmendBean bean = gson.fromJson(jsonString, new TypeToken<DelateOrAmendBean>() {
        }.getType());
        return bean;
    }
}
