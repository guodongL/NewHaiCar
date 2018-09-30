package com.example.administrator.myhaicar.main.mine.Account.MineDetail;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;

public class NewPhoneSucceedActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private ImageView toolBar_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_phone_succeed);
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
