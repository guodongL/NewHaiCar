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
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.MyCar.Adapater.CarTypeAdapter;
import com.example.administrator.myhaicar.main.mine.MyCar.Model.CarTypeBean;
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

public class CarTypeActivity extends SwipeBackToolBarActivity {
    private PullToRefreshListView carType_listView;
    private Context mContext=this;
    private ImageView toolBar_back;
    private ListView listView;
    private CarTypeAdapter typeAdapter;
    private List<CarTypeBean.BrandListBean> list=new ArrayList<>();
    private String id,name,car_id;
    private TextView textView_toolbar;
    private int pag=1;
    private Handler han=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_type);
        initView();
        initSet();
        initClick();
        autoRefresh();
    }
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                carType_listView.setRefreshing(true);
            }
        },100);
    }
    private void initView() {
        Intent intent=this.getIntent();
        id = intent.getStringExtra("brand_id");
        car_id = intent.getStringExtra("car_id");
        name = intent.getStringExtra("brand_name");
        textView_toolbar= (TextView) findViewById(R.id.textView_toolbar);
        carType_listView= (PullToRefreshListView) findViewById(R.id.carType_listView);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        typeAdapter=new CarTypeAdapter(mContext,list);
        textView_toolbar.setText(name);
    }
    private void initSet() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headView = inflater.inflate(R.layout.carname_listview_head, carType_listView, false);
        AbsListView.LayoutParams
                layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(layoutParams);
        listView = carType_listView.getRefreshableView();
        listView.addHeaderView(headView);
        carType_listView.setAdapter(typeAdapter);
    }



    private void initClick() {
        carType_listView.setMode(PullToRefreshBase.Mode.BOTH);
        carType_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                HTTP_CarType();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pag++;
                HTTP_CarMoreType();
                carType_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        carType_listView.onRefreshComplete();
                    }
                }, 1000);
            }
        });
        carType_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 CarTypeBean.BrandListBean item = (CarTypeBean.BrandListBean) typeAdapter.getItem(position-2);
                 String brand_id = item.getBrand_id();
                 String brand_name = item.getBrand_name();
                 Intent intent=new Intent();
                 intent.putExtra("brand_id",brand_id);
                 intent.putExtra("brand_name",brand_name);
                 intent.putExtra("car_id",car_id);
                 intent.putExtra("name",name);
                 intent.setClass(mContext,CarColorsActivity.class);
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

    public void HTTP_CarType(){
        OkHttpUtils.post().url(UrlConfig.Path.CarClass_URL).addParams("acts","class_list").addParams("pgs","1").addParams("brand_id",id)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                carType_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        carType_listView.onRefreshComplete();
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
                CarTypeBean typeBean = parseJsonToMessageBean(jsonString);
                list = typeBean.getBrand_list();
                if (list==null)
                {
                    list=new ArrayList<>();
                }
                carType_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        carType_listView.onRefreshComplete();
                        typeAdapter.reloadGridView(list,true);
                        if (list!=null)
                        {
                            if (list.size()>0)
                            {
                                carType_listView.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);

            }
        });
    }
    public void HTTP_CarMoreType(){
        OkHttpUtils.post().url(UrlConfig.Path.CarClass_URL).addParams("acts","class_list").addParams("pgs",pag+"").addParams("brand_id",id)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                carType_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        carType_listView.onRefreshComplete();
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
                CarTypeBean typeBean = parseJsonToMessageBean(jsonString);
                List<CarTypeBean.BrandListBean> list_M=typeBean.getBrand_list();
                if (list_M!=null)
                {
                    for (int i=0;i<list_M.size();i++)
                    {
                        list.add(list_M.get(i));
                    }
                }
                carType_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        carType_listView.onRefreshComplete();
                        typeAdapter.reloadGridView(list,true);
                    }
                }, 1000);

            }
        });
    }
    public CarTypeBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        CarTypeBean bean = gson.fromJson(jsonString, new TypeToken<CarTypeBean>() {
        }.getType());
        return bean;
    }
}
