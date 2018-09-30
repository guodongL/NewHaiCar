package com.example.administrator.myhaicar.main.order.SubmitCash.Acticity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.order.SubmitCash.Adapater.InformationAdapter;
import com.example.administrator.myhaicar.main.order.SubmitCash.Model.HasReadBean;
import com.example.administrator.myhaicar.main.order.SubmitCash.Model.InfoBean;
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

public class InfomationActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private ImageView toolBar_back;
    private PullToRefreshListView infor_listView;
    private InformationAdapter inforAdapter;
    private InfoBean infoBean;
    private List<InfoBean.NewslistinfoBean> list=new ArrayList<>();
    private String usr_id;
    private TextView textView_save;
    private Handler han=new Handler();
    private int pgs=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        initView();
        initClick();
        autoRefresh();

    }
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                infor_listView.setRefreshing(true);
            }
        },100);
    }


    private void initView() {
        textView_save= (TextView) findViewById(R.id.textView_save);
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        infor_listView= (PullToRefreshListView) findViewById(R.id.infor_listView);
        inforAdapter=new InformationAdapter(mContext,list);

    }

    private void initClick() {
        infor_listView.setAdapter(inforAdapter);
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        infor_listView.setMode(PullToRefreshBase.Mode.BOTH);
        infor_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                        HTTP_Info();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                             pgs++;
                         HTTP_MoreInfo();
            }
        });
        infor_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final InfoBean.NewslistinfoBean item = (InfoBean.NewslistinfoBean) inforAdapter.getItem(position - 1);
                String news_id = item.getNews_id();
                OkHttpUtils.post().url(UrlConfig.Path.News_URL).addParams("acts","reads").addParams("ids",news_id).addParams("usr_id",usr_id).build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {
                                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                            }
                            @Override
                            public void onResponse(String s) {
                                HasReadBean hasReadBean = hparseJsonToMessageBean(s);
                                String readok = hasReadBean.getReadok();
                                if (readok.equals("1")) {
                                    String news_url = item.getNews_url();
                                    Intent intent = new Intent();
                                    intent.putExtra("news_url", news_url);
                                    intent.setClass(mContext, InforDetailActivity.class);
                                    startActivity(intent);
                                    HTTP_Info();
                                }else {
                                    Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });
        textView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.post().url(UrlConfig.Path.News_URL).addParams("usr_id",usr_id).addParams("acts","reads_all").build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onResponse(String s) {
                        HasReadBean allReadBean = hparseJsonToMessageBean(s);
                        String readok = allReadBean.getReadok();
                        if (readok.equals("1")){
                            infor_listView.setRefreshing();
                        }
                    }
                });
            }
        });
    }
    public  void  HTTP_Info (){
        OkHttpUtils.post().url(UrlConfig.Path.News_URL).addParams("acts","lists").addParams("pgs","1").addParams("usr_id",usr_id).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                infor_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        infor_listView.onRefreshComplete();
                    }
                }, 1500);
            }
            @Override
            public void onResponse(String s) {
                String jsonString=s;
                if (HTTP_GD.IsOrNot_Null(jsonString))
                {
                    return;
                }
                infoBean = parseJsonToMessageBean(jsonString);
                list = infoBean.getNewslistinfo();
                if (list==null)
                {
                    list=new ArrayList<>();
                }
                infor_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        infor_listView.onRefreshComplete();
                        inforAdapter.reloadGridView(list,true);
                     if (list!=null)
                        {
                            if (list.size()>0)
                            {
                                infor_listView.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);
            }
        });
    }
    public  void  HTTP_MoreInfo (){
        OkHttpUtils.post().url(UrlConfig.Path.News_URL).addParams("acts","lists").addParams("pgs",pgs+"").addParams("usr_id",usr_id).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                infor_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        infor_listView.onRefreshComplete();
                    }
                }, 1500);
            }
            @Override
            public void onResponse(String s) {
                String jsonString=s;
                if (HTTP_GD.IsOrNot_Null(jsonString))
                {
                    return;
                }
                infoBean = parseJsonToMessageBean(jsonString);
                List<InfoBean.NewslistinfoBean> list_M=infoBean.getNewslistinfo();
                if (list_M!=null)
                {
                    for (int i=0;i<list_M.size();i++)
                    {
                        list.add(list_M.get(i));
                    }
                }
                infor_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        infor_listView.onRefreshComplete();
                        inforAdapter.reloadGridView(list,true);
                    }
                }, 1500);
            }
        });
    }
    public InfoBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        InfoBean bean = gson.fromJson(jsonString, new TypeToken<InfoBean>() {
        }.getType());
        return bean;
    }
    public HasReadBean hparseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        HasReadBean bean = gson.fromJson(jsonString, new TypeToken<HasReadBean>() {
        }.getType());
        return bean;
    }

}
