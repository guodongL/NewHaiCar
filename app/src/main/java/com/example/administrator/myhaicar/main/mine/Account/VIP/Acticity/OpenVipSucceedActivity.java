package com.example.administrator.myhaicar.main.mine.Account.VIP.Acticity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
/*
* 开通VIP成功后跳转的页面
* */
public class OpenVipSucceedActivity extends SwipeBackToolBarActivity {
private ImageView toolBar_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_vip_succeed);
        initView();
        initClick();
    }
    private void initView() {
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
    }
    private void initClick() {
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
