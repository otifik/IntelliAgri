package com.zxl.baselib.http.load;


import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import com.wang.avi.AVLoadingIndicatorView;
import com.zxl.baselib.R;
import java.util.ArrayList;
/**
 * @author zxl
 * 完美的加载---
 */
public class PerfectLoader {
    /**
     * 按比例显示
     */
    private static final  int LOAD_SIZE_SCALE = 8;
    /**
     * 缩放
     */
    private static final  int LOAD_OFFSET_SCALE = 10;

    /**
     * 方便进行管理的 --- 这里固然是不可能为一个变量的; 故而不能够等于
     */
    private static final ArrayList<AppCompatDialog> LOADS  = new ArrayList<>();

    private static String defType = LoaderStyle.BallBeatIndicator.name();

    public static void showLoading(Context context,String type){
        AppCompatDialog appCompatDialog = new AppCompatDialog(context, R.style.loadDialog);
        AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        appCompatDialog.setContentView(avLoadingIndicatorView);
        // 设置不可点击来取消加载
        appCompatDialog.setCancelable(false);
        int deviceWidth = context.getResources().getDisplayMetrics().widthPixels;
        int deviceHeight = context.getResources().getDisplayMetrics().heightPixels;
        Window window = appCompatDialog.getWindow();
        if (window != null){
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = deviceWidth / LOAD_SIZE_SCALE;
            attributes.height = deviceHeight /LOAD_SIZE_SCALE;
            attributes.height = attributes.height + deviceHeight / LOAD_OFFSET_SCALE;
            attributes.gravity = Gravity.CENTER;
        }
        LOADS.add(appCompatDialog);
        appCompatDialog.show();
    }

    public static void showLoading(Context context , LoaderStyle loaderStyle){
        if (loaderStyle == null) {
            showLoading(context);
        }else {
            showLoading(context, loaderStyle.name());
        }
    }

    public static void showLoading(Context context){
        showLoading(context,defType);
    }

    public static void stopLoading(){
        for (AppCompatDialog dialog : LOADS){
            if (dialog != null){
                if (dialog.isShowing()){
                    dialog.cancel();
                }
            }
        }
    }
}
