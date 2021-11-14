package com.zxl.baselib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zxl.baselib.R;
import com.zxl.baselib.http.load.LoaderStyle;
import com.zxl.baselib.http.load.PerfectLoader;


/**
 * des:加载页面内嵌提示*
 * 可以考虑把加载的效果放到我刚集成的请求框里
 * @author zxl
 */
public class LoadingTip extends LinearLayout {

    private ImageView img_tip_logo;
    private TextView tv_tips;
    private Button bt_operate;
    private String errorMsg;
    private onReloadListener onReloadListener;
    private LoaderStyle mLoaderStyle;

    public LoadingTip(Context context) {
        super(context);
        initView(context);
    }

    public LoadingTip(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingTip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingTip(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    //分为服务器失败，网络加载失败、数据为空、加载中、完成四种状态

    public static enum LoadStatus {
        /**
         * 服务器错误
         */
        ServiceError,
        /**
         * 请求其他错误
         */
        Error,
        /**
         * 数据为空
         */
        Empty,
        /**
         * 正在加载
         */
        Loading,
        /**
         * 完成 ----
         */
        Finish
    }


    private void initView(Context context) {
        View.inflate(context, R.layout.dialog_loading_tip, this);
        img_tip_logo = (ImageView) findViewById(R.id.img_tip_logo);
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        bt_operate = (Button) findViewById(R.id.bt_operate);
        //重新尝试
        bt_operate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onReloadListener!=null){
                    onReloadListener.reload();
                }
            }
        });
        setVisibility(View.GONE);
    }

    /**
     * 设置加载的样式 ------
     * @param loadStyle
     */
    public void setLoadStyle(LoaderStyle loadStyle){
        this.mLoaderStyle = loadStyle;
    }

    public void setTips(String tips){
        if(tv_tips!=null){
            tv_tips.setText(tips);
        }
    }

    /**
     * 根据状态显示不同的提示
     * @param loadStatus
     */
    public void setLoadingTip(LoadStatus loadStatus){
        switch (loadStatus){
            case Empty:
                PerfectLoader.stopLoading();
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                tv_tips.setText(getContext().getText(R.string.empty).toString());
                img_tip_logo.setImageResource(R.drawable.no_content_tip);
                break;
            case ServiceError:
                PerfectLoader.stopLoading();
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(errorMsg)){
                    tv_tips.setText(getContext().getText(R.string.net_error).toString());
                }else {
                    tv_tips.setText(errorMsg);
                }
                img_tip_logo.setImageResource(R.drawable.ic_wrong);
                break;
            case Error:
                PerfectLoader.stopLoading();
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(errorMsg)){
                    tv_tips.setText(getContext().getText(R.string.net_error).toString());
                }else {
                    tv_tips.setText(errorMsg);
                }
                img_tip_logo.setImageResource(R.drawable.ic_wifi_off);
                break;
            case Loading:
                PerfectLoader.showLoading(getContext(),mLoaderStyle);
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.GONE);
                tv_tips.setText("");
                break;
            case Finish:
                PerfectLoader.stopLoading();
                setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }


    public void setOnReloadListener(onReloadListener onReloadListener){
        this.onReloadListener=onReloadListener;
    }
    /**
     * 重新尝试接口
     */
    public interface onReloadListener{
        void reload();
    }
}

