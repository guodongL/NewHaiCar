package com.example.administrator.myhaicar.main.mine.Account.OrderCash;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.utils.HTTP_GD;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class OrderCashActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private PullToRefreshListView listview_orderCash;
    private ImageView toolBar_back;
    private ImageView imaeView_problem;
    private OrderCashAdapter orderCashAdapter;
    private String usr_id;
    private CashBean cashBean;
    private Handler han=new Handler();
    private String endid;
    private List<CashBean.BondlistBean> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cash);
        initView();
        initClick();
        autoRefresh();
    }
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                listview_orderCash.setRefreshing(true);
            }
        },100);
    }
    private void initView() {
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        listview_orderCash= (PullToRefreshListView) findViewById(R.id.listview_orderCash);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        imaeView_problem= (ImageView) findViewById(R.id.imaeView_problem);
        orderCashAdapter=new OrderCashAdapter(mContext,list);
        listview_orderCash.setAdapter(orderCashAdapter);
    }

    private void initClick() {
        imaeView_problem.setOnClickListener(new View.OnClickListener() {
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
        listview_orderCash.setMode(PullToRefreshBase.Mode.BOTH);
        listview_orderCash.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                HTTP_Cash();
                listview_orderCash.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listview_orderCash.onRefreshComplete();
                    }
                },1500);

        }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (orderCashAdapter.getCount()!=0) {
                    int count = orderCashAdapter.getCount() - 1;
                    CashBean.BondlistBean item = (CashBean.BondlistBean) orderCashAdapter.getItem(count);
                    String order_id = item.getOrder_id();
                    endid = order_id;
                    HTTP_MoreCash();
                }else {
                    listview_orderCash.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listview_orderCash.onRefreshComplete();
                        }
                    },1500);
                }
            }
        });
    }
    public void HTTP_Cash(){
        OkHttpUtils.post().url(UrlConfig.Path.Cash_URL).addParams("acts","bond_list").addParams("usr_id",usr_id).addParams("pgs","999999999").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        listview_orderCash.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listview_orderCash.onRefreshComplete();
                            }
                        },1500);
                        Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String s) {
                        if (HTTP_GD.IsOrNot_Null(s))
                        {
                            return;
                        }
                        cashBean = parseJsonToMessageBean(s);
                        list = cashBean.getBondlist();
                        if (list==null)
                        {
                            list=new ArrayList<>();
                            listview_orderCash.onRefreshComplete();
                        }
                        listview_orderCash.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listview_orderCash.onRefreshComplete();
                                orderCashAdapter.reloadGridView(list,true);
                                if (list!=null)
                                {
                                    if (list.size()>0)
                                    {
                                        listview_orderCash.getRefreshableView().setSelection(0);
                                    }
                                }
                            }
                        }, 1500);
                    }
                });
    }
    public void HTTP_MoreCash(){
        OkHttpUtils.post().url(UrlConfig.Path.Cash_URL).addParams("acts","bond_list").addParams("usr_id",usr_id).addParams("pgs",endid).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String s) {
                        if (HTTP_GD.IsOrNot_Null(s))
                        {
                            return;
                        }
                        cashBean = parseJsonToMessageBean(s);
                        list = cashBean.getBondlist();
                        if (list==null)
                        {
                            list=new ArrayList<>();
                        }
                        listview_orderCash.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listview_orderCash.onRefreshComplete();
                                orderCashAdapter.reloadGridView(list,true);

                            }
                        }, 1500);
                    }
                });
    }
    public CashBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        CashBean bean = gson.fromJson(jsonString, new TypeToken<CashBean>() {
        }.getType());
        return bean;
    }
}
