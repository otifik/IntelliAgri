package com.jit.AgriIn.ui.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.ui.activity.repair.RepairListActivity;
import com.jit.AgriIn.ui.activity.robot.RobotManageActivity;
import com.jit.AgriIn.ui.presenter.user.PersonalAtPresenter;
import com.jit.AgriIn.ui.view.user.IPersonalAtView;
import com.jit.AgriIn.util.PictureSHelper;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zxl.baselib.baseapp.AppManager;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.LoggerUtils;
import com.zxl.baselib.util.image.GlideLoaderUtils;
import com.zxl.baselib.util.ui.UIUtils;
import com.zxl.baselib.widget.CustomDialog;
import com.zxl.baselib.widget.WaveView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author zxl on 2018/09/26.
 * Describe: 个人中心活动，活动中不能有耗时操作
 */
public class PersonalActivity extends BaseActivity<IPersonalAtView, PersonalAtPresenter> implements IPersonalAtView {

    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.wave_view)
    WaveView waveView;
    @BindView(R.id.img_avater)
    CircleImageView imgAvater;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ivToolbarNavigation)
    ImageView ivToolbarNavigation;

    public List<LocalMedia> mSingleSelectList = new ArrayList<>();
    @BindView(R.id.stv_about_robot)
    SuperTextView stvAboutRobot;
    @BindView(R.id.stv_robot_manage)
    SuperTextView stvRobotManage;
    @BindView(R.id.stv_logout)
    SuperTextView stvLogout;
    @BindView(R.id.stv_robot_reapir)
    SuperTextView mStvRobotReapir;
    @BindView(R.id.stv_robot_pwd)
    SuperTextView mStvRobotPwd;
    private View mExitView;
    private CustomDialog mExitDialog;

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_personal;
    }

    @Override
    protected PersonalAtPresenter createPresenter() {
        return new PersonalAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initHeadView();
    }

    /**
     * 初始化顶部的head
     */
    private void initHeadView() {
        tvName.setText(UserCache.getUserUsername());
        // 加载图片
        GlideLoaderUtils.display(this, imgAvater, UserCache.getUserImage());
        final RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) imgAvater.getLayoutParams();
        waveView.setOnWaveAnimationListener(y -> {
            lp.setMargins(0, 0, lp.rightMargin, (int) y + 2);
            imgAvater.setLayoutParams(lp);
        });
    }

    @Override
    protected void initData() {
        LoggerUtils.logE("token", UserCache.getUserToken());
    }

    @Override
    protected void initListener() {
        ivToolbarNavigation.setOnClickListener(view -> onBackPressed());

        // 设置单张图片的点击事件
        imgAvater.setOnClickListener(view -> PictureSHelper.getInstance().
                chooseSinglePictureEvent(PersonalActivity.this, mSingleSelectList, PictureConfig.SINGLE));

        stvLogout.setOnSuperTextViewClickListener(superTextView -> {
            if (mExitView == null) {
                mExitView = View.inflate(mContext, R.layout.dialog_exit, null);
                mExitDialog = new CustomDialog(mContext, mExitView, R.style.MyDialog);
                mExitView.findViewById(R.id.tvExitAccount).setOnClickListener((View v) -> {
                    mExitDialog.dismiss();
                    mPresenter.doLogout();
                });

                mExitView.findViewById(R.id.tvExitApp).setOnClickListener(v -> {
                    mExitDialog.dismiss();
                    AppManager.getAppManager().finishAllActivity();
                });
            }
            mExitDialog.show();
        });

        //  跳转向机器人管理 -----
        stvRobotManage.setOnSuperTextViewClickListener(superTextView -> jumpToActivity(RobotManageActivity.class));

        // 跳转向修改密码
        mStvRobotPwd.setOnSuperTextViewClickListener(superTextView -> jumpToActivity(ChangePwdActivity.class));

        // 跳转向维修提交界面
        mStvRobotReapir.setOnSuperTextViewClickListener((SuperTextView superTextView) -> {
            jumpToActivity(RepairListActivity.class);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.SINGLE:
                    mSingleSelectList = PictureSelector.obtainMultipleResult(data);
                    // 执行P层的上传图片
                    mPresenter.doPostHeadImage(mSingleSelectList.get(0).getPath());
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 上传头像
     *
     * @param imagePath
     */
    @Override
    public void postHeadSuccess(String imagePath) {
        UserCache.setUserImage(imagePath);
        mSingleSelectList.clear();
        GlideLoaderUtils.display(this, imgAvater, imagePath);
    }

    @Override
    public void postHeadFailure(String error) {
        UIUtils.showToast(error);
    }

    /**
     * 退出登陆成功
     */
    @Override
    public void logoutSuccess() {
        AppManager.getAppManager().finishAllActivity();
        jumpToActivityAndClearTask(LoginActivity.class);
    }

}
