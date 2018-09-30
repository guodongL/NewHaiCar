package com.example.administrator.myhaicar.main.mine.Account.This.Acticity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.Account.This.Model.DrawMoneySuccfulBean;
import com.example.administrator.myhaicar.main.mine.Account.This.Model.UserBankBean;
import com.example.administrator.myhaicar.main.mine.Account.This.Model.WithDrawBean;
import com.example.administrator.myhaicar.main.mine.Account.This.View.ConfirmPopWindow;
import com.example.administrator.myhaicar.main.mine.Account.This.View.DrawConfirmPopWindow;
import com.example.administrator.myhaicar.tools.DateHelper;
import com.example.administrator.myhaicar.tools.ListDataSave;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

public class DrawSucceedActivity extends SwipeBackToolBarActivity {
private Context mContext=this;
    private Button button_drawSucceed;
    private String phone_number;
    private RelativeLayout activity_draw_succeed;
    private ImageView toolBar_back;
    private EditText editText_phone;
    private String usr_id;
    private  String total_amount;
    private  String user_name;
    private  String user_card;
    private String user_bank;
    private String strRand = "";
    private long l;
    private ListDataSave dataSave;
    private ArrayList<UserBankBean> listBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_succeed);
        initView();
        initClick();
    }
    private void initView() {
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        Intent intent=this.getIntent();
        total_amount = intent.getStringExtra("total_amount");
        user_name = intent.getStringExtra("user_name");
        user_card = intent.getStringExtra("user_card");
        user_bank = intent.getStringExtra("user_bank");
        editText_phone= (EditText) findViewById(R.id.editText_phone);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        button_drawSucceed= (Button) findViewById(R.id.button_drawSucceed);
        activity_draw_succeed= (RelativeLayout) findViewById(R.id.activity_draw_succeed);
        dataSave = new ListDataSave(mContext, "Person_bank");
        listBean = new ArrayList<UserBankBean>();
    }

    private void initClick() {
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_drawSucceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_drawSucceed.setClickable(false);
               Complete();

            }
        });
    }
    public void Complete() {
        phone_number = PreferencesUtils.getString(mContext, "phone_number");
        final String authCode = editText_phone.getText().toString().trim();
        final String tradeNo_id = getTradeNo_ID();
        OkHttpUtils.post().url(UrlConfig.Path.DrawWith_URL).addParams("acts", "postal_up").addParams("usr_id", usr_id).addParams("phone",phone_number)
                .addParams("out_trade_no", tradeNo_id).addParams("total_amount", total_amount).addParams("user_name", user_name)
                .addParams("user_card", user_card).addParams("user_bank", user_bank).addParams("veri_code", authCode).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络访问失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String s) {
               WithDrawBean withDrawBean = MyparseJsonToMessageBean(s);
                DrawMoneySuccfulBean drawMoneySuccfulBean = parseJsonToMessageBean(s);
                if ("".equals(authCode)) {
                    ConfirmPopWindow confirmPopWindow = new ConfirmPopWindow(mContext,"请您输入验证码","验证码不能为空");
                    confirmPopWindow.showAtLocation(activity_draw_succeed, Gravity.CENTER, 0, 0);
                } else if (authCode.length() != 6) {
                    ConfirmPopWindow confirmPopWindow = new ConfirmPopWindow(mContext,"请您检查验证码","验证码为6位");
                    confirmPopWindow.showAtLocation(activity_draw_succeed, Gravity.CENTER, 0, 0);
                } else {
                   String usr_veri_info = withDrawBean.getUsr_veri_info();
                    String usr_veri = withDrawBean.getUsr_veri();
                    String usr_bond = drawMoneySuccfulBean.getUsr_bond();
                    if ((usr_bond!=null)&&(usr_bond.equals("1"))) {
                        String upmoneyed = drawMoneySuccfulBean.getUpmoneyed();
                        PreferencesUtils.putString(mContext,"usr_money",upmoneyed);
                        DrawConfirmPopWindow confirmPopWindowtwo = new DrawConfirmPopWindow(mContext,"提现成功",button_drawSucceed);
                        confirmPopWindowtwo.showAtLocation(activity_draw_succeed, Gravity.CENTER, 0, 0);
                        UserBankBean user = new UserBankBean();
                        user.setName(user_name);
                        user.setBank(user_bank);
                        user.setBankNumber(user_card);
                        listBean.add(user);
                        dataSave.setDataList("javaBean", listBean);
                    } else {
                        DrawConfirmPopWindow confirmPopWindowtwo = new DrawConfirmPopWindow(mContext,usr_veri_info,button_drawSucceed);
                        confirmPopWindowtwo.showAtLocation(activity_draw_succeed, Gravity.CENTER, 0, 0);
                    }
                }
            }
        });
    }
    public WithDrawBean MyparseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        WithDrawBean bean = gson.fromJson(jsonString, new TypeToken<WithDrawBean>() {
        }.getType());
        return bean;
    }
    public DrawMoneySuccfulBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        DrawMoneySuccfulBean bean = gson.fromJson(jsonString, new TypeToken<DrawMoneySuccfulBean>() {
        }.getType());
        return bean;
    }
    private String getTradeNo_ID() {
        strRand="";
        for(int i=0;i<11;i++){
            strRand += String.valueOf((int)(Math.random() * 10)) ;
        }
        l = System.currentTimeMillis();
        String time = DateHelper.getFormatDate(l + "", "yyyyMMddhhmmssSSS");
        String out_trade_no = time + strRand;
        return out_trade_no;
    }
    //点击空白处隐藏键盘
    public void ondrawSucceed(View view) {
        switch (view.getId()) {
            case R.id.activity_draw_succeed:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;

        }
    }
}
