package com.example.administrator.myhaicar.main.mine.MyCar.Acticity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.MyCar.Adapater.TeamDetailAdapter;
import com.example.administrator.myhaicar.main.mine.MyCar.Model.TeamDetailBean;
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

public class TeamDetailActivity extends SwipeBackToolBarActivity {
private Context mContext=this;
    private int peonum;
    private String usr_id;
    private String car_color,car_name,car_srt_id;
    private PullToRefreshListView teamDetail_listView;
    private List<TeamDetailBean.CarlistinfoBean> list=new ArrayList<>();
    private TeamDetailBean detailBean;
    private TeamDetailAdapter detailAdapter;
    private ImageView toolBar_back;
    private TextView textView_carType;
    private ListView listView;
    private Handler han=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);
        initView();
        initClick();
        autoRefresh();
    }
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                teamDetail_listView.setRefreshing(true);
            }
        },100);
    }
    private void initView() {
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        Intent intent=this.getIntent();
        car_color = intent.getStringExtra("car_color");
        car_name = intent.getStringExtra("car_name");
        car_srt_id = intent.getStringExtra("car_srt_id");
        String car_peonum = intent.getStringExtra("car_peonum");
        peonum = Integer.parseInt(car_peonum);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        teamDetail_listView= (PullToRefreshListView) findViewById(R.id.teamDetail_listView);
        detailAdapter=new TeamDetailAdapter(mContext,list);
    }

    private void initClick() {
           /*添加头部视图*/
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headView = inflater.inflate(R.layout.teamdetail_head, teamDetail_listView, false);
        textView_carType= (TextView) headView.findViewById(R.id.textView_carType);
        textView_carType.setText(car_name+"  "+car_color);
        AbsListView.LayoutParams
                layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(layoutParams);
        listView = teamDetail_listView.getRefreshableView();
        listView.addHeaderView(headView);
        teamDetail_listView.setAdapter(detailAdapter);
        teamDetail_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                HTTP_FleetCar();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        toolBar_back.setOnClickListener(new View.OnClickListener() {
          @Override
         public void onClick(View v) {
                finish();
             }
        });
         }
    private void HTTP_FleetCar(){
        OkHttpUtils.post().url(UrlConfig.Path.CarManager_URL).addParams("acts","plist").addParams("usr_id",usr_id).addParams("pgs","999999999").addParams("color",car_color)
                .addParams("carsid",car_srt_id).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String s) {
                String jsonString = s;
                if (HTTP_GD.IsOrNot_Null(jsonString)) {
                    teamDetail_listView.onRefreshComplete();
                    return;
                }
                detailBean = parseJsonToMessageBean(jsonString);
                list = detailBean.getCarlistinfo();
                if (list == null) {
                    list = new ArrayList<>();
                }
                teamDetail_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        detailAdapter.reloadGridView(list,true);
                        teamDetail_listView.onRefreshComplete();

                    }
                }, 1500);

            }
        });
    }
    public TeamDetailBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        TeamDetailBean bean = gson.fromJson(jsonString, new TypeToken<TeamDetailBean>() {
        }.getType());
        return bean;
    }
}
