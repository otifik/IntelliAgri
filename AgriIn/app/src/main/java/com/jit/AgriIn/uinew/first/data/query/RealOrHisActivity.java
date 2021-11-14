package com.jit.AgriIn.uinew.first.data.query;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;

import com.jit.AgriIn.R;
import com.jit.AgriIn.ui.activity.role_expert.RoleExpertPgAdapter;
import com.jit.AgriIn.uinew.first.HisDataFragment;
import com.jit.AgriIn.uinew.first.RealDataFragment;
import com.jit.AgriIn.widget.GreatRadioGroup;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;
import com.zxl.baselib.util.ui.StatusBarHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-10-29 10:32:07.
 * Describe: 专家身份登陆的首页------
 */

public class RealOrHisActivity extends BaseActivity {


    @BindView(R.id.customList)
    RadioButton mCustomList;
    @BindView(R.id.baikeList)
    RadioButton mBaikeList;
    @BindView(R.id.radioGroup)
    GreatRadioGroup mRadioGroup;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_realorhis;
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
        fragments.add(new RealDataFragment());
        fragments.add(new HisDataFragment());
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
    }

}
