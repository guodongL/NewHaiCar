package com.example.administrator.myhaicar.commond;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zhy.changeskin.SkinManager;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by Administrator on 2016/8/9.
 */
public class SwipeBackToolBarActivity extends ActionBarActivity implements SwipeBackActivityBase{
    private SwipeBackActivityHelper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        SkinManager.getInstance().register(this);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }
 /*   @Subscribe
    public void onEventMainThread(BankName event) {
        String msg =event.getBankName();
        if (msg.equals("1")){
            SkinManager.getInstance().changeSkin("night");


        }else if (msg.equals("2")){
            SkinManager.getInstance().changeSkin("day");
        }

    }*/

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }
    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        getSwipeBackLayout().scrollToFinishActivity();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }

}

