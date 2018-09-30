package com.example.administrator.myhaicar.main.mine.MyOrder.Acticity;

import android.content.Context;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.MyOrder.Adapater.HistoryRecordAdapter;
import com.example.administrator.myhaicar.main.mine.MyOrder.Model.HistoryBean;
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

public class HistoryRecordActivity extends SwipeBackToolBarActivity {
    private ImageView toolBar_back;
    private Context mContext=this;
    private PullToRefreshListView history_listView;
    private HistoryRecordAdapter historyAdapter;
    private LinearLayout linear_Top;
    private List<HistoryBean.ListinfoBean> list=new ArrayList<>();
    private HistoryBean historyBean;
    private String endid;
    private String usr_id;
    private Handler han=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_record);
        initView();
        initClick();
        autoRefresh();
    }
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                history_listView.setRefreshing(true);
            }
        },100);
    }
    private void initView() {
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        linear_Top= (LinearLayout) findViewById(R.id.linear_Top);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        history_listView= (PullToRefreshListView) findViewById(R.id.history_listView);
        historyAdapter=new HistoryRecordAdapter(mContext,list);
        history_listView.setAdapter(historyAdapter);
    }

    private void initClick() {
         toolBar_back.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  finish();
                  }
            });
        linear_Top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history_listView.getRefreshableView().smoothScrollToPosition(0);
            }
        });
        history_listView.setMode(PullToRefreshBase.Mode.BOTH);
        history_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                      HTTP_History();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (historyAdapter.getCount()!=0) {
                    int count = historyAdapter.getCount() - 1;
                    HistoryBean.ListinfoBean item = (HistoryBean.ListinfoBean) historyAdapter.getItem(count);
                    String order_id = item.getOrder_id();
                    endid = order_id;
                    HTTP_MoreHistory();
                }else {
                    history_listView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            history_listView.onRefreshComplete();
                        }
                    }, 1500);
                }
            }
        });
       }
    public void HTTP_History(){
        OkHttpUtils.post().url(UrlConfig.Path.MyOrder_URL).addParams("act","listed").addParams("user_id",usr_id).addParams("endid","999999999")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                history_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        history_listView.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onResponse(String s) {
                if (HTTP_GD.IsOrNot_Null(s))
                {
                    return;
                }
                historyBean = parseJsonToMessageBean(s);
                list = historyBean.getListinfo();
                if (list==null)
                {
                    list=new ArrayList<>();
                    history_listView.onRefreshComplete();
                }
                history_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        history_listView.onRefreshComplete();
                        historyAdapter.reloadGridView(list,true);
                        if (list!=null)
                        {
                            if (list.size()>0)
                            {
                                history_listView.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);
            }
        });
    }
    public void HTTP_MoreHistory(){
        OkHttpUtils.post().url(UrlConfig.Path.MyOrder_URL).addParams("act","listed").addParams("user_id",usr_id).addParams("endid",endid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                history_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        history_listView.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onResponse(String s) {
                if (HTTP_GD.IsOrNot_Null(s))
                {
                    return;
                }
                historyBean = parseJsonToMessageBean(s);
                List<HistoryBean.ListinfoBean> list_M=historyBean.getListinfo();
                if (list_M!=null)
                {
                    for (int i=0;i<list_M.size();i++)
                    {
                        list.add(list_M.get(i));
                    }
                }
                history_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        history_listView.onRefreshComplete();
                        historyAdapter.reloadGridView(list,true);
                    }
                }, 1500);
            }
        });
    }
    public HistoryBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        HistoryBean bean = gson.fromJson(jsonString, new TypeToken<HistoryBean>() {
        }.getType());
        return bean;
    }

}
