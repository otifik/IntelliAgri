package com.jit.AgriIn.app;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.mob.MobSDK;
import com.videogo.openapi.EZOpenSDK;
import com.zxl.baselib.baseapp.BaseApp;
import com.zxl.open.constansts.OpenConstant;

import org.litepal.LitePal;


/**
 * @author 张先磊
 * @date 2018/4/17
 */

public class MyApp extends BaseApp {

    //开发者需要填入自己申请的appkey
    public static String AppKey = "373f90fa7c534e02bbbf020ade384bf5";

    public static EZOpenSDK getOpenSDK() {
        return EZOpenSDK.getInstance();
    }




    @Override
    public void onCreate() {
        super.onCreate();

//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);

        initSDK();

        // litePal初始化
        LitePal.initialize(this);
        // 初始化短信验证
        MobSDK.init(this, OpenConstant.MOB_APP_ID,OpenConstant.MOB_APP_SECRET);


        // 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5bc45d1a");

    }



    private void initSDK() {
        {
            /**
             * sdk日志开关，正式发布需要去掉
             */
            EZOpenSDK.showSDKLog(true);

            /**
             * 设置是否支持P2P取流,详见api
             */
            EZOpenSDK.enableP2P(true);

            /**
             * APP_KEY请替换成自己申请的
             */
//            EZOpenSDK.initLib(this, AppKey);
        }
    }


}
