package com.example.administrator.myhaicar.main.mine.Account.OrderCash;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.order.SubmitCash.Acticity.InforDetailActivity;
import com.example.administrator.myhaicar.utils.HTTP_GD;
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

public class CommonProblemActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private PullToRefreshListView listview_haiCarproblem;
    private CommonProblemAdapter problemAdapter;
    private ImageView toolBar_back;
    private CommonProblemBean problemBean;
    private String endid;
    private Handler han=new Handler();
    private List<CommonProblemBean.HelplistBean> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_problem);
        initView();
        initClick();
        autoRefresh();
    }
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                listview_haiCarproblem.setRefreshing(true);
            }
        },200);
    }
    private void initView() {
        listview_haiCarproblem= (PullToRefreshListView) findViewById(R.id.listview_haiCarProblem);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        problemAdapter=new CommonProblemAdapter(mContext,list);
        listview_haiCarproblem.setAdapter(problemAdapter);
    }

    private void initClick() {
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listview_haiCarproblem.setMode(PullToRefreshBase.Mode.BOTH);
        listview_haiCarproblem.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                HTTP_Common();
                listview_haiCarproblem.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listview_haiCarproblem.onRefreshComplete();
                    }
                },1500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (problemAdapter.getCount() !=0) {
                    int count = problemAdapter.getCount() - 1;
                    CommonProblemBean.HelplistBean item = (CommonProblemBean.HelplistBean) problemAdapter.getItem(count);
                    String order_id = item.getNews_id();
                    endid = order_id;
                    HTTP_MoreCommon();
                    listview_haiCarproblem.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listview_haiCarproblem.onRefreshComplete();
                        }
                    }, 1500);
                }else {
                    listview_haiCarproblem.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listview_haiCarproblem.onRefreshComplete();
                        }
                    }, 1500);
                }
            }
        });
        listview_haiCarproblem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonProblemBean.HelplistBean item = (CommonProblemBean.HelplistBean) problemAdapter.getItem(position-1);
                String news_url = item.getNews_url();
                Intent intent=new Intent();
                intent.putExtra("news_url",news_url);
                intent.setClass(mContext, InforDetailActivity.class);
                startActivity(intent);

            }
        });
    }

    public void HTTP_Common(){
        OkHttpUtils.post().url(UrlConfig.Path.CommonHelp_URL).addParams("endid","999999999").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String s) {
                if (HTTP_GD.IsOrNot_Null(s))
                {
                    return;
                }
                problemBean = parseJsonToMessageBean(s);
                list = problemBean.getHelplist();
                if (list==null)
                {
                    list=new ArrayList<>();

                }
                listview_haiCarproblem.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listview_haiCarproblem.onRefreshComplete();
                        problemAdapter.reloadGridView(list,true);
                        if (list!=null)
                        {
                            if (list.size()>0)
                            {
                                listview_haiCarproblem.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);
            }
        });
    }
    public void HTTP_MoreCommon(){
        OkHttpUtils.post().url(UrlConfig.Path.CommonHelp_URL).addParams("endid",endid).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String s) {
                if (HTTP_GD.IsOrNot_Null(s))
                {
                    return;
                }
                problemBean = parseJsonToMessageBean(s);
                List<CommonProblemBean.HelplistBean> list_M=problemBean.getHelplist();
                if (list_M!=null)
                {
                    for (int i=0;i<list_M.size();i++)
                    {
                        list.add(list_M.get(i));
                    }
                }
                listview_haiCarproblem.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listview_haiCarproblem.onRefreshComplete();
                        problemAdapter.reloadGridView(list,true);
                    }
                }, 1500);
            }
        });
    }
    public CommonProblemBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        CommonProblemBean bean = gson.fromJson(jsonString, new TypeToken<CommonProblemBean>() {
        }.getType());
        return bean;
    }
}
