package com.jit.AgriIn.ui.fragment.baike;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jit.AgriIn.R;
import com.jit.AgriIn.ui.activity.baike.BaikePagerFragmentAdapter;
import com.zxl.baselib.ui.base.BaseFragment;
import com.zxl.baselib.ui.base.BasePresenter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018/10/26.
 * Describe:
 */
public class BaikePinFragment extends BaseFragment {
    @BindView(R.id.tabBaiKe)
    TabLayout mTabBaiKe;
    @BindView(R.id.vpBaiKe)
    ViewPager mVpBaiKe;

    @Override
    public void init() {

    }

    public static BaikePinFragment newInstance() {

        Bundle args = new Bundle();

        BaikePinFragment fragment = new BaikePinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_baike_content;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initView(View rootView) {
        ArrayList<String> mTitleList = new ArrayList<>(5);
        ArrayList<Fragment> mFragments = new ArrayList<>(5);
        mTitleList.clear();
        mFragments.clear();
        mTitleList.add("鱼类");
        mTitleList.add("虾类");
        mTitleList.add("蟹类");
        mTitleList.add("其他");
        mTitleList.add("所有");
        mFragments.add(BaikeProductListFragment.newInstance("鱼类"));
        mFragments.add(BaikeProductListFragment.newInstance("虾类"));
        mFragments.add(BaikeProductListFragment.newInstance("蟹类"));
        mFragments.add(BaikeProductListFragment.newInstance("其他"));
        mFragments.add(BaikeProductListFragment.newInstance("所有"));
        mVpBaiKe.setAdapter(new BaikePagerFragmentAdapter(getChildFragmentManager(),mFragments,mTitleList));
        mVpBaiKe.setOffscreenPageLimit(mTitleList.size());
        mVpBaiKe.setCurrentItem(0);
        mTabBaiKe.setTabMode(TabLayout.MODE_FIXED);
        mTabBaiKe.setupWithViewPager(mVpBaiKe);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
