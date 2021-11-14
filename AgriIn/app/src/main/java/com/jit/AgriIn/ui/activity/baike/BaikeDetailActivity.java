package com.jit.AgriIn.ui.activity.baike;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jit.AgriIn.R;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.ui.activity.role_expert.EBKDisAddActivity;
import com.jit.AgriIn.ui.activity.role_expert.EBKDrugAddActivity;
import com.jit.AgriIn.ui.activity.role_expert.EBKFeedAddActivity;
import com.jit.AgriIn.ui.activity.role_expert.EBKProductAddActivity;
import com.jit.AgriIn.ui.activity.role_expert.EBKSeedAddActivity;
import com.jit.AgriIn.ui.fragment.baike.BaikeDiseaseListFragment;
import com.jit.AgriIn.ui.fragment.baike.BaikeDrugListFragment;
import com.jit.AgriIn.ui.fragment.baike.BaikeFeedListFragment;
import com.jit.AgriIn.ui.fragment.baike.BaikeProductListFragment;
import com.jit.AgriIn.ui.fragment.baike.BaikeSeedListFragment;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-10-12 11:29:02.
 * Describe: 百科主活动
 */

/**
 * @author crazyZhangxl on 2018-10-12 11:29:02.
 * Describe: 百科主活动
 */

public class BaikeDetailActivity extends BaseActivity {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.ibAddMenu)
    ImageButton ibAddMenu;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;


    @BindView(R.id.tabBaiKe)
    TabLayout mTabBaiKe;
    @BindView(R.id.vpBaiKe)
    ViewPager mVpBaiKe;




    private BaikePageFragmentAdapter adapter;

    ArrayList<String> mTitleList = new ArrayList<>(5);
    ArrayList<Fragment> mFragments = new ArrayList<>(5);

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_baike_detail;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        if (UserCache.getUserRole().contains("USER")){ // 普通用户
            ibAddMenu.setVisibility(View.GONE);
        }else if (UserCache.getUserRole().contains("EXPERT")){ // 专家用户
            ibAddMenu.setVisibility(View.VISIBLE);
        }


        mTvToolbarTitle.setText("知识百科");
        mTitleList.clear();
        mFragments.clear();
        mTitleList.add("病症");
        mTitleList.add("农药");
        mTitleList.add("肥料");
        mTitleList.add("品种");
        mTitleList.add("综合");
        mFragments.add(BaikeDiseaseListFragment.newInstance("所有"));
        mFragments.add(BaikeDrugListFragment.newInstance("所有"));
        mFragments.add(BaikeProductListFragment.newInstance("所有"));
        mFragments.add(BaikeSeedListFragment.newInstance("所有"));
        mFragments.add(BaikeFeedListFragment.newInstance("所有"));
        adapter = new BaikePageFragmentAdapter(getSupportFragmentManager(),mFragments,mTitleList);
        mVpBaiKe.setAdapter(adapter);
        mVpBaiKe.setOffscreenPageLimit(mTitleList.size());
        mVpBaiKe.setCurrentItem(0);
        mTabBaiKe.setTabMode(TabLayout.MODE_FIXED);
        mTabBaiKe.setupWithViewPager(mVpBaiKe);


    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());


        ibAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               switch (mTabBaiKe.getSelectedTabPosition()){
                   case 0:
                       jumpToActivity(EBKDisAddActivity.class);
                       break;
                   case 1:
                       jumpToActivity(EBKDrugAddActivity.class);
                       break;
                   case 2:
                       jumpToActivity(EBKProductAddActivity.class);
                       break;
                   case 3:
                       jumpToActivity(EBKSeedAddActivity.class);
                       break;
                   case 4:
                       jumpToActivity(EBKFeedAddActivity.class);
                       break;
               }
            }
        });
    }






    public class BaikePageFragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mTitleList = new ArrayList<>();

        public BaikePageFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titleList) {
            super(fm);
            mFragmentList = fragments;
            mTitleList = titleList;
        }

        @Override
        public Fragment getItem(int position) {
            if(mFragmentList == null) {
                return null;
            }
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            if (mFragmentList == null) {
                return 0;
            }
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mTitleList == null) {
                return null;
            }
            return mTitleList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
            return POSITION_NONE;
        }

//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        // 把 Object 强转为 View，然后将 view 从 ViewGroup 中清除
//        container.removeView((View) object);
//    }
    }



}
