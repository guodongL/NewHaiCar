package com.example.administrator.myhaicar.main.mine.Event.Acticity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.Event.Adapater.WelMyselfAdapter;
import com.example.administrator.myhaicar.main.mine.Event.Model.MyWelfBean;
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

public class MyWelfareActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private PullToRefreshListView myWel_listView;
    private WelMyselfAdapter welAdapter;
    private ImageView toolBar_back;
    private Handler han=new Handler();
    private String endid;
    private String usr_id;
    private List<MyWelfBean.ActiInfoBean> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_welfare);
        initView();
        initClick();
        autoRefresh();
    }
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                myWel_listView.setRefreshing(true);
            }
        },200);
    }
    private void initView() {
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        myWel_listView= (PullToRefreshListView) findViewById(R.id.myWel_listView);
        welAdapter=new WelMyselfAdapter(mContext,list);
        myWel_listView.setAdapter(welAdapter);
    }

    private void initClick() {
        myWel_listView.setMode(PullToRefreshBase.Mode.BOTH);
        myWel_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                HTTP_Event();
                myWel_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myWel_listView.onRefreshComplete();
                    }
                },1500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (welAdapter.getCount() !=0) {
                    int count = welAdapter.getCount() - 1;
                    MyWelfBean.ActiInfoBean item = ( MyWelfBean.ActiInfoBean) welAdapter.getItem(count);
                    String order_id = item.getActi_id();
                    endid = order_id;
                    HTTP_MoreEvent();
                    myWel_listView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myWel_listView.onRefreshComplete();
                        }
                    }, 1500);
                }else {
                    myWel_listView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myWel_listView.onRefreshComplete();
                        }
                    }, 1500);
                }
            }
        });
        myWel_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyWelfBean.ActiInfoBean item = (MyWelfBean.ActiInfoBean) welAdapter.getItem(position-1);
                String acti_url = item.getActi_url();
                String acti_id = item.getActi_id();
                Intent intent=new Intent();
                intent.putExtra("acti_url",acti_url);
                intent.putExtra("acti_id",acti_id);
                intent.setClass(mContext,MyWelDetailActivity.class);
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
    public  void  HTTP_Event(){
        OkHttpUtils.post().url(UrlConfig.Path.MyEvent_URL).addParams("acts","lists").addParams("usr_id",usr_id).addParams("pgs","999999999")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                myWel_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myWel_listView.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onResponse(String s) {
                String jsonString=s;
                if (HTTP_GD.IsOrNot_Null(jsonString))
                {
                    return;
                }
                MyWelfBean myWelfBean = parseJsonToMessageBean(s);
                list=myWelfBean.getActi_info();
                if (list==null)
                {
                    list=new ArrayList<>();
                }
                myWel_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myWel_listView.onRefreshComplete();
                        welAdapter.reloadGridView(list,true);
                        if (list!=null)
                        {
                            if (list.size()>0)
                            {
                                myWel_listView.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);

            }
        });
    }
    public  void  HTTP_MoreEvent () {
        OkHttpUtils.post().url(UrlConfig.Path.MyEvent_URL).addParams("acts","lists").addParams("usr_id",usr_id).addParams("pgs", endid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                myWel_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myWel_listView.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onResponse(String s) {
                String jsonString = s;
                if (HTTP_GD.IsOrNot_Null(jsonString)) {
                    return;
                }
                MyWelfBean myWelfBean = parseJsonToMessageBean(s);
                List<MyWelfBean.ActiInfoBean> list_M = myWelfBean.getActi_info();
                if (list_M != null) {
                    for (int i = 0; i < list_M.size(); i++) {
                        list.add(list_M.get(i));
                    }
                }
                myWel_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myWel_listView.onRefreshComplete();
                        welAdapter.reloadGridView(list, true);
                    }
                }, 1500);

            }
        });
    }
    public MyWelfBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        MyWelfBean bean = gson.fromJson(jsonString, new TypeToken<MyWelfBean>() {
        }.getType());
        return bean;
    }
}
