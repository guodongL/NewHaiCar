package com.example.administrator.myhaicar.main.order.SubmitCash.Acticity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.commond.ConfirmPopWindowThree;
import com.example.administrator.myhaicar.main.mine.Account.This.Model.BankName;
import com.example.administrator.myhaicar.main.order.This.Model.OpenVipsBean;
import com.example.administrator.myhaicar.main.order.This.View.ConfirmMargenSuccfulPopWindow;
import com.example.administrator.myhaicar.main.order.alipay.OrderInfoUtil2_0;
import com.example.administrator.myhaicar.main.order.alipay.PayResult;
import com.example.administrator.myhaicar.main.order.weipay.WeiXinOrder;
import com.example.administrator.myhaicar.tools.DateHelper;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

import okhttp3.Call;

public class MarginActivity extends SwipeBackToolBarActivity {
    private Context mContext = this;
    private RadioButton radioButton_balance;
    private RadioButton radioButton_alipay;
    private RadioButton radioButton_wechat;
    private LinearLayout linear_balance;
    private LinearLayout linear_alipay;
    private LinearLayout linear_wechat;
    private boolean flag1 = false;
    private boolean flag2 = false;
    private boolean flag3 = false;
    private TextView textView_openVip;
    private Button button_pay;
    private RelativeLayout activity_margin;
    private ImageView toolBar_back;
    private String usr_money;
    private String order_price;
    private String order_id;
    private String order_data;
    private String order_time;
    private String order_cartype;
    private ProgressDialog dialog;
    private long time;
    private String strRand = "";
    private String msg;
    private String  usr_id;
    private TextView textView_data,textView_time,textView_marginMoney;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                Toast.makeText(MarginActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext,"保证金办理成功",button_pay);
                confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);

            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                Toast.makeText(MarginActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_margin);
        initView();
        initData();
        initClick();
    }

    private void initData() {
        textView_data.setText(order_data);
        textView_time.setText(order_time+"   "+order_cartype);
        textView_marginMoney.setText("¥"+order_price+"/人");
    }

    private void initView() {
        EventBus.getDefault().register(this);
        usr_money = PreferencesUtils.getString(mContext, "usr_money");
        Intent intent = this.getIntent();
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        order_id = intent.getStringExtra("order_id");
        order_data = intent.getStringExtra("order_data");
        order_time = intent.getStringExtra("order_time");
        order_cartype = intent.getStringExtra("order_cartype");
        order_price = intent.getStringExtra("order_price");
        textView_data= (TextView) findViewById(R.id.textView_data);
        textView_time= (TextView) findViewById(R.id.textView_time);
        textView_marginMoney= (TextView) findViewById(R.id.textView_marginMoney);
        activity_margin= (RelativeLayout) findViewById(R.id.activity_margin);
        linear_balance = (LinearLayout) findViewById(R.id.linear_balance);
        linear_alipay = (LinearLayout) findViewById(R.id.linear_alipay);
        linear_wechat = (LinearLayout) findViewById(R.id.linear_wechat);
        radioButton_balance = (RadioButton) findViewById(R.id.radioButton_balance);
        radioButton_alipay = (RadioButton) findViewById(R.id.radioButton_alipay);
        radioButton_wechat = (RadioButton) findViewById(R.id.radioButton_wechat);
        button_pay= (Button) findViewById(R.id.button_pay);
        textView_openVip= (TextView) findViewById(R.id.textView_openVip);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        radioButton_balance.setChecked(false);
        radioButton_alipay.setChecked(false);
        radioButton_wechat.setChecked(false);
        radioButton_balance.setButtonDrawable(android.R.color.transparent);
        radioButton_alipay.setButtonDrawable(android.R.color.transparent);
        radioButton_wechat.setButtonDrawable(android.R.color.transparent);
    }
    private void initClick() {
        textView_openVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext,"开通会员敬请期待");
                confirmPopWindowtwo.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
            }
        });
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
                button_pay.setClickable(false);
                dialog = ProgressDialog.show(mContext, "提示", "支付中...");
               /* Intent intent=new Intent();
                intent.setClass(mContext,OpenVipSucceedActivity.class);*/
                if (flag1) {
                    String trade_this = getTradeNo_ID();
                    String usr_money = PreferencesUtils.getString(mContext, "usr_money");
               /*     if (  Double.valueOf(usr_money)<Double.valueOf(order_price))
                    {
                        ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext,"您的余额不足",button_pay);
                        confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
                        return;
                    }*/
                    //测试钱的地方
                    OkHttpUtils.post().url(UrlConfig.Path.Magin_URL).addParams("usr_id", usr_id).addParams("doid", order_id).addParams("out_trade_no", trade_this).
                            addParams("total_amount", order_price).addParams("acts", "bond_up").build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext, "网络链接失败，稍后重试", button_pay);
                            confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
                        }

                        @Override
                        public void onResponse(String s) {
                            dialog.dismiss();
                            OpenVipsBean openVipsBean = parseJsonMessageBean(s);
                            String usr_bond = openVipsBean.getUsr_bond();
                            String usr_bond_info = openVipsBean.getUsr_bond_info();
                            if (usr_bond.equals("1")) {
                                ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext, usr_bond_info, button_pay);
                                confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
                                EventBus.getDefault().post(new BankName("5"));
                                //Toast.makeText(mContext,usr_bond_info, Toast.LENGTH_SHORT).show();
                            } else {
                                ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext, usr_bond_info, button_pay);
                                confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
                            }
                        }
                    });
                } else if (flag2) {
                    final String trade_this = getTradeNo_ID();
                    //Toast.makeText(mContext, trade_this, Toast.LENGTH_SHORT).show();
                    OkHttpUtils.post().url(UrlConfig.Path.Cash_URL).addParams("usr_id", usr_id).addParams("order_id", order_id).addParams("out_trade_no", trade_this)
                            .addParams("total_amount", order_price).addParams("acts", "order").build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            dialog.dismiss();
                            ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext, "网络链接失败，稍后重试", button_pay);
                            confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
                        }

                        @Override
                        public void onResponse(String s) {
                            dialog.dismiss();
                            OpenVipsBean openVipsBean = parseJsonMessageBean(s);
                            String usr_bond = openVipsBean.getUsr_bond();
                            if (usr_bond.equals("1")) {
                                AliPay(trade_this);
                                button_pay.setClickable(true);
                            } else {
                                ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext, "网络链接失败，稍后重试", button_pay);
                                confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
                            }
                        }
                    });
                } else if (flag3) {
                    //微信支付
                    final String trade_this = getTradeNo_ID();
                    OkHttpUtils.post().url(UrlConfig.Path.Cash_URL).addParams("acts", "order").addParams("usr_id", usr_id)
                            .addParams("out_trade_no", trade_this).addParams("total_amount", order_price).addParams("order_id", order_id).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            dialog.dismiss();
                            ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext, "网络链接失败，稍后重试", button_pay);
                            confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
                        }
                        @Override
                        public void onResponse(String s) {
                            dialog.dismiss();
                            OpenVipsBean openVipsBean = parseJsonMessageBean(s);
                            String usr_bond = openVipsBean.getUsr_bond();
                            if (usr_bond.equals("1")) {
                              //  Toast.makeText(mContext,usr_bond,Toast.LENGTH_LONG).show();
                                WeiXinOrder order = new WeiXinOrder(mContext, "1", "出车保证金", trade_this);
                                order.WeiChatPay();
                                button_pay.setClickable(true);
                            } else {
                                ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext, "网络链接失败，稍后重试", button_pay);
                                confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
                            }
                        }
                    });
                }
                else
                {   dialog.dismiss();
                    button_pay.setClickable(true);
                    Toast.makeText(mContext,"请选择支付方式",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private String getTradeNo_ID() {
        strRand="";
        for (int i = 0; i < 11; i++) {
            strRand += String.valueOf((int) (Math.random() * 10));
        }
        time = System.currentTimeMillis();
        String time1 = DateHelper.getFormatDate(time + "", "yyyyMMddhhmmssSSS");
        String out_trade_no = time1 + strRand;
        return out_trade_no;
    }
    private void AliPay(String tradeNo) {
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap("出车保证金","0.01",tradeNo);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params);
        final String orderInfo = orderParam + "&" + sign;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(MarginActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public OpenVipsBean parseJsonMessageBean(String jsonString) {
        Gson gson = new Gson();
        OpenVipsBean bean = gson.fromJson(jsonString, new TypeToken<OpenVipsBean>() {
        }.getType());
        return bean;
    }
    @Subscribe
    public void onEventMainThread(BankName event) {
        msg =event.getBankName();
        if (msg.equals("88")){
                        ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext,"保证金办理成功",button_pay);
                        confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
        }
    }


}
