package com.example.administrator.myhaicar.tools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/6/7.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler instance;  //单例引用，这里我们做成单例的，因为我们一个应用程序里面只需要一个UncaughtExceptionHandler实例
    private Context context;

    private CrashHandler(){}

    public synchronized static CrashHandler getInstance(){  //同步方法，以免单例多线程环境下出现异常
        if (instance == null){
            instance = new CrashHandler();
        }
        return instance;
    }

    public void init(Context ctx){  //初始化，把当前对象设置成UncaughtExceptionHandler处理器
        context = ctx;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //当有未处理的异常发生时，就会来到这里。。
        Toast.makeText(context,"程序出现异常，请退出后重新登陆",Toast.LENGTH_LONG).show();
        android.os.Process.killProcess(android.os.Process.myPid());

    }
}
