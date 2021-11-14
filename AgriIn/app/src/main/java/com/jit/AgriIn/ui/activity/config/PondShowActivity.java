package com.jit.AgriIn.ui.activity.config;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.PondMainResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import butterknife.BindView;

/**
 * @author zxl on 2018/08/29.
 *         discription: 我的塘口
 */
public class PondShowActivity extends BaseActivity {
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.tvPondName)
    TextView mTvPondName;
    @BindView(R.id.ll_pond)
    LinearLayout mLlPond;
    @BindView(R.id.tvPondLength)
    TextView mTvPondLength;
    @BindView(R.id.tvPondWidth)
    TextView mTvPondWidth;
    @BindView(R.id.tvPondDepth)
    TextView mTvPondDepth;
    @BindView(R.id.tvMaxPondDepth)
    TextView mTvMaxPondDepth;
    @BindView(R.id.tvPondAddress)
    TextView mTvPondAddress;
    @BindView(R.id.llPondAddress)
    LinearLayout mLlPondAddress;
    @BindView(R.id.etDetailAddress)
    TextView mEtDetailAddress;
    @BindView(R.id.tvInputDirection)
    TextView mTvInputDirection;
    @BindView(R.id.llPondDirection)
    LinearLayout mLlPondDirection;
    @BindView(R.id.tvPondLatitude)
    TextView mTvPondLatitude;
    @BindView(R.id.llPondLatitude)
    LinearLayout mLlPondLatitude;

    private int mPondID;
    private String mPondName;
    private float mPondLength;
    private float mPondWidth;
    private float mPondDepth;
    private float mPondMaxDepth;
    private String mDetailAddress;
    private float eSLongitude = AppConstant.LATITUDE_DEFAULT;
    private float eSLatitude = AppConstant.LATITUDE_DEFAULT;
    private float eNLongitude = AppConstant.LATITUDE_DEFAULT;
    private float eNLatitude = AppConstant.LATITUDE_DEFAULT;
    private float wSLongitude = AppConstant.LATITUDE_DEFAULT;
    private float wSLatitude = AppConstant.LATITUDE_DEFAULT;
    private float wNLongitude = AppConstant.LATITUDE_DEFAULT;
    private float wNLatitude = AppConstant.LATITUDE_DEFAULT;
    private String mDirection;
    private PondMainResponse mPondBean;
    private int mItemIndex;

    @Override
    protected void init() {
        if (getIntent() != null){
            mPondBean = (PondMainResponse) getIntent().getExtras().getSerializable(AppConstant.BUNDLE_POND_BEAN);
            mPondID = mPondBean.getId();
            mPondName = mPondBean.getName();
            mPondLength =mPondBean.getLength();
            mPondWidth = mPondBean.getWidth();
            mPondDepth = mPondBean.getDepth();
            mPondMaxDepth = mPondBean.getMax_depth();
            mDetailAddress = mPondBean.getLocation();
            eNLatitude = mPondBean.getLatitude1();
            eSLatitude = mPondBean.getLatitude2();
            wNLatitude = mPondBean.getLatitude3();
            eSLatitude = mPondBean.getLatitude4();
            eNLongitude = mPondBean.getLongitude1();
            eSLongitude = mPondBean.getLongitude2();
            wNLongitude = mPondBean.getLongitude3();
            wSLongitude = mPondBean.getLongitude4();
            mDirection = mPondBean.getToward();
            mItemIndex = getIntent().getIntExtra(AppConstant.EXTRA_ITEM_INDEX,-1);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pond_info_show;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText(R.string.title_my_pond_config);
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.modify);

    }

    @Override
    protected void initData() {
        getConfigWithSet();
    }

    /**
     * 获得并设置塘口数据
     * (这里还是可以执行刷新的----)
     */
    private void getConfigWithSet() {
        refreshView();
    }

    private void refreshView() {
        mTvPondName.setText(mPondName);
        mTvPondLength.setText(String.valueOf(mPondLength));
        mTvPondWidth.setText(String.valueOf(mPondWidth));
        mTvPondDepth.setText(String.valueOf(mPondDepth));
        mTvMaxPondDepth.setText(String.valueOf(mPondMaxDepth));
        mEtDetailAddress.setText(mDetailAddress);
        // 设置经纬度
        mTvPondLatitude.setText(getString(R.string.latitude_format_total,
                String.valueOf(eNLongitude), String.valueOf(eNLatitude),
                String.valueOf(eSLongitude),String.valueOf(eSLatitude),
                String.valueOf(wSLongitude),String.valueOf(wSLatitude),
                String.valueOf(wNLongitude),String.valueOf(wNLatitude)));
        mTvInputDirection.setText(mDirection);
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        mTvPublishNow.setOnClickListener(v -> {
            Intent intent = new Intent(PondShowActivity.this,PondUpdateActivity.class);
            intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,mItemIndex);
            intent.putExtra(AppConstant.EXTRA_IS_FROM_POND_MAIN,false);
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstant.BUNDLE_POND_BEAN,mPondBean);
            intent.putExtras(bundle);
            PondShowActivity.this.startActivityForResult(intent,AppConstant.RECODE_MODIFY_POND_CONFIG);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case AppConstant.RECODE_MODIFY_POND_CONFIG:
                    eSLongitude =  data.getFloatExtra(AppConstant.DirectionExtra.LONG_E_S.name(),
                            AppConstant.LATITUDE_DEFAULT);
                    eNLongitude = data.getFloatExtra(AppConstant.DirectionExtra.LONG_E_N.name(),AppConstant.LATITUDE_DEFAULT);
                    wNLongitude = data.getFloatExtra(AppConstant.DirectionExtra.LONG_W_N.name(),AppConstant.LATITUDE_DEFAULT);
                    wSLongitude = data.getFloatExtra(AppConstant.DirectionExtra.LONG_W_S.name(),AppConstant.LATITUDE_DEFAULT);
                    eSLatitude  =  data.getFloatExtra(AppConstant.DirectionExtra.LAT_E_S.name(),AppConstant.LATITUDE_DEFAULT);
                    eNLatitude =  data.getFloatExtra(AppConstant.DirectionExtra.LAT_E_N.name(),AppConstant.LATITUDE_DEFAULT);
                    wSLatitude =  data.getFloatExtra(AppConstant.DirectionExtra.LAT_W_S.name(),AppConstant.LATITUDE_DEFAULT);
                    wNLatitude =  data.getFloatExtra(AppConstant.DirectionExtra.LAT_W_N.name(),AppConstant.LATITUDE_DEFAULT);
                    mPondID =  data.getIntExtra(AppConstant.PondNormalConfig.POND_ID.name(),-1);
                    mPondName = data.getStringExtra(AppConstant.PondNormalConfig.POND_NAME.name());
                    mPondLength = data.getFloatExtra(AppConstant.PondNormalConfig.POND_LENGTH.name(),AppConstant.LATITUDE_DEFAULT);
                    mPondWidth = data.getFloatExtra(AppConstant.PondNormalConfig.POND_WIDTH.name(),AppConstant.LATITUDE_DEFAULT);
                    mPondDepth = data.getFloatExtra(AppConstant.PondNormalConfig.POND_DEPTH.name(),AppConstant.LATITUDE_DEFAULT);
                    mPondMaxDepth = data.getFloatExtra(AppConstant.PondNormalConfig.POND_MAX_DEPTH.name(),AppConstant.LATITUDE_DEFAULT);
                    mDetailAddress = data.getStringExtra(AppConstant.PondNormalConfig.POND_DETAIL_ADDRESS.name());
                    mDirection = data.getStringExtra(AppConstant.PondNormalConfig.POND_DIRECTION.name());
                    // 重新获得数据之后再进行刷新 ---
                    refreshView();
                    break;
                default:
                    break;
            }
        }
    }
}
