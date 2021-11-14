package com.jit.AgriIn.ui.activity.culture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-9-30 15:02:29.
 * Describe:
 */

public class WeatherSelectActivity extends BaseActivity {
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.rvSelectionList)
    RecyclerView mRvSelectionList;
    private List<String> mShowList = new ArrayList<>();
    private BaseQuickAdapter<String,BaseViewHolder> mAdapter;
    private int nowIndex = -1;
    private int preIndex = -1;

    @Override
    protected void init() {
        mShowList.clear();
        mShowList.addAll(Arrays.asList(AppConstant.WEATHER_SHOW));
        if (getIntent() != null){
            String weatherState = getIntent().getStringExtra(AppConstant.EXTRA_WEATHER_STATE);
            if (!weatherState.startsWith(getString(R.string.onClick))){
                nowIndex = mShowList.indexOf(weatherState);
            }
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_weather_select;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText("确认");
        mTvToolbarTitle.setText("选择天气");
        initAdapter();
    }

    private void initAdapter() {
        mRvSelectionList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_weather_list,mShowList) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                ImageView mIvMakeTrue = helper.getView(R.id.ivMakeTrue);
                if (nowIndex == helper.getAdapterPosition()){
                    mIvMakeTrue.setVisibility(View.VISIBLE);
                }else {
                    mIvMakeTrue.setVisibility(View.GONE);
                }
                helper.setText(R.id.tvWeather,item);
            }
        };
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRvSelectionList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

        mIvToolbarNavigation.setOnClickListener(view -> onBackPressed());

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (nowIndex == position){
                return;
            }
            preIndex = nowIndex;
            nowIndex = position;
            if (preIndex != -1) {
                mAdapter.notifyItemChanged(preIndex);
            }
            mAdapter.notifyItemChanged(nowIndex);
        });

        mTvPublishNow.setOnClickListener(view -> {
            if (nowIndex == -1){
                UIUtils.showToast("客官请先选择条目再提交");
                return;
            }

            Intent intent = new Intent();
            intent.putExtra(AppConstant.SINGLE_ITEM_SELECTED,mShowList.get(nowIndex));
            setResult(RESULT_OK,intent);
            finish();
        });
    }
}
