package com.example.administrator.myhaicar.main.mine.MyCar.Acticity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.MyCar.Adapater.CarNameAdapter;
import com.example.administrator.myhaicar.main.mine.MyCar.Model.CarNameBean;
import com.example.administrator.myhaicar.utils.HTTP_GD;
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

public class CarNameActivity extends SwipeBackToolBarActivity {
    private PullToRefreshListView carName_listView;
    private Context mContext=this;
    private ImageView toolBar_back;
    private ListView listView;
    private CarNameAdapter nameAdapter;
    private int pag=1;
    private List<CarNameBean.BrandListBean> list=new ArrayList<>();
    private Handler han=new Handler();
    private String car_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_name);
        initView();
        initSet();
        initClick();
        autoRefresh();
    }
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                carName_listView.setRefreshing(true);
            }
        },200);
    }

    private void initView() {
        Intent intent=this.getIntent();
        car_id = intent.getStringExtra("car_id");
        carName_listView= (PullToRefreshListView) findViewById(R.id.carName_listView);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        nameAdapter=new CarNameAdapter(mContext,list);
    }
    private void initSet() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headView = inflater.inflate(R.layout.carname_listview_head, carName_listView, false);

        AbsListView.LayoutParams
                layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(layoutParams);
        SkinManager.getInstance().injectSkin(headView);
        listView = carName_listView.getRefreshableView();
        listView.addHeaderView(headView);
        carName_listView.setAdapter(nameAdapter);
    }



    private void initClick() {
        carName_listView.setMode(PullToRefreshBase.Mode.BOTH);
        carName_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                HTTP_CarName();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    pag++;
                HTTP_CarMoreName();
                carName_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        carName_listView.onRefreshComplete();
                    }
                }, 1000);
            }
        });
        carName_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarNameBean.BrandListBean item = (CarNameBean.BrandListBean) nameAdapter.getItem(position-2);
                String brand_id = item.getBrand_id();
                String brand_name = item.getBrand_name();
                Intent intent=new Intent();
                intent.putExtra("brand_id",brand_id);
                intent.putExtra("car_id",car_id);
                intent.putExtra("brand_name",brand_name);
                intent.setClass(mContext,CarTypeActivity.class);
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
    public void HTTP_CarName(){
        OkHttpUtils.post().url(UrlConfig.Path.CarClass_URL).addParams("acts","brand_list").addParams("pgs","1")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                carName_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        carName_listView.onRefreshComplete();
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
                CarNameBean nameBean = parseJsonToMessageBean(jsonString);
                list = nameBean.getBrand_list();
                if (list==null)
                {
                    list=new ArrayList<>();
                }
                carName_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        carName_listView.onRefreshComplete();
                        nameAdapter.reloadGridView(list,true);
                        if (list!=null)
                        {
                            if (list.size()>0)
                            {
                                carName_listView.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);

            }
        });
    }
    public void HTTP_CarMoreName(){
        OkHttpUtils.post().url(UrlConfig.Path.CarClass_URL).addParams("acts","brand_list").addParams("pgs",pag+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                carName_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        carName_listView.onRefreshComplete();
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
                CarNameBean nameBean = parseJsonToMessageBean(jsonString);
                List<CarNameBean.BrandListBean> list_M=nameBean.getBrand_list();
                if (list_M!=null)
                {
                    for (int i=0;i<list_M.size();i++)
                    {
                        list.add(list_M.get(i));
                    }
                }
                carName_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        carName_listView.onRefreshComplete();
                        nameAdapter.reloadGridView(list,true);
                    }
                }, 1000);

            }
        });
    }
    public CarNameBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        CarNameBean bean = gson.fromJson(jsonString, new TypeToken<CarNameBean>() {
        }.getType());
        return bean;
    }
}
