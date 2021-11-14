package com.jit.AgriIn.ui.activity.role_expert;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.jit.AgriIn.R;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.ui.activity.user.ChangePwdActivity;
import com.jit.AgriIn.ui.activity.user.LoginActivity;
import com.jit.AgriIn.ui.fragment.role_expert.ExpertCusListFragment;
import com.jit.AgriIn.ui.fragment.role_expert.ZhishiListFragment;
import com.jit.AgriIn.widget.GreatRadioGroup;
import com.zxl.baselib.baseapp.AppManager;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;
import com.zxl.baselib.util.ui.StatusBarHelper;
import com.zxl.baselib.widget.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018-10-29 10:32:07.
 * Describe: 专家身份登陆的首页------
 */

public class RoleExpertActivity extends BaseActivity {


    @BindView(R.id.customList)
    RadioButton mCustomList;
    @BindView(R.id.baikeList)
    RadioButton mBaikeList;
    @BindView(R.id.radioGroup)
    GreatRadioGroup mRadioGroup;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.setting)
    ImageView mIvSetting;
    private View mExitView;
    private CustomDialog mExitDialog;
    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_role_expert;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mCustomList.setChecked(true);
        initViewPager();
        StatusBarHelper.setStatusBarLightMode(this);
    }

    private void initViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ExpertCusListFragment());
        fragments.add(new ZhishiListFragment());
        mViewPager.setAdapter(new RoleExpertPgAdapter(getSupportFragmentManager(),fragments));
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setCurrentItem(0,false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.customList){
                mViewPager.setCurrentItem(0,false);
            }else if (checkedId == R.id.baikeList){
                mViewPager.setCurrentItem(1,false);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mViewPager.getAdapter() == null
                        || mViewPager.getAdapter().getCount() <= 0) {
                    return;
                }
                if (position == 0){
                    mCustomList.setChecked(true);
                }else if (position == 1){
                    mBaikeList.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mIvSetting.setOnClickListener(v -> {
            if (mExitView == null){
                mExitView = View.inflate(mContext,R.layout.dialog_exit_expert,null);
                mExitDialog = new CustomDialog(mContext,mExitView,R.style.MyDialog);
                mExitView.findViewById(R.id.tvExitAccount).setOnClickListener(v1 -> {
                    mExitDialog.dismiss();
                    doLogout();
                });

                mExitView.findViewById(R.id.tvExitApp).setOnClickListener(v12 -> {
                    mExitDialog.dismiss();
                    AppManager.getAppManager().finishAllActivity();
                });

                mExitView.findViewById(R.id.tvChangePwd).setOnClickListener(v13 -> {
                    mExitDialog.dismiss();
                    jumpToActivity(ChangePwdActivity.class);
                });
            }
            mExitDialog.show();
        });
    }

    @SuppressLint("CheckResult")
    private void doLogout() {
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            UserCache.clear();
            emitter.onNext(true);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean){
                        AppManager.getAppManager().finishAllActivity();
                        jumpToActivityAndClearTask(LoginActivity.class);
                    }
                });
    }

}
