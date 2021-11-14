package com.jit.AgriIn.ui.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jit.AgriIn.R;
import com.zxl.baselib.ui.base.BaseFragment;
import com.zxl.baselib.ui.base.BasePresenter;

import butterknife.BindView;

/**
 * @author zxl on 2018/8/17.
 *         discription:
 */

public class NewsFragment extends BaseFragment {
    @BindView(R.id.tv)
    TextView mTv;

    @Override
    public void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_launch;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initView(View rootView) {
        Log.e("initView"," ---- 第二个Fragment-----");
    }

    @Override
    public void initData() {
        mTv.setText(NewsFragment.class.getName());
        Log.e("initData"," ---- 第二个Fragment-----");
    }

    @Override
    public void initListener() {

    }
}
