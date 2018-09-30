package com.example.administrator.myhaicar.main.mine.MyCar.Acticity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.MySwipeMenuListView;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.commond.ConfirmPopWindowThree;
import com.example.administrator.myhaicar.main.mine.Account.This.View.ConfirmPopWindow;
import com.example.administrator.myhaicar.main.mine.MyCar.Adapater.CarManagerAdapter;
import com.example.administrator.myhaicar.main.mine.MyCar.Model.CarDeleBean;
import com.example.administrator.myhaicar.main.mine.MyCar.Model.CarManagerBean;
import com.example.administrator.myhaicar.main.mine.MyCar.Model.DelateOrAmendBean;
import com.example.administrator.myhaicar.main.mine.MyCar.Model.IsOrNoAddCarBean;
import com.example.administrator.myhaicar.utils.HTTP_GD;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MyCarActivity extends SwipeBackToolBarActivity {
    private Context mContext=this;
    private PullToRefreshScrollView car_scrollView;
    private MySwipeMenuListView swipeMenuListView_carActivity;
    private CarManagerAdapter managerAdapter;
    private ImageView add_car;
    private ImageView imaeView_history;
    private List<CarManagerBean.CarlistinfoBean> list=new ArrayList<>();
    private ImageView toolBar_back;
    private RelativeLayout activity_my_car;
    boolean skin;
    private String usr_id;
    private CarManagerBean carManagerBean;
    private RelativeLayout relative_addcar;
   private RelativeLayout relative_first;
    private ImageView image_first;
    private ImageView image_iKnow;
    private Handler han=new Handler();
    private  boolean firstAddCar;
    private RelativeLayout relative_nocar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car);
        initView();
        initClick();
        HTTP_CarManger();
        autoRefresh();
    }
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                car_scrollView.setRefreshing(true);
            }
        },100);
    }

    private void initView() {
       // EventBus.getDefault().register(this);
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        image_first= (ImageView) findViewById(R.id.image_first);
        image_iKnow= (ImageView) findViewById(R.id.image_iKnow);
        //firstAddCar = PreferencesUtils.getBoolean(mContext, "firstAddCar");
        relative_nocar= (RelativeLayout) findViewById(R.id.relative_nocar);
        activity_my_car= (RelativeLayout) findViewById(R.id.activity_my_car);
        relative_addcar= (RelativeLayout) findViewById(R.id.relative_addcar);
        relative_first= (RelativeLayout) findViewById(R.id.relative_first);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        imaeView_history= (ImageView) findViewById(R.id.imaeView_history);
        add_car= (ImageView) findViewById(R.id.add_car);
        skin = PreferencesUtils.getBoolean(mContext, "skin");
        car_scrollView= (PullToRefreshScrollView) findViewById(R.id.car_scrollView);
        swipeMenuListView_carActivity= (MySwipeMenuListView) findViewById(R.id.swipeMenuListView_carActivity);

        managerAdapter=new CarManagerAdapter(mContext,list);
        SwipeMenuCreator creator=new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                float density = getResources().getDisplayMetrics().density;
                int width_menu = (int) (70 * density);
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
                if (skin){
                    deleteItem.setBackground(new ColorDrawable(Color.parseColor("#FF060505")));
                }else {
                    deleteItem.setBackground(new ColorDrawable(Color.parseColor("#eeeeee")));
                }
                deleteItem.setWidth(width_menu);
                //deleteItem.setBackground(Color.parseColor("FF2D2D2D"));
                deleteItem.setTitle("删除");
                deleteItem.setIcon(R.drawable.cardelete);
                menu.addMenuItem(deleteItem);
            }
        };
        swipeMenuListView_carActivity.setMenuCreator(creator);


    }

    private void initClick() {
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imaeView_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,CarTeamActivity.class);
                startActivity(intent);
            }
        });
        add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.post().url(UrlConfig.Path.CarManager_URL).addParams("acts", "addture").addParams("usr_id",usr_id).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String s) {
                        IsOrNoAddCarBean isOrNoAddCarBean = AddparseJsonToMessageBean(s);
                        String ret_info = isOrNoAddCarBean.getRet_info();
                        String ret_prompt = isOrNoAddCarBean.getRet_prompt();
                        if (ret_prompt.equals("1")){
                            Intent intent=new Intent();
                            intent.setClass(mContext,CarNameActivity.class);
                            startActivity(intent);
                        }else {
                            ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext, ret_info);
                            confirmPopWindowtwo.showAtLocation(activity_my_car, Gravity.CENTER, 0, 0);
                        }
                    }
                });

            }
        });
        swipeMenuListView_carActivity.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, final int index) {
                switch (index) {
                    case 0:
                       String car_srt_id = list.get(position).getCar_srt_id();
                        String car_color = list.get(position).getCar_color();
                        OkHttpUtils.post().url(UrlConfig.Path.CarManager_URL).addParams("acts","dupture").addParams("usr_id",usr_id).addParams("car_srt_id",car_srt_id)
                                .addParams("car_color",car_color).build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {
                                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onResponse(String s) {
                                DelateOrAmendBean delateOrAmendBean = DeleteparseJsonToMessageBean(s);
                                String treatedok = delateOrAmendBean.getTreatedok();
                                String treatedok_info = delateOrAmendBean.getTreatedok_info();
                                if (treatedok.equals("1")){
                                    final String car_id = list.get(position).getCar_id();
                                    OkHttpUtils.post().url(UrlConfig.Path.CarManager_URL).addParams("acts", "dele").addParams("car_id", car_id)
                                            .build().execute(new StringCallback() {
                                        @Override
                                        public void onError(Call call, Exception e) {
                                            Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                                            car_scrollView .postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    car_scrollView.onRefreshComplete();
                                                }
                                            }, 1500);
                                        }
                                        @Override
                                        public void onResponse(String s) {
                                            CarDeleBean carDeleBean = delparseJsonToMessageBean(s);
                                            String treatedok = carDeleBean.getTreatedok();
                                            if (treatedok.equals("1")){
                                                list.remove(position);
                                                car_scrollView.setRefreshing(true);
                                                car_scrollView .postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        managerAdapter.reloadGridView(list,true);
                                                    }
                                                }, 1500);


                                            }else {
                                                Toast.makeText(mContext,"删除失败",Toast.LENGTH_SHORT).show();
                                            }
                                            //list.remove(position);
                                        }
                                    });
                                }else {
                                    ConfirmPopWindow confirmPopWindow = new ConfirmPopWindow(mContext, "提示", treatedok_info);
                                    confirmPopWindow.showAtLocation(activity_my_car, Gravity.CENTER, 0, 0);
                                }
                            }
                        });
                        break;
                }
                return false;
            }
        });
        swipeMenuListView_carActivity.setAdapter(managerAdapter);
        car_scrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        car_scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
              HTTP_CarManger();
                car_scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        car_scrollView.onRefreshComplete();
                    }
                }, 1500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            }
        });
    }
    public void HTTP_CarManger(){
        OkHttpUtils.post().url(UrlConfig.Path.CarManager_URL).addParams("acts", "lists").addParams("usr_id", usr_id).addParams("pgs", "1")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext,"网络获取失败",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(String s) {
                String jsonString = s;
                if (HTTP_GD.IsOrNot_Null(jsonString)) {
                    return;
                }
                carManagerBean = parseJsonToMessageBean(jsonString);
                list = carManagerBean.getCarlistinfo();

                if (list == null) {
                    list = new ArrayList<>();
                }else if (list.size()!=0)
                {
                    relative_nocar.setVisibility(View.GONE);
                    if (!PreferencesUtils.getBoolean(mContext, "firstAddCar")){
                        relative_addcar.setVisibility(View.VISIBLE);
                        PreferencesUtils.putBoolean(mContext,"firstAddCar",true);
                    }
                    image_iKnow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            relative_addcar.setVisibility(View.GONE);
                            relative_first.setVisibility(View.VISIBLE);
                        }
                    });
                    image_first.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            relative_first.setVisibility(View.GONE);
                        }
                    });
                }else if (list.size()==0){
                    relative_nocar.setVisibility(View.VISIBLE);
                }
                car_scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        managerAdapter.reloadGridView(list, true);
                        car_scrollView.onRefreshComplete();
                    }
                }, 1500);
            }
        });
    }
    public CarManagerBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        CarManagerBean bean = gson.fromJson(jsonString, new TypeToken<CarManagerBean>() {
        }.getType());
        return bean;
    }
    public IsOrNoAddCarBean AddparseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        IsOrNoAddCarBean bean = gson.fromJson(jsonString, new TypeToken<IsOrNoAddCarBean>() {
        }.getType());
        return bean;
    }
    public CarDeleBean delparseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        CarDeleBean bean = gson.fromJson(jsonString, new TypeToken<CarDeleBean>() {
        }.getType());
        return bean;
    }
    public DelateOrAmendBean DeleteparseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        DelateOrAmendBean bean = gson.fromJson(jsonString, new TypeToken<DelateOrAmendBean>() {
        }.getType());
        return bean;
    }
}
