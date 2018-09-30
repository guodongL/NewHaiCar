package com.example.administrator.myhaicar.main.mine.Account.MineDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;

public class DetailsNextActivity extends SwipeBackToolBarActivity {
private Context mContext=this;
    private TextView textView_details;
    private ImageView toolBar_back;
    private EditText edit_personDetail;
    private String hight;
    private RelativeLayout activity_details_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_next);
        initView();
        initData();
    }
    private void initView() {
        activity_details_next= (RelativeLayout) findViewById(R.id.activity_details_next);
        edit_personDetail= (EditText) findViewById(R.id.edit_personDetail);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        textView_details= (TextView) findViewById(R.id.textView_details);
        Intent intent=this.getIntent();
         hight = intent.getStringExtra("hight");
        textView_details.setText(hight);
    }
    private void initData() {
        edit_personDetail.addTextChangedListener(textWatcher);
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //下面方法是解决防止进入界面键盘自动弹出
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_details_next:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;

        }
    }
    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String personDetail = edit_personDetail.getText().toString()+"";
            String result = personDetail.trim().toString();
            Intent intent = new Intent();
            intent.putExtra("result", result);
            setResult(1000, intent);
        }
    };
}
