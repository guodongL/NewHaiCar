package com.example.administrator.myhaicar.main.mine.Setting;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.ConfirmPopWindowThree;
import com.example.administrator.myhaicar.commond.GsonParseJson;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.login.This.Model.NoLoginBean;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class BondIDActivity extends SwipeBackToolBarActivity {
private Context mContext=this;
    private RelativeLayout activity_bond_id;
    private ImageView toolBar_back;
    private EditText edit_bond;
    private Button button_boundTrue;
    private TextView textView_boundID;
    private String usr_id;
    private String usr_recommend_id;
    private LinearLayout linearLayout_boundID;
    private LinearLayout linearLayout_gone;
    private String edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bond_id);
        initView();
        initClick();
    }
    private void initView() {
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        usr_recommend_id = PreferencesUtils.getString(mContext, "usr_recommend_id");
        edit_bond= (EditText) findViewById(R.id.edit_bond);
        linearLayout_boundID= (LinearLayout) findViewById(R.id.linearLayout_boundID);
        linearLayout_gone= (LinearLayout) findViewById(R.id.linearLayout_gone);
        activity_bond_id= (RelativeLayout) findViewById(R.id.activity_bond_id);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        button_boundTrue= (Button) findViewById(R.id.button_boundTrue);
        textView_boundID= (TextView) findViewById(R.id.textView_boundID);
        textView_boundID.setText(usr_recommend_id);
    }

    private void initClick() {
       if (!usr_recommend_id.equals("-1")){
            linearLayout_gone.setVisibility(View.GONE);
            linearLayout_boundID.setVisibility(View.VISIBLE);
        }
      button_boundTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit = edit_bond.getText()+"";
                if (!edit.equals("")){
                    OkHttpUtils.post().url(UrlConfig.Path.BondID_URL).addParams("acts","recommend_id").addParams("usr_id",usr_id)
                            .addParams("upcont",edit).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            Toast.makeText(mContext,"网络请求失败",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String s) {
                            BoundBean boundBean = parseNoJsonToMessageBean(s);
                            String treatedok = boundBean.getTreatedok();
                            if (treatedok.equals("1")){
                                linearLayout_gone.setVisibility(View.GONE);
                                linearLayout_boundID.setVisibility(View.VISIBLE);
                                PreferencesUtils.putString(mContext,"usr_recommend_id",edit);
                                textView_boundID.setText(edit);

                            }else {
                                Toast.makeText(mContext,"绑定ID失败",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }else {
                    ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext, "请输入绑定人ID");
                    confirmPopWindowtwo.showAtLocation(activity_bond_id, Gravity.CENTER, 0, 0);
                }
            }
        });
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //点击空白处隐藏键盘
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_bond_id:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;

        }
    }
    public BoundBean parseNoJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        BoundBean bean = gson.fromJson(jsonString, new TypeToken<BoundBean>() {
        }.getType());

        return bean;
    }
}
