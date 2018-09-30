package com.example.administrator.myhaicar.main.mine.MyCar.Acticity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.SwipeBackToolBarActivity;
import com.example.administrator.myhaicar.main.mine.Account.This.View.ConfirmPopWindow;
import com.example.administrator.myhaicar.main.mine.MyCar.Adapater.CarColorsPagerAdapter;
import com.example.administrator.myhaicar.main.mine.MyCar.View.CarColorsFirstFragment;
import com.example.administrator.myhaicar.main.mine.MyCar.View.CarColorsThreeFragment;
import com.example.administrator.myhaicar.main.mine.MyCar.View.CarColorsTwoFragment;

import java.util.ArrayList;
import java.util.List;

public class CarColorsActivity extends SwipeBackToolBarActivity implements CarColorsFirstFragment.MyListener ,CarColorsThreeFragment.MyListener,CarColorsTwoFragment.MyListener{
    private Context mContext=this;
    private ViewPager viewPager_carColors;
    private RadioGroup radioGroup_carColors;
    private List<Fragment> list=new ArrayList<>();
    private CarColorsPagerAdapter pagerAdapter;
    private RadioButton[] arrRadioButton = null;
    private RelativeLayout objectView;
    private Object colorsSelect;
    private Button button_selsctCarColors;
    private ImageView toolBar_back;
    private RelativeLayout activity_car_colors;
    private String brand_id,brand_name,name,car_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_colors);
        initView();
        initData();
        initDots();
    }
    private void initView() {
        Intent intent=this.getIntent();
         brand_id = intent.getStringExtra("brand_id");
         brand_name = intent.getStringExtra("brand_name");
         name = intent.getStringExtra("name");
         car_id = intent.getStringExtra("car_id");
        activity_car_colors= (RelativeLayout) findViewById(R.id.activity_car_colors);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        button_selsctCarColors= (Button) findViewById(R.id.button_selsctCarColors);
        viewPager_carColors= (ViewPager) findViewById(R.id.viewPager_carColors);
        radioGroup_carColors= (RadioGroup) findViewById(R.id.radioGroup_carColors);

    }

    private void initData() {
            CarColorsFirstFragment firstFragment=new CarColorsFirstFragment();
            CarColorsTwoFragment twoFragment=new CarColorsTwoFragment();
            CarColorsThreeFragment threeFragment=new CarColorsThreeFragment();
            list.add(firstFragment);
            list.add(twoFragment);
            list.add(threeFragment);
        pagerAdapter=new CarColorsPagerAdapter(this.getSupportFragmentManager(),list);
        viewPager_carColors.setAdapter(pagerAdapter);
        viewPager_carColors.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                arrRadioButton[position].setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        button_selsctCarColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (colorsSelect==null) {
                    ConfirmPopWindow confirmPopWindow = new ConfirmPopWindow(mContext,"请选择车辆颜色","车辆颜色不能为空");
                    confirmPopWindow.showAtLocation(activity_car_colors, Gravity.CENTER, 0, 0);
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("colors",colorsSelect+"");
                    intent.putExtra("brand_id",brand_id);
                    intent.putExtra("car_id",car_id);
                    intent.setClass(mContext, CarPhotoActivity.class);
                    startActivity(intent);

                }
            }
        });
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //三个颜色组的回调方法
    @Override
    public void onClickOnClickListener(RelativeLayout view,String string) {

        setSelectColors(view,string);

    }
    private void setSelectColors(RelativeLayout view,String string)
    {   //颜色单选公用方法
        if (objectView==null)
        {
            objectView = view;
            RadioButton  button = (RadioButton) objectView.getChildAt(1);
            button.setChecked(true);
            colorsSelect=string;
        }
        else if(objectView != view)
        {
            RadioButton  buttonNew = (RadioButton) view.getChildAt(1);
            RadioButton  buttonOld = (RadioButton) objectView.getChildAt(1);
            buttonOld.setChecked(false);//!buttonOld.isChecked()
            buttonNew.setChecked(true);//!buttonNew.isChecked()
            objectView = view;
            colorsSelect=string;
        }
    }
    //
    private void initDots() {
        arrRadioButton = new RadioButton[list.size()];

        for (int i = 0; i <list.size(); i++) {
            arrRadioButton[i] = new RadioButton(mContext);
            //将RadioButton前方的圆圈修改成希望的图片
            arrRadioButton[i].setButtonDrawable(R.drawable.carcolors_guidselector);
            arrRadioButton[i].setPadding(15,0,15,0);
            radioGroup_carColors.addView(arrRadioButton[i]);
        }
        arrRadioButton[0].setChecked(true);

        //radioGroup设置监听器
        radioGroup_carColors.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < list.size(); i++) {
                    if (arrRadioButton[i].getId() == checkedId) {
                        viewPager_carColors.setCurrentItem(i);
                    }
                }
            }
        });
    }
}
