package com.example.administrator.myhaicar.main.order.This.Acticity;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.login.This.Acticity.LoginActivity;
import com.example.administrator.myhaicar.main.login.This.Model.LoginBean;
import com.example.administrator.myhaicar.main.mine.Account.MineDetail.MineDetailActivity;
import com.example.administrator.myhaicar.main.mine.Account.OrderCash.CommonProblemActivity;
import com.example.administrator.myhaicar.main.mine.Account.OrderCash.HaiCarHintActivity;
import com.example.administrator.myhaicar.main.mine.Account.This.Acticity.AccountActivity;
import com.example.administrator.myhaicar.main.mine.Account.This.Model.BankName;
import com.example.administrator.myhaicar.commond.ConfirmPopWindowThree;
import com.example.administrator.myhaicar.main.mine.Account.This.View.ConfirmPopWindow;
import com.example.administrator.myhaicar.main.mine.Business.Acticity.BusinessActivity;
import com.example.administrator.myhaicar.main.mine.Event.Acticity.WelfareActivity;
import com.example.administrator.myhaicar.main.mine.MyCar.Acticity.MyCarActivity;
import com.example.administrator.myhaicar.main.mine.MyOrder.Acticity.MyOrderActivity;
import com.example.administrator.myhaicar.main.mine.Setting.SettingActivity;
import com.example.administrator.myhaicar.main.order.SubmitCash.Acticity.CityActivity;
import com.example.administrator.myhaicar.main.order.SubmitCash.Acticity.InfomationActivity;
import com.example.administrator.myhaicar.main.order.SubmitCash.Model.CityName;
import com.example.administrator.myhaicar.main.order.This.Adapater.WeddingAdapter;
import com.example.administrator.myhaicar.main.order.This.Model.OrderBean;
import com.example.administrator.myhaicar.main.order.This.View.ConfirmHotCarPopu;
import com.example.administrator.myhaicar.main.order.This.View.ConfirmPopWindowApp;
import com.example.administrator.myhaicar.main.order.This.View.ConfirmWeddingPopu;
import com.example.administrator.myhaicar.utils.DragFloatActionButton;
import com.example.administrator.myhaicar.utils.HTTP_GD;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jauker.widget.BadgeView;
import com.zhy.changeskin.SkinManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

//出车抢单的主页面
public class OrderActivity extends AppCompatActivity {
    //初始化带刷新的列表listview
    private PullToRefreshListView wedding_listView;
    //初始化listview的适配器
    private WeddingAdapter weddingAdapter;
    private Context mContext=this;
    //浮动按钮，点击进行筛选
    private DragFloatActionButton fab;
    //初始化第一个弹出的自定义半透明界面，用于时间，价钱等的筛选
    private ConfirmWeddingPopu confirmWeddingPopu;
    //侧边抽屉控件的初始化
    private DrawerLayout activity_main;
    private CoordinatorLayout coord_main;
    //打开和关闭抽屉的按钮
    private ImageView toolBar_drawOpen;
    //侧面抽屉中我的账单列表，点击跳转到我的账单
    private LinearLayout textView_myAccount;
    //回到顶部的点击按钮，
    private LinearLayout linear_Top;
    private TimePickerView pvTime;
    private boolean flag=true;
    //城市选择点击按钮
    private TextView textView_city;
    //开通VIP的点击按钮，点击跳转到开通VIP界面
    private ImageView image_openVip;
    //初始化第二个弹出的自定义半透明界面，用于筛选热门车型和时间段的选择
    private ConfirmHotCarPopu confirmHotCarPopu;
    //头像设置
    private SimpleDraweeView head_image;
    private Handler han=new Handler();
    //我的订单
    private LinearLayout linear_myOrder;
    //车辆管理
    private LinearLayout linear_carManager;
    //设置
    private LinearLayout linear_setUp;
    private Toolbar toolBar;
    //嗨福利
    private LinearLayout linear_haiWelf;
    //嗨商家
    private LinearLayout linear_haiBusiness;
    //退出
    private Button button_back;
    private LinearLayout line_name;
    private TextView textView_nameID;
    private TextView textView_money;
    private LinearLayout line_balance;
    private LinearLayout linear_help;
    private ImageView image_infor;
    private  String usr_id;
    private String sorts;
    private  String msg;
    private String forme;
    private OrderBean horderBean;
    private String stTime;
    private String enTime;
    private String endid;
    private String carID;
    private BadgeView badgeView;
    private String usr_headimgurl;
    private String  app_version;
    private String un_news;
    private int usr_vip;
    private TextView textView_NoOrder;
    private boolean flagnoOreder=true;
    private RelativeLayout relative_first;
    private ImageView image_first;
    private List<OrderBean.HotlistinfoBean> orderBean=new ArrayList<>();
    private ImageView image_dop1,image_dop2,image_dop3,image_dop4,image_dop5,image_dop7;
    private String order,account,busi,acti,car,help;
    private String un_order,un_account,un_busi,un_acti,un_car,un_help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkinManager.getInstance().register(this);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        sorts="0";
        forme="0";
        carID="0";
        stTime="2000-01-01";
        enTime="2099-01-01";
        endid="999999999";
        flagnoOreder=true;
        initView();
       // HTTP_Order();
       // initTimePicker();
        initClick();
        autoRefresh();
    }


    private void initView() {
        EventBus.getDefault().register(this);

        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        String usr_headimgurl = PreferencesUtils.getString(mContext, "usr_headimgurl");
        boolean firstLogin = PreferencesUtils.getBoolean(mContext, "firstLogin");
        String un_news = PreferencesUtils.getString(mContext, "un_news");
        String usr_vip = PreferencesUtils.getString(mContext, "usr_vip");
        String usr_name = PreferencesUtils.getString(mContext, "usr_name");
        String usr_money = PreferencesUtils.getString(mContext, "usr_money");
        String usr_mime_id = PreferencesUtils.getString(mContext, "usr_mime_id");

        image_dop1= (ImageView) findViewById(R.id.image_dop1);
        image_dop2= (ImageView) findViewById(R.id.image_dop2);
        image_dop3= (ImageView) findViewById(R.id.image_dop3);
        image_dop4= (ImageView) findViewById(R.id.image_dop4);
        image_dop5= (ImageView) findViewById(R.id.image_dop5);
        image_dop7= (ImageView) findViewById(R.id.image_dop7);
        textView_NoOrder= (TextView) findViewById(R.id.textView_NoOrder);
        textView_money= (TextView) findViewById(R.id.textView_money);
        relative_first= (RelativeLayout) findViewById(R.id.relative_first);
        image_first= (ImageView) findViewById(R.id.image_first);
        head_image= (SimpleDraweeView) findViewById(R.id.head_image);
        textView_nameID= (TextView) findViewById(R.id.textView_nameID);
        image_infor= (ImageView) findViewById(R.id.image_infor);
        linear_help= (LinearLayout) findViewById(R.id.linear_help);
        line_name= (LinearLayout) findViewById(R.id.line_name);
        line_balance= (LinearLayout) findViewById(R.id.line_balance);
        button_back= (Button) findViewById(R.id.button_back);
        linear_haiBusiness= (LinearLayout) findViewById(R.id.linear_haiBusiness);
        linear_haiWelf= (LinearLayout) findViewById(R.id.linear_haiWelf);
        linear_setUp= (LinearLayout) findViewById(R.id.linear_setUp);
        linear_carManager= (LinearLayout) findViewById(R.id.linear_carManager);
        linear_myOrder= (LinearLayout) findViewById(R.id.linear_myOrder);
        wedding_listView= (PullToRefreshListView) findViewById(R.id.wedding_listView);
        fab= (DragFloatActionButton) findViewById(R.id.fab);
        textView_city= (TextView) findViewById(R.id.textView_city);
        toolBar_drawOpen= (ImageView) findViewById(R.id.toolBar_drawOpen);
        coord_main= (CoordinatorLayout) findViewById(R.id.coord_main);
        textView_myAccount= (LinearLayout) findViewById(R.id.textView_myAccount);
        linear_Top= (LinearLayout) findViewById(R.id.linear_Top);
        weddingAdapter=new WeddingAdapter(mContext,orderBean,usr_id);
        image_openVip= (ImageView) findViewById(R.id.image_openVip);
        activity_main= (DrawerLayout) findViewById(R.id.activity_main);
        head_image.setImageURI(Uri.parse(usr_headimgurl));
        if (!firstLogin){
            relative_first.setVisibility(View.VISIBLE);
            PreferencesUtils.putBoolean(mContext,"firstLogin",true);
        }
        image_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative_first.setVisibility(View.GONE);
            }
        });
        //String result = usr_money.substring(0, usr_money.indexOf("."));
        if (usr_money==null||usr_money.equals("")){
            textView_money.setText("余额"+"     "+"0.00");
        }else {
            textView_money.setText("余额"+"     "+usr_money);
        }
        textView_nameID.setText(usr_name+"   "+usr_mime_id);
        badgeView = new BadgeView(mContext);
        badgeView.setTargetView(image_infor);
        if (un_news!=null) {
            int i = Integer.parseInt(un_news);
            badgeView.setBadgeCount(i);
            badgeView.setTextSize(8);
            badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        }
        setVip();

    }
    //下拉刷新列表的方法
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                wedding_listView.setRefreshing(true);
            }
        },100);
    }

    @Override
    protected void onResume() {
        super.onResume();
        order = PreferencesUtils.getString(mContext, "un_order");
        account = PreferencesUtils.getString(mContext, "un_account");
        busi = PreferencesUtils.getString(mContext, "un_busi");
        acti = PreferencesUtils.getString(mContext, "un_acti");
        car = PreferencesUtils.getString(mContext, "un_car");
        help = PreferencesUtils.getString(mContext, "un_help");
        weddingAdapter.notifyDataSetChanged();
        HTTP_Myself();
    }
/* @Override
    protected void onResume() {
        super.onResume();
        autoRefresh();
    }*/

    private void initClick() {
        wedding_listView.setAdapter(weddingAdapter);
        image_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, InfomationActivity.class);
                startActivity(intent);
            }
        });
        linear_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, CommonProblemActivity.class);
                startActivity(intent);
                activity_main.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity_main.closeDrawer(GravityCompat.START);
                    }
                }, 700);
                if (un_help!=null&&!un_help.equals(help)){
                    PreferencesUtils.putString(mContext,"un_help",un_help);
                }
            }
        });
        line_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, AccountActivity.class);
                startActivity(intent);
                activity_main.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity_main.closeDrawer(GravityCompat.START);
                    }
                }, 700);
            }
        });
        line_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, MineDetailActivity.class);
                startActivity(intent);
                activity_main.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity_main.closeDrawer(GravityCompat.START);
                    }
                }, 700);
            }
        });
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putBoolean(mContext,"skin",false);
                Intent intent = new Intent();
                intent.setClass(mContext, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                button_back.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SkinManager.getInstance().changeSkin("day");
                    }
                }, 800);

                PreferencesUtils.putString(mContext,"usr_flag","NO");

            }
        });
        linear_haiBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, BusinessActivity.class);
                startActivity(intent);
                activity_main.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity_main.closeDrawer(GravityCompat.START);
                    }
                }, 700);
                if (un_busi!=null&&!un_busi.equals(busi)){
                    PreferencesUtils.putString(mContext,"un_busi",un_busi);
                }
            }
        });
        linear_haiWelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, WelfareActivity.class);
                startActivity(intent);
                activity_main.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity_main.closeDrawer(GravityCompat.START);
                    }
                }, 700);
                if (un_acti!=null&&!un_acti.equals(acti)){
                    PreferencesUtils.putString(mContext,"un_acti",un_acti);
                }
            }
        });
        linear_setUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, SettingActivity.class);
                startActivity(intent);
            }
        });
        linear_carManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, MyCarActivity.class);
                startActivity(intent);
                activity_main.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity_main.closeDrawer(GravityCompat.START);
                    }
                }, 700);
                if (un_car!=null&&!un_car.equals(car)){
                    PreferencesUtils.putString(mContext,"un_car",un_car);
                }
            }
        });
        linear_myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, MyOrderActivity.class);
                startActivity(intent);
                activity_main.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity_main.closeDrawer(GravityCompat.START);
                    }
                }, 700);
                if (un_order!=null&&!un_order.equals(order)){
                    PreferencesUtils.putString(mContext,"un_order",un_order);
                }
            }
        });
        head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, MineDetailActivity.class);
                startActivity(intent);
                activity_main.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity_main.closeDrawer(GravityCompat.START);
                    }
                }, 700);
            }
        });
        wedding_listView.setMode(PullToRefreshBase.Mode.BOTH);
        //列表刷新监听，上拉加载下拉刷新
        wedding_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新完成方法
                HTTP_Order();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (weddingAdapter.getCount() != 0) {
                    int count = weddingAdapter.getCount() - 1;
                    OrderBean.HotlistinfoBean item = (OrderBean.HotlistinfoBean) weddingAdapter.getItem(count);
                    String order_id = item.getOrder_id();
                    endid = order_id;
                    HTTP_MoreOrder();
                }else {
                    wedding_listView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wedding_listView.onRefreshComplete();
                        }
                    }, 1500);
                }
            }
        });

        textView_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, CityActivity.class);
                startActivity(intent);
            }
        });
        image_openVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext,"开通会员敬请期待");
                confirmPopWindowtwo.showAtLocation(activity_main, Gravity.CENTER, 0, 0);
            }
        });
        linear_Top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wedding_listView.getRefreshableView().smoothScrollToPosition(0);
            }
        });
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹出半透明view，进行筛选
                        confirmWeddingPopu = new ConfirmWeddingPopu(mContext,itemsOnClick);
                        //confirmWeddingPopu.showAtLocation(fab);
                            confirmWeddingPopu.showAtLocation(coord_main, Gravity.CENTER, 0, 0);

                }
            });
        toolBar_drawOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity_main.isDrawerOpen(GravityCompat.START)) {
                    activity_main.closeDrawer(GravityCompat.START);
                } else {
                    activity_main.openDrawer(GravityCompat.START);
                }
            }
        });
        //跳转到我的账户页面并且关闭抽屉
       textView_myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, AccountActivity.class);
                startActivity(intent);
                activity_main.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity_main.closeDrawer(GravityCompat.START);
                    }
                }, 700);
                if (un_account!=null&&!un_account.equals(account)){
                    PreferencesUtils.putString(mContext,"un_account",un_order);
                }
            }
        });
        /*confirmHotCarPopu=new ConfirmHotCarPopu(mContext,hotitemsOnClick);
        confirmHotCarPopu.setOnItemClickListener(new ConfirmHotCarPopu.OnItemClickListener() {
            @Override
            public void onItemClick(String carsTypeID, String startTime, String endTime) {
                confirmHotCarPopu.dismiss();
                Toast.makeText(mContext,carsTypeID+startTime+endTime,Toast.LENGTH_LONG).show();
            }
        });*/
    }
    //第一个半透明view中的各个按钮点击事件
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                //价格选择
                case R.id.image_chooseprice:
                    wedding_listView.setRefreshing(true);
                    sorts="2";
                    confirmWeddingPopu.dismiss();
                    flagnoOreder=false;
                    break;
                //时间选择
                case R.id.image_choosetime:
                    wedding_listView.setRefreshing(true);
                    sorts="3";
                    confirmWeddingPopu.dismiss();
                    flagnoOreder=false;
                    break;
                //筛选选择，弹出第二个半透明view
                case R.id.image_choose:
                    confirmWeddingPopu.dismiss();
                    confirmHotCarPopu=new ConfirmHotCarPopu(mContext,hotitemsOnClick);
                     confirmHotCarPopu.showAtLocation(coord_main, Gravity.CENTER, 0, 0);
                    confirmHotCarPopu.setOnItemClickListener(new ConfirmHotCarPopu.OnItemClickListener() {
                        @Override
                        public void onItemClick(String carsTypeID, String startTime, String endTime) {
                            confirmHotCarPopu.dismiss();
                            carID= carsTypeID == null || carsTypeID.equals("") ? "0" : carsTypeID;
                           stTime= startTime==null||startTime.equals("")?"2000-01-01":startTime;
                            enTime= endTime==null||endTime.equals("")?"2099-01-01":endTime;
                           if (!carID.equals("0")||!stTime.equals("2000-01-01")||!enTime.equals("2099-01-01")){
                                flagnoOreder=false;
                            }
                            wedding_listView.setRefreshing(true);
                        }
                    });
                    break;
                //与我相关
                case R.id.image_forme:
                    flagnoOreder=false;
                    wedding_listView.setRefreshing(true);
                    forme="1";
                    confirmWeddingPopu.dismiss();
                    break;
                //恢复默认
                case R.id.image_regain:
                    flagnoOreder=true;
                    wedding_listView.setRefreshing(true);
                    sorts="0";
                    forme="0";
                    carID="0";
                    stTime="2000-01-01";
                    enTime="2099-01-01";
                    endid="999999999";
                    confirmWeddingPopu.dismiss();
                    break;
                default:
                    break;
            }
            HTTP_Order();
        }
    };

    //第二个半透明view总的按钮点击事件
    private View.OnClickListener hotitemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //销毁按钮的点击事件
                case R.id.image_dismiss:
                    confirmHotCarPopu.dismiss();
                    confirmWeddingPopu = new ConfirmWeddingPopu(mContext,itemsOnClick);
                     confirmWeddingPopu.showAtLocation(coord_main, Gravity.CENTER, 0, 0);
                    break;
            }
        }
    };
    public void HTTP_Myself(){
        OkHttpUtils.post().url(UrlConfig.Path.Phone_URL).addParams("act","re_load").addParams("usr_id",usr_id).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String s) {
                if (HTTP_GD.IsOrNot_Null(s)) {
                    return;
                }
                LoginBean loginBean = MyparseJsonToMessageBean(s);
                usr_headimgurl = loginBean.getUsr_headimgurl();
                usr_vip = loginBean.getUsr_vip();
                un_news = loginBean.getUn_news();
                 un_order = loginBean.getUn_order();
                 un_account = loginBean.getUn_account();
                 un_busi = loginBean.getUn_busi();
                 un_car = loginBean.getUn_car();
                 un_acti = loginBean.getUn_acti();
                 un_help = loginBean.getUn_help();
                if (!un_order.equals(order)){
                    image_dop1.setVisibility(View.VISIBLE);
                }else {
                    image_dop1.setVisibility(View.GONE);
                }
                if (!un_account.equals(account)){
                    image_dop2.setVisibility(View.VISIBLE);
                }else {
                    image_dop2.setVisibility(View.GONE);
                }
                if (!un_car.equals(car)){
                    image_dop3.setVisibility(View.VISIBLE);
                }else {
                    image_dop3.setVisibility(View.GONE);
                }
                if (!un_acti.equals(acti)){
                    image_dop4.setVisibility(View.VISIBLE);
                }else {
                    image_dop4.setVisibility(View.GONE);
                }
                if (!un_busi.equals(busi)){
                    image_dop5.setVisibility(View.VISIBLE);
                }else {
                    image_dop5.setVisibility(View.GONE);
                }
                if (!un_help.equals(help)){
                    image_dop7.setVisibility(View.VISIBLE);
                }else {
                    image_dop7.setVisibility(View.GONE);
                }
                String usr_money = loginBean.getUsr_money();
                String app_version1 = PreferencesUtils.getString(mContext, "app_version");
                app_version = loginBean.getApp_version();
                String replace = app_version1.replace(".", "");
                String replaceNew = app_version.replace(".", "");
                int NewApp = Integer.parseInt(replaceNew);
                int OldApp = Integer.parseInt(replace);
                if (NewApp>OldApp){
                    ConfirmPopWindowApp confirmPopWindow = new ConfirmPopWindowApp(mContext, "提示", "请下载最新版本");
                    confirmPopWindow.showAtLocation(activity_main, Gravity.CENTER, 0, 0);
                }
                String usr_headimgurl = loginBean.getUsr_headimgurl();
                int news = Integer.parseInt(un_news);
                PreferencesUtils.putString(mContext,"usr_vip",usr_vip+"");
                PreferencesUtils.putString(mContext,"un_news",un_news);
               if (usr_money!=null||!usr_money.equals("0")) {
                   String result = usr_money.substring(0, usr_money.indexOf("."));
                   textView_money.setText("余额"+"     "+result);
               }else {
                   textView_money.setText("余额"+"     "+"0");
               }
                badgeView.setBadgeCount(news);
                badgeView.setTextSize(8);
                setVip();
                //头像
                PreferencesUtils.putString(mContext,"usr_headimgurl",usr_headimgurl);
                head_image.setImageURI(Uri.parse(usr_headimgurl));
               /*
                usr_vip = loginBean.getUsr_vip();
                usr_bond_c = loginBean.getUsr_bond_c();
                //保证金额
                PreferencesUtils.putString(mContext,"usr_bond_c",usr_bond_c);
                PreferencesUtils.putString(mContext,"usr_name",usr_name);
                //头像
                PreferencesUtils.putString(mContext,"usr_headimgurl",usr_headimgurl);
                //用户嗨车ID
                PreferencesUtils.putString(mContext,"usr_mime_id",usr_mime_id);
                //用户账户上的余额
                PreferencesUtils.putString(mContext,"usr_money",usr_money);
                PreferencesUtils.putString(mContext,"usr_vip",usr_vip+"");*/
            }
        });
    }
    public void setVip()
    {
        String usr_vip = PreferencesUtils.getString(mContext, "usr_vip");
        if (usr_vip.equals("1")){
            image_openVip.setImageResource(R.drawable.isvip);
        }else {
            image_openVip.setImageResource(R.drawable.openvip);
        }
    }
    public  void  HTTP_Order (){
        String text = textView_city.getText().toString();
        OkHttpUtils.post().url(UrlConfig.Path.Order_URL).addParams("act","index").addParams("datee",enTime).addParams("dates",stTime).addParams("usr_id",usr_id).addParams("endid","999999999").addParams("cartype",carID)
                .addParams("order_city",text).addParams("sorts",sorts).addParams("me",forme).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                wedding_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wedding_listView.onRefreshComplete();
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
                horderBean = parseJsonToMessageBean(jsonString);
               orderBean = horderBean.getHotlistinfo();
                if (orderBean==null)
                {
                    if (flagnoOreder){
                        textView_NoOrder.setVisibility(View.VISIBLE);
                        textView_NoOrder.setText("当前没有出车订单");
                    }else {
                        textView_NoOrder.setVisibility(View.VISIBLE);
                        textView_NoOrder.setText("您当前的搜索条件没有符合的订单");
                    }
                    orderBean=new ArrayList<>();

                }else {
                    textView_NoOrder.setVisibility(View.GONE);
                }
                wedding_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wedding_listView.onRefreshComplete();
                        weddingAdapter.reloadGridView(orderBean,true);
                        if (orderBean!=null)
                        {
                            if (orderBean.size()>0)
                            {
                                wedding_listView.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);
            }
        });
    }
    public  void HTTP_MoreOrder (){
        String text = textView_city.getText().toString();
        OkHttpUtils.post().url(UrlConfig.Path.Order_URL).addParams("act","index").addParams("datee","0").addParams("dates","0").addParams("usr_id",usr_id).addParams("endid",endid).addParams("cartype","0")
                .addParams("order_city",text).addParams("sorts",sorts).addParams("me",forme).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                wedding_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wedding_listView.onRefreshComplete();
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
                horderBean = parseJsonToMessageBean(jsonString);
                List<OrderBean.HotlistinfoBean> list_M=horderBean.getHotlistinfo();
                if (list_M!=null)
                {
                    for (int i=0;i<list_M.size();i++)
                    {
                        orderBean.add(list_M.get(i));
                    }
                }
                wedding_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wedding_listView.onRefreshComplete();
                        weddingAdapter.reloadGridView(orderBean,true);
                    }
                }, 1500);
            }
        });
    }
    //换肤的evenbus回调
    @Subscribe
    public void onEventMainThread(BankName event) {
       msg =event.getBankName();
        if (msg.equals("5")){
            autoRefresh();
        }

        weddingAdapter.reloadGridView(orderBean,true);
    }
    @Subscribe
    public void onEventMainThread(CityName event) {
        String cityName =event.CityName();
        textView_city.setText(cityName);
    }

  /*  private void initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2010,0,0);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2030,11,28);
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Button editText= (Button) v;
                editText.setText(getTime(date));
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setDate(selectedDate)
                .setRangDate(startDate,endDate)
                .setLabel("","","","","","")
                .build();

    }*/
    //可根据需要将时间自行截取数据显示
   /* private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }*/
    //控制抽屉开关
    @Override
    public void onBackPressed() {
        if (activity_main.isDrawerOpen(GravityCompat.START)) {
            activity_main.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
      /*  if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);*/
        return false;
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
        SkinManager.getInstance().unregister(this);
    }
    public OrderBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        OrderBean bean = gson.fromJson(jsonString, new TypeToken<OrderBean>() {
        }.getType());
        return bean;
    }
    public LoginBean MyparseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        LoginBean bean = gson.fromJson(jsonString, new TypeToken<LoginBean>() {
        }.getType());

        return bean;
    }

}
