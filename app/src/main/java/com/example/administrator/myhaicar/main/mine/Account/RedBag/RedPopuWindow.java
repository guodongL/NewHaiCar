package com.example.administrator.myhaicar.main.mine.Account.RedBag;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * Created by Administrator on 2017/3/7.
 */

public class RedPopuWindow extends PopupWindow {
    private Context mContext;
    private ImageView image_redUp, imageView_down;
    private ImageView imageView_open;
    private RelativeLayout relativeLayout_red;
    final List <Integer> list_ani = new ArrayList<Integer>();
    private View mMenuView;
    private LinearLayout layout_pop;
    private boolean boo_HTTP;
    private TextView text_pop;
    private String pop_Money;
    private boolean boo_click;
    private String usr_id;
    private RedBagActivity redActivity;
    private OpenRedBean openRedBean;
    private String types;
    private String id;
    //acts=opens&red_id=17664&usr_id=8
    int count = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (count < 20) {
                imageView_open.setImageResource(list_ani.get(count));
                count++;
                mHandler.sendEmptyMessageDelayed(0, 50);
            } else {
                if (count == 20) {
                    if (boo_click) {
                        OkHttpUtils.post().url(UrlConfig.Path.RedBag_URL).addParams("acts", "opens").addParams("types",types).addParams("red_id", id).addParams("usr_id", usr_id).build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {
                                Toast.makeText(mContext, "网络异常，请稍后重试", Toast.LENGTH_LONG).show();
                                count = 88;
                            }

                            @Override
                            public void onResponse(String s) {
                                String jsonString = s;
                                if (HTTP_GD.IsOrNot_Null(jsonString)) {
                                    Toast.makeText(mContext, "网络异常，请稍后重试", Toast.LENGTH_LONG).show();
                                    count = 88;
                                    return;
                                }
                                openRedBean = parseJsonToMessageBean(jsonString);
                                String info = openRedBean.getMoney();
                                if (Integer.valueOf(info) > 0) {
                                    mItemOnClickListener.itemOnClickListener();
                                    count = 99;
                                    if (openRedBean == null) {
                                        openRedBean = new OpenRedBean();
                                    }
                                    String money = openRedBean.getMoney();
                                    pop_Money = Integer.valueOf(money).toString();
                                } else {
                                    Toast.makeText(mContext, "网络异常，请稍后重试", Toast.LENGTH_LONG).show();
                                    count = 88;
                                }
                            }
                        });
                        boo_click = false;
                    }
                  /*      Handler handler=new Handler();
                        //开启子线程延时2秒跳转页面
                       handler.postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               count=99;
                               boo_click=false;
                           }
                       },500);*/

                    count = 0;
                    mHandler.sendEmptyMessageDelayed(0, 50);
                } else if (count == 99) {
                    //打开成功
                    imageView_open.setVisibility(View.INVISIBLE);
                    anim_view();
                } else {
                    Toast.makeText(mContext, "打开失败", Toast.LENGTH_SHORT).show();
                    imageView_open.setImageResource(list_ani.get(0));
                    //打开失败
                }
            }
        }
    };

    private ItemOnClickListener mItemOnClickListener;

    public void setmItemOnClickListener(ItemOnClickListener listener){
        this.mItemOnClickListener = listener;
    }

    public interface ItemOnClickListener{
        public void itemOnClickListener();
    }

    public RedPopuWindow(final Context mContext , LinearLayout popLayout , TextView popText,String red_id,String types) {
        super(mContext);
        this.types = types;
        alloc_list();
        boo_HTTP=true;
        this.id=red_id;
        text_pop=popText;
        layout_pop=popLayout;
        this.mContext = mContext;
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.red_packet_anim, null);
        imageView_open = (ImageView) mMenuView.findViewById(R.id.imageView_open);
        imageView_open.setImageResource(R.drawable.redbag_1);
        image_redUp = (ImageView) mMenuView.findViewById(R.id.image_redUp);
        relativeLayout_red = (RelativeLayout) mMenuView.findViewById(R.id.relativeLayout_red);
        imageView_down = (ImageView) mMenuView.findViewById(R.id.imageView_down);
        imageView_down.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        image_redUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        imageView_open.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (boo_HTTP)
                {
                    boo_click=true;
                    boo_HTTP=false;
                    count=0;
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mHandler.sendEmptyMessage(0);
                        }
                    });
                }
            }
        });

        //设置按钮监听
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.anim_popuwindow_red);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.relativeLayout_red).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


    public void anim_view()
    {
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(image_redUp, "translationY", 0F, -860F);//Y轴平移旋转
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(image_redUp, "scaleX", 1.0F, 1.5F);//360度旋转
        AnimatorSet set = new AnimatorSet();
        set.play(animator3).with(animator2);
        set.setDuration(300);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dismiss();
                imageView_open.setVisibility(View.VISIBLE);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(image_redUp, "translationY", -860F,0F );//Y轴平移旋转
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(image_redUp, "scaleX", 1.5F, 1.0F);//360度旋转
                AnimatorSet set = new AnimatorSet();
                set.play(animator3).with(animator2);
                set.setDuration(300);
                set.start();
                boo_HTTP=true;
                text_pop.setText(pop_Money);
                layout_pop.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageView_down, "translationY", 0F, 860F);//Y轴平移旋转
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(imageView_down, "scaleX", 1.0F, 1.5F);//360度旋转
        AnimatorSet set1 = new AnimatorSet();
        set1.play(animator1).with(animator4);
        set1.setDuration(300);
        set1.start();
        set1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageView_down, "translationY", 860F, 0F);//Y轴平移旋转
                ObjectAnimator animator4 = ObjectAnimator.ofFloat(imageView_down, "scaleX", 1.5F, 1.0F);//360度旋转
                AnimatorSet set1 = new AnimatorSet();
                set1.play(animator1).with(animator4);
                set1.setDuration(300);
                set1.start();
                dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    public void alloc_list ()
    {
        list_ani.add(R.drawable.redbag_1);
        list_ani.add(R.drawable.redbag_2);
        list_ani.add(R.drawable.redbag_3);
        list_ani.add(R.drawable.redbag_4);
        list_ani.add(R.drawable.redbag_5);
        list_ani.add(R.drawable.redbag_6);
        list_ani.add(R.drawable.redbag_7);
        list_ani.add(R.drawable.redbag_8);
        list_ani.add(R.drawable.redbag_9);
        list_ani.add(R.drawable.redbag_10);
        list_ani.add(R.drawable.redbag_11);
        list_ani.add(R.drawable.redbag_12);
        list_ani.add(R.drawable.redbag_13);
        list_ani.add(R.drawable.redbag_14);
        list_ani.add(R.drawable.redbag_15);
        list_ani.add(R.drawable.redbag_16);
        list_ani.add(R.drawable.redbag_17);
        list_ani.add(R.drawable.redbag_18);
        list_ani.add(R.drawable.redbag_19);
        list_ani.add(R.drawable.redbag_20);
    }

        public OpenRedBean parseJsonToMessageBean(String jsonString) {
            Gson gson = new Gson();
            OpenRedBean bean = gson.fromJson(jsonString, new TypeToken<OpenRedBean>() {
            }.getType());
            return bean;
        }

}

