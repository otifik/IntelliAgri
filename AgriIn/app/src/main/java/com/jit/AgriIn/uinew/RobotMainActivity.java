package com.jit.AgriIn.uinew;

import android.os.Bundle;

import com.jit.AgriIn.R;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 仿微信交互方式Demo   tip: 多使用右上角的"查看栈视图"
 * Created by YoKeyword on 16/6/30.
 */
public class RobotMainActivity extends BaseActivity {



    @Override
    protected void init() {
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_robotmain;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }



    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }










}
