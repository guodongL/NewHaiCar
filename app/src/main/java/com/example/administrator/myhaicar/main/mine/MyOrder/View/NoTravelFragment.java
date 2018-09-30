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
import com.example.administrator.myhaicar.main.mine.MyOrder.Adapater.NoTravelAdapter;
import com.example.administrator.myhaicar.main.mine.MyOrder.Model.NoTravelBean;
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

/**
 * Created by Administrator on 2017/5/12.
 */

public class NoTravelFragment extends Fragment {
    private Context mContext;
    private PullToRefreshListView noOrder_listView;
    private NoTravelAdapter noTravelAdapter;
    private LinearLayout linear_Top;
    private List<NoTravelBean.ListinfoBean> listinfo=new ArrayList<>();
    private NoTravelBean noTravelBean;
    private String endid;
    private String usr_id;
    private Handler han=new Handler();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
         usr_id = PreferencesUtils.getString(mContext, "usr_id");
        noTravelAdapter=new NoTravelAdapter(mContext,listinfo);
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
        noOrder_listView.setAdapter(noTravelAdapter);
        noOrder_listView.setMode(PullToRefreshBase.Mode.BOTH);
        noOrder_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新完成方法
                HTTP_NOTrael();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (noTravelAdapter.getCount() != 0) {
                    int count = noTravelAdapter.getCount() - 1;
                    NoTravelBean.ListinfoBean item = (NoTravelBean.ListinfoBean) noTravelAdapter.getItem(count);
                    String order_id = item.getOrder_id();
                    endid = order_id;
                    HTTP_noMore();
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
    public  void  HTTP_NOTrael (){
        OkHttpUtils.post().url(UrlConfig.Path.MyOrder_URL).addParams("act","listing").addParams("user_id",usr_id).addParams("endid","999999999")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                noOrder_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noOrder_listView.onRefreshComplete();
                    }
                },1500);
                Toast.makeText(mContext,"网络连接失败",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String s) {
                if (HTTP_GD.IsOrNot_Null(s))
                {
                    return;
                }
                noTravelBean = parseJsonToMessageBean(s);
                listinfo = noTravelBean.getListinfo();
                if (listinfo==null)
                {
                    listinfo=new ArrayList<>();
                    noOrder_listView.onRefreshComplete();
                }
                noOrder_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noOrder_listView.onRefreshComplete();
                        noTravelAdapter.reloadGridView(listinfo,true);
                        if (listinfo!=null)
                        {
                            if (listinfo.size()>0)
                            {
                                noOrder_listView.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);

            }
        });
    }
    public void HTTP_noMore(){
        OkHttpUtils.post().url(UrlConfig.Path.MyOrder_URL).addParams("act","listing").addParams("user_id",usr_id).addParams("endid",endid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String s) {
                if (HTTP_GD.IsOrNot_Null(s))
                {
                    return;
                }
                noTravelBean = parseJsonToMessageBean(s);
                List<NoTravelBean.ListinfoBean> list_M=noTravelBean.getListinfo();
                if (list_M!=null)
                {
                    for (int i=0;i<list_M.size();i++)
                    {
                        listinfo.add(list_M.get(i));
                    }
                }
                noOrder_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noOrder_listView.onRefreshComplete();
                        noTravelAdapter.reloadGridView(listinfo,true);
                    }
                }, 1500);

            }
        });
    }
    public NoTravelBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        NoTravelBean bean = gson.fromJson(jsonString, new TypeToken<NoTravelBean>() {
        }.getType());
        return bean;
    }
}
