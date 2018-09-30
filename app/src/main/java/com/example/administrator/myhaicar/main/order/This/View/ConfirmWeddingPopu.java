package com.example.administrator.myhaicar.main.order.This.View;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import com.example.administrator.myhaicar.R;

/**
 * Created by Administrator on 2017/3/18.
 */

public class ConfirmWeddingPopu extends PopupWindow{
    private Context mContext;
    private LinearLayout linearLayout_all, linearLayout_data, linearLayout_high,linearLayout_bottom;
    private View mMenuView;
    private RelativeLayout relative_wedding;
    private RadioButton radioButton_hot1,radioButton_hot2,radioButton_hot3,radioButton_hot4;
    private ImageView image_chooseprice,image_choosetime,image_regain,image_forme,image_choose;
    private LinearLayout linearLayout_hotFragment;

    public ConfirmWeddingPopu(Context mContext, OnClickListener itemsOnClick) {
        super(mContext);
        this.mContext=mContext;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.confirm_dialog, null);
       relative_wedding= (RelativeLayout) mMenuView.findViewById(R.id.relative_wedding);
        image_chooseprice= (ImageView) mMenuView.findViewById(R.id.image_chooseprice);
        image_choosetime= (ImageView) mMenuView.findViewById(R.id.image_choosetime);
        image_regain= (ImageView) mMenuView.findViewById(R.id.image_regain);
        image_forme= (ImageView) mMenuView.findViewById(R.id.image_forme);
        image_choose= (ImageView) mMenuView.findViewById(R.id.image_choose);
        //设置按钮监听
      /*  radioButton_hot1.setButtonDrawable(android.R.color.transparent);
        radioButton_hot2.setButtonDrawable(android.R.color.transparent);
        radioButton_hot3.setButtonDrawable(android.R.color.transparent);
        radioButton_hot4.setButtonDrawable(android.R.color.transparent);*/
        image_chooseprice.setOnClickListener(itemsOnClick);
        image_choosetime.setOnClickListener(itemsOnClick);
        image_regain.setOnClickListener(itemsOnClick);
        image_forme.setOnClickListener(itemsOnClick);
        image_choose.setOnClickListener(itemsOnClick);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.anim_wedpopuwindow_go);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable();
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.relative_wedding).getTop();
                int width=mMenuView.findViewById(R.id.relative_wedding).getWidth();
                int y=(int) event.getY();
                int x = (int) event.getX();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    if (x<width){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

}
