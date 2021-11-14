package com.zxl.baselib.baseapp;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @author zxl on 2018/8/8.
 *         discription:
 */

public class BaseApp extends MultiDexApplication {
    /**
     * 主线程Handler
     */
    private static Handler mHandler;
    /**
     * 主线程id
     */
    private static long mMainThreadId;

    /**
        以下属性应用于整个应用程序，合理利用资源，减少资源浪费
        上下文
     */
    private static Context mContext;
    /**
     * 屏幕密度
     */
    public static float screenDensity;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();
        screenDensity = getApplicationContext().getResources().getDisplayMetrics().density;
        //初始化Logger
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static Context getContext() {
        return mContext;
    }
    public static Handler getMainHandler() {
        return mHandler;
    }
    public static long getMainThreadId() {
        return mMainThreadId;
    }
}
