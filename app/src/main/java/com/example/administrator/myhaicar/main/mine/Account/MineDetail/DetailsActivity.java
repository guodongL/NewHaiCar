package com.example.administrator.myhaicar.main.mine.Account.MineDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.ConfirmPopWindowThree;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.utils.HTTP_GD;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class DetailsActivity extends SwipeBackToolBarActivity {
    private LinearLayout line_hight,line_study,line_weight,line_sign,line_salary,line_interest,line_smoke
            ,line_drink,line_pet,line_married,line_eat,line_star,line_program,line_oneself;
    private Context mContext=this;
    private RelativeLayout activity_details;
    private TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8,textView9,
    textView10,textView11,textView12,textView13,textView14;
    private String string1,string2,string3,string4,string5,string6,string7,string8,string9,string10,string11,string12
            ,string13,string14;
    private String usr_id;
    private TextView textView_user_height,textView_user_education,textView_user_weight,textView_user_constellation,textView_user_yearly,textView_user_hobby
            ,textView_user_smoke,textView_user_drink,textView_user_pet,textView_user_marriage,textView_user_food,textView_user_star,
            textView_user_program,textView_user_introduce;
    private TextView textView_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();
        initClick();
        HTTP_Detail();

    }
    private void initView() {
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        textView_save= (TextView) findViewById(R.id.textView_save);
        activity_details= (RelativeLayout) findViewById(R.id.activity_details);
        textView_user_height= (TextView) findViewById(R.id.textView_user_height);
        textView_user_education= (TextView) findViewById(R.id.textView_user_education);
        textView_user_weight= (TextView) findViewById(R.id.textView_user_weight);
        textView_user_constellation= (TextView) findViewById(R.id.textView_user_constellation);
        textView_user_yearly= (TextView) findViewById(R.id.textView_user_yearly);
        textView_user_hobby= (TextView) findViewById(R.id.textView_user_hobby);
        textView_user_smoke= (TextView) findViewById(R.id.textView_user_smoke);
        textView_user_drink= (TextView) findViewById(R.id.textView_user_drink);
        textView_user_pet= (TextView) findViewById(R.id.textView_user_pet);
        textView_user_marriage= (TextView) findViewById(R.id.textView_user_marriage);
        textView_user_food= (TextView) findViewById(R.id.textView_user_food);
        textView_user_star= (TextView) findViewById(R.id.textView_user_star);
        textView_user_program= (TextView) findViewById(R.id.textView_user_program);
        textView_user_introduce= (TextView) findViewById(R.id.textView_user_introduce);
        line_hight= (LinearLayout) findViewById(R.id.line_hight);
        line_study= (LinearLayout) findViewById(R.id.line_study);
        line_weight= (LinearLayout) findViewById(R.id.line_weight);
        line_sign= (LinearLayout) findViewById(R.id.line_sign);
        line_salary= (LinearLayout) findViewById(R.id.line_salary);
        line_interest= (LinearLayout) findViewById(R.id.line_interest);
        line_smoke= (LinearLayout) findViewById(R.id.line_smoke);
        line_drink= (LinearLayout) findViewById(R.id.line_drink);
        line_pet= (LinearLayout) findViewById(R.id.line_pet);
        line_married= (LinearLayout) findViewById(R.id.line_married);
        line_eat= (LinearLayout) findViewById(R.id.line_eat);
        line_star= (LinearLayout) findViewById(R.id.line_star);
        line_program= (LinearLayout) findViewById(R.id.line_program);
        line_oneself= (LinearLayout) findViewById(R.id.line_oneself);
        textView1= (TextView) findViewById(R.id.textView1);
        textView2= (TextView) findViewById(R.id.textView2);
        textView3= (TextView) findViewById(R.id.textView3);
        textView4= (TextView) findViewById(R.id.textView4);
        textView5= (TextView) findViewById(R.id.textView5);
        textView6= (TextView) findViewById(R.id.textView6);
        textView7= (TextView) findViewById(R.id.textView7);
        textView8= (TextView) findViewById(R.id.textView8);
        textView9= (TextView) findViewById(R.id.textView9);
        textView10= (TextView) findViewById(R.id.textView10);
        textView11= (TextView) findViewById(R.id.textView11);
        textView12= (TextView) findViewById(R.id.textView12);
        textView13= (TextView) findViewById(R.id.textView13);
        textView14= (TextView) findViewById(R.id.textView14);
        string1 = textView1.getText().toString();
        string2 = textView2.getText().toString();
        string3 = textView3.getText().toString();
        string4 = textView4.getText().toString();
        string5 = textView5.getText().toString();
        string6 = textView6.getText().toString();
        string7= textView7.getText().toString();
        string8 = textView8.getText().toString();
        string9 = textView9.getText().toString();
        string10 = textView10.getText().toString();
        string11 = textView11.getText().toString();
        string12 = textView12.getText().toString();
        string13 = textView13.getText().toString();
        string14 = textView14.getText().toString();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
        if(requestCode == 1000 )
        {
            String result_value = data.getStringExtra("result");
            textView_user_height.setText(result_value);
        }else if (requestCode==1001){
            String result_value = data.getStringExtra("result");
            textView_user_education.setText(result_value);
        }else if (requestCode==1002){
            String result_value = data.getStringExtra("result");
            textView_user_weight.setText(result_value);
        }else if (requestCode==1003){
            String result_value = data.getStringExtra("result");
            textView_user_constellation.setText(result_value);
        }else if (requestCode==1004){
            String result_value = data.getStringExtra("result");
            textView_user_yearly.setText(result_value);
        }else if (requestCode==1005){
            String result_value = data.getStringExtra("result");
            textView_user_hobby.setText(result_value);
        }else if (requestCode==1006){
            String result_value = data.getStringExtra("result");
            textView_user_smoke.setText(result_value);
        }else if (requestCode==1007){
            String result_value = data.getStringExtra("result");
            textView_user_drink.setText(result_value);
        }else if (requestCode==1008){
            String result_value = data.getStringExtra("result");
            textView_user_pet.setText(result_value);
        }else if (requestCode==1009){
            String result_value = data.getStringExtra("result");
            textView_user_marriage.setText(result_value);
        }else if (requestCode==1010){
            String result_value = data.getStringExtra("result");
            textView_user_food.setText(result_value);
        }else if (requestCode==1011){
            String result_value = data.getStringExtra("result");
            textView_user_star.setText(result_value);
        }else if (requestCode==1012){
            String result_value = data.getStringExtra("result");
            textView_user_program.setText(result_value);
        }else if (requestCode==1013){
            String result_value = data.getStringExtra("result");
            textView_user_introduce.setText(result_value);
        }
        }
    }

    private void initClick() {
        line_hight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("hight",string1);
                intent.setClass(mContext,DetailsNextActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        line_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("hight",string2);
                intent.setClass(mContext,DetailsNextActivity.class);
                startActivityForResult(intent, 1001);
            }
        });
        line_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("hight",string3);
                intent.setClass(mContext,DetailsNextActivity.class);
                startActivityForResult(intent, 1002);
            }
        });
        line_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("hight",string4);
                intent.setClass(mContext,DetailsNextActivity.class);
                startActivityForResult(intent, 1003);
            }
        });
        line_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("hight",string5);
                intent.setClass(mContext,DetailsNextActivity.class);
                startActivityForResult(intent, 1004);
            }
        });
        line_interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("hight",string6);
                intent.setClass(mContext,DetailsNextActivity.class);
                startActivityForResult(intent, 1005);
            }
        });
        line_smoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("hight",string7);
                intent.setClass(mContext,DetailsNextActivity.class);
                startActivityForResult(intent, 1006);
            }
        });
        line_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("hight",string8);
                intent.setClass(mContext,DetailsNextActivity.class);
                startActivityForResult(intent, 1007);
            }
        });
        line_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("hight",string9);
                intent.setClass(mContext,DetailsNextActivity.class);
                startActivityForResult(intent, 1008);
            }
        });
        line_married.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("hight",string10);
                intent.setClass(mContext,DetailsNextActivity.class);
                startActivityForResult(intent, 1009);
            }
        });
        line_eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("hight",string11);
                intent.setClass(mContext,DetailsNextActivity.class);
                startActivityForResult(intent, 1010);
            }
        });
        line_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("hight",string12);
                intent.setClass(mContext,DetailsNextActivity.class);
                startActivityForResult(intent, 1011);
            }
        });
        line_program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("hight",string13);
                intent.setClass(mContext,DetailsNextActivity.class);
                startActivityForResult(intent, 1012);
            }
        });
        line_oneself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("hight",string14);
                intent.setClass(mContext,DetailsNextActivity.class);
                startActivityForResult(intent, 1013);
            }
        });
        textView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_height = textView_user_height.getText().toString();
                String user_education = textView_user_education.getText().toString();
                String user_weight = textView_user_weight.getText().toString();
                String user_constellation = textView_user_constellation.getText().toString();
                String user_yearly = textView_user_yearly.getText().toString();
                String user_hobby = textView_user_hobby.getText().toString();
                String user_smoke = textView_user_smoke.getText().toString();
                String user_drink = textView_user_drink.getText().toString();
                String user_pet = textView_user_pet.getText().toString();
                String user_marriage = textView_user_marriage.getText().toString();
                String user_food = textView_user_food.getText().toString();
                String user_star = textView_user_star.getText().toString();
                String user_program = textView_user_program.getText().toString();
                String user_introduce = textView_user_introduce.getText().toString();
        OkHttpUtils.post().url(UrlConfig.Path.PersonalDetail_URL).addParams("acts","updad").addParams("usr_id",usr_id).addParams("user_height",user_height).addParams("user_education",user_education).addParams("user_weight",user_weight).addParams("user_constellation",user_constellation)
        .addParams("user_yearly",user_yearly).addParams("user_hobby",user_hobby).addParams("user_smoke",user_smoke).addParams("user_drink",user_drink).addParams("user_pet",user_pet).addParams("user_marriage",user_marriage).addParams("user_food",user_food).addParams("user_star",user_star).addParams("user_program",user_program).addParams("user_introduce",user_introduce)
                .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext,"网络获取失败",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String s) {
                        ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext, "保存成功");
                        confirmPopWindowtwo.showAtLocation(activity_details, Gravity.CENTER, 0, 0);
                    }
                });
            }
        });
    }
    public void HTTP_Detail(){
        OkHttpUtils.post().url(UrlConfig.Path.PersonalDetail_URL).addParams("usr_id",usr_id).addParams("acts","views").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext,"网络获取失败",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String s) {
                String jsonString=s;
                if (HTTP_GD.IsOrNot_Null(jsonString))
                {
                    return;
                }
                PersonalBean personalBean = MyparseJsonToMessageBean(jsonString);
                String user_height = personalBean.getUser_height();
                String user_education = personalBean.getUser_education();
                String user_weight = personalBean.getUser_weight();
                String user_constellation = personalBean.getUser_constellation();
                String user_yearly = personalBean.getUser_yearly();
                String user_hobby = personalBean.getUser_hobby();
                String user_smoke = personalBean.getUser_smoke();
                String user_drink = personalBean.getUser_drink();
                String user_pet = personalBean.getUser_pet();
                String user_marriage = personalBean.getUser_marriage();
                String user_food = personalBean.getUser_food();
                String user_star = personalBean.getUser_star();
                String user_program = personalBean.getUser_program();
                String user_introduce = personalBean.getUser_introduce();
                textView_user_height.setText(user_height);
                textView_user_education.setText(user_education);
                textView_user_weight.setText(user_weight);
                textView_user_constellation.setText(user_constellation);
                textView_user_yearly.setText(user_yearly);
                textView_user_hobby.setText(user_hobby);
                textView_user_smoke.setText(user_smoke);
                textView_user_drink.setText(user_drink);
                textView_user_pet.setText(user_pet);
                textView_user_marriage.setText(user_marriage);
                textView_user_food.setText(user_food);
                textView_user_star.setText(user_star);
                textView_user_program.setText(user_program);
                textView_user_introduce.setText(user_introduce);
            }
        });
    }
    public PersonalBean MyparseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        PersonalBean bean = gson.fromJson(jsonString, new TypeToken<PersonalBean>() {
        }.getType());
        return bean;
    }
}
