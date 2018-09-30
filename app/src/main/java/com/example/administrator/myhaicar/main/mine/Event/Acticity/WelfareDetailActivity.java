package com.example.administrator.myhaicar.main.mine.Event.Acticity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.Event.Model.ApplyBean;
import com.example.administrator.myhaicar.main.mine.Event.View.ApplyConfirmPopWindow;
import com.example.administrator.myhaicar.tools.DateHelper;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class WelfareDetailActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private ImageView toolBar_back;
    private WebView webView_welfareDetail;
    private Button button_apply;
    private String acti_id;
    private String acti_url;
    private String acti_price;
    private String acti_date;
    private String usr_id;
    private String acti_title;
    private long time;
    private String strRand = "";
    private ImageView image_myWelfare;
    private RelativeLayout activity_welfare_detail;
    private  String usr_mime_id;
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
        setContentView(R.layout.activity_welfare_detail);
        initView();

        initClick();
    }

    private void initView() {
        usr_mime_id = PreferencesUtils.getString(mContext, "usr_mime_id");
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        Intent intent=this.getIntent();
        acti_id = intent.getStringExtra("acti_id");
        acti_url = intent.getStringExtra("acti_url");
        acti_price = intent.getStringExtra("acti_price");
        acti_date = intent.getStringExtra("acti_date");
        acti_title = intent.getStringExtra("acti_title");
        button_apply= (Button) findViewById(R.id.button_apply);
        image_myWelfare= (ImageView) findViewById(R.id.image_myWelfare);
        activity_welfare_detail= (RelativeLayout) findViewById(R.id.activity_welfare_detail);
        webView_welfareDetail= (WebView) findViewById(R.id.webView_welfareDetail);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
    }

    private void initClick() {
        button_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_apply.setClickable(false);
                final String trade_this = getTradeNo_ID();
                OkHttpUtils.post().url(UrlConfig.Path.Event_URL).addParams("acts","sup").addParams("usr_id",usr_id).addParams("acti_id",acti_id).addParams("total_amount",acti_price)
                        .addParams("out_trade_no",trade_this).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "网络链接错误，报名失败", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String s) {
                        ApplyBean applyBean = parseJsonMessageBean(s);
                        String usr_acti = applyBean.getUsr_acti();
                        String usr_acti_info = applyBean.getUsr_acti_info();
                        if (usr_acti.equals("0")){
                            ApplyConfirmPopWindow confirmPopWindow = new ApplyConfirmPopWindow(mContext,usr_acti_info,button_apply);
                            confirmPopWindow.showAtLocation(activity_welfare_detail, Gravity.CENTER, 0, 0);
                        }else {
                            if (usr_acti_info.equals("可以报名！")){
                                    Intent intent=new Intent();
                                    intent.putExtra("acti_id",acti_id);
                                    intent.putExtra("acti_price",acti_price);
                                    intent.putExtra("acti_date",acti_date);
                                    intent.putExtra("acti_title",acti_title);
                                    intent.setClass(mContext,ApplyActivity.class);
                                    startActivity(intent);
                                    button_apply.setClickable(true);
                            }else {
                                ApplyConfirmPopWindow confirmPopWindow = new ApplyConfirmPopWindow(mContext,usr_acti_info,button_apply);
                                confirmPopWindow.showAtLocation(activity_welfare_detail, Gravity.CENTER, 0, 0);
                            }
                        }
                    }
                });
            }
        });
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
                UMImage thumb =  new UMImage(WelfareDetailActivity.this, R.drawable.icon);
                UMWeb web = new UMWeb("http://apptest.yingqin8.com/haiche/app/share/?acti_id="+acti_id+"&usr_id="+usr_id);
                web.setTitle("嗨车365");//标题
                web.setThumb(thumb);  //缩略图
                web.setDescription("赚钱养车，交友娱乐，开启人车新生活！");
                new ShareAction(WelfareDetailActivity.this).withText("hello")
                        .withMedia(web)
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener).open();
            }
        });
    }
    private String getTradeNo_ID() {
        strRand="";
        for (int i = 0; i < 11; i++) {
            strRand += String.valueOf((int) (Math.random() * 10));
        }
        time = System.currentTimeMillis();
        String time1 = DateHelper.getFormatDate(time + "", "yyyyMMddhhmmssSSS");
        String out_trade_no = time1 + strRand;
        //Toast.makeText(mContent,out_trade_no,Toast.LENGTH_SHORT).show();
        return out_trade_no;
    }
    public ApplyBean parseJsonMessageBean(String jsonString) {
        Gson gson = new Gson();
        ApplyBean bean = gson.fromJson(jsonString, new TypeToken<ApplyBean>() {
        }.getType());
        return bean;
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
