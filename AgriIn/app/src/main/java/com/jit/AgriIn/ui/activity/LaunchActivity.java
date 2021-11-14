package com.jit.AgriIn.ui.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.jit.AgriIn.R;
import com.jit.AgriIn.factory.LaunchFgFactory;
import com.jit.AgriIn.ui.fragment.DetFragment;
import com.jit.AgriIn.ui.fragment.MeFragment;
import com.jit.AgriIn.ui.fragment.MsgFragment;
import com.jit.AgriIn.ui.fragment.NewsFragment;
import com.roughike.bottombar.BottomBar;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author zxl on 2018/08/17.
 *         discription: 启动界面
 */
public class LaunchActivity extends BaseActivity {
    @BindView(R.id.fgMain)
    FrameLayout mFgMain;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    private SupportFragment[] mSupportFragments = new SupportFragment[4];
    private static final int DET = 0;
    private static  final  int NEWS = 1;
    private static final  int MSG = 2;
    private static final int ME = 3;
    private int prePosition;
    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_launch;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null){
            mSupportFragments[DET] = LaunchFgFactory.getFactoryInstance().getDetFgInstance();
            mSupportFragments[NEWS] = LaunchFgFactory.getFactoryInstance().getNewFgInstance();
            mSupportFragments[MSG] = LaunchFgFactory.getFactoryInstance().getMsgInstance();
            mSupportFragments[ME] = LaunchFgFactory.getFactoryInstance().getMeFgInstacne();
            loadMultipleRootFragment(R.id.fgMain,DET,
                    mSupportFragments[DET],
                    mSupportFragments[NEWS],
                    mSupportFragments[MSG],
                    mSupportFragments[ME]);
        }else {
            mSupportFragments[DET] = findFragment(DetFragment.class);
            mSupportFragments[NEWS] = findFragment(NewsFragment.class) ;
            mSupportFragments[MSG] = findFragment(MsgFragment.class);
            mSupportFragments[ME] = findFragment(MeFragment.class) ;

        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
//        mBottomBar.setOnTabSelectListener(tabId -> {
//            switch (tabId){
//                case R.id.tabDet:
//                    showHideFragment(mSupportFragments[DET],mSupportFragments[prePosition]);
//                    prePosition = DET;
//                    break;
//                case R.id.tabMsg:
//                    showHideFragment(mSupportFragments[MSG],mSupportFragments[prePosition]);
//                    prePosition = MSG;
//                    break;
//                case R.id.tabNews:
//                    showHideFragment(mSupportFragments[NEWS],mSupportFragments[prePosition]);
//                    prePosition = NEWS;
//                    break;
//                case R.id.tabMe:
//                    showHideFragment(mSupportFragments[ME],mSupportFragments[prePosition]);
//                    prePosition = ME;
//                    break;
//                default:
//                    break;
//            }
//        });



    }

}
