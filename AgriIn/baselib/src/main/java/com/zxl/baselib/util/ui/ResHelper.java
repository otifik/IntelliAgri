package com.zxl.baselib.util.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.zxl.baselib.R;
import com.zxl.baselib.baseapp.BaseApp;

/**
 * @author zxl on 2018/8/8.
 *         discription: 系统资源的帮助工具
 */

public class ResHelper {

    /**
     * 得到上下文
     *
     * @return
     */
    public static Context getContext() {
        return BaseApp.getContext();
    }

    /**
     * 得到resources对象
     *
     * @return
     */
    public static Resources getResource() {
        return getContext().getResources();
    }

    /**
     * 得到string.xml中的字符串
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    /**
     * 得到string.xml中的字符串，带点位符
     *
     * @return
     */
    public static String getString(int id, Object... formatArgs) {
        return getResource().getString(id, formatArgs);
    }

    /**
     * 得到string.xml中和字符串数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArr(int resId) {
        return getResource().getStringArray(resId);
    }

    /**
     * 得到colors.xml中的颜色
     *
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return ContextCompat.getColor(getContext(), colorId);
    }

    public static Drawable getDrawable(int resID) {
        return ContextCompat.getDrawable(getContext(), resID);
    }

    public static Bitmap getBitmap(int pictureId){
        return BitmapFactory.decodeResource(getResource(),pictureId);
    }


    /**
     * 依据字符串来获取颜色值
     * @param color #00ff00
     * @return
     */
    public static int getColor(String color){
        return Color.parseColor(color);
    }

}
