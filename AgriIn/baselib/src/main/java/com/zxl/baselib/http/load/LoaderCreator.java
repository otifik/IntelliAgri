package com.zxl.baselib.http.load;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * @author zxl on 2018/8/14.
 *         discription: 以一种缓存的方式来创建Load
 */

public class LoaderCreator {

    private static final WeakHashMap<String,Indicator> LOADING_MAP = new WeakHashMap<>();

    static AVLoadingIndicatorView create(String type, Context context){
        AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if (LOADING_MAP.get(type) == null){
            Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type,indicator);

        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }

    private static Indicator getIndicator(String name){
        if (name == null ||name.isEmpty()){
            return null;
        }

        final StringBuilder sb = new StringBuilder();
        if (!name.contains(".")){
            String defaultPackaggeName = AVLoadingIndicatorView.class.getPackage().getName();
            sb.append(defaultPackaggeName)
            .append(".indicators")
            .append(".");
        }
        sb.append(name);
        try {
            final Class<?> drawableClass = Class.forName(sb.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
