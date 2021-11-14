package com.jit.AgriIn.ui.fragment.role_expert;

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
 * @author crazyZhangxl on 2018/10/29.
 * Describe:
 */
public class ExpertBaikeListFragment extends BaseFragment {
    @BindView(R.id.tabBaiKe)
    TabLayout mTabBaiKe;
    @BindView(R.id.vpBaiKe)
    ViewPager mVpBaiKe;

    @Override
    public void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_role_expert_baike_list;
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
        mTitleList.add("疾病百科");
        mTitleList.add("产品百科");
        mTitleList.add("药品百科");
        mTitleList.add("种苗百科");
        mTitleList.add("投喂百科");
        mFragments.add(new EBkDisFragment());
        mFragments.add(new EBKProductFragment());
        mFragments.add(new EBKDrugFragment());
        mFragments.add(new EBKSeedFragment());
        mFragments.add(new EBKFeedFragment());
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
