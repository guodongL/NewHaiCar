package com.example.administrator.myhaicar.main.login.Guide.Acticity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;

public class StatementActivity extends SwipeBackToolBarActivity {
private ImageView toolBar_back;
    private WebView webView_stateMent;
    private String state_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        initView();
        initData();
    }

    private void initView() {
        webView_stateMent= (WebView) findViewById(R.id.webView_stateMent);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        state_url="http://apptest.yingqin8.com/haiche/app/page/?id=1";
    }
    private void initData() {
        webView_stateMent.loadUrl(state_url);
        //设置让Webview支持JavaScript脚本语言
        webView_stateMent.getSettings().setJavaScriptEnabled(true);
        //让webview控件充当打开网页的客户端
        webView_stateMent.setWebViewClient(new WebViewClient());
        //执行类似于alert这样的JavaScript语句
        webView_stateMent.setWebChromeClient(new WebChromeClient());
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
