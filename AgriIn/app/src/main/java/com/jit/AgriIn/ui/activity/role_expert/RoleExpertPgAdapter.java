package com.jit.AgriIn.ui.activity.role_expert;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/10/29.
 * Describe:
 */
public class RoleExpertPgAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    public RoleExpertPgAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.mFragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragmentList == null) {
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
}
