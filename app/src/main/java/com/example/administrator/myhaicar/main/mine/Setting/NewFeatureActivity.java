package com.example.administrator.myhaicar.main.mine.Setting;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;

public class NewFeatureActivity extends SwipeBackToolBarActivity {
private ImageView toolBar_back;
    private WebView webView_newWeb;
    private String new_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feature);
        initView();
        initData();
    }

    private void initView() {
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        new_url="http://apptest.yingqin8.com/haiche/app/page/?id=3";
        webView_newWeb= (WebView) findViewById(R.id.webView_newWeb);
    }

    private void initData() {
        webView_newWeb.loadUrl(new_url);
        //设置让Webview支持JavaScript脚本语言
        webView_newWeb.getSettings().setJavaScriptEnabled(true);
        //让webview控件充当打开网页的客户端
        webView_newWeb.setWebViewClient(new WebViewClient());
        //执行类似于alert这样的JavaScript语句
        webView_newWeb.setWebChromeClient(new WebChromeClient());
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
