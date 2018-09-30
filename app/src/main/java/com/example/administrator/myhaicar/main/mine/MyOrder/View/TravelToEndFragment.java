package com.example.administrator.myhaicar.main.mine.MyOrder.View;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.mine.Account.This.Model.BankName;
import com.example.administrator.myhaicar.main.mine.MyOrder.Adapater.TravelToEndAdapter;
import com.example.administrator.myhaicar.main.mine.MyOrder.Model.TravelToEndBean;
import com.example.administrator.myhaicar.utils.HTTP_GD;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/15.
 */

public class TravelToEndFragment extends Fragment {
    private Context mContext;
    private PullToRefreshListView noOrder_listView;
    private LinearLayout linear_Top;
    private TravelToEndAdapter toEndAdapter;
    private List<TravelToEndBean.ListinfoBean> list=new ArrayList<>();
    private TravelToEndBean toEndBean;
    private String usr_id;
    private  String msg;
    private String endid;
    private Handler han=new Handler();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        EventBus.getDefault().register(this);
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        toEndAdapter=new TravelToEndAdapter(mContext,list);
        autoRefresh();
    }
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                noOrder_listView.setRefreshing(true);
            }
        },100);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.notravel_fragment,container,false);
        linear_Top= (LinearLayout) view.findViewById(R.id.linear_Top);
        noOrder_listView= (PullToRefreshListView) view.findViewById(R.id.noOrder_listView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        noOrder_listView.setAdapter(toEndAdapter);
        noOrder_listView.setMode(PullToRefreshBase.Mode.BOTH);
        noOrder_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新完成方法
               HTTP_ToEnd();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (toEndAdapter.getCount() != 0) {
                    int count = toEndAdapter.getCount() - 1;
                    TravelToEndBean.ListinfoBean item = (TravelToEndBean.ListinfoBean) toEndAdapter.getItem(count);
                    String order_id = item.getOrder_id();
                    endid = order_id;
                    HTTP_MoreEnd();
                }else {
                    noOrder_listView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            noOrder_listView.onRefreshComplete();
                        }
                    }, 1500);
                }
            }
        });
        linear_Top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noOrder_listView.getRefreshableView().smoothScrollToPosition(0);
            }
        });

    }
    public  void  HTTP_ToEnd (){
        OkHttpUtils.post().url(UrlConfig.Path.MyOrder_URL).addParams("act","listend_stay").addParams("user_id",usr_id).addParams("endid","999999999")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext,"网络连接失败",Toast.LENGTH_LONG).show();
                noOrder_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noOrder_listView.onRefreshComplete();
                    }
                },1500);
            }

            @Override
            public void onResponse(String s) {
                if (HTTP_GD.IsOrNot_Null(s))
                {
                    return;
                }
                toEndBean = parseJsonToMessageBean(s);
                list = toEndBean.getListinfo();
                if (list==null)
                {
                    list=new ArrayList<>();
                    noOrder_listView.onRefreshComplete();
                }
                noOrder_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noOrder_listView.onRefreshComplete();
                        toEndAdapter.reloadGridView(list,true);
                        if (list!=null)
                        {
                            if (list.size()>0)
                            {
                                noOrder_listView.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);

            }
        });
    }
    public void HTTP_MoreEnd(){
        OkHttpUtils.post().url(UrlConfig.Path.MyOrder_URL).addParams("act","listend_stay").addParams("user_id",usr_id).addParams("endid",endid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                noOrder_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noOrder_listView.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onResponse(String s) {
                if (HTTP_GD.IsOrNot_Null(s))
                {
                    return;
                }
                toEndBean = parseJsonToMessageBean(s);
                List<TravelToEndBean.ListinfoBean> list_M=toEndBean.getListinfo();
                if (list_M!=null)
                {
                    for (int i=0;i<list_M.size();i++)
                    {
                        list.add(list_M.get(i));
                    }
                }
                noOrder_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noOrder_listView.onRefreshComplete();
                        toEndAdapter.reloadGridView(list,true);
                    }
                }, 1500);

            }
        });
    }
    @Subscribe
    public void onEventMainThread(BankName event) {
        msg =event.getBankName();
        if (msg.equals("6")){
            autoRefresh();
        }

    }
    public TravelToEndBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        TravelToEndBean bean = gson.fromJson(jsonString, new TypeToken<TravelToEndBean>() {
        }.getType());
        return bean;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}
