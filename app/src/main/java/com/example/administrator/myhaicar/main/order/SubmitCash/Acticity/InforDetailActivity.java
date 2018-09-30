package com.example.administrator.myhaicar.main.order.SubmitCash.Acticity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;

public class InforDetailActivity extends SwipeBackToolBarActivity {
private Context mContext=this;
    private ImageView toolBar_back;
    private WebView webView_inforDetail;
    private String news_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_detail);
        initView();
        initData();
    }
    private void initView() {
        webView_inforDetail= (WebView) findViewById(R.id.webView_inforDetail);
        toolBar_back=(ImageView)findViewById(R.id.toolBar_back);
    }
    private void initData() {
        Intent intent = this.getIntent();
        news_url = intent.getStringExtra("news_url");
        webView_inforDetail.loadUrl(news_url);
        //设置让Webview支持JavaScript脚本语言
        webView_inforDetail.getSettings().setJavaScriptEnabled(true);
        //让webview控件充当打开网页的客户端
        webView_inforDetail.setWebViewClient(new WebViewClient());
        //执行类似于alert这样的JavaScript语句
        webView_inforDetail.setWebChromeClient(new WebChromeClient());
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
