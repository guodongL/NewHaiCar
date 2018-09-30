package com.example.administrator.myhaicar.main.order.This.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.bigkoo.pickerview.TimePickerView;
import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.MyGridView;
import com.example.administrator.myhaicar.main.order.This.Adapater.HotCarAdapter;
import com.example.administrator.myhaicar.main.order.This.Model.CarGridViewBean;
import com.example.administrator.myhaicar.utils.HTTP_GD;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/18.
 */

public class ConfirmHotCarPopu extends PopupWindow{
    private Context mContext;
    private View mMenuView;
    private HotCarAdapter hotCarAdapter;
    private RelativeLayout relative_hotCar;
    private MyGridView gridView_hotCar;
    private Button button_complete;
    private ImageView image_dismiss;
    private Button button_startTime;
    private Button button_endTime;
    private String carsTypeID;
    private List<CarGridViewBean.CarsortBean> list=new ArrayList<>();
    private TimePickerView pvTime;
    public interface OnItemClickListener {
        public void onItemClick(String carsTypeID,String startTime,String endTime);
    }

    private OnItemClickListener mListener;

    //写一个设置接口监听的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ConfirmHotCarPopu(final Context mContext, OnClickListener itemsOnClick) {
        super(mContext);
        this.mContext = mContext;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.confirm_choose, null);
        initTimePicker();
        image_dismiss = (ImageView) mMenuView.findViewById(R.id.image_dismiss);
        relative_hotCar = (RelativeLayout) mMenuView.findViewById(R.id.relative_hotCar);
        gridView_hotCar = (MyGridView) mMenuView.findViewById(R.id.gridView_hotCar);
        button_complete = (Button) mMenuView.findViewById(R.id.button_complete);
        button_startTime = (Button) mMenuView.findViewById(R.id.button_startTime);
        button_endTime = (Button) mMenuView.findViewById(R.id.button_endTime);
        hotCarAdapter = new HotCarAdapter(mContext,list);
        gridView_hotCar.setAdapter(hotCarAdapter);
        HTTP_Car();
        hotCarAdapter.setmItemOnClickListener(new HotCarAdapter.ItemOnClickListener() {
            @Override
            public void itemOnClickListener(String carTypeID) {
                carsTypeID=carTypeID;
               // EventBus.getDefault().post(new BankName(carsTypeID));
            }
        });
        gridView_hotCar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        button_complete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String startTime = button_startTime.getText().toString();
                String endTime = button_endTime.getText().toString();
                mListener.onItemClick(carsTypeID, startTime,endTime);
            }
        });
        image_dismiss.setOnClickListener(itemsOnClick);
        button_startTime.setOnClickListener(itemsOnClick);
        button_endTime.setOnClickListener(itemsOnClick);
        button_startTime.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
           pvTime.show(button_startTime);
        }
    });
        button_endTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show(button_endTime);
            }
        });
        //设置按钮监听
      /*  radioButton_hot1.setButtonDrawable(android.R.color.transparent);
        radioButton_hot2.setButtonDrawable(android.R.color.transparent);
        radioButton_hot3.setButtonDrawable(android.R.color.transparent);
        radioButton_hot4.setButtonDrawable(android.R.color.transparent);*/
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
       // this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.setTouchable(true);
        this.setFocusable(false);
        //  this.update();
        //设置SelectPicPopupWindow弹出窗体动画效果
       this.setAnimationStyle(R.style.anim_wedpopuwindow_go);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable();
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(null);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
     /*   mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.relative_hotCar).getTop();
                int width=mMenuView.findViewById(R.id.relative_hotCar).getWidth();
                int y=(int) event.getY();
                int x = (int) event.getX();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    if (x>width){
                        dismiss();
                    }
                }
                return true;
            }
        });*/

    }
    private void HTTP_Car(){
        OkHttpUtils.post().url(UrlConfig.Path.CarGrid_URL).addParams("act","carttypelist").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String s) {
                if (HTTP_GD.IsOrNot_Null(s))
                {
                    return;
                }
                CarGridViewBean carGridViewBean = parseJsonToMessageBean(s);
                list=carGridViewBean.getCarsort();
                if (list==null)
                {
                    list=new ArrayList<>();
                }
                hotCarAdapter.reloadGridView(list,true);

            }
        });
    }
    private void initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2010, 0, 0);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2099, 11, 28);
        pvTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Button editText = (Button) v;
                editText.setText(getTime(date));
            }
        })

                .setTextColorCenter(Color.parseColor("#a67c33"))
                .setOutSideCancelable(true)
                .setTitleColor(Color.parseColor("#a67c33"))
                .setCancelColor(Color.parseColor("#a67c33"))
                .setSubmitColor(Color.parseColor("#a67c33"))
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLabel("", "", "", "", "", "")
                .build();

    }

    //可根据需要将时间自行截取数据显示
    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    public CarGridViewBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        CarGridViewBean bean = gson.fromJson(jsonString, new TypeToken<CarGridViewBean>() {
        }.getType());
        return bean;
    }

}
