package com.example.administrator.myhaicar.main.mine.Event.Acticity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class MyWelDetailActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private ImageView toolBar_back;
    private WebView webView_welfareDetail;
    private ImageView image_myWelfare;
    private String acti_url;
    private  String usr_mime_id;
    private String usr_id;
    private String acti_id;
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.i("plat","platform"+platform);

            //Toast.makeText(InviteFrentsActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            //Toast.makeText(InviteFrentsActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.i("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            //Toast.makeText(InviteFrentsActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wel_detail);
        initView();
        initClick();

    }
    private void initView() {
        usr_mime_id = PreferencesUtils.getString(mContext, "usr_mime_id");
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        Intent intent=this.getIntent();
        acti_url = intent.getStringExtra("acti_url");
        acti_id = intent.getStringExtra("acti_id");
        image_myWelfare= (ImageView) findViewById(R.id.image_myWelfare);
        webView_welfareDetail= (WebView) findViewById(R.id.webView_welfareDetail);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
    }

    private void initClick() {
        webView_welfareDetail.loadUrl(acti_url);
        //设置让Webview支持JavaScript脚本语言
        webView_welfareDetail.getSettings().setJavaScriptEnabled(true);
        //让webview控件充当打开网页的客户端
        webView_welfareDetail.setWebViewClient(new WebViewClient());
        //执行类似于alert这样的JavaScript语句
        webView_welfareDetail.setWebChromeClient(new WebChromeClient());
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        image_myWelfare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UMImage thumb =  new UMImage(MyWelDetailActivity.this, R.drawable.icon);
                UMWeb web = new UMWeb("http://apptest.yingqin8.com/haiche/app/share/?acti_id="+acti_id+"&usr_id="+usr_id);
                web.setTitle("嗨车365");//标题
                web.setThumb(thumb);  //缩略图
                web.setDescription("赚钱养车，交友娱乐，开启人车新生活！");
                new ShareAction(MyWelDetailActivity.this).withText("hello")
                        .withMedia(web)
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener).open();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
