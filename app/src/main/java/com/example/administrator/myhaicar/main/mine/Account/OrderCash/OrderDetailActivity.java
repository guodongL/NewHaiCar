package com.example.administrator.myhaicar.main.mine.Account.OrderCash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.MyOrder.Model.MyOrderDetailBean;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class OrderDetailActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private OrderDetailAdapter detailAdapter;
    private ImageView imaeView_detailproblem;
    private ImageView toolBar_back;
    private ImageView image_carClass;
    private ImageView image_order_bond;
    private String order_id;
    private String usr_id;
    private int order_bond;
    private boolean skin;
    private ImageView image_carType;
    private MyOrderDetailBean.OrderInfoBean order_info;
    private MyOrderDetailBean detailBean;
    private TextView textView_order_num,textView_order_carclass,textView_order_cartype,textView_order_countdown,
            textView_order_date,textView_order_site,textView_order_km,textView_order_price,textView_order_price1,textView_order_remarks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initView();
        initClick();
        HTTP_OrderDetail();

    }
    private void initView() {
        skin = PreferencesUtils.getBoolean(mContext, "skin");
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        Intent intent=this.getIntent();
        order_id = intent.getStringExtra("order_id");
        image_carType= (ImageView) findViewById(R.id.image_carType);
        textView_order_num= (TextView) findViewById(R.id.textView_order_num);
        image_order_bond= (ImageView) findViewById(R.id.image_order_bond);
        image_carClass= (ImageView) findViewById(R.id.image_carClass);
        textView_order_carclass= (TextView) findViewById(R.id.textView_order_carclass);
        textView_order_cartype= (TextView) findViewById(R.id.textView_order_cartype);
        textView_order_countdown= (TextView) findViewById(R.id.textView_order_countdown);
        textView_order_site= (TextView) findViewById(R.id.textView_order_site);
        textView_order_price= (TextView) findViewById(R.id.textView_order_price);
        textView_order_price1= (TextView) findViewById(R.id.textView_order_price1);
        textView_order_remarks= (TextView) findViewById(R.id.textView_order_remarks);
        textView_order_date= (TextView) findViewById(R.id.textView_order_date);
        textView_order_km= (TextView) findViewById(R.id.textView_order_km);
        imaeView_detailproblem= (ImageView) findViewById(R.id.imaeView_detailproblem);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        detailAdapter=new OrderDetailAdapter(mContext);

    }

    private void initClick() {
        imaeView_detailproblem.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.setClass(mContext,CommonProblemActivity.class);
        startActivity(intent);
    }
});
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void HTTP_OrderDetail(){
        OkHttpUtils.post().url(UrlConfig.Path.MyOrder_URL).addParams("order_id",order_id).addParams("user_id",usr_id).addParams("act","listcontent").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext,"网络访问失败",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String s) {
                         detailBean = parseJsonToMessageBean(s);
                         order_bond = detailBean.getOrder_bond();
                         order_info = detailBean.getOrder_info();
                        textView_order_carclass.setText(order_info.getOrder_carclass());
                        textView_order_num.setText("订单编号"+order_info.getOrder_num());
                        textView_order_cartype.setText(order_info.getOrder_cartype()+"   "+order_info.getOrder_color());
                        textView_order_countdown.setText("离出车时间还有"+order_info.getOrder_countdown()+"天");
                        textView_order_date.setText(order_info.getOrder_date()+"   "+order_info.getOrder_time());
                        textView_order_site.setText(order_info.getOrder_site());
                        textView_order_km.setText(order_info.getOrder_km()+"Km");
                        textView_order_price.setText(order_info.getOrder_price()+"元");
                        Glide.with(mContext).load(order_info.getOrder_cartypelogo()).into(image_carType);
                        String order_carclass = order_info.getOrder_carclass();
                        if (order_carclass.equals("头车")){
                            if (skin){
                                image_carClass.setImageResource(R.drawable.corner_night);
                            }else {
                                image_carClass.setImageResource(R.drawable.corner_day);
                            }
                        }else {
                                image_carClass.setImageResource(R.drawable.corner1);
                        }
                        if (order_bond==1){
                            image_order_bond.setVisibility(View.GONE);
                            textView_order_price1.setText("保证金0元");
                        }else if (order_bond==2){
                            if (skin){
                                image_order_bond.setImageResource(R.drawable.hasend_night);
                            }else {
                                image_order_bond.setImageResource(R.drawable.hasend_day);
                            }
                            textView_order_price1.setText("保证金"+order_info.getOrder_price()+"元");
                        }else {
                            if (skin){
                                image_order_bond.setImageResource(R.drawable.noend_night);
                            }else {
                                image_order_bond.setImageResource(R.drawable.noend_day);
                            }
                            textView_order_price1.setText("保证金"+order_info.getOrder_price()+"元");
                        }

                        textView_order_remarks.setText(order_info.getOrder_remarks());
                    }
                });
    }
    public MyOrderDetailBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        MyOrderDetailBean bean = gson.fromJson(jsonString, new TypeToken<MyOrderDetailBean>() {
        }.getType());
        return bean;
    }
}
