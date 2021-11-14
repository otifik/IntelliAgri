package com.jit.AgriIn.ui.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.LoginResponse;
import com.jit.AgriIn.ui.activity.role_expert.RoleExpertActivity;
import com.jit.AgriIn.ui.activity.user.LoginActivity;
import com.jit.AgriIn.uinew.RobotMainActivity;
import com.jit.AgriIn.uinew.role_admin.RoleAdminActivity;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;
import com.zxl.baselib.util.LoggerUtils;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author zxl on 2018/09/03.
 *         discription: 闪屏页界面
 *         该块大致实现: 1.申请运行时权限(是否可以放到主页呢) 2. 界面可是的渐进效果 3. 进入主页的动画效果、
 *         4. 增加
 */
@RuntimePermissions
public class SplashActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.llContent)
    RelativeLayout mLlContent;
    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.tv_version_name)
    TextView mTvVersionName;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    private AnimatorSet mAnimatorSet;
    private ScheduledExecutorService mScheduledService;
    @Override
    protected void init() {
        PgyUpdateManager.setIsForced(true);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvVersionName.setText(getString(R.string.version_name, UIUtils.getVersionName()));
        setAnimation();
        SplashActivityPermissionsDispatcher.needsNormalPermissionWithCheck(this);
    }

    private void setAnimation() {
        ObjectAnimator alphaContent = ObjectAnimator
                .ofFloat(mLlContent, "alpha", 0.4f, 1f);

        ObjectAnimator alphaText = ObjectAnimator
                .ofFloat(mTvTitle, "alpha", 0.4f, 1f);
        ObjectAnimator alphaVersion = ObjectAnimator
                .ofFloat(mTvVersionName, "alpha", 0.4f, 1f);
        ObjectAnimator alphaName = ObjectAnimator
                .ofFloat(mTvName, "alpha", 0.4f, 1f);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(alphaContent).with(alphaText).with(alphaVersion).with(alphaName);
        mAnimatorSet.setDuration(2000);
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (mProgressBar != null &&mProgressBar.getVisibility() == View.INVISIBLE  ){
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                updateApp();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     *   用于更系APP
     */
    private void updateApp() {
        // 执行蒲公英 ---- 界面进入
        mScheduledService = Executors.newScheduledThreadPool(1);
        mScheduledService.schedule(() -> SplashActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.INVISIBLE);
                jumpToNextActivity();
            }
        }), 2, TimeUnit.SECONDS);
        PgyUpdateManager.register(this, new UpdateManagerListener() {
            @Override
            public void onNoUpdateAvailable() {
                LoggerUtils.logE("检测更新","========无更新=========");
            }

            @Override
            public void onUpdateAvailable(String s) {
                mScheduledService.shutdownNow();
                mScheduledService.shutdown();
                mProgressBar.setVisibility(View.INVISIBLE);
                AppBean appBean = getAppBeanFromString(s);
                showPSMaterialDialog("版本更新", "新版本提示", "确定", "取消",
                        (MaterialDialog dialog, DialogAction which) ->
                        {
                            dialog.dismiss();
                            startDownloadTask(SplashActivity.this, appBean.getDownloadURL());
                        }, (dialog, which) -> {
                            dialog.dismiss();
                            jumpToNextActivity();
                        });
            }
        });

    }

    /**
     * 根据是否登陆跳转入不同的活动
     */
    @SuppressLint("CheckResult")
    private void jumpToNextActivity(){
        if (TextUtils.isEmpty(UserCache.getUserToken())){
            jumpToActivity(LoginActivity.class);
        }else {
            // 暗含的刷新token -----
            ApiRetrofit.getInstance().requestLogin(UserCache.getUserUsername(),UserCache.getUserPwd())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(loginResponseBaseResponse -> {
                        if (loginResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS) {
                            // 执行普通数据的存储----
                            LoginResponse loginResponse = loginResponseBaseResponse.getData();
                            UserCache.setUserToken(getString(R.string.token_suffix, loginResponse.getToken()));

                            if (AppConstant.ROLE_CUSTOM.equals(UserCache.getUserRole()) ) {
                                jumpToActivity(RobotMainActivity.class);
                            }else if (AppConstant.ROLE_EXPERT.equals(UserCache.getUserRole())){
                                jumpToActivity(RoleExpertActivity.class);
                            }else if (AppConstant.ROLE_ADMIN.equals(UserCache.getUserRole())){
                                jumpToActivity(RoleAdminActivity.class);
                            }
                        }else {
                            UIUtils.showToast(loginResponseBaseResponse.getMsg());
                            jumpToActivity(LoginActivity.class);
                        }
                    }, throwable -> {

                    });



//            if (AppConstant.ROLE_CUSTOM.equals(UserCache.getUserRole()) ) {

//                JPushInterface.setAlias(mContext,0,UserCache.getUserImage());
//                jumpToActivity(RobotMainActivity.class);
//            }else if (AppConstant.ROLE_EXPERT.equals(UserCache.getUserRole())){
//                jumpToActivity(RoleExpertActivity.class);
//            }
        }
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PgyUpdateManager.unregister();
    }

    /**
     * 动画跳转入主活动 ----
     */
    private void  toMainActivity(){
        Intent intent = new Intent(this, RobotMainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    })
    void needsNormalPermission() {
        mAnimatorSet.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO})
    void showNormalPermission(final PermissionRequest request) {
        showPSMaterialDialog(null, getString(R.string.needs_your_award,getPermissionTips()),
                getString(R.string.ensure), getString(R.string.cancel),
                (dialog, which) -> {
                    dialog.dismiss();
                    request.proceed();
                }, (dialog, which) -> {
                    dialog.dismiss();
                    request.cancel();
                });
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO})
    void onNormalDenied() {
        showPSMaterialDialog(getString(R.string.permission_tip_title),getString(R.string.open_needs_pm,
                getPermissionTips()),
                getString(R.string.set_permission_txt), "", (dialog, which) -> {
                    dialog.dismiss();
                    SplashActivityPermissionsDispatcher.needsNormalPermissionWithCheck(SplashActivity.this);
                },null);
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO})
    void onNeverAskAgain() {
        showPSMaterialDialog(null,getString(R.string.needs_pm_to_setting,getPermissionTips()), getString(R.string.turn_to_open), "", (dialog, which) -> {
            dialog.dismiss();
            getAppDetailSettingIntent();
        },null);
    }

    private String getPermissionTips(){
        StringBuilder mStringBuilder = new StringBuilder();
        if (lackPermission(Manifest.permission.READ_PHONE_STATE)){
            mStringBuilder.append(getString(R.string.pm_phone_info));
        }
        if (lackPermission(Manifest.permission.READ_EXTERNAL_STORAGE) || lackPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            if (TextUtils.isEmpty(mStringBuilder.toString())) {
                mStringBuilder.append(getString(R.string.pm_read_no_space));
            }else {
                mStringBuilder.append(getString(R.string.pm_read_space));
            }
        }

        if (lackPermission(Manifest.permission.ACCESS_FINE_LOCATION)){
            if (TextUtils.isEmpty(mStringBuilder.toString())) {
                mStringBuilder.append(getString(R.string.pm_location_no_space));
            }else {
                mStringBuilder.append(getString(R.string.pm_location_space));
            }
        }
        return mStringBuilder.toString();
    }


    @Override
    public void onBackPressedSupport() {
    }
}
