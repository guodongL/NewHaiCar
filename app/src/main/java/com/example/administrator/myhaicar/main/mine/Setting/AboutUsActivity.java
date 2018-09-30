package com.example.administrator.myhaicar.main.mine.Setting;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;

public class AboutUsActivity extends SwipeBackToolBarActivity {
    private ImageView toolBar_back;
    private WebView webView_AboutUs;
    private String state_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initView();

        initData();
    }

    private void initView() {
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        webView_AboutUs= (WebView) findViewById(R.id.webView_AboutUs);
        state_url="http://apptest.yingqin8.com/haiche/app/page/?id=46";
    }

    private void initData() {
        webView_AboutUs.loadUrl(state_url);
        //设置让Webview支持JavaScript脚本语言
        webView_AboutUs.getSettings().setJavaScriptEnabled(true);
        //让webview控件充当打开网页的客户端
        webView_AboutUs.setWebViewClient(new WebViewClient());
        //执行类似于alert这样的JavaScript语句
        webView_AboutUs.setWebChromeClient(new WebChromeClient());
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
