package com.example.administrator.myhaicar.main.mine.Event.Acticity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.Account.This.Model.BankName;
import com.example.administrator.myhaicar.main.mine.Event.Model.ActiPayBean;
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

public class ApplyActivity extends SwipeBackToolBarActivity {
    private boolean flag1 = false;
    private boolean flag2 = false;
    private boolean flag3 = false;
    private Context mContext=this;
    private LinearLayout linear_balance;
    private LinearLayout linear_alipay;
    private LinearLayout linear_wechat;
    private RadioButton radioButton_balance;
    private RadioButton radioButton_alipay;
    private RadioButton radioButton_wechat;
    private String acti_id,acti_price,acti_date,acti_title;
    private Button button_pay;
    private String  usr_id;
    private long time;
    private String strRand = "";
    private String msg;
    private ProgressDialog dialog;
    private RelativeLayout activity_margin;
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
                ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext,"活动办理成功",button_pay);
                confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                Toast.makeText(ApplyActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        initView();
        initData();
        initClick();
    }


    private void initView() {
        EventBus.getDefault().register(this);
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        Intent intent = this.getIntent();
         acti_id = intent.getStringExtra("acti_id");
         acti_price = intent.getStringExtra("acti_price");
         acti_date = intent.getStringExtra("acti_date");
         acti_title = intent.getStringExtra("acti_title");
        activity_margin= (RelativeLayout) findViewById(R.id.activity_margin);
        linear_balance = (LinearLayout) findViewById(R.id.linear_balance);
        linear_alipay = (LinearLayout) findViewById(R.id.linear_alipay);
        linear_wechat = (LinearLayout) findViewById(R.id.linear_wechat);
        radioButton_balance = (RadioButton) findViewById(R.id.radioButton_balance);
        radioButton_alipay = (RadioButton) findViewById(R.id.radioButton_alipay);
        radioButton_wechat = (RadioButton) findViewById(R.id.radioButton_wechat);
        textView_data= (TextView) findViewById(R.id.textView_data);
        textView_time= (TextView) findViewById(R.id.textView_time);
        textView_marginMoney= (TextView) findViewById(R.id.textView_marginMoney);
        button_pay= (Button) findViewById(R.id.button_pay);
        radioButton_balance.setChecked(false);
        radioButton_alipay.setChecked(false);
        radioButton_wechat.setChecked(false);
        radioButton_balance.setButtonDrawable(android.R.color.transparent);
        radioButton_alipay.setButtonDrawable(android.R.color.transparent);
        radioButton_wechat.setButtonDrawable(android.R.color.transparent);
    }
    private void initData() {
        textView_data.setText(acti_date);
        textView_time.setText(acti_title);
        textView_marginMoney.setText("¥"+acti_price+"/人");
    }
    private void initClick() {
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
                dialog = ProgressDialog.show(mContext, "提示", "支付中...");
                button_pay.setClickable(false);
                if (flag1) {
                    String trade_this = getTradeNo_ID();
                    //测试钱的地方
                    OkHttpUtils.post().url(UrlConfig.Path.Magin_URL).addParams("usr_id",usr_id).addParams("doid",acti_id).addParams("out_trade_no", trade_this).
                            addParams("total_amount",acti_price).addParams("acts", "acti_up").build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext, "网络链接失败，稍后重试",button_pay);
                            confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
                        }

                        @Override
                        public void onResponse(String s) {
                            dialog.dismiss();
                            ActiPayBean payBean = parseJsonMessageBean(s);
                            String usr_bond = payBean.getUsr_bond();
                            String usr_bond_info = payBean.getUsr_bond_info();
                            if (usr_bond.equals("1")) {
                                ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext,usr_bond_info,button_pay);
                                confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
                                //Toast.makeText(mContext,usr_bond_info, Toast.LENGTH_SHORT).show();
                            } else {
                                ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext,usr_bond_info,button_pay);
                                confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
                            }
                        }
                    });

                } else if (flag2)
                {
                    final String trade_this = getTradeNo_ID();
                    OkHttpUtils.post().url(UrlConfig.Path.Cash_URL).addParams("usr_id", usr_id).addParams("order_id",acti_id).addParams("out_trade_no", trade_this)
                            .addParams("total_amount", acti_price).addParams("acts", "order").build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            dialog.dismiss();
                            ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext, "网络链接失败，稍后重试",button_pay);
                            confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
                        }

                        @Override
                        public void onResponse(String s) {
                            dialog.dismiss();
                            ActiPayBean payBean = parseJsonMessageBean(s);
                            String usr_bond = payBean.getUsr_bond();
                            if (usr_bond.equals("1")) {
                                AliPay(trade_this);
                            }else {
                                ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext, "网络链接失败，稍后重试",button_pay);
                                confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
                            }
                        }
                    });

                } else if (flag3)
                {
                    //微信支付
                    final String trade_this = getTradeNo_ID();
                    OkHttpUtils.post().url(UrlConfig.Path.Cash_URL).addParams("acts", "order").addParams("usr_id", usr_id)
                            .addParams("out_trade_no", trade_this).addParams("total_amount",acti_price).addParams("order_id", acti_id).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            dialog.dismiss();
                            ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext, "网络链接失败，稍后重试", button_pay);
                            confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
                        }
                        @Override
                        public void onResponse(String s) {
                            dialog.dismiss();
                            ActiPayBean payBean = parseJsonMessageBean(s);
                            String usr_bond = payBean.getUsr_bond();
                            if (usr_bond.equals("1")) {
                                //  Toast.makeText(mContext,usr_bond,Toast.LENGTH_LONG).show();
                                WeiXinOrder order = new WeiXinOrder(mContext, "1", "活动办理", trade_this);
                                order.WeiChatPay();
                                button_pay.setClickable(true);
                            } else {
                                ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext, "网络链接失败，稍后重试", button_pay);
                                confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
                            }
                        }
                    });
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
        //Toast.makeText(mContent,out_trade_no,Toast.LENGTH_SHORT).show();
        return out_trade_no;
    }
    private void AliPay(String tradeNo) {
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap("活动办理","0.01",tradeNo);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params);
        final String orderInfo = orderParam + "&" + sign;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(ApplyActivity.this);
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
    public ActiPayBean parseJsonMessageBean(String jsonString) {
        Gson gson = new Gson();
        ActiPayBean bean = gson.fromJson(jsonString, new TypeToken<ActiPayBean>() {
        }.getType());
        return bean;
    }
    @Subscribe
    public void onEventMainThread(BankName event) {
        msg =event.getBankName();
        if (msg.equals("88")){
            ConfirmMargenSuccfulPopWindow confirmPopWindow = new ConfirmMargenSuccfulPopWindow(mContext,"活动办理成功",button_pay);
            confirmPopWindow.showAtLocation(activity_margin, Gravity.CENTER, 0, 0);
        }
    }
}
