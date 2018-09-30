package com.example.administrator.myhaicar.main.mine.Account.MineDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class ShareIdActivity extends SwipeBackToolBarActivity {
private Context mContext=this;
    private ImageView toolBar_back;
    private Button button_share;
    private TextView textView_id;
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
        setContentView(R.layout.activity_share_id);
           initView();
           initClick();
    }

    private void initView() {
        usr_mime_id = PreferencesUtils.getString(mContext, "usr_mime_id");
        textView_id= (TextView) findViewById(R.id.textView_id);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        button_share= (Button) findViewById(R.id.button_share);
        textView_id.setText(usr_mime_id);
    }
    private void initClick() {
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMImage thumb =  new UMImage(ShareIdActivity.this, R.drawable.icon);
                UMWeb web = new UMWeb("http://apptest.yingqin8.com/haiche/app/downloadapp/index.html");
                web.setTitle("嗨车365");//标题
                web.setThumb(thumb);  //缩略图
                web.setDescription("赚钱养车，交友娱乐，开启人车新生活！");
                new ShareAction(ShareIdActivity.this).withText("hello")
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
