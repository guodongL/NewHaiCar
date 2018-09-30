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
import com.example.administrator.myhaicar.main.mine.Event.Adapater.WelfareAdapter;
import com.example.administrator.myhaicar.main.mine.Event.Model.EvenBean;
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

public class WelfareActivity extends SwipeBackToolBarActivity {
private Context mContext=this;
    private PullToRefreshListView myWel_listView;
    private WelfareAdapter welAdapter;
    private ImageView toolBar_back;
    private String usr_id;
    private String endid;
    private ImageView image_myWelfare;
    private  String msg;
    private Handler han=new Handler();
    private List<EvenBean.ActilistinfoBean> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare);
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
        image_myWelfare= (ImageView) findViewById(R.id.image_myWelfare);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        myWel_listView= (PullToRefreshListView) findViewById(R.id.myWel_listView);
        welAdapter=new WelfareAdapter(mContext,list);
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
                    EvenBean.ActilistinfoBean item = (EvenBean.ActilistinfoBean) welAdapter.getItem(count);
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
                EvenBean.ActilistinfoBean item = (EvenBean.ActilistinfoBean) welAdapter.getItem(position-1);
                String acti_id = item.getActi_id();
                String acti_url = item.getActi_url();
                String acti_price = item.getActi_price();
                String acti_date = item.getActi_date();
                String acti_title = item.getActi_title();
                Intent intent=new Intent();
                intent.putExtra("acti_id",acti_id);
                intent.putExtra("acti_url",acti_url);
                intent.putExtra("acti_price",acti_price);
                intent.putExtra("acti_date",acti_date);
                intent.putExtra("acti_title",acti_title);
                intent.setClass(mContext,WelfareDetailActivity.class);
                startActivity(intent);
            }
        });
        image_myWelfare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,MyWelfareActivity.class);
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
        OkHttpUtils.post().url(UrlConfig.Path.Event_URL).addParams("acts","lists").addParams("usr_id",usr_id).addParams("pgs","999999999")
                .addParams("citys","沈阳").build().execute(new StringCallback() {
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
                EvenBean evenBean = parseJsonToMessageBean(s);
                list=evenBean.getActilistinfo();
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
        OkHttpUtils.post().url(UrlConfig.Path.Event_URL).addParams("acts", "lists").addParams("usr_id", usr_id).addParams("pgs", endid)
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
                EvenBean evenBean = parseJsonToMessageBean(s);
                List<EvenBean.ActilistinfoBean> list_M=evenBean.getActilistinfo();
                if (list_M!=null)
                {
                    for (int i=0;i<list_M.size();i++)
                    {
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
    public EvenBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        EvenBean bean = gson.fromJson(jsonString, new TypeToken<EvenBean>() {
        }.getType());
        return bean;
    }
  /*  @Subscribe
    public void onEventMainThread(BankName event) {
        msg =event.getBankName();
        if (msg.equals("5")){
            autoRefresh();
        }

    }*/
}
