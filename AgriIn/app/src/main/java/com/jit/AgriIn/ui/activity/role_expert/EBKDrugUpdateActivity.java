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
import com.jit.AgriIn.model.bean.EBKDrugUpdateBean;
import com.jit.AgriIn.model.response.BaikeDrugBean;
import com.jit.AgriIn.ui.presenter.expert_baike.EBKUpdateDrugAtPresenter;
import com.jit.AgriIn.ui.view.expert_baike.EBKUpdateDrugAtView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-10-30 13:26:50.
 * Describe:
 */

public class EBKDrugUpdateActivity extends BaseActivity<EBKUpdateDrugAtView,EBKUpdateDrugAtPresenter> implements EBKUpdateDrugAtView {
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.tvDiseaseName)
    TextView mTvDiseaseName;
    @BindView(R.id.etDiseaseName)
    EditText mEtDiseaseName;
    @BindView(R.id.tvDiseaseType)
    TextView mTvDiseaseType;
    @BindView(R.id.etDiseaseType)
    EditText mEtDiseaseType;
//    @BindView(R.id.flAdd)
//    FrameLayout mFlAdd;
    @BindView(R.id.tvCompany)
    TextView mTvCompany;
    @BindView(R.id.etCompany)
    EditText mEtCompany;
    @BindView(R.id.etDef)
    EditText mEtDef;

    public List<LocalMedia> mSingleSelectList = new ArrayList<>();
    private boolean isHeadPicAdd = true;
    private boolean isHavingChanged = false;
    private int baikeID;
    private int mItemIndex;
    private BaikeDrugBean mBaikeDrugBean = new BaikeDrugBean();

    @Override
    protected void init() {
        // 从上一个活动接受数据
        if (getIntent() != null){
            baikeID =  getIntent().getIntExtra(AppConstant.EXTRA_BAIKE_ID,-1);
            mItemIndex = getIntent().getIntExtra(AppConstant.EXTRA_ITEM_INDEX, -1);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ebkdrug_add;
    }

    @Override
    protected EBKUpdateDrugAtPresenter createPresenter() {
        return new EBKUpdateDrugAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("修改药品百科");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.save);
        if (baikeID == 0 || baikeID == -1){
            finish();
        }
        mEtDef.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    protected void initData() {
        mPresenter.queryBaikeDetail(baikeID);
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

//        // 点击添加封面图 =======
//        mTvAddHeadPic.setOnClickListener(v -> {
//            mSingleSelectList.clear();
//            PictureSHelper.getInstance().chooseSinglePictureEvent(this, mSingleSelectList, PictureConfig.SINGLE);
//        });
//
//        mIvHeadPic.setOnClickListener(v -> {
//            // 这里就是展示图片咯
//            //ShowBigImageActivity.startAction(mContext, mIvHeadPic, mSingleSelectList.get(0).getPath());
//        });
//
//        mDelete.setOnClickListener(v -> {
//            mDelete.setVisibility(View.GONE);
//            mFlAdd.setVisibility(View.GONE);
//            mTvAddHeadPic.setVisibility(View.VISIBLE);
//            isHeadPicAdd = false;
//            isHavingChanged = true;
//        });

        mTvPublishNow.setOnClickListener(v -> doCheckAndSubmit());
    }

    private void doCheckAndSubmit() {
        String drugName = mEtDiseaseName.getText().toString();
        if (TextUtils.isEmpty(drugName)){
            UIUtils.showToast(getString(R.string.drug_name));
            return;
        }
        String drugType = mEtDiseaseType.getText().toString();
        if (TextUtils.isEmpty(drugType)){
            UIUtils.showToast(getString(R.string.drug_subkind));
            return;
        }
        if (!isHeadPicAdd){
            UIUtils.showToast(getString(R.string.dis_pic));
            return;
        }

//        String price = mEtPrice.getText().toString();
//        if (TextUtils.isEmpty(price)){
//            UIUtils.showToast(getString(R.string.pls_bk_price));
//            return;
//        }
        String company = mEtCompany.getText().toString();
        if (TextUtils.isEmpty(company)){
            UIUtils.showToast(getString(R.string.pls_bk_company));
            return;
        }

//        String telName = mEtTeiLphone.getText().toString();
//        if (TextUtils.isEmpty(telName)){
//            UIUtils.showToast(getString(R.string.pls_bk_tel));
//            return;
//        }

//        String address = mEtAddress.getText().toString();
//        if (TextUtils.isEmpty(address)){
//            UIUtils.showToast(getString(R.string.pls_bk_address));
//            return;
//        }

        String def = mEtDef.getText().toString();
        if (TextUtils.isEmpty(def)){
            UIUtils.showToast(getString(R.string.drug_defination));
            return;
        }
        mBaikeDrugBean.setName(drugName);
        mBaikeDrugBean.setType(drugType);
        mBaikeDrugBean.setFromSource(company);
        mBaikeDrugBean.setContent(def);
//        if (isHavingChanged) {
//            mPresenter.doDDrugBaikeUpdateWithPic(baikeID, drugName, drugType, price, telName, address, company, def, mSingleSelectList.get(0).getPath());
//        }else {
            mPresenter.doDDrugBaikeUpdateNoPic(baikeID, mBaikeDrugBean);
//        }
    }

    @Override
    public void queryBaikeShowSuccess(BaikeDrugBean baikeDrugBean) {
//        mTvAddHeadPic.setVisibility(View.GONE);
//        mFlAdd.setVisibility(View.VISIBLE);
//        mDelete.setVisibility(View.VISIBLE);
        if (baikeDrugBean != null){
            mBaikeDrugBean = baikeDrugBean;
//            GlideLoaderUtils.display(this,mIvHeadPic, baikeDrugBean.getImage());
            mEtCompany.setText(baikeDrugBean.getFromSource());
            mEtCompany.setSelection(baikeDrugBean.getFromSource().length());
            mEtDiseaseType.setText(baikeDrugBean.getType());
            mEtDiseaseType.setSelection(baikeDrugBean.getType().length());
            mEtDiseaseName.setText(baikeDrugBean.getName());
            mEtDiseaseName.setSelection(baikeDrugBean.getName().length());
//            mEtTeiLphone.setText(baikeDrugBean.getTelPhone());
//            mEtTeiLphone.setSelection(baikeDrugBean.getTelPhone().length());
            mEtDef.setText(baikeDrugBean.getContent());
            mEtDef.setSelection(baikeDrugBean.getContent().length());
//            mEtPrice.setText(baikeDrugBean.getPrice());
//            mEtPrice.setSelection(baikeDrugBean.getPrice().length());
//            mEtAddress.setText(baikeDrugBean.getContact());
//            mEtAddress.setSelection(baikeDrugBean.getContact().length());
        }
    }

    @Override
    public void queryBaikeShowFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void updateDrugSuccess(BaikeDrugBean baikeDrugBean) {
        EBKDrugUpdateBean ebkDrugUpdateBean = new EBKDrugUpdateBean();
        ebkDrugUpdateBean.setIndex(mItemIndex);
        ebkDrugUpdateBean.setBaikeDrugBean(baikeDrugBean);
        mRxManager.post(AppConstant.RX_UPDATE_DRUG_BAIKE,ebkDrugUpdateBean);
        finish();
    }

    @Override
    public void updateDrugFailure(String error) {
        UIUtils.showToast(error);
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
