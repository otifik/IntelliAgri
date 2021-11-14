package com.jit.AgriIn.uinew.second.rizhi;

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
import com.jit.AgriIn.model.bean.RizhiUpdateBean;
import com.jit.AgriIn.model.response.RizhiResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.ui.UIUtils;

import butterknife.BindView;

/**
 * @author zxl on 2018/08/29.
 *         discription: 新增塘口配置
 */
public class RizhiUpdateActivity extends BaseActivity<RizhiUpdateView,RizhiUpdatePresenter> implements RizhiUpdateView {


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
    @BindView(R.id.tvGudingType)
    TextView mTvGudingType;
    @BindView(R.id.etDescription)
    EditText mEtRizhiContent;

    private RizhiResponse mRizhiResponse;
    private String mRizhiName;
    private String mRizhiContent;
    private int mItemIndex;
    private int mRizhiId;
    private boolean isFromPondMain = false;

    @Override
    protected void init() {
        if (getIntent() != null){
            mRizhiResponse = (RizhiResponse) getIntent().getExtras().getSerializable(AppConstant.BUNDLE_POND_BEAN);
            mRizhiName = mRizhiResponse.getName();
            mRizhiContent = mRizhiResponse.getContent();
            mRizhiId = mRizhiResponse.getId();
            mItemIndex = getIntent().getIntExtra(AppConstant.EXTRA_ITEM_INDEX,-1);
            isFromPondMain = getIntent().getBooleanExtra(AppConstant.EXTRA_IS_FROM_POND_MAIN,false);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_rizhi_add;
    }

    @Override
    protected RizhiUpdatePresenter createPresenter() {
        return new RizhiUpdatePresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("修改固定成本");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);

        mTvGudingType.setText(mRizhiName);
        mEtRizhiContent.setText(mRizhiContent);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());


        mTvPublishNow.setOnClickListener(v -> requestSubmit());

        mTvGudingType.setOnClickListener(view -> mPresenter.getObserveType());
    }

    /**
     * 进行数据的提交
     */
    private void requestSubmit() {
        checkFormatData();
    }

    /**
     * 检查数据格式执行判空操作
     */
    private void checkFormatData() {



        String gudingType = mTvGudingType.getText().toString();
        if (TextUtils.isEmpty(gudingType)){
            UIUtils.showToast("请选择巡视事件类型");
            return;
        }

        String rizhiContent = mEtRizhiContent.getText().toString();
        if (TextUtils.isEmpty(rizhiContent)){
            UIUtils.showToast("请输入事件详细内容");
            return;
        }



        if (!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doPondAddCommit(gudingType,rizhiContent);
        }
    }


    private void doPondAddCommit(String rizhiName, String rizhiContent) {
        mPresenter.updateRizhi(rizhiName,rizhiContent,0,mRizhiId);
    }




    @Override
    public void updateRizhiSuccess(RizhiResponse rizhiResponse) {
        RizhiUpdateBean rizhiUpdateBean = new RizhiUpdateBean();
        rizhiUpdateBean.setItemPosition(mItemIndex);
        rizhiUpdateBean.setRizhiResponse(rizhiResponse);
        mRxManager.post(AppConstant.RX_UPDATE_POND, rizhiUpdateBean);
        finish();
    }

    @Override
    public void updateRizhiFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getRizhiTypeSuccess(String[] gudingType) {
        new MaterialDialog.Builder(this)
                .title("巡视事件类型")
                .items(gudingType)
                .itemsCallback((dialog, view, which, text) -> mTvGudingType.setText(text))
                .positiveText(android.R.string.cancel)
                .show();
    }

    @Override
    public void getRizhiTypeFailure(String error) {
        UIUtils.showToast(error);
    }
}
