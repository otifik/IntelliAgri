package com.jit.AgriIn.ui.activity.config;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.PondUpdateBean;
import com.jit.AgriIn.model.response.PondMainResponse;
import com.jit.AgriIn.ui.presenter.config.PondUpdateAtPresenter;
import com.jit.AgriIn.ui.view.config.PondUpdateAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author zxl on 2018/08/29.
 *         discription: 修改生产单元配置
 */
public class PondUpdateActivity extends BaseActivity<PondUpdateAtView,PondUpdateAtPresenter> implements PondUpdateAtView {
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.ll_pond)
    LinearLayout mLlPond;
    @BindView(R.id.etPondLength)
    EditText mEtPondLength;
    @BindView(R.id.etPondWidth)
    EditText mEtPondWidth;
    @BindView(R.id.etPondDepth)
    EditText mEtPondDepth;
    @BindView(R.id.etMaxPondDepth)
    EditText mEtMaxPondDepth;
    @BindView(R.id.tvPondAddress)
    TextView mTvPondAddress;
    @BindView(R.id.llPondAddress)
    LinearLayout mLlPondAddress;
    @BindView(R.id.etDetailAddress)
    EditText mEtDetailAddress;
    @BindView(R.id.tvInputDirection)
    TextView mTvInputDirection;
    @BindView(R.id.llPondDirection)
    LinearLayout mLlPondDirection;
    @BindView(R.id.tvPondLatitude)
    TextView mTvPondLatitude;
    @BindView(R.id.llPondLatitude)
    LinearLayout mLlPondLatitude;
    @BindView(R.id.etPondName)
    EditText mEtPondName;

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

    private List<String> directionList = new ArrayList<>();
    private int mItemIndex;
    private PondMainResponse mPondBean;
    private boolean isFromPondMain = false;

    @Override
    protected void init() {
        directionList = Arrays.asList(AppConstant.DIRECTION_ARRAYS);
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
            isFromPondMain = getIntent().getBooleanExtra(AppConstant.EXTRA_IS_FROM_POND_MAIN,false);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pond_info_change;
    }

    @Override
    protected PondUpdateAtPresenter createPresenter() {
        return new PondUpdateAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText(R.string.update_pond);
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.save);
        refreshView();
    }

    /**
     * 从展示界面跳转而来并刷新对应的View 
     */
    private void refreshView() {
        mEtPondName.setText(mPondName);
        mEtPondName.setSelection(mPondName.length());

        mEtPondLength.setText(String.valueOf(mPondLength));
        mEtPondLength.setSelection((String.valueOf(mPondLength)).length());
        mEtPondWidth.setText(String.valueOf(mPondWidth));
        mEtPondWidth.setSelection((String.valueOf(mPondWidth)).length());
        mEtPondDepth.setText(String.valueOf(mPondDepth));
        mEtPondDepth.setSelection((String.valueOf(mPondDepth)).length());
        mEtMaxPondDepth.setText(String.valueOf(mPondMaxDepth));
        mEtMaxPondDepth.setSelection((String.valueOf(mPondMaxDepth)).length());
        mEtDetailAddress.setText(mDetailAddress);
        mEtDetailAddress.setSelection(mDetailAddress.length());
        // 设置经纬度
        mTvPondLatitude.setText(getString(R.string.latitude_format_total,
                String.valueOf(eNLongitude), String.valueOf(eNLatitude),
                String.valueOf(eSLongitude),String.valueOf(eSLatitude),
                String.valueOf(wSLongitude),String.valueOf(wSLatitude),
                String.valueOf(wNLongitude),String.valueOf(wNLatitude)));
        mTvInputDirection.setText(mDirection);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressedSupport());

        mTvPublishNow.setOnClickListener(v -> checkData());


        mLlPondDirection.setOnClickListener(v -> directionDialogShow());

        mLlPondLatitude.setOnClickListener(v -> {
            Intent intent = new Intent(PondUpdateActivity.this,LatitudesActivity.class);
            intent.putExtra(AppConstant.EXTRA_IS_CHANGE_LATITUDES,true);
            intent.putExtra(AppConstant.DirectionExtra.LAT_E_N.name(), eNLatitude);
            intent.putExtra(AppConstant.DirectionExtra.LAT_E_S.name(), eSLatitude);
            intent.putExtra(AppConstant.DirectionExtra.LAT_W_N.name(), wNLatitude);
            intent.putExtra(AppConstant.DirectionExtra.LAT_W_S.name(), wSLatitude);
            intent.putExtra(AppConstant.DirectionExtra.LONG_E_S.name(), eSLongitude);
            intent.putExtra(AppConstant.DirectionExtra.LONG_E_N.name(), eNLongitude);
            intent.putExtra(AppConstant.DirectionExtra.LONG_W_S.name(), wSLongitude);
            intent.putExtra(AppConstant.DirectionExtra.LONG_W_N.name(), wNLongitude);
            startActivityForResult(intent,AppConstant.RECODE_MODIFY_LATITUDE);
        });
    }

    private void checkData() {
        mPondName = mEtPondName.getText().toString();
        if (TextUtils.isEmpty(mPondName)){
            UIUtils.showToast(getString(R.string.input_pond_name));
            return;
        }

        String pondLength = mEtPondLength.getText().toString();
        if (TextUtils.isEmpty(pondLength)){
            UIUtils.showToast(getString(R.string.input_pond_length));
            return;
        }
        mPondLength = Float.parseFloat(pondLength);

        String pondWidth = mEtPondWidth.getText().toString();
        if (TextUtils.isEmpty(pondWidth)){
            UIUtils.showToast(getString(R.string.input_pond_width));
            return;
        }
        mPondWidth = Float.parseFloat(pondWidth);

        mDetailAddress = mEtDetailAddress.getText().toString();
        if (TextUtils.isEmpty(mDetailAddress)){
            UIUtils.showToast(getString(R.string.input_pond_detail_address));
            return;
        }

        mDirection = mTvInputDirection.getText().toString();
        if (TextUtils.isEmpty(mDirection)){
            UIUtils.showToast(getString(R.string.choose_pond_direction));
            return;
        }

        String tvPondDepth = mEtPondDepth.getText().toString();
        if (TextUtils.isEmpty(tvPondDepth)){
            UIUtils.showToast(getString(R.string.input_pond_depth));
            return;
        }
        mPondDepth = Float.parseFloat(tvPondDepth);

        String tvPondMaxDepth = mEtMaxPondDepth.getText().toString();
        if (TextUtils.isEmpty(tvPondMaxDepth)){
            UIUtils.showToast(getString(R.string.input_pond_max_depth));
            return;
        }
        mPondMaxDepth = Float.parseFloat(tvPondMaxDepth);

        String tvLatitude = mTvPondLatitude.getText().toString();
        if (TextUtils.isEmpty(tvLatitude)){
            UIUtils.showToast(getString(R.string.input_pond_attitude));
            return;
        }

        if (!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
        }else {
            doPondUpdateCommit();
        }

    }

    private void doPondUpdateCommit() {
        mPresenter.updatePond(mPondID,eNLatitude,eSLatitude,wNLatitude,wSLatitude,
                eNLongitude,eSLongitude,wNLongitude,wSLongitude,mPondWidth,mPondLength,mPondDepth,mPondMaxDepth,mDetailAddress,mPondName,mDirection
        );
    }


    /**
     * 提交完了之后再回传
     * 将更新的值进行传递
     * 将更新的结果回传给显示的生产单元配置 ---
     * 提交成功了执行
     */
    private void setResult(){
        Intent intent = new Intent();
        intent.putExtra(AppConstant.PondNormalConfig.POND_ID.name(),mPondID);
        intent.putExtra(AppConstant.PondNormalConfig.POND_NAME.name(),mPondName);
        intent.putExtra(AppConstant.PondNormalConfig.POND_LENGTH.name(),mPondLength);
        intent.putExtra(AppConstant.PondNormalConfig.POND_WIDTH.name(),mPondWidth);
        intent.putExtra(AppConstant.PondNormalConfig.POND_DEPTH.name(),mPondDepth);
        intent.putExtra(AppConstant.PondNormalConfig.POND_MAX_DEPTH.name(),mPondMaxDepth);
        intent.putExtra(AppConstant.PondNormalConfig.POND_DETAIL_ADDRESS.name(),mDetailAddress);
        intent.putExtra(AppConstant.PondNormalConfig.POND_DIRECTION.name(),mDirection);
        intent.putExtra(AppConstant.DirectionExtra.LAT_E_N.name(), eNLatitude);
        intent.putExtra(AppConstant.DirectionExtra.LAT_E_S.name(), eSLatitude);
        intent.putExtra(AppConstant.DirectionExtra.LAT_W_N.name(), wNLatitude);
        intent.putExtra(AppConstant.DirectionExtra.LAT_W_S.name(), wSLatitude);
        intent.putExtra(AppConstant.DirectionExtra.LONG_E_S.name(), eSLongitude);
        intent.putExtra(AppConstant.DirectionExtra.LONG_E_N.name(), eNLongitude);
        intent.putExtra(AppConstant.DirectionExtra.LONG_W_S.name(), wSLongitude);
        intent.putExtra(AppConstant.DirectionExtra.LONG_W_N.name(), wNLongitude);
        setResult(RESULT_OK,intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case AppConstant.RECODE_MODIFY_LATITUDE:
                    eSLongitude =  data.getFloatExtra(AppConstant.DirectionExtra.LONG_E_S.name(),
                            AppConstant.LATITUDE_DEFAULT);
                    eNLongitude = data.getFloatExtra(AppConstant.DirectionExtra.LONG_E_N.name(),AppConstant.LATITUDE_DEFAULT);
                    wNLongitude = data.getFloatExtra(AppConstant.DirectionExtra.LONG_W_N.name(),AppConstant.LATITUDE_DEFAULT);
                    wSLongitude = data.getFloatExtra(AppConstant.DirectionExtra.LONG_W_S.name(),AppConstant.LATITUDE_DEFAULT);
                    eSLatitude = data.getFloatExtra(AppConstant.DirectionExtra.LAT_E_S.name(),AppConstant.LATITUDE_DEFAULT);
                    eNLatitude = data.getFloatExtra(AppConstant.DirectionExtra.LAT_E_N.name(),AppConstant.LATITUDE_DEFAULT);
                    wSLatitude = data.getFloatExtra(AppConstant.DirectionExtra.LAT_W_S.name(),AppConstant.LATITUDE_DEFAULT);
                    wNLatitude = data.getFloatExtra(AppConstant.DirectionExtra.LAT_W_N.name(),AppConstant.LATITUDE_DEFAULT);
                    mTvPondLatitude.setText(getString(R.string.latitude_format_total,
                            String.valueOf(eNLongitude), String.valueOf(eNLatitude),
                            String.valueOf(eSLongitude),String.valueOf(eSLatitude),
                            String.valueOf(wSLongitude),String.valueOf(wSLatitude),
                            String.valueOf(wNLongitude),String.valueOf(wNLatitude)));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onBackPressedSupport() {
        showMaterialDialog(getString(R.string.info_tips),
                getString(R.string.update_exit),
                getString(R.string.dialog_ensure),
                getString(R.string.dialog_cancel),
                (dialog, which) -> {
                    dialog.dismiss();
                    super.onBackPressedSupport();
                }, (dialog, which) -> dialog.dismiss());
    }

    public void directionDialogShow() {
        new MaterialDialog.Builder(this)
                .title(R.string.pond_direction)
                .items(directionList)
                .itemsCallback((dialog, view, which, text) -> mTvInputDirection.setText(text))
                .positiveText(android.R.string.cancel)
                .show();
    }

    @Override
    public void updatePondSuccess(PondMainResponse response) {
        if (isFromPondMain) {
            PondUpdateBean pondUpdateBean = new PondUpdateBean();
            pondUpdateBean.setItemPosition(mItemIndex);
            pondUpdateBean.setPondMainResponse(response);
            mRxManager.post(AppConstant.RX_UPDATE_POND, pondUpdateBean);
        }else {
            setResult();
        }
        finish();
    }

    @Override
    public void updatePondFailure(String error) {
        UIUtils.showToast(error);
    }
}
