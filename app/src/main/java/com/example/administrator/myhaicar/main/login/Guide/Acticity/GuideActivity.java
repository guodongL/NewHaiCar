package com.example.administrator.myhaicar.main.login.Guide.Acticity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.login.Guide.Adapater.WelcomeViewPagerAdapter;
import com.example.administrator.myhaicar.main.login.This.Acticity.LoginActivity;

import java.util.ArrayList;
import java.util.List;
/*
* 界面为欢迎引导界面
* */
public class GuideActivity extends AppCompatActivity {
    private Context mContext = this;
    //引导页，初始化viewpager
    private ViewPager viewPager_welcome;
    //下面的radiobutton，随着图片按钮改变
    private RadioGroup radioGroup_main;
    //为viewpager设置适配器，
    private WelcomeViewPagerAdapter adapter = null;
    //将图片放置到list集合中
    private List<ImageView> totalList = new ArrayList<>();
    //radiobutton放置到数组中
    private RadioButton[] arrRadioButton = null;
//获得资源文件中的图片
    private TypedArray array = null;
    //立即体验的点击按钮
    private Button button;
    //跳过图片的初始化
    private ImageView imageView_jump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //初始化数据，从资源文件中获取图片
        initData();

        initView();
//设置viewpager切换时下面小点的变化
        initDots();
    }
    private void initData() {
        array = getResources().obtainTypedArray(R.array.arrImages);
        for (int i = 0; i < array.length(); i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageDrawable(array.getDrawable(i));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            totalList.add(imageView);
        }
    }

    private void initView() {
        radioGroup_main = (RadioGroup) findViewById(R.id.radioGroup_main);
        viewPager_welcome = (ViewPager) findViewById(R.id.viewPager_welcome);
        button= (Button) findViewById(R.id.button_jump);
        imageView_jump= (ImageView) findViewById(R.id.imageView_jump);
        //立即体验点击跳转到登录页面
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,LoginActivity.class);
                startActivity(intent);
            }
        });
        //点击跳过跳转到登录页面
        imageView_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,LoginActivity.class);
                startActivity(intent);
            }
        });
        //初始化adatper
        adapter = new WelcomeViewPagerAdapter(mContext,totalList);
        viewPager_welcome.setAdapter(adapter);

        //viewPager添加监听器
        viewPager_welcome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==2){
                    button.setVisibility(View.VISIBLE);
                }else {
                    button.setVisibility(View.INVISIBLE);
                }
                arrRadioButton[position].setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initDots() {
        arrRadioButton = new RadioButton[array.length()];

        for (int i = 0; i < array.length(); i++) {
            arrRadioButton[i] = new RadioButton(mContext);
            //将RadioButton前方的圆圈修改成希望的图片
            arrRadioButton[i].setButtonDrawable(R.drawable.dots_selector);
            arrRadioButton[i].setPadding(15,0,15,0);
            radioGroup_main.addView(arrRadioButton[i]);
        }
        arrRadioButton[0].setChecked(true);

        //radioGroup设置监听器
        radioGroup_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < array.length(); i++) {
                    if (arrRadioButton[i].getId() == checkedId) {
                        viewPager_welcome.setCurrentItem(i);
                    }
                }
            }
        });
    }
}
