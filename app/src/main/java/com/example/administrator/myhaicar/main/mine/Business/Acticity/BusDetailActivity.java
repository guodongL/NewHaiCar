package com.example.administrator.myhaicar.main.mine.Business.Acticity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.Business.Model.Contact;
import com.example.administrator.myhaicar.main.mine.Business.Model.ContactService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BusDetailActivity extends SwipeBackToolBarActivity {
    private WebView webView_BusDetail;
    private ImageView toolBar_back;
    private Context mContext = this;
    private String Base_Url = "http://apptest.yingqin8.com/haiche/app/business/view.php?id=";
    private String busi_id;
    private ContactService contactService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);
        initView();
        initSet();
    }

    private void initView() {
        Intent intent = this.getIntent();
        busi_id = intent.getStringExtra("busi_id");
        contactService = new ContactService();
        toolBar_back = (ImageView) findViewById(R.id.toolBar_back);
        webView_BusDetail = (WebView) findViewById(R.id.webView_BusDetail);
    }

    private void initSet() {
        webView_BusDetail.loadUrl(Base_Url + busi_id);
        //设置让Webview支持JavaScript脚本语言
        webView_BusDetail.getSettings().setJavaScriptEnabled(true);
        //让webview控件充当打开网页的客户端
        //  webView_BusDetail.setWebViewClient(new WebViewClient());
        //执行类似于alert这样的JavaScript语句
        webView_BusDetail.setWebChromeClient(new WebChromeClient());
        //不加，单击超连接，启动系统的浏览器，加了之后在我们自己的APP中显示网页。
        webView_BusDetail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading
                    (WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return false;
        }
        });

        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class ContactsPlugin {
        /**
         * 此方法将执行 JS 代码，调用 JS 函数：show()
         * 实现，将联系人信息展示到 HTML 页面上
         */
        @SuppressWarnings("unused")
        public void getContacts() {
            List<Contact> contacts = contactService.getContacts();
            try {
                JSONArray array = new JSONArray();
                for (Contact contact : contacts) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", contact.getId());
                    jsonObject.put("mobile", contact.getMobile());
                    jsonObject.put("name", contact.getName());
                    array.put(jsonObject);
                }
                String json = array.toString();
                webView_BusDetail.loadUrl("javascript:show('" + json + "')");
            } catch (JSONException e) {

            }
        }

        /**
         * 拨号
         */
        @SuppressWarnings("unused")
        public void call(String phoneCode) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneCode));
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
        }
    }

}
