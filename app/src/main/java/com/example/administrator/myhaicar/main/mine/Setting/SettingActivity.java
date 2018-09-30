package com.example.administrator.myhaicar.main.mine.Setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.login.Guide.Acticity.StatementActivity;
import com.example.administrator.myhaicar.main.login.This.Acticity.LoginActivity;
import com.example.administrator.myhaicar.main.mine.Account.MineDetail.ShareIdActivity;
import com.example.administrator.myhaicar.main.mine.Account.This.Model.BankName;
import com.example.administrator.myhaicar.main.mine.Account.This.View.ConfirmPopWindow;
import com.example.administrator.myhaicar.utils.DataCleanManager;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.zhy.changeskin.SkinManager;

import org.greenrobot.eventbus.EventBus;

public class SettingActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private ToggleButton togBtn_skin;
    private ImageView toolBar_back;
    private LinearLayout linear_bindID;
    private LinearLayout linear_invit;
    private LinearLayout line_forUs;
    private Button button_quit;
    //新功能介绍
    private LinearLayout linear_newFeature;
    //免责声明
    private LinearLayout linear_disclaimer;
    private TextView textView_app_version;
    private LinearLayout linear_dataClean;
    private RelativeLayout activity_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkinManager.getInstance().register(this);
        setContentView(R.layout.activity_setting);
        initView();
        initClick();
    }

    private void initView() {
        boolean skin = PreferencesUtils.getBoolean(mContext, "skin");
        String app_version = PreferencesUtils.getString(mContext, "app_version");
        activity_setting= (RelativeLayout) findViewById(R.id.activity_setting);
        textView_app_version= (TextView) findViewById(R.id.textView_app_version);
        button_quit= (Button) findViewById(R.id.button_quit);
        line_forUs= (LinearLayout) findViewById(R.id.line_forUs);
        linear_dataClean= (LinearLayout) findViewById(R.id.linear_dataClean);
        linear_newFeature= (LinearLayout) findViewById(R.id.linear_newFeature);
        linear_disclaimer= (LinearLayout) findViewById(R.id.linear_disclaimer);
        linear_invit= (LinearLayout) findViewById(R.id.linear_invit);
        linear_bindID= (LinearLayout) findViewById(R.id.linear_bindID);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        togBtn_skin= (ToggleButton) findViewById(R.id.togBtn_skin);
        togBtn_skin.setChecked(skin);
        textView_app_version.setText(app_version);
    }

    private void initClick() {
        line_forUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,AboutUsActivity.class);
                startActivity(intent);
            }
        });
        button_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putString(mContext,"usr_flag","NO");
                button_quit.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        togBtn_skin.setChecked(false);
                    }
                }, 500);
                Intent intent = new Intent();
                intent.setClass(mContext, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                button_quit.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 800);

            }
        });
        linear_newFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,NewFeatureActivity.class);
                startActivity(intent);
            }
        });
        linear_disclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, StatementActivity.class);
                startActivity(intent);
            }
        });
        linear_invit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,ShareIdActivity.class);
                startActivity(intent);
            }
        });
        linear_dataClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.clearAllCache(mContext);
                ConfirmPopWindow confirmPopWindow = new ConfirmPopWindow(mContext, "提示", "缓存清除成功");
                confirmPopWindow.showAtLocation(activity_setting, Gravity.CENTER, 0, 0);
            }
        });
        togBtn_skin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean checked = buttonView.isChecked();
                PreferencesUtils.putBoolean(mContext,"skin",checked);
                if(checked){
                    SkinManager.getInstance().changeSkin("night");
                    EventBus.getDefault().post(new BankName("1"));
                }else{
                   SkinManager.getInstance().changeSkin("day");
                    EventBus.getDefault().post(new BankName("2"));
                }
            }
        });
        linear_bindID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, BondIDActivity.class);
                startActivity(intent);
            }
        });
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }

}
