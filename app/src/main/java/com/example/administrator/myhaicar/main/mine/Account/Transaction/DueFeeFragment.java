package com.example.administrator.myhaicar.main.mine.Account.Transaction;

import android.content.Context;
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
 * Created by Administrator on 2017/6/5.
 */

public class DueFeeFragment extends Fragment {
    private Context mContext;
    private PullToRefreshListView trading_listView;
    private TransactionAdapter tradingsAdapter;
    private List<TradingBean.RecolistinfoBean> totaList=new ArrayList<>();
    private int page=1;
    private String usr_id;
    private Handler han=new Handler();
    private String endid;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        tradingsAdapter=new TransactionAdapter(mContext,totaList);
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        autoRefresh();
    }
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                trading_listView.setRefreshing(true);
            }
        },200);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.allfragment,container,false);
        SkinManager.getInstance().injectSkin(view);
        trading_listView= (PullToRefreshListView) view.findViewById(R.id.trading_listView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        trading_listView.setAdapter(tradingsAdapter);
        trading_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        trading_listView.setMode(PullToRefreshBase.Mode.BOTH);
        trading_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新完成方法
                HTTP_TraAllOrder();
                trading_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        trading_listView.onRefreshComplete();
                    }
                },1500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Toast.makeText(mContext,totaList+"",Toast.LENGTH_LONG).show();
                    if (tradingsAdapter.getCount()!=0){
                    int count = tradingsAdapter.getCount() - 1;
                    TradingBean.RecolistinfoBean item = (TradingBean.RecolistinfoBean) tradingsAdapter.getItem(count);
                    String order_id = item.getReco_id();
                    endid = order_id;
                    HTTP_More_TraAllOrder();
                    trading_listView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            trading_listView.onRefreshComplete();
                        }
                    }, 1500);
                }else {
                    trading_listView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            trading_listView.onRefreshComplete();
                        }
                    }, 1500);
                }
            }
        });
    }
    public  void  HTTP_TraAllOrder ()
    {
        page=1;
        OkHttpUtils.post().url(UrlConfig.Path.Transaction_URL).addParams("acts","usermoney").addParams("usr_id",usr_id).addParams("pgs","9999999999")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                trading_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        trading_listView.onRefreshComplete();
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
                TradingBean tradingBean = parseJsonToMessageBean(jsonString);
                totaList=tradingBean.getRecolistinfo();
                if (totaList==null)
                {
                    totaList=new ArrayList<>();
                }
                trading_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        trading_listView.onRefreshComplete();
                        tradingsAdapter.reloadGridView(totaList,true);
                        if (totaList!=null)
                        {
                            if (totaList.size()>0)
                            {
                                trading_listView.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);

            }
        });
    }
    public  void  HTTP_More_TraAllOrder ()
    {
        OkHttpUtils.post().url(UrlConfig.Path.Transaction_URL).addParams("acts","usermoney").addParams("usr_id",usr_id).addParams("pgs",endid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                trading_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        trading_listView.onRefreshComplete();
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
                TradingBean tradingBean = parseJsonToMessageBean(jsonString);
                List<TradingBean.RecolistinfoBean> list_M=tradingBean.getRecolistinfo();
                if (list_M!=null)
                {
                    for (int i=0;i<list_M.size();i++)
                    {
                        totaList.add(list_M.get(i));
                    }
                }
                trading_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        trading_listView.onRefreshComplete();
                        tradingsAdapter.reloadGridView(totaList,true);
                    }
                }, 1500);
            }
        });
    }
    public TradingBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        TradingBean bean = gson.fromJson(jsonString, new TypeToken<TradingBean>() {
        }.getType());
        return bean;
    }
}
