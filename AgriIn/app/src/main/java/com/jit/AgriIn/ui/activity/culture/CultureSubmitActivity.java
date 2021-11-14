package com.jit.AgriIn.ui.activity.culture;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.request.InsertFeedRequest;
import com.jit.AgriIn.model.request.InsertWaterRequest;
import com.jit.AgriIn.model.response.PondMainResponse;
import com.jit.AgriIn.ui.presenter.culture.CultureSubAtPresenter;
import com.jit.AgriIn.ui.view.culture.CultureSubAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.time.TimeUtil;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-9-30 11:07:24.
 * Describe: 种养日志提交的活动(分为水质和投喂数量的提交)----
 * <p>
 * 溶解氧 / ph / 温度 有效
 */

public class CultureSubmitActivity extends BaseActivity<CultureSubAtView, CultureSubAtPresenter> implements CultureSubAtView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.ll_pond)
    LinearLayout mLlPond;
    @BindView(R.id.tvPondSelected)
    TextView mTvPondSelected;
    @BindView(R.id.tvTimeSelected)
    TextView mTvTimeSelected;
    @BindView(R.id.ll_inputTime)
    LinearLayout mLlInputTime;
    @BindView(R.id.tvWeatherSelected)
    TextView mTvWeatherSelected;
    @BindView(R.id.ll_inputWeather)
    LinearLayout mLlInputWeather;
    @BindView(R.id.etTmpInput)
    EditText mEtTmpInput;
    @BindView(R.id.ll_nano2)
    LinearLayout mLlNano2;
    @BindView(R.id.etPHInput)
    EditText mEtPHInput;
    @BindView(R.id.ll_nh)
    LinearLayout mLlNh;
    @BindView(R.id.etO2Input)
    EditText mEtO2Input;
    @BindView(R.id.ll_o2)
    LinearLayout mLlO2;
    @BindView(R.id.tvTypeDefine)
    TextView mTvTypeDefine;
    @BindView(R.id.rl_typeDefine)
    RelativeLayout mRlTypeDefine;
    @BindView(R.id.tvReMarkInput)
    TextView mTvReMarkInput;
    @BindView(R.id.rl_ReMark)
    RelativeLayout mRlReMark;
    @BindView(R.id.tvAddFeed)
    TextView mTvAddFeed;
    @BindView(R.id.tvDelFeed)
    TextView mTvDelFeed;
    @BindView(R.id.llFeedIncrease)
    LinearLayout mLlFeedIncrease;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.logTab)
    FloatingActionButton mFloatingActionButtonLog;
    @BindView(R.id.scrollview)
    NestedScrollView mNestedScrollView;

    private List<String> mPondStrList = new ArrayList<>();
    private List<PondMainResponse> mPondBenList = new ArrayList<>();
    private int mSelectPosition = -1;
    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_culture_submit;
    }

    @Override
    protected CultureSubAtPresenter createPresenter() {
        return new CultureSubAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("新增日志");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText("保存");
        // 初始化时间
        mTvTimeSelected.setText(TimeUtil.date2String
                (new Date(System.currentTimeMillis()), "yyyy-MM-dd"));
    }

    @Override
    protected void initData() {
        mPresenter.queryPondInfo();
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(view -> onBackPressed());

        mTvPublishNow.setOnClickListener(view -> doSubmit());

        mTvAddFeed.setOnClickListener(v -> addFeed());

        mTvDelFeed.setOnClickListener(v -> delFeed());

        mLlPond.setOnClickListener(v -> showPondList());

        mLlInputTime.setOnClickListener(v -> showCustomDatePicker());

        mRlReMark.setOnClickListener(v -> {
            Intent intent = new Intent(this, KeyInputActivity.class);
            intent.putExtra(AppConstant.KEY_INPUT_TITLE, getString(R.string.str_title_write_remark));
            intent.putExtra(AppConstant.KEY_TO_INPUT_CONTENT, mTvReMarkInput.getText().toString());
            startActivityForResult(intent, AppConstant.RCODE_INPUT_FROM_REMARK_DETAIL);
        });

        mRlTypeDefine.setOnClickListener(v -> {
            Intent intent = new Intent(this, KeyInputActivity.class);
            intent.putExtra(AppConstant.KEY_INPUT_TITLE, getString(R.string.str_title_write_type_define));
            intent.putExtra(AppConstant.KEY_TO_INPUT_CONTENT, mTvTypeDefine.getText().toString());
            startActivityForResult(intent, AppConstant.RCODE_INPUT_FROM_TYPE_DEFINE);
        });

        mLlInputWeather.setOnClickListener(view -> {
            Intent intent = new Intent(CultureSubmitActivity.this,WeatherSelectActivity.class);
            intent.putExtra(AppConstant.EXTRA_WEATHER_STATE,mTvWeatherSelected.getText().toString().trim());
            startActivityForResult(intent,AppConstant.RECODE_TO_SINGLE_ITEM_LIST);
        });

        /**
         * 跳转向种养日志的界面
         */
        mFloatingActionButtonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToActivity(CultureMainActivity.class);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mNestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    boolean isSignificantDelta = Math.abs(scrollY -oldScrollY) > ViewConfiguration.getTouchSlop();
                    if (isSignificantDelta) {
                        // 判定滑动的方向
                        if ((scrollY-oldScrollY) > 0) {
                            hideLogTab();
                        } else {
                            showLogTab();
                        }
                    }
                }
            });
        }

    }

    private void showLogTab() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
                 Animation.RELATIVE_TO_PARENT,1,Animation.ABSOLUTE, 0);
        translateAnimation.setDuration(500);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        mFloatingActionButtonLog.startAnimation(translateAnimation);
    }

    private void hideLogTab() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_PARENT, 1);
        translateAnimation.setDuration(500);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        mFloatingActionButtonLog.startAnimation(translateAnimation);
    }

    /**
     * 检测完了进行提交即可
     */
    @SuppressWarnings("AlibabaMethodTooLong")
    private void doSubmit() {
        if (mSelectPosition == -1){
            UIUtils.showToast("请添加对应塘口再进行日志提交");
            return;
        }

        String time = mTvTimeSelected.getText().toString();
        if (TextUtils.isEmpty(time)){
            UIUtils.showToast("请选择投放时间");
            return;
        }

        String weather = mTvWeatherSelected.getText().toString();
        if (TextUtils.isEmpty(weather)){
            UIUtils.showToast("请选择投放天气");
            return;
        }

        String temp = mEtTmpInput.getText().toString();
        if (TextUtils.isEmpty(temp)){
            UIUtils.showToast("请填写温度");
            return;
        }

        String ph = mEtPHInput.getText().toString();
        if (TextUtils.isEmpty(ph)){
            UIUtils.showToast("请填写PH");
            return;
        }

        String o2 = mEtO2Input.getText().toString();
        if (TextUtils.isEmpty(o2)){
            UIUtils.showToast("请填写溶解氧");
            return;
        }

        String drugDefine = mTvTypeDefine.getText().toString();
        String remark = mTvReMarkInput.getText().toString();
        InsertWaterRequest requestWater = new InsertWaterRequest();
        requestWater.setDate(time);
        requestWater.setTemperature(temp);
        requestWater.setPound_id(mPondBenList.get(mSelectPosition).getId());
        requestWater.setO2(o2);
        requestWater.setPh(ph);
        requestWater.setWeather(weather);
        requestWater.setMedicine(drugDefine);
        requestWater.setRemark(remark);

        InsertFeedRequest requestFeed = new InsertFeedRequest();
        requestFeed.setDate(time);
        requestFeed.setPound_id(mPondBenList.get(mSelectPosition).getId());
        if (mLlFeedIncrease.getChildCount() != 0) {
            for (int i = 0; i < mLlFeedIncrease.getChildCount(); i++) {
                View view = mLlFeedIncrease.getChildAt(i);
                EditText etNum = view.findViewById(R.id.tvFeedNum);
                // 这个是啥 我给忘记了
                etNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                long seedNum = 0;
                String trim = etNum.getText().toString().trim();
                if (!TextUtils.isEmpty(trim)){
                    seedNum = Long.parseLong(trim);
                }
                switch (i) {
                    case 0:
                        requestFeed.setCount1(seedNum);
                        break;
                    case 1:
                        requestFeed.setCount2(seedNum);
                        break;
                    case 2:
                        requestFeed.setCount3(seedNum);
                        break;
                    case 3:
                        requestFeed.setCount4(seedNum);
                        break;
                    case 4:
                        requestFeed.setCount5(seedNum);
                        break;
                    case 5:
                        requestFeed.setCount6(seedNum);
                        break;
                    default:
                        break;
                }
            }
        }
        mPresenter.submitWaterWithFeed(requestWater,requestFeed);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case AppConstant.RCODE_INPUT_FROM_REMARK_DETAIL:
                    mTvReMarkInput.setText(data.getStringExtra(AppConstant.KEY_FROM_INPUT));
                    break;
                case AppConstant.RCODE_INPUT_FROM_TYPE_DEFINE:
                    mTvTypeDefine.setText(data.getStringExtra(AppConstant.KEY_FROM_INPUT));
                    break;
                case AppConstant.RECODE_TO_SINGLE_ITEM_LIST:
                    mTvWeatherSelected.setText(data.getStringExtra(AppConstant.SINGLE_ITEM_SELECTED));
                    break;
                    default:
                        break;
            }
        }
    }

    public void showCustomDatePicker() {
        new MaterialDialog.Builder(this)
                .title("时间选择")
                .customView(R.layout.dialog_datepicker, false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which) -> {
                    DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
                    mTvTimeSelected.setText(String.format("%d-%02d-%d", datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth()));
                })
                .show();
    }

    public void showPondList() {
        if (mPondStrList.size() == 0) {
            return;
        }
        new MaterialDialog.Builder(this)
                .title("塘口选择")
                .items(mPondStrList)
                .itemsCallback((dialog, view, which, text) -> {
                    mSelectPosition = which;
                    mTvPondSelected.setText(text);
                })
                .positiveText(android.R.string.cancel)
                .show();
    }

    /***
     * 减少餐数
     * */
    private void delFeed() {
        if (mLlFeedIncrease.getChildCount() != 0) {
            mLlFeedIncrease.removeViewAt(mLlFeedIncrease.getChildCount() - 1);
        } else {
            UIUtils.showToast("无餐数可减少");
        }
    }

    /**
     * 增加餐数
     * */
    private void addFeed() {
        if (mLlFeedIncrease.getChildCount() >= AppConstant.MAX_FEED_NUM) {
            UIUtils.showToast("每日仅可投放6餐");
        } else {
            View feedView = LayoutInflater.from(this).inflate(R.layout.item_feed_mg, null);
            TextView tvID = feedView.findViewById(R.id.tvID);
            tvID.setText(String.valueOf(mLlFeedIncrease.getChildCount() + 1));
            mLlFeedIncrease.addView(feedView);
        }
    }

    @Override
    public void cultureSubmitSuccess() {
        UIUtils.showToast("提交成功!");
        initViewToOrigin();
    }

    /**
     *设置View为初始状态---
     * */
    private void initViewToOrigin() {
        mTvTimeSelected.setText(TimeUtil.date2String
                (new Date(System.currentTimeMillis()), "yyyy-MM-dd"));
        mEtTmpInput.setText("");
        mTvWeatherSelected.setText("");
        mEtO2Input.setText("");
        mEtPHInput.setText("");
        mTvTypeDefine.setText("");
        mTvReMarkInput.setText("");
        mLlFeedIncrease.removeAllViews();
    }

    @Override
    public void cultureSubmitFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getPondInfoSuccess(List<PondMainResponse> pondBeanList, List<String> pondStrList) {
        mPondBenList.clear();
        mPondBenList.addAll(pondBeanList);
        mPondStrList.clear();
        mPondStrList.addAll(pondStrList);
        if (mPondStrList != null && mPondStrList.size()!= 0){
            mTvPondSelected.setText(mPondStrList.get(0));
            mSelectPosition = 0;
        }
    }

    @Override
    public void getPondInfoFailure(String error) {
        UIUtils.showToast(error);
    }

}
