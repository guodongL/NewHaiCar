package com.example.administrator.myhaicar.main.mine.MyCar.Acticity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.MyCar.Adapater.CarTeamAdapter;
import com.example.administrator.myhaicar.main.mine.MyCar.Adapater.RecyclerAdapter;
import com.example.administrator.myhaicar.main.mine.MyCar.Model.CarTeamBean;
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

public class CarTeamActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private RecyclerView rv;
    private RecyclerAdapter adapter;
    private List<CarTeamBean.CarlistinfoBean> list=new ArrayList<>();
    private CarTeamAdapter teamAdapter;
    private ImageView toolBar_back;
    private PullToRefreshListView carTeam_listView;
    private  CarTeamBean carTeamBean;
    private String usr_id;
    private Handler han=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclermain);
        initView();
        initSet();
       autoRefresh();
    }
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                carTeam_listView.setRefreshing(true);
            }
        },200);
    }
    private void initView() {
         usr_id = PreferencesUtils.getString(mContext, "usr_id");
        carTeam_listView= (PullToRefreshListView) findViewById(R.id.carTeam_listView);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        teamAdapter=new CarTeamAdapter(mContext,list);
        //rv = (RecyclerView) findViewById(R.id.rv);
    }
    private void initSet() {
        carTeam_listView.setAdapter(teamAdapter);
        carTeam_listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        carTeam_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新完成方法
                HTTP_Carteam();
                carTeam_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        carTeam_listView.onRefreshComplete();
                    }
                },1500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        carTeam_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarTeamBean.CarlistinfoBean  item =(CarTeamBean.CarlistinfoBean) teamAdapter.getItem(position-1);
                String car_color = item.getCar_color();
                String car_name = item.getCar_name();
                String car_srt_id = item.getCar_srt_id();
                int car_peonum = item.getCar_peonum();
                Intent intent=new Intent();
                intent.putExtra("car_color",car_color);
                intent.putExtra("car_name",car_name);
                intent.putExtra("car_srt_id",car_srt_id);
                intent.putExtra("car_peonum",car_peonum+"");
                intent.setClass(mContext,TeamDetailActivity.class);
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
    private void HTTP_Carteam() {
        OkHttpUtils.post().url(UrlConfig.Path.CarManager_URL).addParams("acts", "clist").addParams("usr_id",usr_id).addParams("pgs", "9999999")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                carTeam_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        carTeam_listView.onRefreshComplete();
                    }
                }, 1000);
            }
            @Override
            public void onResponse(String s) {
                String jsonString = s;
                if (HTTP_GD.IsOrNot_Null(jsonString)) {
                    return;
                }
                carTeamBean = parseJsonToMessageBean(jsonString);
                list = carTeamBean.getCarlistinfo();
                if (list == null) {
                    list = new ArrayList<>();
                }
                carTeam_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        carTeam_listView.onRefreshComplete();
                        teamAdapter.reloadGridView(list, true);
                    }
                }, 1500);
            }
        });

    }
    public CarTeamBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        CarTeamBean bean = gson.fromJson(jsonString, new TypeToken<CarTeamBean>() {
        }.getType());
        return bean;
    }
}
