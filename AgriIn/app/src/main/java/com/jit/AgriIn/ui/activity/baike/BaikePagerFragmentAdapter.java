package com.jit.AgriIn.ui.activity.baike;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author crazyZhangxl on 2018/10/15.
 * Describe:
 */
public class BaikePagerFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();

    public BaikePagerFragmentAdapter(FragmentManager fm,List<Fragment> fragments,List<String> titleList) {
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
