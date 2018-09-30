package com.example.administrator.myhaicar.main.order.This.View;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;

/**
 * Created by Administrator on 2017/6/7.
 */

public class OrderPopuwindow extends PopupWindow{
    private Context mContext;
    private OnDialogClickListener dialogClickListener;
    private ImageView image_suful;
    private TextView titleTv;
    private String title;
    public OrderPopuwindow( Context mContext,String title){
        super(mContext);
        this.title=title;
        this.mContext=mContext;
        initalize();
    }
    private void initalize() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.order_popuwindow, null);
        setContentView(view);
        image_suful = (ImageView) view.findViewById(R.id.image_suful);
        titleTv = (TextView) view.findViewById(R.id.title_name);
        titleTv.setText(title);
        image_suful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(dialogClickListener != null){
                    dialogClickListener.onOKClick();
                }
            }
        });
        initWindow();
    }
    private void initWindow() {
        ColorDrawable dw = new ColorDrawable(0);
        this.setBackgroundDrawable(dw);
        DisplayMetrics d = mContext.getResources().getDisplayMetrics();
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.update();
    }

    public void showAtBottom(View view){
        showAsDropDown(view, Math.abs((view.getWidth() - getWidth())/2), 20);
    }

    public void setOnDialogClickListener(OnDialogClickListener clickListener){
        dialogClickListener = clickListener;
    }

    public interface OnDialogClickListener{
        void onOKClick();
    }

}
