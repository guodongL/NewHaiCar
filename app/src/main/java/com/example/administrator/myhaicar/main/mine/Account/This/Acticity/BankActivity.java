package com.example.administrator.myhaicar.main.mine.Account.This.Acticity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.Account.This.Adapater.BankNameAdapter;
import com.example.administrator.myhaicar.main.mine.Account.This.Model.BankName;
import com.example.administrator.myhaicar.main.mine.Account.This.Model.BanksNameBean;
import com.example.administrator.myhaicar.utils.HTTP_GD;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class BankActivity extends SwipeBackToolBarActivity {
private Context mContext=this;
    private ListView listView_bank;
    private ImageView toolBar_back;
    private List<String> banks=new ArrayList<>();
    private BankNameAdapter adapter;
    private ItemOnClickListener mItemOnClickListener;
    public interface ItemOnClickListener{
        public void itemOnClickListener(String bank);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        initView();
        initData();
        HTTP_Bank();
    }

    private void initView() {
        listView_bank = (ListView) findViewById(R.id.list_bank);
        toolBar_back = (ImageView) findViewById(R.id.toolBar_back);
        adapter = new BankNameAdapter(mContext, banks);
    }
    private void initData() {
      /*  final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,R.layout.bank_listview_item,strings );*/
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headView = inflater.inflate(R.layout.bank_listview_head, listView_bank, false);
        AbsListView.LayoutParams
                layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(layoutParams);
        listView_bank.addHeaderView(headView);
        listView_bank.setAdapter(adapter);
        listView_bank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String string = parent.getAdapter().getItem(position).toString();
                EventBus.getDefault().post(new BankName(string));
                finish();
            }
        });
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public  void  HTTP_Bank (){
        OkHttpUtils.post().url(UrlConfig.Path.BANK_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String s) {
                String jsonString=s;
                if (HTTP_GD.IsOrNot_Null(jsonString))
                {
                    return;
                }
                BanksNameBean banksNameBean = parseJsonToMessageBean(jsonString);
                 banks = banksNameBean.getBanks();
                if (banks==null)
                {
                    banks=new ArrayList<>();
                }
                adapter.reloadGridView(banks,true);
            }
        });
    }
    public BanksNameBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        BanksNameBean bean = gson.fromJson(jsonString, new TypeToken<BanksNameBean>() {
        }.getType());
        return bean;
    }

}
