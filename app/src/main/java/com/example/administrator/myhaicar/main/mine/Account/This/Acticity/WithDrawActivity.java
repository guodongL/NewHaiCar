package com.example.administrator.myhaicar.main.mine.Account.This.Acticity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.MyListView;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.Account.MineDetail.NewPhoneBean;
import com.example.administrator.myhaicar.main.mine.Account.This.Adapater.BankAdapter;
import com.example.administrator.myhaicar.main.mine.Account.This.Model.BankName;
import com.example.administrator.myhaicar.main.mine.Account.This.Model.DrawMoneyBean;
import com.example.administrator.myhaicar.commond.ConfirmPopWindowThree;
import com.example.administrator.myhaicar.main.mine.Account.This.Model.UserBankBean;
import com.example.administrator.myhaicar.tools.ListDataSave;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class WithDrawActivity extends SwipeBackToolBarActivity  {
private LinearLayout linear_bank;
    private Context mContext=this;
    private MyListView listView_bankNumber;
    private DrawerLayout activity_with_draw;
    private BankAdapter bankAdapter;
    private Button button_bankTrue;
    private RelativeLayout objectView;
    private String bankName;
    private String name;
    private String bankNumber;
    private Button button_withDraw;
    private EditText edit_drawMoney;
    private EditText edit_peopleName;
    private TextView text_bankName;
    private ImageView image_drawOpen;
    private EditText edit_bankNumber;
    private CoordinatorLayout coord_withDraw;
    private ImageView toolBar_back;
    private String bank;
    private boolean skin;
    private String usr_money;
    private int money;
    private String drawMoney;
    private String number;
    private String peopleName;
    private String blank;
    private String phone_number;
    private  int intmoney;
    private String usr_id;
    private ListDataSave dataSave;
    private List<UserBankBean> listBean;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw);
        initView();
        initClick();
    }
    private void initView() {
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        skin = PreferencesUtils.getBoolean(mContext, "skin");
        EventBus.getDefault().register(this);
        preferences = mContext.getSharedPreferences("Person_bank", Context.MODE_PRIVATE);
        editor = preferences.edit();
        usr_money = PreferencesUtils.getString(mContext, "usr_money");
        phone_number = PreferencesUtils.getString(mContext, "phone_number");
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        coord_withDraw= (CoordinatorLayout) findViewById(R.id.coord_withDraw);
        image_drawOpen= (ImageView) findViewById(R.id.image_drawOpen);
        edit_bankNumber= (EditText) findViewById(R.id.edit_bankNumber);
        button_withDraw= (Button) findViewById(R.id.button_withDraw);
        edit_drawMoney= (EditText) findViewById(R.id.edit_drawMoney);
        edit_peopleName= (EditText) findViewById(R.id.edit_peopleName);
        text_bankName= (TextView) findViewById(R.id.text_bankName);
        button_bankTrue= (Button) findViewById(R.id.button_bankTrue);
        listView_bankNumber= (MyListView) findViewById(R.id.listView_bankNumber);
        linear_bank= (LinearLayout) findViewById(R.id.linear_bank);
        activity_with_draw= (DrawerLayout) findViewById(R.id.activity_with_draw);
        dataSave = new ListDataSave(mContext, "Person_bank");
        listBean = new ArrayList<UserBankBean>();
        bankAdapter=new BankAdapter(mContext,listBean);
    }
    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text_L = edit_drawMoney.getText().toString();
            String text_S = text_L.substring(0,start);
            String text_E = text_L.substring(start,text_L.length());
            if (text_S.equals("0")&&text_E.equals("0")) {
                edit_drawMoney.setText("0");
            }
            else if(text_S.equals("0")&&!text_E.equals("0")) {
                edit_drawMoney.setText(text_E);
            }
            edit_drawMoney.setSelection(edit_drawMoney.length());
        }

        @Override
        public void afterTextChanged(Editable s) {
            drawMoney = edit_drawMoney.getText().toString();
        }
    };
    private void initClick() {
       /* double d = Double.parseDouble(usr_money);
        double floor = Math.floor(d);
        money= (int) floor;*/
        blank = text_bankName.getText().toString().trim();
        number = edit_bankNumber.getText().toString().trim();
        peopleName = edit_peopleName.getText().toString().trim();
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_withDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawMoney = edit_drawMoney.getText().toString().trim();
                intmoney = Integer.parseInt(drawMoney);
                final int result = intmoney - money;
                blank = text_bankName.getText().toString().trim();
                number = edit_bankNumber.getText().toString().trim();
                peopleName = edit_peopleName.getText().toString().trim();
                if (blank.equals("")) {
                    ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext, "请输入您的开户银行");
                    confirmPopWindowtwo.showAtLocation(activity_with_draw, Gravity.CENTER, 0, 0);
                } else if (number.equals("")) {
                    ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext, "请输入您的银行卡号");
                    confirmPopWindowtwo.showAtLocation(activity_with_draw, Gravity.CENTER, 0, 0);

                } else if (peopleName.equals("")) {
                    ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext, "请输入您的银行卡开户姓名");
                    confirmPopWindowtwo.showAtLocation(activity_with_draw, Gravity.CENTER, 0, 0);
                } else if (drawMoney.equals("") ? true : (Integer.parseInt(drawMoney) == 0 ? true : false)) {
                    ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext, "请输入正确的提现金额");
                    confirmPopWindowtwo.showAtLocation(activity_with_draw, Gravity.CENTER, 0, 0);
                } else {
                    OkHttpUtils.post().url(UrlConfig.Path.DrawWith_URL).addParams("acts","verimoney").addParams("usr_id",usr_id)
                            .addParams("total_amount",drawMoney).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            Toast.makeText(mContext, "网络访问失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String s) {
                            DrawMoneyBean drawMoneyBean = parseJsonToMessageBean(s);
                            String usr_bond = drawMoneyBean.getUsr_bond();
                            String usr_bond_info = drawMoneyBean.getUsr_bond_info();
                            if (usr_bond.equals("1")){
                                OkHttpUtils.post().url(UrlConfig.Path.Phone_URL).addParams("act","smsobtain").addParams("phone",phone_number)
                                        .build().execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e) {
                                        Toast.makeText(mContext, "网络访问失败", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(String s) {
                                        NewPhoneBean newPhoneBean = MyparseJsonToMessageBean(s);
                                        String ret_prompt = newPhoneBean.getRet_prompt();
                                        if (ret_prompt.equals("1")){
                                            Intent intent = new Intent();
                                            intent.putExtra("total_amount", drawMoney);
                                            intent.putExtra("user_name", peopleName);
                                            intent.putExtra("user_card", number);
                                            intent.putExtra("user_bank", blank);
                                            intent.setClass(mContext, DrawSucceedActivity.class);
                                            startActivity(intent);

                                        }else {
                                            ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext, "您的电话号码不正确");
                                            confirmPopWindowtwo.showAtLocation(activity_with_draw, Gravity.CENTER, 0, 0);
                                        }
                                    }
                                });
                            }else {
                                ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext, usr_bond_info);
                                confirmPopWindowtwo.showAtLocation(activity_with_draw, Gravity.CENTER, 0, 0);
                            }

                        }
                    });
                }
                }
        });
        image_drawOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏键盘
                InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (activity_with_draw.isDrawerOpen(GravityCompat.END)) {
                    activity_with_draw.closeDrawer(GravityCompat.END);
                } else {
                    activity_with_draw.openDrawer(GravityCompat.END);
                }
                String strJson = preferences.getString("javaBean", null);
                listBean = BankparseJsonToMessageBean(strJson);
                if (listBean==null)
                {
                    listBean=new ArrayList<>();
                }
                bankAdapter.reloadGridView(listBean,true);
            }
        });
        edit_drawMoney.addTextChangedListener(textWatcher);
            button_bankTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_bankName.setText(bankName);
                if (skin){
                    text_bankName.setTextColor(mContext.getResources().getColor(R.color.textColor_night));
                }else {
                    text_bankName.setTextColor(mContext.getResources().getColor(R.color.cardview_dark_background));
                }
                edit_peopleName.setText(name);
                edit_bankNumber.setText(bankNumber);
                activity_with_draw.closeDrawer(GravityCompat.END);


            }
        });
        bankAdapter.setmItemOnClickListener(new BankAdapter.ItemOnClickListener() {
            @Override
            public void itemOnClickListener(RelativeLayout selectView, String withDraw_bank, String withDraw_name, String withDraw_bankNumber) {
                setSelectColors(selectView,withDraw_bank,withDraw_name,withDraw_bankNumber);
            }
        });
        listView_bankNumber.setAdapter(bankAdapter);
        activity_with_draw.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        linear_bank.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(mContext,BankActivity.class);
            startActivity(intent);
        }
    });

    }
    private void setSelectColors(RelativeLayout view, String withDraw_bank,String withDraw_name,String withDraw_bankNumber)
    {   //颜色单选公用方法
        if (objectView==null)
        {
            objectView = view;
            RadioButton button = (RadioButton) objectView.getChildAt(0);
            button.setChecked(true);
            bankName=withDraw_bank;
             name=withDraw_name;
             bankNumber=withDraw_bankNumber;
        }
        else if(objectView != view)
        {
            RadioButton  buttonNew = (RadioButton) view.getChildAt(0);
            RadioButton  buttonOld = (RadioButton) objectView.getChildAt(0);
            buttonOld.setChecked(false);//!buttonOld.isChecked()
            buttonNew.setChecked(true);//!buttonNew.isChecked()
            objectView = view;
             bankName=withDraw_bank;
             name=withDraw_name;
             bankNumber=withDraw_bankNumber;
        }
    }

    @Subscribe
    public void onEventMainThread(BankName event) {
                String msg =event.getBankName();
        if (skin){
            text_bankName.setTextColor(this.getResources().getColor(R.color.textColor_night));
        }else {
            text_bankName.setTextColor(Color.parseColor("#010101"));
            text_bankName.setTextColor(this.getResources().getColor(R.color.cardview_dark_background));
        }


        text_bankName.setText(msg);

          }
//点击空白处隐藏键盘
    public void ondrawClick(View view) {
        switch (view.getId()) {
            case R.id.coord_withDraw:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;

        }
    }
    public DrawMoneyBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        DrawMoneyBean bean = gson.fromJson(jsonString, new TypeToken<DrawMoneyBean>() {
        }.getType());

        return bean;
    }
    public NewPhoneBean MyparseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        NewPhoneBean bean = gson.fromJson(jsonString, new TypeToken<NewPhoneBean>() {
        }.getType());
        return bean;
    }
    public List<UserBankBean> BankparseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        List<UserBankBean> list = gson.fromJson(jsonString, new TypeToken<List<UserBankBean>>() {}.getType());
        return list;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}
