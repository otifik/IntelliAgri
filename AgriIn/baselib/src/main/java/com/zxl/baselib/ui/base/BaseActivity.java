package com.zxl.baselib.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.luck.picture.lib.rxbus2.RxBus;
import com.zxl.baselib.baseapp.AppManager;
import com.zxl.baselib.baserx.RxManager;
import com.zxl.baselib.http.load.LoaderStyle;
import com.zxl.baselib.http.load.PerfectLoader;
import com.zxl.baselib.widget.LoadingProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 *
 * @author zxl
 * @date 2018/4/17
 */

public abstract class BaseActivity<V extends BaseView,T extends BasePresenter<V>> extends SupportActivity {
    protected T mPresenter;
    protected Context mContext;
    private MaterialDialog mMaterialDialog;
    private MaterialDialog mWaitMaterialDialog;
    private MaterialDialog mPSMaterialDialog;
    private Dialog mNiceDialog;
    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder = null;
    private boolean isConfigChange=false;
    public RxManager mRxManager;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConfigChange=false;
        mRxManager=new RxManager();
        doBeforeSetContentView();
        mContext = this;
        init();
        mPresenter = createPresenter();
        if (mPresenter != null) {
            //因为之后所有的子类都要实现对应的View接口
            mPresenter.attachView((V) this);
        }
        setContentView(provideContentViewId());
        mUnbinder = ButterKnife.bind(this);
        initView(savedInstanceState);
        initData();
        initListener();
    }

    private void doBeforeSetContentView() {
        // 设置添加活动管理
        AppManager.getAppManager().addActivity(this);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    //在setContentView()调用之前调用，可以设置WindowFeature(如：this.requestWindowFeature(Window.FEATURE_NO_TITLE);)
    protected abstract void init();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void initListener();


    public void jumpToActivity(Intent intent) {
        startActivity(intent);
    }

    public void jumpToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    /**
     *  含有bundle跳转
     *  */
    public void jumpToActivity(Class cls,Bundle bundle){
        Intent intent = new Intent(this,cls);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void jumpToActivityForResult(Class cls,int requestCode){
        jumpToActivityForResult(cls,null,requestCode);
    }

    public void jumpToActivityForResult(Class cls,Bundle bundle,int requestCode){
        Intent intent = new Intent(this,cls);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
    }

    public void jumpToActivityAndClearTask(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

/*
    */
/* 跳转向含数字的多图显示的活动 *//*

    public static void jumpToMulPicNumActivity(BaseActivity baseActivity, int nowPosition, ArrayList<String> mUriStrList){
        MulPicWithNumActivity.startAction(baseActivity,2,nowPosition,mUriStrList);
    }
*/

    public void showNiceDialog(Context context){
        try {
            mNiceDialog = LoadingProgressDialog.createLoadingDialog(context);
            mNiceDialog.setCanceledOnTouchOutside(false);
            mNiceDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelNiceDialog() {
        try {
            if (mNiceDialog != null && mNiceDialog.isShowing()) {
                mNiceDialog.dismiss();
            }
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }


    public void showWaitingDialog(String tip) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .content(tip)
                .progress(true, 0)
                .progressIndeterminateStyle(false);
        mWaitMaterialDialog = builder.build();
        mWaitMaterialDialog.setCancelable(false);
        mWaitMaterialDialog.show();
    }



    public void hideWaitingDialog() {
        if (mWaitMaterialDialog != null) {
            mWaitMaterialDialog.dismiss();
            mWaitMaterialDialog = null;
        }
    }

    public void showLoadingDialog() {
        PerfectLoader.showLoading(mContext, LoaderStyle.BallScaleRippleMultipleIndicator);
    }

    public void hideLoadingDialog() {
        PerfectLoader.stopLoading();
    }



    /**
     * 显示MaterialDialog
     */
    public MaterialDialog showMaterialDialog(String title, String message, String positiveText, String negativeText, MaterialDialog.SingleButtonCallback positiveCallBack,MaterialDialog.SingleButtonCallback negativeCallBack) {
        hideMaterialDialog();
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        if (!TextUtils.isEmpty(title)) {
            builder.title(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.content(message);
        }
        if (!TextUtils.isEmpty(positiveText)) {
            builder.positiveText(positiveText).onPositive(positiveCallBack);
        }
        if (!TextUtils.isEmpty(negativeText)) {
            builder.negativeText(negativeText).onNegative(negativeCallBack);
        }
        mMaterialDialog = builder.build();
        mMaterialDialog.show();
        return mMaterialDialog;
    }

    /**
     * 隐藏MaterialDialog
     */
    public void hideMaterialDialog() {
        if (mMaterialDialog != null) {
            mMaterialDialog.dismiss();
            mMaterialDialog = null;
        }
    }


    /**
     * 显示权限MaterialDialog
     */
    public MaterialDialog showPSMaterialDialog(String title, String message, String positiveText, String negativeText, MaterialDialog.SingleButtonCallback positiveCallBack,MaterialDialog.SingleButtonCallback negativeCallBack) {
        hidePSMaterialDialog();
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        if (!TextUtils.isEmpty(title)) {
            builder.title(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.content(message);
        }
        if (!TextUtils.isEmpty(positiveText)) {
            builder.positiveText(positiveText).onPositive(positiveCallBack);
        }
        if (!TextUtils.isEmpty(negativeText)) {
            builder.negativeText(negativeText).onNegative(negativeCallBack);
        }
        mPSMaterialDialog = builder.build();
        mPSMaterialDialog.setCancelable(false);
        mPSMaterialDialog.show();
        return mPSMaterialDialog;
    }

    /**
     * 隐藏权限MaterialDialog
     */
    public void hidePSMaterialDialog() {
        if (mPSMaterialDialog != null) {
            mPSMaterialDialog.dismiss();
            mPSMaterialDialog = null;
        }
    }

    public void showSnackBar(View view ,String string){
        Snackbar.make(view, string, Snackbar.LENGTH_LONG)
                .show();
    }

    /**
     * 判断是否权少权限
    * */
    public boolean lackPermission(String permission){
        return ContextCompat.checkSelfPermission(mContext,permission) == PackageManager.PERMISSION_DENIED;
    }

    /**
     * 跳转到设置权限的界面
     * */
    public void getAppDetailSettingIntent() {
        // 可以看下这篇文章
        // https://blog.csdn.net/cbbbc/article/details/60148864
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        isConfigChange=true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }

        if(mRxManager!=null) {
            mRxManager.clear();
        }

        if(!isConfigChange) {
            AppManager.getAppManager().removeActivity(this);
        }
    }
}

