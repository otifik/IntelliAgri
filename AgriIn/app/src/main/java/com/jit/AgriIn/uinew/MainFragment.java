package com.jit.AgriIn.uinew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jit.AgriIn.R;
import com.jit.AgriIn.uinew.first.data.CellListFragment;
import com.jit.AgriIn.uinew.fourth.ProfileFragment;
import com.jit.AgriIn.uinew.second.VideoFragment;
import com.jit.AgriIn.uinew.third.DiaryFragment;
import com.jit.AgriIn.uinew.view.BottomBar;
import com.jit.AgriIn.uinew.view.BottomBarTab;

import me.yokeyword.fragmentation.SupportFragment;


/**
 * Created by YoKeyword on 16/6/30.
 */
public class MainFragment extends SupportFragment {
    private static final int REQ_MSG = 10;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
//    public static final int FIFTH = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private BottomBar mBottomBar;


    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        if (savedInstanceState == null) {
            mFragments[FIRST] = CellListFragment.newInstance();
            mFragments[SECOND] = VideoFragment.newInstance();
            mFragments[THIRD] = DiaryFragment.newInstance();
            mFragments[FOURTH] = ProfileFragment.newInstance();
//            mFragments[FIFTH] = ExpertSysFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
//                    mFragments[FIFTH]);
        } else {
            // ?????????????????????Fragment??????,?????????????????????????????????, ????????????????????????

            // ????????????????????????mFragments?????????,???????????????getChildFragmentManager.getFragments()????????????????????????(???????????????),????????????????????????????????????
            mFragments[FIRST] = findChildFragment(CellListFragment.class);
            mFragments[SECOND] = findChildFragment(VideoFragment.class);
            mFragments[THIRD] = findChildFragment(DiaryFragment.class);
            mFragments[FOURTH] = findChildFragment(ProfileFragment.class);
//            mFragments[FIFTH] = findChildFragment(ExpertSysFragment.class);
        }

        initView(view);
        return view;
    }

    private void initView(View view) {
//        EventBus.getDefault().register(this);
        mBottomBar = (BottomBar) view.findViewById(R.id.bottomBar);

        mBottomBar
                .addItem(new BottomBarTab(_mActivity, R.mipmap.tab_cruise_normal, "????????????"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.tab_rzhi_normal, "????????????"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.tab_breed_normal, "????????????"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.tab_my_normal, "????????????"));
//                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_expert_white, "????????????"));

        // ??????????????????
        mBottomBar.getItem(FIRST).setUnreadCount(0);

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);

//                BottomBarTab tab = mBottomBar.getItem(FIRST);
//                if (position == FIRST) {
//                    tab.setUnreadCount(0);
//                } else {
//                    tab.setUnreadCount(tab.getUnreadCount() + 1);
//                }
        }

        @Override
        public void onTabUnselected(int position) {

        }

            @Override
            public void onTabReselected(int position) {
                // ??????????????????EventBus????????? -> ??????
                // ???FirstPagerFragment,FirstHomeFragment?????????, ??????????????????Fragment
                // ??????????????????: ??????tab ??????????????????????????????????????????,?????????????????????,?????????
//                EventBus.getDefault().post(new TabSelectedEvent(position));
            }
        });
    }

//    @Override
//    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
//        super.onFragmentResult(requestCode, resultCode, data);
//        if (requestCode == REQ_MSG && resultCode == RESULT_OK) {
//
//        }
//    }



    /**
     * start other BrotherFragment
     */
//    @Subscribe
//    public void startBrother(StartBrotherEvent event) {
//        start(event.targetFragment);
//    }

    @Override
    public void onDestroyView() {
//        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }


}
