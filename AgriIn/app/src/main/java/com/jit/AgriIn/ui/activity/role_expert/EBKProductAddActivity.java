package com.jit.AgriIn.ui.activity.role_expert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.BaikeProductBean;
import com.jit.AgriIn.ui.presenter.expert_baike.EBKProductAddPresenter;
import com.jit.AgriIn.ui.view.expert_baike.EBKAddAtView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-10-29 15:39:09.
 * Describe:
 */

public class EBKProductAddActivity extends BaseActivity<EBKAddAtView, EBKProductAddPresenter> implements EBKAddAtView {
    public List<LocalMedia> mSingleSelectList = new ArrayList<>();
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.tvProductName)
    TextView mTvProductName;
    @BindView(R.id.etProductName)
    EditText mEtProductName;
    @BindView(R.id.tvProductType)
    TextView mTvProductType;
    @BindView(R.id.etFeedType)
    EditText mEtFeedType;

    @BindView(R.id.tvProductCompany)
    TextView mTvProductCompany;
    @BindView(R.id.etProdPuctCompany)
    EditText mEtProdPuctCompany;
    @BindView(R.id.etRange)
    EditText mEtRange;

    @BindView(R.id.etDef)
    EditText mEtDef;
    private boolean isHeadPicAdd = false;

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ebkproduct_add;
    }

    @Override
    protected EBKProductAddPresenter createPresenter() {
        return new EBKProductAddPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("添加物品百科");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);
        mEtDef.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());



        mTvPublishNow.setOnClickListener(v -> doCheckAndSubmit());
    }

    private void doCheckAndSubmit() {
        String productName = mEtProductName.getText().toString();
        if (TextUtils.isEmpty(productName)){
            UIUtils.showToast(getString(R.string.product_name));
            return;
        }
        String productType = mEtFeedType.getText().toString();
        if (TextUtils.isEmpty(productType)){
            UIUtils.showToast(getString(R.string.product_subkind));
            return;
        }

//        if (!isHeadPicAdd){
//            UIUtils.showToast(getString(R.string.dis_pic));
//            return;
//        }

        String productCompany = mEtProdPuctCompany.getText().toString();
        if (TextUtils.isEmpty(productCompany)){
            UIUtils.showToast("请输入生产公司");
            return;
        }

        String range = mEtRange.getText().toString();
        if (TextUtils.isEmpty(range)){
            UIUtils.showToast("请添加适用范围描述");
            return;
        }

        String def = mEtDef.getText().toString();
        if (TextUtils.isEmpty(def)){
            UIUtils.showToast(getString(R.string.feed_def));
            return;
        }

        BaikeProductBean baikeProductBean = new BaikeProductBean();
        baikeProductBean.setName(productName);
        baikeProductBean.setType(productType);
        baikeProductBean.setCompany(productCompany);
        baikeProductBean.setCrop_use(range);
        baikeProductBean.setContent(def);

        mPresenter.doProductAddRequest(baikeProductBean);
    }

    @Override
    public void ebkAddSuccess() {
        // 发送刷新疾病百科
        mRxManager.post(AppConstant.RX_ADD_PRODUCT_BAIKE,AppConstant.RX_POST_SUCCESS);
        finish();
    }

    @Override
    public void ebkAddFailure(String msg) {
        UIUtils.showToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.SINGLE:
                    mSingleSelectList = PictureSelector.obtainMultipleResult(data);
                    String filePath = mSingleSelectList.get(0).getPath();
                    isHeadPicAdd = true; // 改变图片选择状态
//                    mTvAddHeadPic.setVisibility(View.GONE);
//                    mFlAdd.setVisibility(View.VISIBLE);
//                    mDelete.setVisibility(View.VISIBLE);
//                    Glide.with(mContext).load(filePath).into(mIvHeadPic);
                    break;
                default:
                    break;
            }
        }
    }

}
