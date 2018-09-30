package com.example.administrator.myhaicar.main.mine.Account.RedBag;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

public class RedBagActivity extends AppCompatActivity {
    private Context mContext=this;
    private ImageView toolBar_back;
    private RedBagAdapter redAdapter;
    private PullToRefreshListView listview_redBag;
    private RedPopuWindow redPopuWindow;
    private LinearLayout linearLayout_redPaget;
    private RelativeLayout activity_red_bag;
    private TextView textView_backLine;
    private TextView textView_redMoey;
    private RedPacketBean redPacketBean;
    private List<RedPacketBean.RedlistinfoBean> totaList=new ArrayList<>();
    private String usr_id;
    private int page=1;
    private String endid;
    private Handler han=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkinManager.getInstance().register(this);
        setContentView(R.layout.activity_red_bag);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView();

        initClick();
        autoRefresh();
    }
    //下拉刷新列表的方法

    private void initView() {
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        textView_redMoey= (TextView) findViewById(R.id.textView_redMoey);
        textView_backLine= (TextView) findViewById(R.id.textView_backLine);
        activity_red_bag= (RelativeLayout) findViewById(R.id.activity_red_bag);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        linearLayout_redPaget= (LinearLayout) findViewById(R.id.linearLayout_redPaget);
        listview_redBag= (PullToRefreshListView) findViewById(R.id.listview_redBag);
        redAdapter=new RedBagAdapter(mContext,totaList);

    }
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                listview_redBag.setRefreshing(true);
            }
        },200);
    }
    private void initClick() {
        listview_redBag.setAdapter(redAdapter);
        listview_redBag.setMode(PullToRefreshBase.Mode.BOTH);
        listview_redBag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RedPacketBean.RedlistinfoBean item = (RedPacketBean.RedlistinfoBean) redAdapter.getItem(position-1);
                String red_id = item.getRed_id();
                String types_red = item.getTypes();
                redPopuWindow=new RedPopuWindow(mContext,linearLayout_redPaget,textView_redMoey,red_id,types_red);
                redPopuWindow.setmItemOnClickListener(new RedPopuWindow.ItemOnClickListener() {
                    @Override
                    public void itemOnClickListener() {
                       autoRefresh();
                    }
                });
                redPopuWindow.showAtLocation(activity_red_bag, Gravity.CENTER,0,0);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp);
                redPopuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                redPopuWindow=null;
            }
        });
        textView_backLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_redPaget.setVisibility(View.INVISIBLE);
            }
        });

        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listview_redBag.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadNetworkData();
                listview_redBag.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listview_redBag.onRefreshComplete();
                    }
                }, 1500);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (redAdapter.getCount()!=0) {
                    int count = redAdapter.getCount() - 1;
                    RedPacketBean.RedlistinfoBean item = (RedPacketBean.RedlistinfoBean) redAdapter.getItem(count);
                    String order_id = item.getRed_id();
                    endid = order_id;
                    MoreData();
                    listview_redBag.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listview_redBag.onRefreshComplete();
                        }
                    }, 1500);
                }else {
                    listview_redBag.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listview_redBag.onRefreshComplete();
                        }
                    }, 1500);
                }
            }
        });
    }
    public void loadNetworkData() {
        OkHttpUtils.post().url(UrlConfig.Path.RedBag_URL).addParams("acts","lists").addParams("pgs","999999999").addParams("usr_id",usr_id)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                listview_redBag.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listview_redBag.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onResponse(String s) {
                String jsonString = s;
                if (HTTP_GD.IsOrNot_Null(jsonString)) {
                    return;
                }
               redPacketBean = parseJsonToMessageBean(jsonString);
                totaList = redPacketBean.getRedlistinfo();
                if (totaList == null) {
                    totaList = new ArrayList<>();

                }
                listview_redBag.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listview_redBag.onRefreshComplete();
                        redAdapter.reloadGridView(totaList,true);
                        if (totaList!=null)
                        {
                            if (totaList.size()>0)
                            {
                                listview_redBag.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);
            }
        });
    }
    public void MoreData(){
        OkHttpUtils.post().url(UrlConfig.Path.RedBag_URL).addParams("acts","lists").addParams("pgs",endid).addParams("usr_id",usr_id)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
                listview_redBag.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listview_redBag.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onResponse(String s) {
                String jsonString = s;
                if (HTTP_GD.IsOrNot_Null(jsonString)) {
                    return;
                }
                RedPacketBean redPacketBean = parseJsonToMessageBean(jsonString);
                List<RedPacketBean.RedlistinfoBean> list_M= redPacketBean.getRedlistinfo();
                if (list_M!=null)
                {
                    for (int i=0;i<list_M.size();i++)
                    {
                        totaList.add(list_M.get(i));
                    }
                }
                listview_redBag.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listview_redBag.onRefreshComplete();
                        redAdapter.reloadGridView(totaList,true);
                    }
                }, 1500);
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
    public RedPacketBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        RedPacketBean bean = gson.fromJson(jsonString, new TypeToken<RedPacketBean>() {
        }.getType());
        return bean;
    }
}
