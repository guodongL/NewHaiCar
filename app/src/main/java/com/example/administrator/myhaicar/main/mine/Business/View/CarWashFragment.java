package com.example.administrator.myhaicar.main.mine.Business.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.mine.Business.Acticity.BusDetailActivity;
import com.example.administrator.myhaicar.main.mine.Business.Adapater.RankFragmentAdapter;
import com.example.administrator.myhaicar.main.mine.Business.Model.BusinessBean;
import com.example.administrator.myhaicar.utils.HTTP_GD;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.changeskin.SkinManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/6/15.
 */

public class CarWashFragment extends Fragment {
    private Context mContext;
    private RankFragmentAdapter rankAdapter;
    private PullToRefreshListView rankFragment_listView;
    private List<BusinessBean> list=new ArrayList<>();
    private Handler han=new Handler();
    private String usr_id;
    private String endid;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        rankAdapter=new RankFragmentAdapter(mContext,list);
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        autoRefresh();
    }
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                rankFragment_listView.setRefreshing(true);
            }
        },200);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.business_rankfragment,container,false);
        SkinManager.getInstance().injectSkin(view);
        rankFragment_listView= (PullToRefreshListView) view.findViewById(R.id.rankFragment_listView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rankFragment_listView.setAdapter(rankAdapter);
        rankFragment_listView.setMode(PullToRefreshBase.Mode.BOTH);
        rankFragment_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新完成方法
                HTTP_Business();
                rankFragment_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rankFragment_listView.onRefreshComplete();
                    }
                },1500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (rankAdapter.getCount() !=0) {
                    int count = rankAdapter.getCount() - 1;
                    BusinessBean item = (BusinessBean) rankAdapter.getItem(count);
                    String order_id = item.getBusi_id();
                    endid = order_id;
                    HTTP_MoreBusiness();
                    rankFragment_listView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rankFragment_listView.onRefreshComplete();
                        }
                    }, 1500);
                }else {
                    rankFragment_listView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rankFragment_listView.onRefreshComplete();
                        }
                    }, 1500);
                }
            }
        });
        rankFragment_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BusinessBean item = (BusinessBean) rankAdapter.getItem(position-1);
                String busi_id = item.getBusi_id();
                Intent intent=new Intent();
                intent.putExtra("busi_id",busi_id);
                intent.setClass(mContext, BusDetailActivity.class);
                startActivity(intent);
            }
        });
    }
    public  void  HTTP_Business (){
        OkHttpUtils.post().url(UrlConfig.Path.Business_URL).addParams("acts","lists").addParams("usr_id",usr_id).addParams("pgs","999999999")
                .addParams("sorts","0").addParams("classes","6").addParams("citys","沈阳").addParams("xyz","0,0").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                rankFragment_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rankFragment_listView.onRefreshComplete();
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
                list = parseJsonToMessageBean(s);
                if (list==null)
                {
                    list=new ArrayList<>();
                }
                rankFragment_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rankFragment_listView.onRefreshComplete();
                        rankAdapter.reloadGridView(list,true);
                        if (list!=null)
                        {
                            if (list.size()>0)
                            {
                                rankFragment_listView.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);

            }
        });
    }
    public  void  HTTP_MoreBusiness () {
        OkHttpUtils.post().url(UrlConfig.Path.Business_URL).addParams("acts", "lists").addParams("usr_id", usr_id).addParams("pgs", endid)
                .addParams("sorts", "0").addParams("classes", "6").addParams("citys", "沈阳").addParams("xyz","0,0").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                rankFragment_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rankFragment_listView.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onResponse(String s) {
                String jsonString = s;
                if (HTTP_GD.IsOrNot_Null(jsonString)) {
                    return;
                }
                list = parseJsonToMessageBean(s);
                List<BusinessBean> list_M=list;
                if (list_M!=null)
                {
                    for (int i=0;i<list_M.size();i++)
                    {
                        list.add(list_M.get(i));
                    }
                }
                rankFragment_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rankFragment_listView.onRefreshComplete();
                        rankAdapter.reloadGridView(list, true);
                    }
                }, 1500);

            }
        });
    }
    public List<BusinessBean> parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        List<BusinessBean> list = gson.fromJson(jsonString, new TypeToken<List<BusinessBean>>() {}.getType());
        return list;
    }
}
