package com.example.administrator.myhaicar.main.mine.Account.This.Acticity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.login.This.Model.LoginBean;
import com.example.administrator.myhaicar.main.mine.Account.MineDetail.MineDetailActivity;
import com.example.administrator.myhaicar.main.mine.Account.OrderCash.OrderCashActivity;
import com.example.administrator.myhaicar.main.mine.Account.RedBag.RedBagActivity;
import com.example.administrator.myhaicar.commond.ConfirmPopWindowThree;
import com.example.administrator.myhaicar.main.mine.Account.Transaction.TransactionActivity;
import com.example.administrator.myhaicar.utils.HTTP_GD;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/*
* 我的账单页面
* */
public class AccountActivity extends SwipeBackToolBarActivity {
    //图片点击返回上一页面
    private ImageView toolBar_back;
    //开通VIP列表，点击跳转到开通VIP页面
    private LinearLayout linear_openVIP;
    private Context mContext=this;
    private LinearLayout linear_mineDetail;
    private PullToRefreshScrollView account_scrollView;
    private LinearLayout linear_orderCash;
    private LinearLayout linear_transaction;
    private LinearLayout linear_redBag;
    private Button button_withDraw;
    private TextView textView_nameID;
    private TextView textView_money;
    private SimpleDraweeView image_myHead;
    private RelativeLayout activity_account;
    private String usr_id;
    private Handler han=new Handler();
    private String usr_bond_c;
    private TextView textView_orderMoney;
    private  String usr_headimgurl,usr_name,usr_money,usr_mime_id;
    private  int usr_vip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView();
        initClick();
        autoRefresh();

    }

    @Override
    protected void onResume() {
        super.onResume();
        HTTP_Account();
    }

    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                account_scrollView.setRefreshing(true);
            }
        },100);
    }


    private void initView() {
        String usr_money = PreferencesUtils.getString(mContext, "usr_money");
        String usr_name = PreferencesUtils.getString(mContext, "usr_name");
        String usr_mime_id = PreferencesUtils.getString(mContext, "usr_mime_id");
        String usr_bond_c = PreferencesUtils.getString(mContext, "usr_bond_c");
        String usr_headimgurl = PreferencesUtils.getString(mContext, "usr_headimgurl");
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        textView_orderMoney= (TextView) findViewById(R.id.textView_orderMoney);
        textView_nameID= (TextView) findViewById(R.id.textView_nameID);
        textView_money= (TextView) findViewById(R.id.textView_money);
        image_myHead= (SimpleDraweeView) findViewById(R.id.image_myHead);
        account_scrollView= (PullToRefreshScrollView) findViewById(R.id.account_scrollView);
        activity_account= (RelativeLayout) findViewById(R.id.activity_account);
        button_withDraw= (Button) findViewById(R.id.button_withDraw);
        linear_redBag= (LinearLayout) findViewById(R.id.linear_redBag);
        linear_transaction= (LinearLayout) findViewById(R.id.linear_transaction);
        linear_orderCash= (LinearLayout) findViewById(R.id.linear_orderCash);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        linear_openVIP= (LinearLayout) findViewById(R.id.linear_openVIP);
        linear_mineDetail= (LinearLayout) findViewById(R.id.linear_mineDetail);
        image_myHead.setImageURI(Uri.parse(usr_headimgurl));
        if (usr_money==null||usr_money.equals("")){
            textView_money.setText("0.00");
        }else {
            textView_money.setText(usr_money);
        }
        if (usr_name==null||usr_name.equals("")){
            textView_nameID.setText("嗨车"+"  "+usr_mime_id);
        }else {
            textView_nameID.setText(usr_name + "  " + usr_mime_id);
        }
        if (usr_bond_c==null||usr_bond_c.equals("")){
            textView_orderMoney.setText("0"+"元");
        }else {
            textView_orderMoney.setText(usr_bond_c+"元");
        }
    }
    private void initClick() {
        account_scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                HTTP_Account();
                account_scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        account_scrollView.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });
        button_withDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,WithDrawActivity.class);
                startActivity(intent);
            }
        });
        linear_redBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, RedBagActivity.class);
                startActivity(intent);
            }
        });
        linear_orderCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, OrderCashActivity.class);
                startActivity(intent);
            }
        });
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        linear_openVIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext,"开通会员敬请期待");
                confirmPopWindowtwo.showAtLocation(activity_account, Gravity.CENTER, 0, 0);
            }
        });
        linear_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, TransactionActivity.class);
                startActivity(intent);
            }
        });
        linear_mineDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, MineDetailActivity.class);
                startActivity(intent);
            }
        });
    }
    public  void  HTTP_Account (){
        OkHttpUtils.post().url(UrlConfig.Path.Phone_URL).addParams("act","re_load").addParams("usr_id",usr_id).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String s) {
                if (HTTP_GD.IsOrNot_Null(s)) {
                    return;
                }
                LoginBean loginBean = parseJsonToMessageBean(s);
                usr_headimgurl = loginBean.getUsr_headimgurl();
                usr_name = loginBean.getUsr_name();
                usr_money = loginBean.getUsr_money();
                usr_mime_id = loginBean.getUsr_mime_id();
                usr_vip = loginBean.getUsr_vip();
                usr_bond_c = loginBean.getUsr_bond_c();
                //保证金额
                PreferencesUtils.putString(mContext,"usr_bond_c",usr_bond_c);
                PreferencesUtils.putString(mContext,"usr_name",usr_name);
                //头像
                PreferencesUtils.putString(mContext,"usr_headimgurl",usr_headimgurl);
                //用户嗨车ID
                PreferencesUtils.putString(mContext,"usr_mime_id",usr_mime_id);
                //用户账户上的余额
                PreferencesUtils.putString(mContext,"usr_money",usr_money);
                PreferencesUtils.putString(mContext,"usr_vip",loginBean.getUsr_vip()+"");
                PreferencesUtils.putString(mContext,"usr_vip",usr_vip+"");
                image_myHead.setImageURI(Uri.parse(usr_headimgurl));
                if (usr_bond_c==null||usr_bond_c.equals("")){
                    textView_orderMoney.setText("0"+"元");
                }else {
                    textView_orderMoney.setText(usr_bond_c+"元");
                }
                if (usr_name==null||usr_name.equals("")){
                    textView_nameID.setText("嗨车"+"  "+usr_mime_id);
                }else {
                    textView_nameID.setText(usr_name + "  " + usr_mime_id);
                }
                if (usr_money==null||usr_money.equals("")){
                    textView_money.setText("0.00");
                }else {
                    textView_money.setText(usr_money);
                }
                account_scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        account_scrollView.onRefreshComplete();
                    }
                }, 1500);

            }
        });

    }
    public LoginBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        LoginBean bean = gson.fromJson(jsonString, new TypeToken<LoginBean>() {
        }.getType());

        return bean;
    }

}
