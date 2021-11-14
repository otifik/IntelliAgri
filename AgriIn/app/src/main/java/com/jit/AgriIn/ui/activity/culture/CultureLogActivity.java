package com.jit.AgriIn.ui.activity.culture;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.CultureLogResponse;
import com.jit.AgriIn.ui.presenter.culture.CulLogAtPresenter;
import com.jit.AgriIn.ui.view.culture.CulLogAtView;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.time.TimeUtil;
import com.zxl.baselib.util.ui.ResHelper;
import com.zxl.baselib.util.ui.UIUtils;
import com.zxl.baselib.util.window.DisplayHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-9-30 11:03:34.
 * Describe:  种养日志的活动 （日志按塘口以及日期获取）
 */

public class CultureLogActivity extends BaseActivity<CulLogAtView, CulLogAtPresenter> implements CulLogAtView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.llToolbarTitle)
    LinearLayout mLlToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.llDownload)
    LinearLayout mLlDownload;
    @BindView(R.id.ivDnLog)
    ImageView mIvDnLog;
    @BindView(R.id.llLogAll)
    LinearLayout mLlLogAll;
    @BindView(R.id.llTable)
    LinearLayout mLlTable;
    @BindView(R.id.llSave)
    LinearLayout mLlSave;
    @BindView(R.id.flToolBar)
    FrameLayout mFlToolBar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.tvPondName)
    TextView mTvPondName;
    @BindView(R.id.tvPondLocation)
    TextView mTvPondLocation;
    @BindView(R.id.tvTimeRang)
    TextView mTvTimeRang;
    @BindView(R.id.tvTimeOne)
    TextView mTvTimeOne;
    @BindView(R.id.ivTimeOne)
    ImageView mIvTimeOne;
    @BindView(R.id.llTimeOne)
    LinearLayout mLlTimeOne;
    @BindView(R.id.tvTimeTwo)
    TextView mTvTimeTwo;
    @BindView(R.id.ivTimeTwo)
    ImageView mIvTimeTwo;
    @BindView(R.id.llTimeTwo)
    LinearLayout mLlTimeTwo;
    @BindView(R.id.llTitle)
    LinearLayout mLlTitle;
    @BindView(R.id.rvContent)
    RecyclerView mRvContent;
    @BindView(R.id.scrContent)
    HorizontalScrollView mScrContent;
    @BindView(R.id.tvRecordNum)
    TextView mTvRecordNum;
    private int mPondId = -1;
    private String mPondName;
    private String mPondLocation;

    private BaseQuickAdapter<CultureLogResponse, BaseViewHolder> mAdapter;
    private List<CultureLogResponse> mLogList = new ArrayList<>();

    @Override
    protected void init() {
        if (getIntent() != null) {
            mPondId = getIntent().getIntExtra(AppConstant.POND_ID_SELECTED, -1);
            mPondName = getIntent().getStringExtra(AppConstant.POND_NAME_SELECTED);
            mPondLocation = getIntent().getStringExtra(AppConstant.POND_ADDRESS_SELECTED);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_culture_log;
    }

    @Override
    protected CulLogAtPresenter createPresenter() {
        return new CulLogAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (mPondId == -1) {
            finish();
            return;
        }
        mTvToolbarTitle.setText(R.string.title_farm_log_table);
        mLlLogAll.setVisibility(View.VISIBLE);
        initTime();
        initTitleLog();
        initAdapter();
    }

    private void initAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvContent.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, DisplayHelper.dp2px(mContext, 0.5f), ResHelper.getColor(R.color.bg_line_2)));
        mRvContent.setLayoutManager(manager);
        mAdapter = new BaseQuickAdapter<CultureLogResponse, BaseViewHolder>(R.layout.item_log_content, mLogList) {
            @Override
            protected void convert(BaseViewHolder helper, CultureLogResponse item) {
                helper.setText(R.id.tvData, TimeUtil.getMyTimeDay(item.getDate()));
                helper.setText(R.id.tvWeather, item.getWeather());
                helper.setText(R.id.tvFeedOne, String.valueOf(item.getCount1()));
                helper.setText(R.id.tvFeedTwo, String.valueOf(item.getCount2()));
                helper.setText(R.id.tvFeedThree, String.valueOf(item.getCount3()));
                helper.setText(R.id.tvFeedFour, String.valueOf(item.getCount4()));
                helper.setText(R.id.tvFeedFive, String.valueOf(item.getCount5()));
                helper.setText(R.id.tvFeedSix, String.valueOf(item.getCount6()));
                helper.setText(R.id.tvFeedTotal, String.valueOf(item.getCount_total()));
                helper.setText(R.id.tvPH, item.getPh());
                helper.setText(R.id.tvTemp, item.getTemperature());
                helper.setText(R.id.tvO2, item.getO2());
                helper.setText(R.id.tvMedicine, item.getMedicine());
                TextView tvMedicine = helper.getView(R.id.tvMedicine);
                tvMedicine.setMovementMethod(ScrollingMovementMethod.getInstance());
                helper.setText(R.id.tvRemark, item.getRemark());
                TextView tvRemark = helper.getView(R.id.tvRemark);
                tvRemark.setMovementMethod(ScrollingMovementMethod.getInstance());
            }
        };
        mRvContent.setHasFixedSize(true); //*
        mRvContent.setNestedScrollingEnabled(false); //*
        mRvContent.setAdapter(mAdapter);
    }

    private void initTitleLog() {
        View viewHead = LayoutInflater.from(mContext).inflate(R.layout.ll_log_title, null);
        mLlTitle.addView(viewHead);
    }

    private void initTime() {
        mTvTimeOne.setText(TimeUtil.getPastDate(7));
        mTvTimeTwo.setText(TimeUtil.getPastDate(0));
    }

    @Override
    protected void initData() {
        mTvPondName.setText(mPondName);
        mTvPondLocation.setText(mPondLocation);
        mPresenter.queryPondLogInfoByDate(mPondId, mTvTimeOne.getText().toString(), mTvTimeTwo.getText().toString());
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        mLlTimeOne.setOnClickListener(v -> {
            String[] timeArray = mTvTimeOne.getText().toString().split("-");
            showDataOne(timeArray);
        });

        mLlTimeTwo.setOnClickListener(v -> {
            String[] timeArray = mTvTimeTwo.getText().toString().split("-");
            showDataTwo(timeArray);
        });

        mLlDownload.setOnClickListener(v -> {
            if (mLogList.size() == 0) {
                UIUtils.showToast(getString(R.string.no_log_to_download));
            }else {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstant.EXTRA_DOWNLOAD_NAME,mPondName+ TimeUtil.getPastDate(0));
                bundle.putSerializable(AppConstant.EXTRA_DOWNLOAD_LIST, (Serializable) mLogList);
                jumpToActivity(LogFileCsActivity.class, bundle);
            }
        });

        mIvDnLog.setOnClickListener(v -> {

            jumpToActivity(LogDdListActivity.class);
        });

    }

    private void refreshNum() {
        mTvRecordNum.setText(String.format(getString(R.string.record_count), String.valueOf(mLogList.size())));
    }

    @Override
    public void queryLogSuccess(List<CultureLogResponse> mCulLogList) {
            mLogList.clear();
            mLogList.addAll(mCulLogList);
            mAdapter.notifyDataSetChanged();
            refreshNum();
    }

    @Override
    public void queryLogFailure(String error) {
        UIUtils.showToast(error);
    }

    private void refreshData() {
        mPresenter.queryPondLogInfoByDate(mPondId, mTvTimeOne.getText().toString(), mTvTimeTwo.getText().toString());
    }

    public void showDataOne(String[] timeArray) {
        MaterialDialog dialogOne = new MaterialDialog.Builder(this)
                .title(getString(R.string.title_time_pick))
                .customView(R.layout.dialog_datepicker, false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .showListener(dialog -> ObjectAnimator.ofFloat(mIvTimeOne, View.ROTATION.getName(), 180).start())
                .dismissListener(dialog -> {
                    ObjectAnimator.ofFloat(mIvTimeOne, View.ROTATION.getName(), -180, 0).start();
                    refreshData();
                })
                .onPositive((dialog, which) -> {
                    DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
                    mTvTimeOne.setText(String.format(getString(R.string.time_format), datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth()));
                })
                .build();
        DatePicker datePicker = (DatePicker) dialogOne.findViewById(R.id.datePicker);
        datePicker.init(Integer.parseInt(timeArray[0]), Integer.parseInt(timeArray[1]) - 1, Integer.parseInt(timeArray[2]), null);
        dialogOne.show();

    }

    public void showDataTwo(String[] timeArray) {
        MaterialDialog pickTwo = new MaterialDialog.Builder(this)
                .title(getString(R.string.title_time_pick))
                .customView(R.layout.dialog_datepicker, false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .showListener(dialog -> ObjectAnimator.ofFloat(mIvTimeTwo, View.ROTATION.getName(), 180).start())
                .dismissListener(dialog -> {
                    ObjectAnimator.ofFloat(mIvTimeTwo, View.ROTATION.getName(), -180, 0).start();
                    refreshData();
                })
                .onPositive((dialog, which) -> {
                    DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
                    mTvTimeTwo.setText(String.format(getString(R.string.time_format), datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth()));
                })
                .build();
        DatePicker datePicker = (DatePicker) pickTwo.findViewById(R.id.datePicker);
        datePicker.init(Integer.parseInt(timeArray[0]), Integer.parseInt(timeArray[1]) - 1, Integer.parseInt(timeArray[2]), null);
        pickTwo.show();
    }


}
