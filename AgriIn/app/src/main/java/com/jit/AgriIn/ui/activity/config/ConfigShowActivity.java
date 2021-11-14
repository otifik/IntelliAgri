package com.jit.AgriIn.ui.activity.config;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.ConfigUpdateBean;
import com.jit.AgriIn.model.response.ConfigMainResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author zxl on 2018/09/05.
 *         discription: 配置金科智农完成界面
 */
public class ConfigShowActivity extends BaseActivity {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tvPondName)
    TextView mTvPondName;
    @BindView(R.id.tvRobotNum)
    TextView mTvRobotNum;
    @BindView(R.id.ivEditor)
    ImageView mIvEditor;
    @BindView(R.id.tvCruiseMode)
    TextView mTvCruiseMode;
    @BindView(R.id.tvCameraDepth)
    TextView mTvCameraDepth;
    @BindView(R.id.tvSensorDepth)
    TextView mTvSensorDepth;
    @BindView(R.id.tvCruiseSpeed)
    TextView mTvCruiseSpeed;
    @BindView(R.id.tvCruiseNum)
    TextView mTvCruiseNum;
    @BindView(R.id.tvCruiseReturn)
    TextView mTvCruiseReturn;
    @BindView(R.id.tvBirdState)
    TextView mTvBirdState;
    @BindView(R.id.cv)
    CardView mCv;
    @BindView(R.id.tvFeedName)
    TextView mTvFeedName;
    @BindView(R.id.tvFeedWeight)
    TextView mTvFeedWeight;
    @BindView(R.id.tvFeedSpeed)
    TextView mTvFeedSpeed;
    @BindView(R.id.llFeed)
    LinearLayout mLlFeed;
    @BindView(R.id.tvDrugName)
    TextView mTvDrugName;
    @BindView(R.id.tvDrugWeight)
    TextView mTvDrugWeight;
    @BindView(R.id.tvDrugSpeed)
    TextView mTvDrugSpeed;
    @BindView(R.id.llDrug)
    LinearLayout mLlDrug;
    @BindView(R.id.tvHand)
    TextView mTvHand;
    @BindView(R.id.tvMachine)
    TextView mTvMachine;
    @BindView(R.id.tvCategory)
    TextView mTvCategory;
    /**
     * 数据Bean
     */
    private ConfigMainResponse mConfigBean;
    /**
     * rv 中对应的索引ID
     */
    private int mIndex;

    private List<String> mCruiseModeList;
    private List<String> mSpeedList;


    @Override
    protected void init() {
        if (getIntent() !=  null){
            mConfigBean = (ConfigMainResponse) getIntent().getSerializableExtra(AppConstant.BUNDLE_CONFIG_BEAN);
            mIndex = getIntent().getIntExtra(AppConstant.EXTRA_ITEM_INDEX, -1);
        }
        mCruiseModeList = Arrays.asList(getResources().getStringArray(R.array.array_cruise_mode));
        mSpeedList = Arrays.asList(getResources().getStringArray(R.array.array_cruise_speed));
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_config_show;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("配置详情");
    }



    @Override
    protected void initData() {
        refreshView();

        mRxManager.on(AppConstant.RX_UPDATE_CONFIG, (Consumer<ConfigUpdateBean>) configUpdateBean -> {
            mConfigBean = configUpdateBean.getConfigMainResponse();
            refreshView();
        });
    }

    /**
     * 刷新视图数据
     */
    private void refreshView(){
        if (mConfigBean == null){
            return;
        }
        mTvPondName.setText(mConfigBean.getPound_name());
        mTvRobotNum.setText(mConfigBean.getRobert_name());
        mTvCruiseMode.setText(mCruiseModeList.get(mConfigBean.getType()));
        mTvCameraDepth.setText(String.valueOf(mConfigBean.getCamera_depth()));
        mTvSensorDepth.setText(String.valueOf(mConfigBean.getSensor_depth()));
        mTvCruiseSpeed.setText(mSpeedList.get(mConfigBean.getCruise_velocity()));
        mTvCruiseReturn.setText(mSpeedList.get(mConfigBean.getReturn_velocity()));
        mTvCruiseNum.setText(String.valueOf(mConfigBean.getCircle()));
        mTvBirdState.setText(mConfigBean.getBird_status() == 0 ? getString(R.string.close) : getString(R.string.open));
        mTvFeedName.setText(mConfigBean.getFeed_name());
        mTvFeedWeight.setText(String.valueOf(mConfigBean.getFeed_weight()));
        mTvFeedSpeed.setText(mSpeedList.get(mConfigBean.getFeed_velocity()));
        mTvDrugName.setText(mConfigBean.getMedicine_name());
        mTvDrugWeight.setText(String.valueOf(mConfigBean.getMedicine_weight()));
        mTvDrugSpeed.setText(mSpeedList.get(mConfigBean.getMedicine_velocity()));
        mTvCategory.setText(mConfigBean.getCategory());
    }

    private void jumpToConfigEdit() {
        Intent intent = new Intent(this,ConfigUpdateActivity.class);
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,mIndex);
        intent.putExtra(AppConstant.EXTRA_IS_FROM_CONFIG_MAIN,false);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_CONFIG_BEAN,mConfigBean);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(view -> onBackPressed());

        mIvEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToConfigEdit();
            }
        });

        mTvHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mTvMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
