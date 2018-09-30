package com.example.administrator.myhaicar.main.mine.Account.VIP.Acticity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
/*
* 开通VIP页面
* */
public class OpenVipActivity extends SwipeBackToolBarActivity {
    private Context mContext = this;
    //余额支付的点击按钮
    private RadioButton radioButton_balance;
    //支付宝支付的点击按钮
    private RadioButton radioButton_alipay;
    //微信支付的点击按钮
    private RadioButton radioButton_wechat;
    //余额支付的点击列表
    private LinearLayout linear_balance;
    //支付宝支付的点击列表
    private LinearLayout linear_alipay;
    //微信支付的点击列表
    private LinearLayout linear_wechat;
    private boolean flag1 = false;
    private boolean flag2 = false;
    private boolean flag3 = false;
    //立即支付按钮
    private Button button_pay;
    //返回按钮
    private ImageView toolBar_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_vip);
        initView();
        initClick();
    }
    private void initView() {
        linear_balance = (LinearLayout) findViewById(R.id.linear_balance);
        linear_alipay = (LinearLayout) findViewById(R.id.linear_alipay);
        linear_wechat = (LinearLayout) findViewById(R.id.linear_wechat);
        radioButton_balance = (RadioButton) findViewById(R.id.radioButton_balance);
        radioButton_alipay = (RadioButton) findViewById(R.id.radioButton_alipay);
        radioButton_wechat = (RadioButton) findViewById(R.id.radioButton_wechat);
        button_pay= (Button) findViewById(R.id.button_pay);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        radioButton_balance.setChecked(false);
        radioButton_alipay.setChecked(false);
        radioButton_wechat.setChecked(false);
        radioButton_balance.setButtonDrawable(android.R.color.transparent);
        radioButton_alipay.setButtonDrawable(android.R.color.transparent);
        radioButton_wechat.setButtonDrawable(android.R.color.transparent);
    }
    private void initClick() {
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        linear_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioButton_balance.setChecked(true);
                if (radioButton_balance.isChecked()) {
                    radioButton_alipay.setChecked(false);
                    radioButton_wechat.setChecked(false);
                    flag1 = true;
                    flag2 = false;
                    flag3 = false;
                }
            }
        });
        linear_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioButton_alipay.setChecked(true);
                if (radioButton_alipay.isChecked()) {
                    radioButton_balance.setChecked(false);
                    radioButton_wechat.setChecked(false);
                    flag2 = true;
                    flag1 = false;
                    flag3 = false;

                }
            }
        });
        linear_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioButton_wechat.setChecked(true);
                if (radioButton_wechat.isChecked()) {
                    radioButton_alipay.setChecked(false);
                    radioButton_balance.setChecked(false);
                    flag3 = true;
                    flag2 = false;
                    flag1 = false;

                }
            }
        });
        button_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //button_pay.setClickable(false);
                Intent intent=new Intent();
                intent.setClass(mContext,OpenVipSucceedActivity.class);
                if (flag1)
                {
                    Toast.makeText(mContext,"余额支付",Toast.LENGTH_SHORT).show();
                }
                else if (flag2)
                {
                    Toast.makeText(mContext,"支付宝支付",Toast.LENGTH_SHORT).show();
                }
                else if (flag3)
                {
                    startActivity(intent);
                    Toast.makeText(mContext,"微信支付",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(mContext,"请选择支付方式",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
